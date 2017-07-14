package org.academiadecodigo.mindblowers.client.timers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import org.academiadecodigo.mindblowers.client.Controller;
import org.academiadecodigo.mindblowers.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dgcst on 14/07/17.
 */

public class ButtonTimer {

    private Timer timer;
    private Button button;
    private Controller controller;

    public ButtonTimer(Button button, Controller controller) {
        this.button = button;
        this.controller = controller;
        timer = new Timer();
        timer.schedule(new HideButton(), Constants.BUTTON_SHOW_SECONDS * 1000);
    }

    private class HideButton extends TimerTask {
        @Override
        public void run() {
            button.setVisible(false);
            timer.cancel();
        }
    }

    public void cancelTimer() {
        timer.cancel();
    }
}
