package org.academiadecodigo.mindblowers.client;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.academiadecodigo.mindblowers.constants.Messages;

import java.io.IOException;

/**
 * Created by codecadet on 13/07/17.
 */
public class ServerHandler implements Runnable{

    private Controller controller;

    public ServerHandler(Controller controller) {
        this.controller = controller;

    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = Session.getInstance().getInput().readLine()) != null) {
                parseString(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseString(String message) {
        String[] splittedString = message.split(" ");
        String serverMessage = splittedString[0];

        if (serverMessage.equals(Messages.GAME_START)) {
            try {
                controller.splashToIntro();
                controller.introToInstructions();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }if (serverMessage.equals(Messages.EGO)) {
            controller.setupButtons(true);
            return;
        }
        if (serverMessage.equals(Messages.ALTEREGO)) {
            controller.setupButtons(false);
            return;
        }

        if (serverMessage.equals(Messages.REMOVE_BUBBLE)) {

            if (serverMessage.endsWith("Alt")) {
                controller.hideBtn(splittedString[1].substring(0, splittedString[1].lastIndexOf('A')));
                return;
            }

            controller.hideBtn(splittedString[1].concat("Alt"));
            return;
        }

        if (serverMessage.equals(Messages.PLAYERS_READY)) {
            controller.startGame();
            return;
        }

        if (serverMessage.equals(Messages.NEW_BUBBLE)) {
            controller.setupBubble(splittedString[1], Integer.parseInt(splittedString[2]), Integer.parseInt(splittedString[3]));
        }

    }
}

