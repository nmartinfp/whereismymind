package org.academiadecodigo.mindblowers.client;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.academiadecodigo.mindblowers.client.timers.BackgroundTimer;
import org.academiadecodigo.mindblowers.client.timers.ButtonTimer;
import org.academiadecodigo.mindblowers.client.timers.SplashTimer;
import org.academiadecodigo.mindblowers.constants.Constants;
import org.academiadecodigo.mindblowers.constants.Messages;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class Controller implements Initializable {

    private SequentialTransition fade;
    private Service service;
    private Stage stage;
    private Map<String, Button> buttonsEgo;
    private Map<String, Button> buttonsAlt;
    private int counter;
    private Button currentButton;
    private ButtonTimer buttonTimer;
    private BackgroundTimer backgroundTimer;
    private boolean readyToPlay;

    @FXML
    private Pane gamePane;
    @FXML
    private Button btnStart;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn1Alt;
    @FXML
    private Button btn2Alt;
    @FXML
    private Button btn3Alt;
    @FXML
    private Button btn4Alt;
    @FXML
    private Button btn5Alt;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pane splashPane;
    @FXML
    private Pane introPane;
    @FXML
    private Pane instructionsPane;



    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        service = new Service();
        buttonsEgo = new HashMap<>();
        buttonsAlt = new HashMap<>();
        counter = 0;
        scrollPane.setHmax(Constants.BACKGROUND_IMAGE_WIDTH - Constants.VIEWPANE_WIDTH);

        //new SplashTimer(splashPane);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        currentButton = btn1;

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

    @FXML
    void onMouseClick(MouseEvent event) {
        buttonTimer.cancelTimer();
        Button clicked = ((Button) event.getSource());

        if (clicked.getId().equals(btn1.getId())) {
            System.out.println("clicked own");
            return;
        }

        System.out.println("clicked other");

        clicked.setVisible(false);
        currentButton = clicked;
        getNextButton();
        currentButton.setLayoutX(Math.random() * Constants.MAX_BUTTON_X);
        currentButton.setLayoutY(Math.random() * Constants.MAX_BUTTON_Y);
        fade.jumpTo("start");


        String clickedName = findNameByButton(clicked);

        service.write(Messages.REMOVE_BUBBLE + clickedName);
    }

    private String findNameByButton(Button clicked) {

        String clickedName;

        for (Map.Entry<String, Button> entry : buttonsEgo.entrySet()) {
            if (entry.getValue().equals(clicked)) {
                clickedName = entry.getKey();
                return clickedName;
            }
        }
        for (Map.Entry<String, Button> entry : buttonsAlt.entrySet()) {
            if (entry.getValue().equals(clicked)) {
                clickedName = entry.getKey();
                return clickedName;
            }
        }
        return null;
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

        Iterator it = buttonsEgo.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Button> pair = (Map.Entry<String, Button>) it.next();
            if (pair.getValue() == currentButton && it.hasNext()) {
                currentButton = ((Map.Entry<String, Button>) it.next()).getValue();
                System.out.println("new button " + currentButton);
                break;
            }
            if (pair.getValue() == currentButton && !it.hasNext()) {
                System.out.println("new button " + pair.getKey());
                currentButton = buttonsEgo.get("btn1");
                break;
            }
        }
        currentButton.setVisible(true);
        buttonLoader();
    }

    public void hideBtn(String s) {


        if (s.equals("btn1Alt")) {
            buttonsAlt.get(s).setVisible(false);
        }
    }

    public void setupButtons(boolean isEgo) {

        String id = isEgo ? "ego" : "alterego";

        btn1.setId(id);
        buttonsEgo.put("btn1", btn1);
        btn2.setId(id);
        buttonsEgo.put("btn2", btn2);
        btn3.setId(id);
        buttonsEgo.put("btn3", btn3);
        btn4.setId(id);
        buttonsEgo.put("btn4", btn4);
        btn5.setId(id);
        buttonsEgo.put("btn5", btn5);

        id = isEgo ? "alterego" : "ego";

        btn1Alt.setId(id);
        buttonsAlt.put("btn1Alt", btn1Alt);
        btn2Alt.setId(id);
        buttonsAlt.put("btn2Alt", btn2Alt);
        btn3Alt.setId(id);
        buttonsAlt.put("btn3Alt", btn3Alt);
        btn4Alt.setId(id);
        buttonsAlt.put("btn4Alt", btn4Alt);
        btn5Alt.setId(id);
        buttonsAlt.put("btn5Alt", btn5Alt);

        buttonLoader();
    }

    public void splashToIntro() {
        splashPane.setVisible(false);
        introPane.setVisible(true);
    }

    public void introToInstructions() throws InterruptedException {
        Thread.sleep(2000);
        introPane.setVisible(false);
        instructionsPane.setVisible(true);

    }

    public void onMouseClickStart(MouseEvent mouseEvent) {
        readyToPlay = true;
        service.write(Messages.START_PRESSED);
        btnStart.setDisable(true);
    }


    public void startGame() {
        instructionsPane.setVisible(false);
        instructionsPane.setDisable(true);
        gamePane.setVisible(true);
        scrollPane.setVisible(true);

    }
}
