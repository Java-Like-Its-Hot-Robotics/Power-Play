package org.firstinspires.ftc.teamcode.api.event.listener;

import com.google.common.collect.Multimap;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.Collection;
import java.util.List;

public abstract class AbstractEventListenerManager<T extends Enum<T>> {
    private Multimap<RobotEventI<T>, IRobotEventListener<T>> bindings;
    private List<ContinuousEventListener<T>> continuousListeners;

    private AbstractEventListenerManager() {}

    public AbstractEventListenerManager(Multimap<RobotEventI<T>, IRobotEventListener<T>> bindings,
                                        List<ContinuousEventListener<T>> continuousListeners) {
        this.continuousListeners = continuousListeners;
        this.bindings = bindings;
    }

    public void register(IRobotEventListener<T> eventListenerI, RobotEventI<T> key) {
        bindings.put(null, eventListenerI);
        if (eventListenerI instanceof ContinuousEventListener) {
            continuousListeners.add(
                    (ContinuousEventListener) eventListenerI
            );
        }
    }
    public void unregister(IRobotEventListener<T> binding, RobotEventI<T> key) {
        bindings.remove(key, binding);
    }

    /**
     *
     * @param robotEventI event to search for
     * @return all <code>IEventListener</code>s that are listening for the event
     */
    public Collection<IRobotEventListener<T>> getEventListeners(RobotEventI<T> robotEventI) {
        return bindings.get(robotEventI);
    }

    /**
     *
     * @return all event listeners this manager knows of
     */
    public Collection<IRobotEventListener<T>> getEventListeners() {
        return bindings.values();
    }
    public Collection<ContinuousEventListener<T>> getContinuousListeners() {
        return continuousListeners;
    }
}
