package org.firstinspires.ftc.teamcode.api.event.listener.continuous;

import android.content.Context;
import android.content.Intent;

import com.qualcomm.robotcore.robot.Robot;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Continually check for changes in, for example, a sensor's state or a controller's inputs.
 * This is a class used in <code>ContinuousEventListener</code>. Consider using that before
 * instantiating this class.
 */
public class ContinuousThreadManager {
    private Thread dispatchThread;
    private Runnable eventStep;
    private CountDownLatch latch;

    /**
     * Do not use this constructor if you have any ability to provide a name for the thread.
     * The string parameter in the alternative constructor is use
     * @param eventStep
     */
    public ContinuousThreadManager(Runnable eventStep, CountDownLatch latch) {
        this.eventStep = eventStep;
        this.latch = latch;
        this.dispatchThread = new Thread
                ( this::eventLoop
                , "Default ContinuousThreadManager Thread");
        this.dispatchThread.setPriority(9);
        this.dispatchThread.setDaemon(true);

        startDispatch();
    }
    public ContinuousThreadManager(Runnable eventStep, String threadName, CountDownLatch latch) {
        this.eventStep = eventStep;
        this.latch = latch;
        this.dispatchThread = new Thread
                ( this::eventLoop
                , threadName);
        this.dispatchThread.setDaemon(true);

        startDispatch();
    }

    public void startDispatch() {
        dispatchThread.start();
    }

    private void eventLoop() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!dispatchThread.isInterrupted()) {
            eventStep.run();
        }
    }


}










