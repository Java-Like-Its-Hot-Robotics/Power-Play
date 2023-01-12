package org.firstinspires.ftc.teamcode.api;

//this could be refactored into more classes with visitor pattern,
//but that is a lot of work and is terribly ugly
public enum RobotEvent {
    //FIXME make an enum of events to manage (like ControllerKey, but
    //      for stuff like LiftReachedTop)
    Heartbeat,
    Init,
    Start,
    Stop,
    Debug1,
    Debug2,
}
