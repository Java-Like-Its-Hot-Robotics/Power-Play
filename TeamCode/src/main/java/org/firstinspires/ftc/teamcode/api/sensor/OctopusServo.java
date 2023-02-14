package org.firstinspires.ftc.teamcode.api.sensor;

import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.*;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public class OctopusServo extends DiscreteEventListener {
    private final Servo servo;
    private final double DROP;
    private final double HOLD;
    private static final double TOLERANCE = 0.02;
    private static final double TIMEOUT = 500; //milliseconds
    private boolean isExpanded;
    private static double pressTime = System.currentTimeMillis();

    public OctopusServo(Servo servo) {
        this.servo = servo;
        //TODO: make this a static value (with debugger)
        DROP = -0.5;//servo.getPosition();
        HOLD = 0.3; //0.4
        isExpanded = false;

    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case LiftReachedPickup:
            case ManualPickup:
                servo.setPosition(HOLD);
//                pauseUntil(HOLD);
                isExpanded = true;

                super.notify(OctoServoExpanded);
                break;
            case ManualDrop:
                servo.setPosition(DROP);
//                pauseUntil(DROP);
                isExpanded = false;

                super.notify(OctoServoCompressed);
                break;
            case ManualOctoServoToggle:
                if(!preventRepeatFor(TIMEOUT)) break;
                if (isExpanded) {
                    super.notify(ManualDrop);
                }
                else {
                    super.notify(ManualPickup);
                }
                break;
            case OctoTouchSensorPressed:
                //the touch sensor event has fired
                //pickup what we are holding
                super.notify(ManualPickup);
                //go up to pickup
                super.notify(LiftGoStageUp);

            default: break;
        }
    }

}
