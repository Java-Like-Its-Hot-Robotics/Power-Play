package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Anything meant to manage events and their dispatch should extend this class.
 */
public abstract class AbstractEventDispatcher<T extends Enum<T>> implements IRobotEventMediator<T> {

    private AbstractEventListenerManager<T> listenerManager;
    private Queue<RobotEventI<T>> eventsQueue;
    private IConditionalManager conditionalManager = new ReplaceConditionalHolder();

    private AbstractEventDispatcher() {}

    public AbstractEventDispatcher(AbstractEventListenerManager<T> listenerManager, BlockingQueue<RobotEventI<T>> eventsQueue) {
        this.listenerManager = listenerManager;
        this.eventsQueue = eventsQueue;
    }

    /**  Raises an event to dispatch
     * @param robotEventI
     */
    public void notify(@NonNull RobotEventI<T> robotEventI) {
        eventsQueue.add(robotEventI);
    }

    @Override
    public void notifyWhen(RobotEventI<T> robotEventI, Callable<Boolean> condition) {
        //TODO: make this more abstract and replaceable
        conditionalManager = new ReplaceConditionalHolder(condition, robotEventI);
    }

    protected void dispatch(IRobotEventListener<T> eventListenerI, RobotEventI<T> robotEventI) {
       eventListenerI.handleEvent(robotEventI);
    }


    public AbstractEventDispatcher<T> register(IRobotEventListener<T> eventListenerI, RobotEventI<T> robotEventI) {
        eventListenerI.initHandshake(this);
        listenerManager.register(eventListenerI, robotEventI);
        return this;
    }
    public AbstractEventDispatcher<T> register(IRobotEventListener<T> eventListenerI) {
        eventListenerI.initHandshake(this);
        listenerManager.register(eventListenerI, null);
        return this;
    }
    public AbstractEventDispatcher<T> unregister(IRobotEventListener<T> eventListenerI, RobotEventI<T> robotEventI) {
        listenerManager.unregister(eventListenerI, robotEventI);
        return this;
    }

    protected AbstractEventListenerManager<T> getListenerManager() {
        return listenerManager;
    }

    protected Queue<RobotEventI<T>> getEventsQueue() {
        return eventsQueue;
    }
    protected IConditionalManager getConditionalManager() {
        return conditionalManager;
    }


    /**
     * Send an init event. Useful for OpModes.
     */
//    public abstract void init();
//    public abstract void start();
//
    public abstract void updateWhileStarted();

//    public abstract void stop();

    /**
     * This is what will run during the opmode. It should handle events.
     */
    protected abstract void dispatchLoop();

    /**
     * Conditionals should be checked and executed upon invocation.
     */
    protected abstract void handleConditional();
}
