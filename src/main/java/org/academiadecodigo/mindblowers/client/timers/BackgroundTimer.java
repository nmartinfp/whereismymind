package org.academiadecodigo.mindblowers.client.timers;

import javafx.scene.control.ScrollPane;
import org.academiadecodigo.mindblowers.client.Controller;
import org.academiadecodigo.mindblowers.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dgcst on 14/07/17.
 */

public class BackgroundTimer {

    private ScrollPane scrollPane;
    private Controller controller;

    public BackgroundTimer(ScrollPane scrollPane, Controller controller) {
        this.scrollPane = scrollPane;
        this.controller = controller;
        Timer timer = new Timer();
        timer.schedule(new MoveBackground(), 0, Constants.BACKGROUND_MOVE_MILLISECONDS);
    }

    private class MoveBackground extends TimerTask {
        @Override
        public void run() {
            scrollPane.setHvalue(scrollPane.getHvalue() + Constants.BACKGROUND_MOVE_PIXELS);
            if (scrollPane.getHvalue() == scrollPane.getHmax()) {
                controller.endGame();
            }
        }
    }
}
