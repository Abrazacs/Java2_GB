package Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private static final int PORT = 8089;
    private ArrayList<Handler> clientsList;

    public Server (){
        this.clientsList=new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start!");
            System.out.println("Waiting for connection......");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Handler handler = new Handler(socket, this);
                clientsList.add(handler);
                handler.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendToAll(String message) {
        for (Handler handler : clientsList) {
            handler.sendMessage(message);
        }
    }
}