package org.firstinspires.ftc.teamcode.api;

public interface IRobotEventListener {
    void handleEvent(RobotEvent robotEvent, IRobotEventMediator mediatorI);
    void initHandshake(IRobotEventMediator mediator);
}
