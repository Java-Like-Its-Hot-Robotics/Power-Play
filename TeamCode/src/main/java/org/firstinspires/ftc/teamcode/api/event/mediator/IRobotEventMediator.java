package org.firstinspires.ftc.teamcode.api.event.mediator;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

import java.util.concurrent.Callable;

public interface IRobotEventMediator {
    void notify(RobotEvent robotEvent);
    void notifyWhen(RobotEvent robotEvent, Callable<Boolean> condition);
}
