package org.academiadecodigo.mindblowers.server;

import org.academiadecodigo.mindblowers.constants.Constants;
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
        new Thread(new PlayerHandler(this, bufferedReaders[0], Messages.EGO)).start();
        new Thread(new PlayerHandler(this, bufferedReaders[1], Messages.ALTEREGO)).start();

        write(Messages.EGO, Messages.EGO);
        write(Messages.ALTEREGO, Messages.ALTEREGO);

        generateBubbles();
    }

    public void generateBubbles() {

        for (int i = 0; i < Constants.MAX_BUBBLES; i++) {

            int x = (int) (Math.random() * Constants.MAX_BUTTON_X);
            int y = (int) (Math.random() * Constants.MAX_BUTTON_Y);

            String message = Messages.NEW_BUBBLE + " " + Messages.EGO + " " + x + " " + y;

            synchronized (PrintWriter.class) {
                write(Messages.EGO, message);
                write(Messages.ALTEREGO, message);
            }
        }

        for (int i = 0; i < Constants.MAX_BUBBLES; i++) {

            int x = (int) (Math.random() * Constants.MAX_BUTTON_X);
            int y = (int) (Math.random() * Constants.MAX_BUTTON_Y);

            String message = Messages.NEW_BUBBLE + " " + Messages.ALTEREGO + " " + x + " " + y;

            synchronized (PrintWriter.class) {
                write(Messages.EGO, message);
                write(Messages.ALTEREGO, message);
            }
        }
    }

    public void write(String playerName, String message) {

        if (playerName.equals("ego")) {
            printerWriters[1].println(message);
            return;
        }

        printerWriters[0].println(message);

    }
}
