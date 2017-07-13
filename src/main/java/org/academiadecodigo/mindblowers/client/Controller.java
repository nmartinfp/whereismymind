package org.academiadecodigo.mindblowers.client;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.academiadecodigo.mindblowers.constants.Constants;

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
    private Session session;
    private SequentialTransition fade;

    private Service service;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = new Service();
        //TODO remove
        //  btn1.setLayoutX(Math.random() * 780);
        // btn1.setLayoutY(Math.random() * 555);
        btn1.setStyle("-fx-background-radius: 5em;");
        btn1.setId("ego");

        // Button fading
        fading(btn1);
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        btn1.setLayoutX(Math.random() * Constants.MAX_BUTTON_X);
        btn1.setLayoutY(Math.random() * Constants.MAX_BUTTON_Y);
        fade.jumpTo("start");
    }

    private void fading(Button btn) {
        FadeTransition fadeOut = createFadeOut(btn);

        Timeline blinker = createBlinker(btn);

        fade = new SequentialTransition(
                btn,
                blinker,
                fadeOut
        );
        fade.play();
    }

    private Timeline createBlinker(Node node) {
        Timeline blink = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                )
        );

        return blink;
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        addListener();
    }

    private void addListener() {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                service.connect();
            }

        });
    }

    private FadeTransition createFadeIn(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        return fade;
    }

    private FadeTransition createFadeOut(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }
}
