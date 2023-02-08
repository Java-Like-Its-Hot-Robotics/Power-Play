package org.firstinspires.ftc.teamcode.api.event;

//this could be refactored into more classes with visitor pattern,
//but that is a lot of work and is terribly ugly
public enum RobotEvent {
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
    //misc or unused
    ConeGuideLightSensorDetected
}
