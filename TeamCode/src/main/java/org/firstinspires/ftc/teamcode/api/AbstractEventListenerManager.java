package org.firstinspires.ftc.teamcode.api;

import com.google.common.collect.Multimap;

import java.util.Collection;

public abstract class AbstractEventListenerManager {
    private Multimap<RobotEvent, RobotEventListenerI> bindings;

    private AbstractEventListenerManager() {}

    public AbstractEventListenerManager(Multimap<RobotEvent, RobotEventListenerI> bindings) {
        this.bindings = bindings;
    }

    public void register(RobotEventListenerI eventListenerI, RobotEvent key) {
        bindings.put(key, eventListenerI);
    }
    public void unregister(RobotEventListenerI binding, RobotEvent key) {
        bindings.remove(key, binding);
    }

    public Collection<RobotEventListenerI> getEventListeners(RobotEvent robotEvent) {
        return bindings.get(robotEvent);
    }
}
