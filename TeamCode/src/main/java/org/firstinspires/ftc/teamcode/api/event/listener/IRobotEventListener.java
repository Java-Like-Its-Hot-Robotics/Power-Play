package org.firstinspires.ftc.teamcode.api.event.listener;

import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

public interface IRobotEventListener {
    void handleEvent(RobotEvent robotEvent);
    void initHandshake(IRobotEventMediator mediator);
}
