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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private List<Button> buttonsEgo;
    private List<Button> buttonsAlt;
    private final int MAX_BUTTONS = 10; //TODO decide max buttons final value
    private int counter;
    private Button currentButton;

    @FXML
    private Button btn1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = new Service();
        buttonsEgo = new ArrayList<>();
        buttonsAlt = new ArrayList<>();
        counter = 0;

        currentButton = btn1;
        buttonsEgo.add(currentButton);
        System.out.println( "added 1st " + buttonsEgo.size());
        buttonLoader();

        // Button fading
        //fading(currentButton);
    }

    private void buttonLoader() {
        currentButton.setLayoutX(Math.random() * Constants.MAX_BUTTON_X);
        currentButton.setLayoutY(Math.random() * Constants.MAX_BUTTON_Y);
        fading(currentButton);
        fade.jumpTo("start");

        if (currentButton.getOpacity() == 0.0) {
            System.out.println("opacity is: " + currentButton.getOpacity());

            currentButton.setDisable(true);
            getNextButton();
            buttonLoader();
        }
    }

    @FXML
    void onMouseClick(MouseEvent event) {

        Button clicked = ((Button) event.getSource());
        System.out.println("clicked on " + clicked);
        System.out.println(buttonsEgo.get(0));

        if (!clicked.getId().equals(buttonsEgo.get(0))) {
            //TODO clicked on other player's button
            return;
        }

        clicked.setVisible(false);
        currentButton = clicked;
        getNextButton();
        currentButton.setLayoutX(Math.random() * Constants.MAX_BUTTON_X);
        currentButton.setLayoutY(Math.random() * Constants.MAX_BUTTON_Y);
        // fade.jumpTo("start");

        service.write("clicked " + Buttons.findIdByButton(clicked)); // TODO: 14/07/17 Change message to list position
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
        if (counter == MAX_BUTTONS / 2) {
            counter = 0;
        }
        currentButton = buttonsEgo.get(counter);
        currentButton.setVisible(true);
        buttonLoader();
    }

    public void hideBtn(String s) {
        if (s.equals("btn1Alt")) {
            //btn1Alt.setDisable(true); Maybe this wont be needed
            Buttons.getBtnById(s).setVisible(false);
        }
    }

    public void setupButtons(boolean isEgo) {

        String id = isEgo ? "ego" : "alterego";
        currentButton.setId(id);

        List<Button> list = buttonsEgo;

        for(int i = 0; i < Buttons.values().length; i++) {
            if(i == MAX_BUTTONS/2 - 1){
                isEgo = !isEgo;
                list = buttonsAlt;
            }
            Button btn = Buttons.values()[i].getBtn();
            id = isEgo ? "ego" : "alterego";

            Buttons.setId(btn, id);
            //btn.setId(id);
            //buttonLoader(btn);

            System.out.println("ego " +buttonsEgo.size() );
            System.out.println("alt  " + buttonsAlt.size());

            list.add(btn);
         // System.out.println(btn.getId());
        }

    }
}
