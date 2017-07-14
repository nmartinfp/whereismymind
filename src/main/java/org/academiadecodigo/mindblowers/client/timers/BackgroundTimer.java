package org.academiadecodigo.mindblowers.client.timers;

import javafx.scene.control.ScrollPane;
import org.academiadecodigo.mindblowers.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dgcst on 14/07/17.
 */

public class BackgroundTimer {

    private ScrollPane scrollPane;

    public BackgroundTimer(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        Timer timer = new Timer();
        timer.schedule(new MoveBackground(), 0, Constants.BACKGROUND_MOVE_MILLISECONDS);
    }

    private class MoveBackground extends TimerTask {
        @Override
        public void run() {
            scrollPane.setHvalue(scrollPane.getHvalue() + Constants.BACKGROUND_MOVE_PIXELS);
        }
    }
}
