package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractEventDispatcher implements IRobotEventMediator {

    //TODO Move binding management to AbstractEventListenerManager
    private AbstractEventListenerManager listenerManager;
    //TODO: Ensure the nullary constructor puts an initial heartbeat ping to give each Listener its
    //      instance variable
    private Queue<RobotEvent> eventsQueue;
//    private Thread dispatchThread;
//    private ExecutorService dispatchPool = Executors.newFixedThreadPool(2);

    private AbstractEventDispatcher() {}

    public AbstractEventDispatcher(AbstractEventListenerManager listenerManager, BlockingQueue<RobotEvent> eventsQueue) {
        this.listenerManager = listenerManager;
        this.eventsQueue = eventsQueue;
//        this.dispatchThread = new Thread
//                (this::dispatchLoop
//                , "Event Dispatch Loop");
//        dispatchThread.setDaemon(true);
//        dispatchThread.setPriority(9);
//        dispatchThread.start();
    }

    /**  Raises an event to dispatch
     */
    public void notify(@NonNull RobotEvent robotEvent) {
        eventsQueue.add(robotEvent);
//        try {
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

//    protected Thread getDispatchThread() {
//        return dispatchThread;
//    }

    /**
     * Send an init event. Useful for OpModes.
     */
    public abstract void init();
    public abstract void start();

    public abstract void updateWhileStarted();

    public abstract void stop();
//    public void kill() {
//        dispatchThread = null;
//        dispatchPool.shutdown();
//    }
    protected abstract void dispatchLoop();
}
