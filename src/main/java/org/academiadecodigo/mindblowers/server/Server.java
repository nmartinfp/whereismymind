package org.academiadecodigo.mindblowers.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Server {

    public static void main(String[] args) {

        Server server;
        try {
            server = new Server(8080); //TODO variable
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerSocket serverSocket;

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }


    private void start() throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        Socket[] clients = new Socket[2];

        while (clients[0] == null || clients[1] == null) {

            clients[0] = serverSocket.accept();
            clients[1] = serverSocket.accept();

            threadPool.submit(new Game(clients));

            Arrays.fill(clients, null);
        }
    }
}
