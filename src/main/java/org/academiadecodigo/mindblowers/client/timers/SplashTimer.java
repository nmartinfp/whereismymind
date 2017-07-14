package org.academiadecodigo.mindblowers.client.timers;

import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by codecadet on 14/07/2017.
 */
public class SplashTimer {

    private Pane pane;

    public SplashTimer(Pane pane) {
        this.pane = pane;
        Timer timer = new Timer();
        timer.schedule(new HoldSplash(), 0, 50000);
    }

    private class HoldSplash extends TimerTask {
        @Override
        public void run() {}
    }
}
