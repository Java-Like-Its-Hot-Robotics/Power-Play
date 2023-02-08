package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;

/**
 * An interface meant for anything that can hold conditionals. Some managers
 * may want to only hold a single conditional, while others may want more than one event
 * in queue. They should implement this.
 */
public interface IConditionalManager {
    boolean check();
    void checkAndNotify(IRobotEventMediator mediator);
}
