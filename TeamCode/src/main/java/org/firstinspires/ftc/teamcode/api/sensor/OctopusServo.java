package org.firstinspires.ftc.teamcode.api.sensor;

import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.OctoServoCompressed;
import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.OctoServoExpanded;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public class OctopusServo extends DiscreteEventListener {
    private Servo servo;
    private double DROP;
    private double HOLD;

    public OctopusServo(Servo servo) {
        this.servo = servo;
        //TODO: make this a static value (with debugger)
        DROP = servo.getPosition();
        HOLD = DROP + 0.1;
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case ManualPickup:
            case LiftReachedPickup:
                //TODO: change this value to reflect the servo position
                servo.setPosition(HOLD);
                super.notify(OctoServoExpanded);
                break;
            case ManualDrop:
//                servo.setPosition(DROP);
                servo.setPosition(HOLD);
                super.notify(OctoServoCompressed);
                break;
            default: break;
        }
    }
}
