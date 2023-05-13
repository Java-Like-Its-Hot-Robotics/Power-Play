package org.firstinspires.ftc.teamcode.api.event.listener;

import com.google.common.base.Function;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;

import java.util.ArrayList;
import java.util.Collection;

import kotlin.Unit;

public class EventHandlerManager<T extends Enum<T>> {
    Function<RobotEventI<T>, Unit> composedEventHandler;

    public EventHandlerManager() {
        this.composedEventHandler = (RobotEventI<T> t) -> Unit.INSTANCE;
    }

    public Function<RobotEventI<T>, Unit> getComposedHandler() {
        return composedEventHandler;
    }

    public void add(Function<RobotEventI<T>, Unit> eventHandler) {
        //Effectively . in Haskell; function composition
        // of the form `f . g = \x -> f (g (x))`
        // or `f >> g = \x -> f x >>= \_ -> g x`
        Function<RobotEventI<T>, Unit> oldHandler = composedEventHandler;
        composedEventHandler = (RobotEventI<T> t) -> {
            eventHandler.apply(t);
            oldHandler.apply(t);
            return Unit.INSTANCE;
        };
    }
}
