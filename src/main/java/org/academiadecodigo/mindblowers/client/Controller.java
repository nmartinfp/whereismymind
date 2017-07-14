package org.academiadecodigo.mindblowers.client;

import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
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
    private Map<String, Button> playerButtons;
    private Map<String, Button> teammateButtons;
    private ButtonTimer buttonTimer;
    private BackgroundTimer backgroundTimer;
    private AudioClip introAudio;
    private AudioClip gameAudio;
    private AudioClip bubblePop;

    private int points;

    private int[] count = new int[2];

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
    @FXML
    private Pane gameEnd;
    @FXML
    private Label playerScore;
    @FXML
    private TextField nicknameField;
    @FXML
    private Button okButton;
    @FXML
    private Label score;

    public int getPoints() {
        return points;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        introAudio = new AudioClip(getClass().getResource("/sfx/intro_song.mp3").toExternalForm());
        introAudio.setCycleCount(AudioClip.INDEFINITE);
        introAudio.play();

        gameAudio = new AudioClip(getClass().getResource("/sfx/game_song.mp3").toExternalForm());

        bubblePop = new AudioClip(getClass().getResource("/sfx/pop.mp3").toExternalForm());

        count[0] = 1;
        count[1] = 1;
        service = new Service();
        scrollPane.setHmax(Constants.BACKGROUND_IMAGE_WIDTH - Constants.VIEWPANE_WIDTH);

        playerButtons = new HashMap<>();
        teammateButtons = new HashMap<>();


        //new SplashTimer(splashPane);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        backgroundTimer = new BackgroundTimer(scrollPane, this);
    }


    @FXML
    private void onMouseClick(MouseEvent event) {
        bubblePop.play();

        Button clicked = ((Button) event.getSource());

        updatePoints(clicked.getId());

        System.out.println(findNameByButton(clicked) + " points: " + points);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                score.setTextFill(Paint.valueOf("White"));
                score.setVisible(true);
                score.setText(((Integer) points).toString());

                if (clicked.getId().equals(btn1.getId())) {
                    //own
                } else {
                    //other
                }
                clicked.setVisible(false);
                String clickedName = findNameByButton(clicked);
                service.write(Messages.REMOVE_BUBBLE + " " + clickedName);
                checkButtons();
            }
        });
    }

    @FXML
    private void onOkButtonClick(MouseEvent event) {
        String nickname = nicknameField.getText();
        service.write(Messages.SCORE + " " + nickname + " " + points);
    }

    private void updatePoints(String id) {
        if(id.equals("ego")){
            points++;
            return;
        }
        if(points == 0){
            return;
        }
        points--;
    }

    private String findNameByButton(Button clicked) {

        String clickedName;

        for (Map.Entry<String, Button> entry : playerButtons.entrySet()) {
            if (entry.getValue().equals(clicked)) {
                clickedName = entry.getKey();
                return clickedName;
            }
        }
        for (Map.Entry<String, Button> entry : teammateButtons.entrySet()) {
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

    public void hideBtn(String s) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                teammateButtons.get(s).setVisible(false);
            }
        });

    }

    public void setupButtons(boolean isEgo) {

        String id = "ego";

        btn1.setId(id);
        playerButtons.put("btn1", btn1);
        btn2.setId(id);
        playerButtons.put("btn2", btn2);
        btn3.setId(id);
        playerButtons.put("btn3", btn3);
        btn4.setId(id);
        playerButtons.put("btn4", btn4);
        btn5.setId(id);
        playerButtons.put("btn5", btn5);

        id = "alterego";

        btn1Alt.setId(id);
        teammateButtons.put("btn1Alt", btn1Alt);
        btn2Alt.setId(id);
        teammateButtons.put("btn2Alt", btn2Alt);
        btn3Alt.setId(id);
        teammateButtons.put("btn3Alt", btn3Alt);
        btn4Alt.setId(id);
        teammateButtons.put("btn4Alt", btn4Alt);
        btn5Alt.setId(id);
        teammateButtons.put("btn5Alt", btn5Alt);
    }

    public void setupBubble(String id, int x, int y) {
        final Button button;
        if (id.equals(Messages.EGO)) {
            button = playerButtons.get("btn" + count[0]);
            count[0]++;
            if (count[0] == Constants.MAX_BUBBLES + 1) {
                count[0] = 1;
            }
        } else {
            button = teammateButtons.get("btn" + count[1] + "Alt");
            count[1]++;
            if (count[1] == Constants.MAX_BUBBLES + 1) {
                count[1] = 1;
            }

        }
        button.setVisible(true);

        button.setLayoutX(x);
        button.setLayoutY(y);

    }

    public void checkButtons() {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                for (Map.Entry<String, Button> entry : playerButtons.entrySet()) {

                    if (!entry.getValue().isVisible()) {
                        count++;
                        if (count == Constants.MAX_BUBBLES) {
                            service.write(Messages.BUBBLE_REQUEST);
                        }
                    }
                }
            }
        });


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
        service.write(Messages.START_PRESSED);
        btnStart.setDisable(true);
    }


    public void startGame() {
        instructionsPane.setVisible(false);
        instructionsPane.setDisable(true);
        gamePane.setVisible(true);
        scrollPane.setVisible(true);
        if (introAudio.isPlaying()) {
            introAudio.stop();
        }
        gameAudio.play(0.7);
    }

    public void endGame() {
        gamePane.setVisible(false);
        gameEnd.setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerScore.setText(score.getText());

            }
        });
    }
}
