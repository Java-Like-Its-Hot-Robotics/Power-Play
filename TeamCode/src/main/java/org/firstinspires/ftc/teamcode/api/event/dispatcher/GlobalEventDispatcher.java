package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.concurrent.BlockingQueue;

/**
 * Does not check about registration before forwarding the event to listeners. This simplifies the
 * registration code but may hinder performance. TODO: Confirm if this is the case
 */
public class GlobalEventDispatcher<T extends Enum<T>> extends AbstractEventDispatcher<T> {
    public GlobalEventDispatcher(AbstractEventListenerManager<T> listenerManager, BlockingQueue<RobotEventI<T>> eventsQueue) {
        super(listenerManager, eventsQueue);
    }

    @Override
    protected void dispatchLoop() {
        //make sure conditional(s) are checked
        handleConditional();
        //find any events that fired
        for (RobotEventI<T> event : super.getEventsQueue()) {
            for (IRobotEventListener<T> listenerI : super.getListenerManager().getEventListeners()) {
                dispatch(listenerI, event);
            }
            super.getEventsQueue().poll();
        }
        //go through all of the eventListeners that NEED to get triggered
        for(ContinuousEventListener<T> contListener : super.getListenerManager().getContinuousListeners()) {
            contListener.eventStep();
        }
    }

    @Override
    protected void handleConditional() {
        super.getConditionalManager().check();
    }


    @Override
    public void updateWhileStarted() {
        dispatchLoop();
    }
}
