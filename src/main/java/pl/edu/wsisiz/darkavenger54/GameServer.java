package pl.edu.wsisiz.darkavenger54;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GameServer
{
    private final int PORT;
    private ServerSocket serverSocket;
    private JTextArea serverTextArea;
    private Player player1;
    private Player player2;
    private boolean isOnline;
    private GameBoard gameBoard;
    private EnFigureType currentTurn;

    public GameServer(int port, GameServerUI gameServerUI)
    {
        this.PORT = port;
        this.serverTextArea = gameServerUI.getServerTextArea();
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void start()
    {
        printMessage("Started Server on port: " + PORT);
        isOnline = true;
        new Thread(this::listenForPlayers).start();
    }
    public void stop()
    {
        printMessage("Server stopped");
        try
        {
            sendEventToBothPlayers(EnEventType.SERVER_STOPPED, "Server stopped");
            serverSocket.close();
            isOnline = false;
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    private void listenForPlayers()
    {
        while (isOnline) {
            try {
                Socket socket = serverSocket.accept();
                printMessage("New connection from " + socket.getInetAddress());

                Player player = new Player(socket, this);
                if (player1 == null || !player1.isConnected()) {
                    player1 = player;
                    player1.setName("Player 1");
                    new Thread(player1).start();
                    printMessage("Player 1 connected");
                    if(player2 != null && player2.isConnected())
                    {
                        sendEventToOtherPlayer(EnEventType.CONNECT, "Player 1 connected", player1);
                    }

                } else if (player2 == null || !player2.isConnected()) {
                    player2 = player;
                    player2.setName("Player 2");
                    new Thread(player2).start();
                    printMessage("Player 2 connected");
                    if(player1 != null && player1.isConnected())
                    {
                        sendEventToOtherPlayer(EnEventType.CONNECT, "Player 2 connected", player2);
                    }
                } else {
                    printMessage("Server full. Rejecting connection from " + socket.getInetAddress());
                    PrintWriter tempOut = new PrintWriter(socket.getOutputStream(), true);
                    tempOut.println(EnEventType.SERVER_FULL.getEventType() + "Server is full");
                    tempOut.close();
                    socket.close();
                }
                if (player1 != null && player2 != null &&
                        player1.isConnected() && player2.isConnected()) {
                    sendEventToBothPlayers(EnEventType.CONNECT, "");
                }
            } catch (IOException e) {
                printMessage("Connection error: " + e.getMessage());
            }
        }
    }
    public synchronized void removePlayer(Player player) {
        if (player == player1) {
            player1 = null;
        } else if (player == player2) {
            player2 = null;
        }
    }
    public void printMessage(String message)
    {
        serverTextArea.append(message + "\n");
    }
    public void sendEventToBothPlayers(EnEventType eventType, String data) {
        if (player1 != null && player1.isConnected()) {
            player1.sendEvent(eventType, data);
        }
        if (player2 != null && player2.isConnected()) {
            player2.sendEvent(eventType, data);
        }
    }

    public void sendEventToOtherPlayer(EnEventType eventType, String data, Player player)
    {
        if (player == player1)
        {
            player2.sendEvent(eventType, data);
        } else if (player == player2)
        {
            player1.sendEvent(eventType, data);
        }
    }

    public void startNewGame()
    {
        if (player1 == null || player2 == null || !player1.isConnected() || !player2.isConnected()) {
            printMessage("Cannot start game: one or both players are not connected.");
            return;
        }

        gameBoard = new GameBoard();

        Random rand = new Random();
        boolean player1Starts = rand.nextBoolean();

        Player crossPlayer = player1Starts ? player1 : player2;
        Player circlePlayer = player1Starts ? player2 : player1;

        crossPlayer.setFigureType(EnFigureType.CROSS);
        circlePlayer.setFigureType(EnFigureType.CIRCLE);

        currentTurn = EnFigureType.CROSS;

        crossPlayer.sendEvent(EnEventType.START_GAME, EnFigureType.CROSS.toString());
        circlePlayer.sendEvent(EnEventType.START_GAME, EnFigureType.CIRCLE.toString());

        crossPlayer.sendEvent(EnEventType.YOUR_TURN, "");
        circlePlayer.sendEvent(EnEventType.WAITING_FOR_OPPONENT, "");

        printMessage("Game started: " + crossPlayer.getName() + " is X, " + circlePlayer.getName() + " is O");
    }
    public boolean makeMove(Player player, int index)
    {
        if (gameBoard == null) return false;
        if (!player.isConnected()) return false;
        if (player.getFigureType() != currentTurn) return false;

        boolean success = gameBoard.setMove(index, player.getFigureType());
        if (!success) return false;

        sendEventToBothPlayers(EnEventType.MOVE, index + " " + player.getFigureType());

        EnFigureType winner = gameBoard.checkWinner();
        if (winner != EnFigureType.NONE) {
            if (player1.getFigureType() == winner) {
                player1.sendEvent(EnEventType.WIN, "");
                player2.sendEvent(EnEventType.LOSE, "");
            } else {
                player2.sendEvent(EnEventType.WIN, "");
                player1.sendEvent(EnEventType.LOSE, "");
            }
            return true;
        }

        if (gameBoard.isFull()) {
            player1.sendEvent(EnEventType.DRAW, "");
            player2.sendEvent(EnEventType.DRAW, "");
            return true;
        }

        currentTurn = (currentTurn == EnFigureType.CROSS) ? EnFigureType.CIRCLE : EnFigureType.CROSS;

        if (player == player1) {
            player1.sendEvent(EnEventType.WAITING_FOR_OPPONENT, "");
            player2.sendEvent(EnEventType.YOUR_TURN, "");
        } else {
            player2.sendEvent(EnEventType.WAITING_FOR_OPPONENT, "");
            player1.sendEvent(EnEventType.YOUR_TURN, "");
        }

        return true;
    }

}
