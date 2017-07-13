package org.academiadecodigo.mindblowers.client;

import org.academiadecodigo.mindblowers.constants.Constants;

import java.io.IOException;
import java.net.Socket;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Service {

    public void connect() {
        try {
            Socket socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            Session.getInstance().setSocket(socket);
        } catch (IOException e) {
            //TODO notify user
            e.printStackTrace();
        }
    }
}
