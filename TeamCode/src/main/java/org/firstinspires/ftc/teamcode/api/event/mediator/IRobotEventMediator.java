package org.firstinspires.ftc.teamcode.api.event.mediator;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;

import java.util.concurrent.Callable;

public interface IRobotEventMediator<T extends Enum<T>> {
    void notify(RobotEventI<T> robotEventI);
    void notifyWhen(RobotEventI<T> robotEventI, Callable<Boolean> condition);
}
