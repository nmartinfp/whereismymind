package org.academiadecodigo.mindblowers.client;

import java.io.*;
import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Session {

    private static Session instance;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private Session() {

    }

    public static Session getInstance() {

        if (instance == null) {
            synchronized (Session.class) {
                if (instance == null) {
                    instance = new Session();
                }
            }
        }
        return instance;
    }


    public BufferedReader getInput() {
        return input;
    }

    public PrintWriter getOutput() {
        return output;
    }

    /**
     * Instantiates input and output stream for the specified socket.
     *
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            //TODO notify?
            e.printStackTrace();
        }
    }


    public void closeSocket() {

        try {
            socket.close();
        } catch (IOException e) {
            // TODO: 14/07/2017 - exception
            e.printStackTrace();
        }

    }

}
