package org.academiadecodigo.mindblowers.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Session {

    private static Session instance;

    private Socket socket;
    private BufferedInputStream input;
    private BufferedOutputStream output;

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

    public BufferedInputStream getInput() {
        return input;
    }

    public BufferedOutputStream getOutput() {
        return output;
    }

    /**
     * Instantiates input and output stream for the specified socket, only once.
     *
     * @param socket
     */
    public void setSocket(Socket socket) {
        if (socket == null) {
            this.socket = socket;
            try {
                input = new BufferedInputStream(socket.getInputStream());
                output = new BufferedOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                //TODO notify?
                e.printStackTrace();
            }
        }
    }
}
