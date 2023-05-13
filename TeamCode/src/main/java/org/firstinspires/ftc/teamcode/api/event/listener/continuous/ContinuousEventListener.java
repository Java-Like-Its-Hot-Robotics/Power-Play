package org.firstinspires.ftc.teamcode.api.event.listener.continuous;

import com.google.common.base.Function;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.EventHandlerManager;
import org.firstinspires.ftc.teamcode.api.event.listener.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.event.mediator.IRobotEventMediator;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import kotlin.Unit;

public abstract class ContinuousEventListener<T extends Enum<T>> implements IRobotEventListener<T> {
    private IRobotEventMediator<T> mediator;
    private EventHandlerManager<T> eventHandlerManager = new EventHandlerManager();
    private CountDownLatch mediatorLatch = new CountDownLatch(1);
private double pressTime = 0;

    private ContinuousEventListener() {
    }

    public ContinuousEventListener(String name) {
    }

    @Override
    public void initHandshake(IRobotEventMediator<T> mediator) {
        this.mediator = mediator;
        mediatorLatch.countDown();
    }

    public boolean preventRepeatFor(double timeout) {
        if (System.currentTimeMillis() > pressTime + timeout) {
            pressTime = System.currentTimeMillis();
            return false;
        }
        return true;
    }
    /**
     * Determines the events to send to the mediator. This should be passed to the <code>threadManager</code> or its factory.
     * It is critical this function does not loop. Instead, it should do any checks it
     * needs to do and send an event. <b> If it does not return, the behavior is undefined. </b>
     * Note: This complements the event handlers, which are triggered upon receiving an event.
     * You can add event handlers via <code>addEventHandler</code>
     */
    public abstract void eventStep();

    public IRobotEventMediator<T> getMediator() {
        return mediator;
    }

    public void notify(RobotEventI<T> robotEventI) {
        mediator.notify(robotEventI);
    }

    @Override
    public void notifyWhen(RobotEventI<T> robotEventI, Callable<Boolean> condition) {
        mediator.notifyWhen(robotEventI, condition);
    }

    public void handleEvent(RobotEventI<T> robotEventI) {
        getEventHandler().apply(robotEventI);
    }
    ///////////////////////EVENT HANDLER//////////////////////////////
    @Override
    public Function<RobotEventI<T>, Unit> getEventHandler() {
        return eventHandlerManager.getComposedHandler();
    }

    @Override
    public IRobotEventListener<T> addEventHandler(Function<RobotEventI<T>, Unit> eventHandler) {
        eventHandlerManager.add(eventHandler);
        return this;
    }
}
