package pl.edu.wsisiz.darkavenger54;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable
{
    private String name;
    private EnFigureType figureType;
    private Socket playerSocket;
    private boolean isConnected = false;
    private GameServer gameServer;
    private BufferedReader in;
    private PrintWriter out;

    public Player(Socket socket, GameServer gameServer)
    {
        this.playerSocket = socket;
        this.gameServer = gameServer;
        this.isConnected = true;
        try
        {
            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            out = new PrintWriter(playerSocket.getOutputStream(), true);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
    @Override
    public void run()
    {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] parts = inputLine.split(" ", 2);
                if (parts.length == 0) continue;
                String eventTypeStr = parts[0];
                String eventData = parts.length > 1 ? parts[1] : "";
                EnEventType eventType;
                try {
                    eventType = EnEventType.valueOf(eventTypeStr);
                } catch (IllegalArgumentException ex) {
                    continue;
                }

                switch (eventType) {
                    case MESSAGE:
                        gameServer.printMessage(name + ": " + eventData);
                        gameServer.sendEventToBothPlayers(eventType,  name + ": " + eventData);
                        break;
                    case DISCONNECT:
                        gameServer.removePlayer(this);
                        gameServer.sendEventToBothPlayers(eventType, name);
                        gameServer.printMessage(name + " disconnected");
                        break;
                    case ASK_FOR_GAME:
                        gameServer.sendEventToOtherPlayer(eventType, "", this);
                        break;
                    case CONFIRMED:
                        gameServer.startNewGame();
                        break;
                    case CANCELED:
                        gameServer.sendEventToOtherPlayer(eventType, "", this);
                        break;
                    case MOVE:
                        try {
                            int index = Integer.parseInt(eventData.trim());
                            if (gameServer.makeMove(this, index)) {

                            } else {
                                sendEvent(EnEventType.MESSAGE, "Invalid move or not your turn");
                            }
                        } catch (NumberFormatException ex) {
                            sendEvent(EnEventType.MESSAGE, "Invalid move format");
                        }
                        break;
                    default:
                        System.out.println("eventerror");
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Connection error: " + ex.getMessage());
        } finally {
            isConnected = false;
            try {
                playerSocket.close();
            } catch (IOException ex) {

            }
        }
    }
    public void setName(String name) {
        this.name = name;
        sendEvent(EnEventType.SET_NAME, name);
    }

    public String getName()
    {
        return name;
    }

    public void setFigureType(EnFigureType figureType) {
        this.figureType = figureType;
    }

    public void sendEvent(EnEventType eventType, String data) {
        out.println(eventType.getEventType() + data);
    }

    public EnFigureType getFigureType()
    {
        return figureType;
    }
}