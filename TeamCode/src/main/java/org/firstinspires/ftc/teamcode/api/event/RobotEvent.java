package org.firstinspires.ftc.teamcode.api.event;

//this could be refactored into more classes with visitor pattern,
//but that is a lot of work and is terribly ugly
public enum RobotEvent {
    //FIXME make an enum of events to manage (like ControllerKey, but
    //      for stuff like LiftReachedTop)
    NullEvent,
    //Opmode
    OpmodeInit,
    OpmodeStart,
    OpmodeStop,
    //Sensors
    OctoTouchSensorPressed,
    OctoServoExpanded,
    OctoServoCompressed,
    //Lift
    ////Statuses
    LiftReachedPickup,
    LiftReachedCarry,
    LiftReachedLow,
    LiftReachedMedium,
    LiftReachedHigh,
    ////Commands
    LiftRaiseToCarry,
    LiftRaiseToLow,
    LiftRaiseToMedium,
    LiftRaiseToHigh,
    LiftRaiseToPickup,
    //Manual
    ManualDrop,
    ManualPickup,
    ManualOctoServoToggle,
    DebugOctoMotorUp, DebugOctoMotorDown, ConeGuideLightSensorDetected
}
