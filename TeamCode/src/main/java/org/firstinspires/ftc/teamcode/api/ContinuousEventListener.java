package org.firstinspires.ftc.teamcode.api;

public abstract class ContinuousEventListener implements IRobotEventListener{
    private IRobotEventMediator mediator;
    private final ContinuousThreadManager threadManager;

    private ContinuousEventListener() {
        this.threadManager = new ContinuousThreadManager(this::eventStep);
    }

    public ContinuousEventListener(String name) {
        this.threadManager = new ContinuousThreadManager(this::eventStep, name);
    }

    @Override
    public void initHandshake(IRobotEventMediator mediator) {
        this.mediator = mediator;
    }

    public void startDispatch() {
        threadManager.startDispatch();
    }

    /**
     * Determines the events to send to the mediator. This should be passed to the <code>threadManager</code> or its factory.
     * It is critical this function does not loop. Instead, it should do any checks it
     * needs to do and send an event. <b> If it does not return, the behavior is undefined. </b>
     */
    abstract void eventStep();

//    public void setMediator(IRobotEventMediator mediator) {
//        this.mediator = mediator;
//    }
//
    public IRobotEventMediator getMediator() {
        return mediator;
    }

}
