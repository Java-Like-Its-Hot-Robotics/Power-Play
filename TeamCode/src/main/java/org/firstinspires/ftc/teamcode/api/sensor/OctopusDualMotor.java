package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

@SuppressWarnings("FieldCanBeLocal")
public class OctopusDualMotor extends DiscreteEventListener {
    private final DcMotor motorL;
    private final DcMotor motorR;
    private final int PICKUP_POS  = 0;
    private final int CARRY_POS   = 150;
    private final int LOW_HEIGHT  = 375;
    private final int MED_HEIGHT  = 750;
    private final int HIGH_HEIGHT = 850;

    public OctopusDualMotor(DcMotor motorL, DcMotor motorR) {
//      TODO: refactor into a manager class
        this.motorL = motorL;
        this.motorR = motorR;

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setTargetPosition(0);
        motorR.setTargetPosition(0);

        motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case ConeGuideLightSensorDetected:
                motorL.setTargetPosition(PICKUP_POS);
                motorR.setTargetPosition(PICKUP_POS);
                break;
            case LiftRaiseToCarry:
                setMotorsToCallback(CARRY_POS, RobotEvent.LiftReachedCarry);
                break;
            case LiftRaiseToLow:
                setMotorsToCallback(LOW_HEIGHT, RobotEvent.LiftReachedLow);
                break;
            case LiftRaiseToMedium:
                setMotorsToCallback(MED_HEIGHT, RobotEvent.LiftReachedMedium);
                break;
            case LiftRaiseToHigh:
                setMotorsToCallback(HIGH_HEIGHT, RobotEvent.LiftReachedHigh);
                break;
            case LiftRaiseToPickup:
                setMotorsToCallback(0, RobotEvent.LiftReachedPickup);
                break;
            case OctoTouchSensorPressed:
                //stop the motor where it is
//                motor.setTargetPosition(motor.getCurrentPosition());
                super.notify(RobotEvent.ManualOctoServoToggle);
                break;
            case DebugOctoMotorDown:
                preventRepeatFor(50);
                motorL.setTargetPosition(motorL.getTargetPosition()-5);
                motorR.setTargetPosition(motorL.getTargetPosition()-5);
                break;
            case DebugOctoMotorUp:
                preventRepeatFor(50);
                motorL.setTargetPosition(motorL.getTargetPosition()+5);
                motorR.setTargetPosition(motorL.getTargetPosition()+5);
                break;
        }
    }

    private boolean motorsAt(int pos) {
        return motorL.getCurrentPosition() == pos
                && motorR.getCurrentPosition() == pos;
    }

    private void setMotorsTo(int pos) {
        motorL.setTargetPosition(pos);
        motorR.setTargetPosition(pos);
    }

    private void setMotorsToCallback(int pos, RobotEvent robotEvent) {
        setMotorsTo(pos);
        super.notifyWhen(robotEvent, () -> motorsAt(pos));
    }
//    private synchronized void setPos(int pos) {
//        //keep track between motor resets
//        storedHeight.set(motor.getCurrentPosition());
//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setTargetPosition(pos - storedHeight.get());
//
//    }
}




















