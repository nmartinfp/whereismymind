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

    private Socket socket;
    private BufferedInputStream input;
    private BufferedOutputStream output;

    public Session(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            //TODO notify?
            e.printStackTrace();
        }
    }

    public BufferedInputStream getInput() {
        return input;
    }

    public BufferedOutputStream getOutput() {
        return output;
    }
}
