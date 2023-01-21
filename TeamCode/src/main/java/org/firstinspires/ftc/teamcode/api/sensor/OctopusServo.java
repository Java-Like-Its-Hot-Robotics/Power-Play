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
    private static final double TIMEOUT = 1000; //milliseconds
    private boolean isExpanded;

    public OctopusServo(Servo servo) {
        this.servo = servo;
        //TODO: make this a static value (with debugger)
        DROP = -0.4;//servo.getPosition();
        HOLD = 0.3;
        isExpanded = false;
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case ManualPickup:
            case LiftReachedPickup:
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
                if (isExpanded) {
                    super.notify(ManualDrop);
                }
                else {
                    super.notify(ManualPickup);
                }
            default: break;
        }
    }

    private void pauseUntil(double position) {
        double timeout = System.currentTimeMillis() + TIMEOUT;
        while (Math.abs(servo.getPosition() - position) < TOLERANCE || System.currentTimeMillis() > timeout);
    }
}
