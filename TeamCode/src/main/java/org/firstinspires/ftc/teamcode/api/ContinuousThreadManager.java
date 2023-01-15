package org.firstinspires.ftc.teamcode.api;

import android.content.Context;
import android.content.Intent;

import com.qualcomm.robotcore.robot.Robot;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Continually check for changes in, for example, a sensor's state or a controller's inputs.
 * This is a class used in <code>ContinuousEventListener</code>. Consider using that before
 * instantiating this class.
 */
public class ContinuousThreadManager {
    private Thread dispatchThread;
    private Runnable eventStep;

    public ContinuousThreadManager(Runnable eventStep) {
        this.eventStep = eventStep;
        this.dispatchThread = new Thread
                ( this::eventLoop
                , "Default ContinuousThreadManager Thread");
        this.dispatchThread.setDaemon(true);
//        this.dispatchThread.start();
    }

    public void startDispatch() {
        dispatchThread.start();
    }

    private void eventLoop() {
        while (!dispatchThread.isInterrupted()) {
            eventStep.run();
        }
    }


}










