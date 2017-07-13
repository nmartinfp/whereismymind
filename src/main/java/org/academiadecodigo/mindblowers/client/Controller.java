package org.academiadecodigo.mindblowers.client;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Controller implements Initializable {

    @FXML
    private Button btn1;



    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onMouseClick(MouseEvent event) {
        btn1.setText("Clicked");
        btn1.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

       // addListener();
    }

    private void addListener() {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ClientConnector clientConnector = new ClientConnector();
                clientConnector.connect();
            }
        });
    }
}
