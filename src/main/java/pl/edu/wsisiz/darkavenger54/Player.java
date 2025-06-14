package pl.edu.wsisiz.darkavenger54;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Player implements Runnable
{
    private String name;
    private EnFigureType figureType;
    private ServerSocket serverSocket;
    private Socket playerSocket;
    private boolean isConnected = false;

    public Player(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void acceptConnection() throws IOException
    {
        playerSocket = serverSocket.accept();
        isConnected = true;
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    @Override
    public void run()
    {

    }
}
