package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.concurrent.BlockingQueue;

public class RegisteredEventDispatcher extends AbstractEventDispatcher {


    public RegisteredEventDispatcher(AbstractEventListenerManager listenerManager, BlockingQueue<RobotEvent> eventsQueue) {
        super(listenerManager, eventsQueue);
    }

    @Override
    protected void dispatchLoop() {
        final RobotEvent event = super.getEventsQueue().remove();
        for(IRobotEventListener listenerI : super.getListenerManager().getEventListeners(event)) {
            dispatch(listenerI, event);
        }
    }

    @Override
    protected void handleConditional() {

    }

    @Override
    public void init() {
        notify(RobotEvent.OpmodeInit);
    }

    @Override
    public void start() {
        notify(RobotEvent.OpmodeStart);
    }

    @Override
    public void updateWhileStarted() {}

    @Override
    public void stop() {
        notify(RobotEvent.OpmodeStop);
    }
}
