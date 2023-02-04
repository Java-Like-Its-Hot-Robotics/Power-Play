package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

@SuppressWarnings("FieldCanBeLocal")
public class OctopusDualMotor extends DiscreteEventListener {
    private final DcMotorEx motorL;
    private final DcMotorEx motorR;
    private final int CARRY_POS = 150;
    private final int PICKUP_POS = 0;
    private final int LOW_HEIGHT = 390;
    private final int MED_HEIGHT = 750;
    private final int HIGH_HEIGHT = 850;

    public OctopusDualMotor(DcMotorEx motorL, DcMotorEx motorR) {
//      TODO: refactor into a manager class
        this.motorL = motorL;
        this.motorR = motorR;

        motorL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }


    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            //do not listen for touch, the servo does that and will notify us
//            case ManualDrop:
            case LiftRaiseToCarry:
//            case OctoServoCompressed: //dropped the cone
//            case OctoServoExpanded: //we are at pickup height
                if(motorL.isBusy()) break;
                motorL.setTargetPosition(CARRY_POS);
//                super.notify(RobotEvent.LiftReachedCarry);
                break;
            case ConeGuideLightSensorDetected:
//                if(motor.isBusy()) break;
                motorL.setTargetPosition(PICKUP_POS);
//                super.notify(RobotEvent.LiftReachedPickup);
                break;
            case LiftRaiseToLow:
//                if(motor.isBusy()) break;
                motorL.setTargetPosition(LOW_HEIGHT);
//                super.notify(RobotEvent.LiftReachedLow);
                break;
            case LiftRaiseToMedium:
//                if(motor.isBusy()) break;
                motorL.setTargetPosition(MED_HEIGHT);
//                super.notify(RobotEvent.LiftReachedMedium);
                break;
            case LiftRaiseToHigh:
//                if(motor.isBusy()) break;
                motorL.setTargetPosition(HIGH_HEIGHT);
//                super.notify(RobotEvent.LiftReachedHigh);
                break;
            case LiftRaiseToPickup:
//                if(motor.isBusy()) break;
                motorL.setTargetPosition(-10);
            case OctoTouchSensorPressed:
                //stop the motor where it is
//                motor.setTargetPosition(motor.getCurrentPosition());
//                super.notify(RobotEvent.ManualOctoServoToggle);
                break;
            case DebugOctoMotorDown:
                preventRepeatFor(50);
                motorL.setTargetPosition(motorL.getTargetPosition()-5);
            case DebugOctoMotorUp:
                preventRepeatFor(50);
                motorL.setTargetPosition(motorL.getTargetPosition()+5);
        }
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




















