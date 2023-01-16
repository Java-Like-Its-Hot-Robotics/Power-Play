package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

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
        try {
            while(!super.getDispatchThread().isInterrupted()) {
                final RobotEvent event = super.getEventsQueue().take();
                for(IRobotEventListener listenerI : super.getListenerManager().getEventListeners()) {
                    dispatch(listenerI, event);
                }
                //someone asked to stop this thread
                //set back the interrupt flag and
                //quit the loop
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
