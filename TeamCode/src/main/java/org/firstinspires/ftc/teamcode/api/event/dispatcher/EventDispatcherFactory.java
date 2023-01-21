package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventDispatcherFactory {
    private int capacity = 50;
    private BlockingQueue<RobotEvent> queue = new ArrayBlockingQueue<>(capacity);
    private Multimap<RobotEvent, IRobotEventListener> multimap = MultimapBuilder.hashKeys().arrayListValues().build();
    private List<ContinuousEventListener> continuousListeners = new ArrayList<>(0);
    private AbstractEventListenerManager elm = new AbstractEventListenerManager(multimap, continuousListeners) {};

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

    public AbstractEventDispatcher build() {
        if (isGlobal) {
            return new GlobalEventDispatcher(elm, queue);
        } else {
            return new RegisteredEventDispatcher(elm, queue);
        }
    }
}
