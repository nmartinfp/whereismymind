package org.academiadecodigo.mindblowers.client;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Controller implements Initializable {

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;

        addListener();
    }

    private void addListener() {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                try {
                    Socket socket = new Socket("localhost", 8080); //TODO variable
                    //TODO set socket on session

                    System.out.println("connected"); //TODO remove sout

                } catch (IOException e) {
                    //TODO notify user
                    e.printStackTrace();
                }
            }

        });
    }
}
