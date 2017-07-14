package org.academiadecodigo.mindblowers.client;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.academiadecodigo.mindblowers.client.timers.BackgroundTimer;
import org.academiadecodigo.mindblowers.client.timers.ButtonTimer;
import org.academiadecodigo.mindblowers.constants.Constants;

import java.net.URL;
import java.util.*;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Controller implements Initializable {

    private Session session;
    private SequentialTransition fade;
    private Service service;
    private Stage stage;
    private List<Button> buttonList;
    private final int MAX_BUTTONS = 2; //TODO decide max buttons final value
    private int counter;
    private Button currentButton;
    private ButtonTimer buttonTimer;
    private BackgroundTimer backgroundTimer;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = new Service();
        buttonList = new ArrayList<>();
        counter = 0;
        scrollPane.setHmax(8000 - 860);

        fillButtonList();
        currentButton = buttonList.get(0);

        buttonLoader();

        backgroundTimer = new BackgroundTimer(scrollPane);
    }

    private void buttonLoader() {
        currentButton.setLayoutX(Math.random() * Constants.MAX_BUTTON_X);
        currentButton.setLayoutY(Math.random() * Constants.MAX_BUTTON_Y);
        fading(currentButton);
        fade.jumpTo("start");
        buttonTimer = new ButtonTimer(currentButton, this);
    }

    private void fillButtonList() {
        btn1.setId("ego");
        btn2.setId("ego");
        btn1.setText("1");
        btn2.setText("2");
        buttonList.add(btn1);
        buttonList.add(btn2);
    }

    @FXML
    void onMouseClick(MouseEvent event) {
        buttonTimer.cancelTimer();
        currentButton.setVisible(false);
        getNextButton();
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
                service.connect(Controller.this);
            }
        });
    }

    private FadeTransition createFadeOut(Node node) {
        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);
        fade.setFromValue(1);
        fade.setToValue(0);

        return fade;
    }

    public void getNextButton() {
        counter++;
        if (counter == MAX_BUTTONS) {
            counter = 0;
        }
        currentButton = buttonList.get(counter);
        currentButton.setVisible(true);
        buttonLoader();
    }
}
