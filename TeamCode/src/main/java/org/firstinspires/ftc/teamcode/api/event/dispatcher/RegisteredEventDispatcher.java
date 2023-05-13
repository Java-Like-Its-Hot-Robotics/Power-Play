package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.concurrent.BlockingQueue;

public class RegisteredEventDispatcher<T extends Enum<T>> extends AbstractEventDispatcher<T> {


    public RegisteredEventDispatcher(AbstractEventListenerManager<T> listenerManager, BlockingQueue<RobotEventI<T>> eventsQueue) {
        super(listenerManager, eventsQueue);
    }

    @Override
    protected void dispatchLoop() {
        final RobotEventI<T> event = super.getEventsQueue().remove();
        for(IRobotEventListener<T> listenerI : super.getListenerManager().getEventListeners(event)) {
            dispatch(listenerI, event);
        }
    }

    @Override
    protected void handleConditional() {

    }



    @Override
    public void updateWhileStarted() {}
}
