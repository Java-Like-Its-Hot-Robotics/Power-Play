package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.listener.AbstractEventListenerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class EventDispatcherFactory<T extends Enum<T>> {
    private int capacity = 50;
    private BlockingQueue<RobotEventI<T>> queue = new ArrayBlockingQueue<>(capacity);
    private Multimap<RobotEventI<T>, IRobotEventListener<T>> multimap = MultimapBuilder.hashKeys().arrayListValues().build();
    private List<ContinuousEventListener<T>> continuousListeners = new ArrayList<>(0);
    private AbstractEventListenerManager<T> elm = new AbstractEventListenerManager<T>(multimap, continuousListeners) {};

    private boolean isGlobal = true;

    public EventDispatcherFactory() {}

    public void setMultimap(Multimap<RobotEventI<T>, IRobotEventListener<T>> multimap) {
        this.multimap = multimap;
    }

    public void setElm(AbstractEventListenerManager<T> elm) {
        this.elm = elm;
    }

    public EventDispatcherFactory<T> setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public EventDispatcherFactory<T> setQueue(BlockingQueue<RobotEventI<T>> queue) {
        this.queue = queue;
        return this;
    }

    /**
     * Set if the generated EventDispatcher should care about what listeners are registered to
     * listen for. Defaults to true;
     * @param isGlobal whether or not the EventDispatcher should be global
     * @return <code>this</code>
     */
    public EventDispatcherFactory<T> global(boolean isGlobal) {
        this.isGlobal = isGlobal;
        return this;
    }

    //FIXME does not properly register with the generated AbstractEventDispatcher

    public AbstractEventDispatcher<T> build() {
        if (isGlobal) {
            return new GlobalEventDispatcher<T>(elm, queue);
        } else {
            return new RegisteredEventDispatcher<T>(elm, queue);
        }
    }
}
