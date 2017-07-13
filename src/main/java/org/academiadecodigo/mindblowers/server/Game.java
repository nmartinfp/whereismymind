package org.academiadecodigo.mindblowers.server;

import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Game implements Runnable {

    private Socket[] players;

    public Game(Socket[] players) {
        this.players = players;
    }

    @Override
    public void run() {

    }
}
