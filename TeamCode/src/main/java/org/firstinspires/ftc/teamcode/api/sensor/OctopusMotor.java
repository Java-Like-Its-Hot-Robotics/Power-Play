package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public class OctopusMotor extends DiscreteEventListener {
    private DcMotor motor;
    private final int CARRY_POS = 100;
    private final int PICKUP_POS = 50;
    private final int LOW_HEIGHT = 75;
    private final int MED_HEIGHT = 150;
    private final int HIGH_HEIGHT = 250;

    public OctopusMotor(DcMotor motor) {
        this.motor = motor;
    }


    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            //do not listen for touch, the servo does that and will notify us
            case ManualDrop:
            case LiftRaiseToCarry:
            case OctoServoCompressed: //dropped the cone
            case OctoServoExpanded: //we are at pickup height
                motor.setTargetPosition(CARRY_POS);
                super.notify(RobotEvent.LiftReachedCarry);
                break;
            case ConeguideLightSensorDetected:
                motor.setTargetPosition(PICKUP_POS);
                super.notify(RobotEvent.LiftReachedPickup);
                break;
            case LiftRaiseToLow:
                motor.setTargetPosition(LOW_HEIGHT);
                super.notify(RobotEvent.LiftReachedLow);
                break;
            case LiftRaiseToMedium:
                motor.setTargetPosition(MED_HEIGHT);
                super.notify(RobotEvent.LiftReachedMedium);
                break;
            case LiftRaiseToHigh:
                motor.setTargetPosition(HIGH_HEIGHT);
                super.notify(RobotEvent.LiftReachedHigh);
                break;
        }
    }
}
