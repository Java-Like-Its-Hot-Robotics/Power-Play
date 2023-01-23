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
                if(!pauseUntil(TIMEOUT)) break;
                if (isExpanded) {
                    super.notify(ManualDrop);
                }
                else {
                    super.notify(ManualPickup);
                }
            case OctoTouchSensorPressed:
                super.notify(ManualOctoServoToggle);

            default: break;
        }
    }

//    private boolean pauseUntil(double timeout) {
//        if(System.currentTimeMillis() > pressTime + timeout) {
//            pressTime = System.currentTimeMillis();
//            return true;
//        }
//        return false;
//    }
}
