package pl.edu.wsisiz.darkavenger54;

import javax.sound.sampled.Port;
import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer
{
    private final int PORT;
    private ServerSocket serverSocket;
    private JTextArea serverTextArea;
    private Player player1;
    private Player player2;
    Thread player1Thread;
    Thread player2Thread;

    public GameServer(int port, GameServerUI gameServerUI)
    {
        this.PORT = port;
        this.serverTextArea = gameServerUI.getServerTextArea();
        player1 = new Player(serverSocket);
        player2 = new Player(serverSocket);
        player1Thread = new Thread(player1);
        player2Thread = new Thread(player2);
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
        listenForPlayers();
    }
    public void stop()
    {
        printMessage("Server stopped");
        try
        {
            serverSocket.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    private void listenForPlayers()
    {
        if(!player1.isConnected())
        {
            try
            {
                player1.acceptConnection();
                printMessage("Player1 connected");
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(!player2.isConnected())
        {
            try
            {
                player2.acceptConnection();
                printMessage("Player2 connected");
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    private void printMessage(String message)
    {
        serverTextArea.append(message + "\n");
    }
}
