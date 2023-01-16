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

    /**
     * Do not use this constructor if you have any ability to provide a name for the thread.
     * The string parameter in the alternative constructor is use
     * @param eventStep
     */
    public ContinuousThreadManager(Runnable eventStep) {
        this.eventStep = eventStep;
        this.dispatchThread = new Thread
                ( this::eventLoop
                , "Default ContinuousThreadManager Thread");
        this.dispatchThread.setDaemon(true);
//        this.dispatchThread.start();
    }
    public ContinuousThreadManager(Runnable eventStep, String threadName) {
        this.eventStep = eventStep;
        this.dispatchThread = new Thread
                ( this::eventLoop
                , threadName);
        this.dispatchThread.setDaemon(true);
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










