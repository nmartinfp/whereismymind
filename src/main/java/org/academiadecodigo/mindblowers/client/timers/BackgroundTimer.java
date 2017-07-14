package org.academiadecodigo.mindblowers.client.timers;

import javafx.scene.control.ScrollPane;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dgcst on 14/07/17.
 */

public class BackgroundTimer {

    private Timer timer;
    private ScrollPane scrollPane;
    private final int BACKGROUND_MOVE_SECONDS = 1;

    public BackgroundTimer(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        timer = new Timer();
        timer.schedule(new MoveBackground(), 0, BACKGROUND_MOVE_SECONDS * 250);
    }

    private class MoveBackground extends TimerTask {
        @Override
        public void run() {
            scrollPane.setHvalue(scrollPane.getHvalue() + 25);
            //timer.cancel();
        }
    }

    public void cancelTimer() {
        timer.cancel();
    }
}
