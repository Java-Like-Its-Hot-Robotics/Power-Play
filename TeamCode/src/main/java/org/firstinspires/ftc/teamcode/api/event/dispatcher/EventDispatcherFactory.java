package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventDispatcherFactory {
    private int capacity = 50;
    private BlockingQueue<RobotEvent> queue = new ArrayBlockingQueue<>(capacity);
    private Multimap<RobotEvent, IRobotEventListener> multimap = MultimapBuilder.hashKeys().arrayListValues().build();
    private AbstractEventListenerManager elm = new AbstractEventListenerManager(multimap) {};

    private boolean isGlobal = true;

    public EventDispatcherFactory() {}

    public void setMultimap(Multimap<RobotEvent, IRobotEventListener> multimap) {
        this.multimap = multimap;
    }

    public void setElm(AbstractEventListenerManager elm) {
        this.elm = elm;
    }

    public EventDispatcherFactory setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public EventDispatcherFactory setQueue(BlockingQueue<RobotEvent> queue) {
        this.queue = queue;
        return this;
    }

    /**
     * Set if the generated EventDispatcher should care about what listeners are registered to
     * listen for. Defaults to true;
     * @param isGlobal whether or not the EventDispatcher should be global
     * @return <code>this</code>
     */
    public EventDispatcherFactory global(boolean isGlobal) {
        this.isGlobal = isGlobal;
        return this;
    }

    //FIXME does not properly register with the generated AbstractEventDispatcher
//    public EventDispatcherFactory register(IRobotEventListener eventListener) {
////        elm.register(eventListener, RobotEvent.NullEvent);
//        return this;
//    }
//    public EventDispatcherFactory register(IRobotEventListener eventListener, RobotEvent event) {
////        elm.register(eventListener, event);
//        return this;
//    }

    public AbstractEventDispatcher build() {
        if (isGlobal) {
            return new GlobalEventDispatcher(elm, queue);
        } else {
            return new RegisteredEventDispatcher(elm, queue);
        }
    }
}
