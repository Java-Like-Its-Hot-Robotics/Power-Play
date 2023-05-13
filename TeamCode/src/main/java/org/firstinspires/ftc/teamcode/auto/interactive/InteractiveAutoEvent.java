package org.firstinspires.ftc.teamcode.auto.interactive;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;

public enum InteractiveAutoEvent implements RobotEventI<InteractiveAutoEvent> {
    IncrementValue,
    NullEvent,
    InitEvent,
    StartEvent,
    StopEvent,


    DecrementValue, ChangeFocusedValue, RunStep, StopStep, AdvanceStep, BackStep, ToggleMode, DriveInEditMode, ColorSensorDetectPole, IncrementBig, DecrementBig;

    @Override
    public InteractiveAutoEvent isNull() {
        return NullEvent;
    }

    @Override
    public InteractiveAutoEvent getValue() {
        return this;
    }

    @Override
    public InteractiveAutoEvent getInit() {
        return InitEvent;
    }

    @Override
    public InteractiveAutoEvent getStart() {
        return StartEvent;
    }

    @Override
    public InteractiveAutoEvent getStop() {
        return StopEvent;
    }
}
