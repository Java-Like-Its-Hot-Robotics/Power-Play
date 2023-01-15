package org.firstinspires.ftc.teamcode.api;

import androidx.annotation.NonNull;

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

    /**  Add an event to dispatch
     */
    public void notify(@NonNull RobotEvent robotEvent) {
        eventsQueue.add(robotEvent);
    }

    private void dispatch(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        eventListenerI.handleEvent(robotEvent, this);
    }

    //pull off eventqueue and dispatch. this should be in its own thread
    private void dispatchLoop() {
        try {
            while(!dispatchThread.isInterrupted()) {
                final RobotEvent event = eventsQueue.take();
                for(IRobotEventListener listenerI : listenerManager.getEventListeners(event)) {
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

    public AbstractEventDispatcher register(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        eventListenerI.initHandshake(this);
        listenerManager.register(eventListenerI, robotEvent);
        return this;
    }
    public AbstractEventDispatcher unregister(IRobotEventListener eventListenerI, RobotEvent robotEvent) {
        listenerManager.unregister(eventListenerI, robotEvent);
        return this;
    }

    /**
     * Send an init event. Useful for OpModes.
     */
    public abstract void init();
    public abstract void start();
    public abstract void stop();
//    public void check() {
//        Iterator<IRobotEventListener> keyEventIterator = listenerManager.getEventListeners().iterator();
//
//        //For each key event, call all of its bound handlers with the key event in question and
//        //the gamepad state
//        while(keyEventIterator.hasNext()) {
//            IRobotEventListener nextKeyEvent = keyEventIterator.next();
//            Iterator<IRobotEventListener> bindingIterator = bindings.get(nextKeyEvent).iterator();
//            while(bindingIterator.hasNext()) {
//                IRobotEventListener nextBinding = bindingIterator.next();
//                nextBinding.handleKey(nextKeyEvent, this);
//            }
//
//        }
//    }
}
