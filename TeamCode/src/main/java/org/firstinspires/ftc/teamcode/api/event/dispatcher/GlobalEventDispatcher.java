package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * Does not check about registration before forwarding the event to listeners. This simplifies the
 * registration code but may hinder performance. TODO: Confirm if this is the case
 */
public class GlobalEventDispatcher extends AbstractEventDispatcher {
    public GlobalEventDispatcher(AbstractEventListenerManager listenerManager, BlockingQueue<RobotEvent> eventsQueue) {
        super(listenerManager, eventsQueue);
    }

    @Override
    protected void dispatchLoop() {
//        final RobotEvent event = super.getEventsQueue().remove();
        for (RobotEvent event : super.getEventsQueue()) {
            for (IRobotEventListener listenerI : super.getListenerManager().getEventListeners()) {
                dispatch(listenerI, event);
            }
            super.getEventsQueue().poll();
        }
        //go through all of the eventListeners that NEED to get triggered
        for(ContinuousEventListener contListener : super.getListenerManager().getContinuousListeners()) {
            contListener.eventStep();
        }
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
    public void updateWhileStarted() {
        dispatchLoop();
    }

    @Override
    public void stop() {
        notify(RobotEvent.OpmodeStop);
    }
}
