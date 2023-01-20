package org.firstinspires.ftc.teamcode.api.event.listener.discrete;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

public abstract class DiscreteEventListener implements IRobotEventListener {
    private IRobotEventMediator mediator;

    public DiscreteEventListener() {}

    @Override
    public void initHandshake(IRobotEventMediator mediator) {
        this.mediator = mediator;
    }

    public IRobotEventMediator getMediator() {
        return mediator;
    }

    public void notify(RobotEvent robotEvent) {
        mediator.notify(robotEvent);
    }
}