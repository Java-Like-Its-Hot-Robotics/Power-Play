package org.firstinspires.ftc.teamcode.api.event.listener;

import com.google.common.base.Function;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;

import kotlin.Unit;

public interface IRobotEventListener<T extends Enum<T>> {
    void handleEvent(RobotEventI<T> robotEventI);
    Function<RobotEventI<T>, Unit> getEventHandler();
    IRobotEventListener<T> addEventHandler(Function<RobotEventI<T>, Unit> eventHandler);
    void initHandshake(IRobotEventMediator<T> mediator);

    //TODO: move this into its own interface
    void notify(RobotEventI<T> robotEventI);
    void notifyWhen(RobotEventI<T> robotEventI, Callable<Boolean> condition);
}
