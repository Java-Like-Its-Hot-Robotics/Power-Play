package org.firstinspires.ftc.teamcode.api.event.dispatcher;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;

public class ReplaceConditionalHolder implements IConditionalManager {
    private Callable<Boolean> condition;
    private RobotEventI<? extends Enum<?>> resultEvent;

    public ReplaceConditionalHolder() {
        condition = () -> false;
        resultEvent = RobotEventI.NullEvent.NullEvent;
    }

    public ReplaceConditionalHolder(Callable<Boolean> condition, RobotEventI<? extends Enum<?>> resultEvent) {
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
            resultEvent = RobotEventI.NullEvent.NullEvent;
        }
    }
}
