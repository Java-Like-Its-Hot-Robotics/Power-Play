package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractEventDispatcher implements IRobotEventMediator {

    //TODO Move binding management to AbstractEventListenerManager
    private AbstractEventListenerManager listenerManager;
    //TODO: Ensure the nullary constructor puts an initial heartbeat ping to give each Listener its
    //      instance variable
    private BlockingQueue<RobotEvent> eventsQueue;
    private Thread dispatchThread;

    private AbstractEventDispatcher() {}

    public AbstractEventDispatcher(AbstractEventListenerManager listenerManager, BlockingQueue<RobotEvent> eventsQueue) {
        this.listenerManager = listenerManager;
        this.eventsQueue = eventsQueue;
        this.dispatchThread = new Thread
                (this::dispatchLoop
                , "Event Dispatch Loop");
        dispatchThread.setDaemon(true);
        dispatchThread.start();
    }

    /**  Raises an event to dispatch
     */
    public void notify(@NonNull RobotEvent robotEvent) {
        //blocks if it cant put the event into the current queue
        try {
            eventsQueue.put(robotEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void dispatch(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
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

    protected BlockingQueue<RobotEvent> getEventsQueue() {
        return eventsQueue;
    }

    protected Thread getDispatchThread() {
        return dispatchThread;
    }

    /**
     * Send an init event. Useful for OpModes.
     */
    public abstract void init();
    public abstract void start();
    public abstract void stop();
    protected abstract void dispatchLoop();
}
