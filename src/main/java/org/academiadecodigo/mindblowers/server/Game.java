package org.academiadecodigo.mindblowers.server;

import org.academiadecodigo.mindblowers.constants.Messages;

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
    private boolean isGameOver;


    public Game(Socket[] players) {
        this.players = players;
        bufferedReaders = new BufferedReader[2];
        printerWriters = new PrintWriter[2];
        isGameOver = false;

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
        new Thread(new PlayerHandler(this, bufferedReaders[0], Messages.EGO)).start();
        new Thread(new PlayerHandler(this, bufferedReaders[1], Messages.ALTEREGO)).start();

        write(Messages.EGO, Messages.EGO);
        write(Messages.ALTEREGO, Messages.ALTEREGO);

    }

    public void endGame(){
        isGameOver = true;
    }


    public void write(String playerName, String message){

        if (playerName.equals("ego")) {
            printerWriters[1].println(message);
            return;
        }

        printerWriters[0].println(message);

    }
}
