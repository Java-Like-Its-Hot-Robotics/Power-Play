package org.firstinspires.ftc.teamcode.api.event.listener;

import com.google.common.collect.Multimap;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

import java.util.Collection;

public abstract class AbstractEventListenerManager {
    private Multimap<RobotEvent, IRobotEventListener> bindings;

    private AbstractEventListenerManager() {}

    public AbstractEventListenerManager(Multimap<RobotEvent, IRobotEventListener> bindings) {
        this.bindings = bindings;
    }

    public void register(IRobotEventListener eventListenerI, RobotEvent key) {
        bindings.put(key, eventListenerI);
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
}
