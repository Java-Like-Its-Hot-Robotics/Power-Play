package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Anything meant to manage events and their dispatch should extend this class.
 */
public abstract class AbstractEventDispatcher implements IRobotEventMediator {

    private AbstractEventListenerManager listenerManager;
    private Queue<RobotEvent> eventsQueue;
    private IConditionalManager conditionalManager = new ReplaceConditionalHolder();

    private AbstractEventDispatcher() {}

    public AbstractEventDispatcher(AbstractEventListenerManager listenerManager, BlockingQueue<RobotEvent> eventsQueue) {
        this.listenerManager = listenerManager;
        this.eventsQueue = eventsQueue;
    }

    /**  Raises an event to dispatch
     */
    public void notify(@NonNull RobotEvent robotEvent) {
        eventsQueue.add(robotEvent);
    }

    @Override
    public void notifyWhen(RobotEvent robotEvent, Callable<Boolean> condition) {
        //TODO: make this more abstract and replaceable
        conditionalManager = new ReplaceConditionalHolder(condition, robotEvent);
    }

    protected void dispatch(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        //Dispatching otherwise happens in sequential order. We don't want unbounded
        //concurrency, so a thread pool works well.
       eventListenerI.handleEvent(robotEvent);
    }


    public AbstractEventDispatcher register(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        eventListenerI.initHandshake(this);
        listenerManager.register(eventListenerI, robotEvent);
        return this;
    }
    public AbstractEventDispatcher register(IRobotEventListener eventListenerI) {
        eventListenerI.initHandshake(this);
        listenerManager.register(eventListenerI, RobotEvent.NullEvent);
        return this;
    }
    public AbstractEventDispatcher unregister(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        listenerManager.unregister(eventListenerI, robotEvent);
        return this;
    }

    protected AbstractEventListenerManager getListenerManager() {
        return listenerManager;
    }

    protected Queue<RobotEvent> getEventsQueue() {
        return eventsQueue;
    }
    protected IConditionalManager getConditionalManager() {
        return conditionalManager;
    }


    /**
     * Send an init event. Useful for OpModes.
     */
    public abstract void init();
    public abstract void start();

    public abstract void updateWhileStarted();

    public abstract void stop();

    /**
     * This is what will run during the opmode. It should handle events.
     */
    protected abstract void dispatchLoop();

    /**
     * Conditionals should be checked and executed upon invocation.
     */
    protected abstract void handleConditional();
}
