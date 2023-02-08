package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;

public class ReplaceConditionalHolder implements IConditionalManager {
    private Callable<Boolean> condition;
    private RobotEvent resultEvent;

    public ReplaceConditionalHolder() {
        condition = () -> false;
        resultEvent = RobotEvent.NullEvent;
    }

    public ReplaceConditionalHolder(Callable<Boolean> condition, RobotEvent resultEvent) {
        this.condition = condition;
        this.resultEvent = resultEvent;
    }

    @Override
    public boolean check() {
        try {
            return condition.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void checkAndNotify(IRobotEventMediator mediator) {
        if(check()) {
            mediator.notify(resultEvent);
            condition = () -> false;
            resultEvent = RobotEvent.NullEvent;
        }
    }
}
