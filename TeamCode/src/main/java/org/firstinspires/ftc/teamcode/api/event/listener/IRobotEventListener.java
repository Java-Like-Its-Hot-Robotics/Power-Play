package org.firstinspires.ftc.teamcode.api.event.listener;

import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

import java.util.concurrent.Callable;

public interface IRobotEventListener {
    void handleEvent(RobotEvent robotEvent);
    void initHandshake(IRobotEventMediator mediator);

    void notify(RobotEvent robotEvent);
    void notifyWhen(RobotEvent robotEvent, Callable<Boolean> condition);
}
