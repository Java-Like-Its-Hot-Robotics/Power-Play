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
        try {
            while(!super.getDispatchThread().isInterrupted()) {
                final RobotEvent event = super.getEventsQueue().take();
                for(IRobotEventListener listenerI : super.getListenerManager().getEventListeners(event)) {
                    dispatch(listenerI, event);
                }
                //someone asked to stop this thread
                //set back the interrupt flag and
                //quit the loop
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    public void stop() {
        notify(RobotEvent.OpmodeStop);
    }
}
