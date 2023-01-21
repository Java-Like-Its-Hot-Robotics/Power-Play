package org.firstinspires.ftc.teamcode.api.event.listener.continuous;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public abstract class ContinuousEventListener implements IRobotEventListener {
    private IRobotEventMediator mediator;
    private CountDownLatch mediatorLatch = new CountDownLatch(1);
//    private final ContinuousThreadManager threadManager;

    private ContinuousEventListener() {
//        this.threadManager = new ContinuousThreadManager(this::eventStep, mediatorLatch);
    }

    public ContinuousEventListener(String name) {
//        this.threadManager = new ContinuousThreadManager(this::eventStep, name, mediatorLatch);
    }

    @Override
    public void initHandshake(IRobotEventMediator mediator) {
        this.mediator = mediator;
        mediatorLatch.countDown();
    }


    /**
     * Determines the events to send to the mediator. This should be passed to the <code>threadManager</code> or its factory.
     * It is critical this function does not loop. Instead, it should do any checks it
     * needs to do and send an event. <b> If it does not return, the behavior is undefined. </b>
     */
    public abstract void eventStep();

    public IRobotEventMediator getMediator() {
        return mediator;
    }

    public void notify(RobotEvent robotEvent) {
        mediator.notify(robotEvent);
    }

}
