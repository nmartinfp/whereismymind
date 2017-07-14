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

    public void connect(Controller controller) {
        try {
            Socket socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            Session.getInstance().setSocket(socket);

            new Thread(new ServerHandler(controller)).start();

        } catch (IOException e) {
            //TODO notify user
            e.printStackTrace();
        }
    }


    public void write(String message){
        Session.getInstance().getOutput().println(message);
    }

}
