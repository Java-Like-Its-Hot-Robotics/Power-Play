package org.firstinspires.ftc.teamcode.api.sensor;

import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.OctoServoExpanded;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public class OctopusServo extends DiscreteEventListener {
    private Servo servo;

    public OctopusServo(Servo servo) {
        this.servo = servo;
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case ManualPickup:
            case LiftReachedPickup:
                //TODO: change this value to reflect the servo position
                servo.setPosition(0.5);
                super.notify(OctoServoExpanded);
                break;
            case ManualDrop:
                servo.setPosition(0);
                break;
            default: break;
        }
    }
}
