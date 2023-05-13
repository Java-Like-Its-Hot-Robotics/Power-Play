package org.firstinspires.ftc.teamcode.api.event.listener.discrete;

import com.google.common.base.Function;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.EventHandlerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;

import kotlin.Unit;

public abstract class DiscreteEventListener<T extends Enum<T>> implements IRobotEventListener<T> {
    private IRobotEventMediator<T> mediator;
    private EventHandlerManager<T> eventHandlerManager = new EventHandlerManager();
//    private
    private double pressTime = 0;

    public DiscreteEventListener() {}

    @Override
    public void initHandshake(IRobotEventMediator<T> mediator) {
        this.mediator = mediator;
    }

    public IRobotEventMediator<T> getMediator() {
        return mediator;
    }

    @Override
    public void notify(RobotEventI<T> robotEventI) {
        mediator.notify(robotEventI);
    }

    @Override
    public void notifyWhen(RobotEventI<T> robotEventI, Callable<Boolean> condition) {
        mediator.notifyWhen(robotEventI, condition);
    }

    public boolean preventRepeatFor(double timeout) {
        if(System.currentTimeMillis() > pressTime + timeout) {
            pressTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }
    //////////////////////////EVENT HANDLER////////////////////
    @Override
    public Function<RobotEventI<T>, Unit> getEventHandler() {
        return eventHandlerManager.getComposedHandler();
    }

    @Override
    public IRobotEventListener<T> addEventHandler(Function<RobotEventI<T>, Unit> eventHandler) {
        eventHandlerManager.add(eventHandler);
        return this;
    }

    @Override
    public void handleEvent(RobotEventI<T> robotEventI) {
        getEventHandler().apply(robotEventI);
    }
}
