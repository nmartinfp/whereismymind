package org.academiadecodigo.mindblowers.server;

import java.io.*;
import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Game implements Runnable {

    private Socket[] players;
    private BufferedReader[] bufferedReaders;
    private PrintWriter[] printerWriters;


    public Game(Socket[] players) {
        this.players = players;
        bufferedReaders = new BufferedReader[2];
        printerWriters = new PrintWriter[2];

        try {
            populateArrays();
        } catch (IOException e) {
            //TODO create exception
            e.printStackTrace();
        }

    }

    private void populateArrays() throws IOException {
        bufferedReaders[0] = new BufferedReader(new InputStreamReader(players[0].getInputStream()));
        bufferedReaders[1] = new BufferedReader(new InputStreamReader(players[1].getInputStream()));

        printerWriters[0] = new PrintWriter(players[0].getOutputStream(), true);
        printerWriters[1] = new PrintWriter(players[1].getOutputStream(), true);

    }

    @Override
    public void run() {

    }


}
