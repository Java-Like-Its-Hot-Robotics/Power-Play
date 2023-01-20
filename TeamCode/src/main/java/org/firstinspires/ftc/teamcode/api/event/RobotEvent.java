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
    LiftReachedPickup,
    LiftReachedCarry,
    LiftReachedLow,
    LiftReachedMedium,
    LiftReachedHigh,
    LiftRaiseToCarry,
    LiftRaiseToLow,
    LiftRaiseToMedium,
    LiftRaiseToHigh,
    //Manual
    ManualDrop,
    ManualPickup,
    ConeguideLightSensorDetected
}
