package org.firstinspires.ftc.teamcode.api;

import com.google.common.collect.Multimap;

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

    public Collection<IRobotEventListener> getEventListeners(RobotEvent robotEvent) {
        return bindings.get(robotEvent);
    }
}
