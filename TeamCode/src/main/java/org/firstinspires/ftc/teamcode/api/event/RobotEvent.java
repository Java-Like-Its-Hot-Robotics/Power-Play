package org.firstinspires.ftc.teamcode.api.event;

public enum RobotEvent implements RobotEventI<RobotEvent> {
    NullEvent,
    //Opmode
    OpmodeInit,
    OpmodeStart,
    OpmodeStop,
    //Sensors
    OctoTouchSensorPressed,
    OctoServoExpanded,
    OctoServoCompressed,
    ////////////////////////////////////
    //Lift
    ////Statuses
    LiftReachedPickup,
    LiftReachedCarry,
    LiftReachedLow,
    LiftReachedMedium,
    LiftReachedHigh,
    LiftReachedPrevLevel,
    LiftReachedNextLevel,
    ////Commands
    LiftRaiseToPickup,
    LiftRaiseToCarry,
    LiftRaiseToLow,
    LiftRaiseToMedium,
    LiftRaiseToHigh,
    /////Level Agnostic
    LiftGoStageUp,
    LiftGoStageDown,
    /////////////////////////////////////
    //Manual
    ManualDrop,
    ManualPickup,
    ManualOctoServoToggle,
    //Debug
    DebugOctoMotorUp,
    DebugOctoMotorDown,
    ColorSensorDetectRed, //misc or unused
    ColorSensorDetectBlue, ConeGuideLightSensorDetected;

    @Override
    public RobotEvent isNull() {
        return NullEvent;
    }

    @Override
    public RobotEvent getValue() {
        return this;
    }

    @Override
    public RobotEvent getInit() {
        return OpmodeInit;
    }

    @Override
    public RobotEvent getStart() {
        return OpmodeStart;
    }

    @Override
    public RobotEvent getStop() {
        return OpmodeStart;
    }
}
