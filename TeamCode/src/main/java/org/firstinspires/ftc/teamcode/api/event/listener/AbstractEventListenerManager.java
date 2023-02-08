package org.firstinspires.ftc.teamcode.api.event.listener;

import com.google.common.collect.Multimap;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.IConditionalManager;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class AbstractEventListenerManager {
    private Multimap<RobotEvent, IRobotEventListener> bindings;
    private List<ContinuousEventListener> continuousListeners;

    private AbstractEventListenerManager() {}

    public AbstractEventListenerManager(Multimap<RobotEvent, IRobotEventListener> bindings,
                                        List<ContinuousEventListener> continuousListeners) {
        this.continuousListeners = continuousListeners;
        this.bindings = bindings;
    }

    public void register(IRobotEventListener eventListenerI, RobotEvent key) {
        bindings.put(key, eventListenerI);
        if (eventListenerI instanceof ContinuousEventListener) {
            continuousListeners.add(
                    (ContinuousEventListener) eventListenerI
            );
        }
    }
    public void unregister(IRobotEventListener binding, RobotEvent key) {
        bindings.remove(key, binding);
    }

    /**
     *
     * @param robotEvent event to search for
     * @return all <code>IEventListener</code>s that are listening for the event
     */
    public Collection<IRobotEventListener> getEventListeners(RobotEvent robotEvent) {
        return bindings.get(robotEvent);
    }

    /**
     *
     * @return all event listeners this manager knows of
     */
    public Collection<IRobotEventListener> getEventListeners() {
        return bindings.values();
    }
    public Collection<ContinuousEventListener> getContinuousListeners() {
        return continuousListeners;
    }
}
