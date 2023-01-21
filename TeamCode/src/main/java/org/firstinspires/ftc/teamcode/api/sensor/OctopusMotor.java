package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("FieldCanBeLocal")
public class OctopusMotor extends DiscreteEventListener {
    private final DcMotor motor;
    private final int CARRY_POS = 150;
    private final int PICKUP_POS = 0;
    private final int LOW_HEIGHT = 250;
    private final int MED_HEIGHT = 500;
    private final int HIGH_HEIGHT = 750;
    private static AtomicInteger storedHeight = new AtomicInteger(0);

    public OctopusMotor(DcMotor motor) {
        this.motor = motor;
    }


    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            //do not listen for touch, the servo does that and will notify us
//            case ManualDrop:
            case LiftRaiseToCarry:
//            case OctoServoCompressed: //dropped the cone
//            case OctoServoExpanded: //we are at pickup height
                if(motor.isBusy()) break;
                motor.setTargetPosition(CARRY_POS);
//                super.notify(RobotEvent.LiftReachedCarry);
                break;
            case ConeGuideLightSensorDetected:
//                if(motor.isBusy()) break;
                motor.setTargetPosition(PICKUP_POS);
//                super.notify(RobotEvent.LiftReachedPickup);
                break;
            case LiftRaiseToLow:
//                if(motor.isBusy()) break;
                motor.setTargetPosition(LOW_HEIGHT);
//                super.notify(RobotEvent.LiftReachedLow);
                break;
            case LiftRaiseToMedium:
//                if(motor.isBusy()) break;
                motor.setTargetPosition(MED_HEIGHT);
//                super.notify(RobotEvent.LiftReachedMedium);
                break;
            case LiftRaiseToHigh:
                if(motor.isBusy()) break;
                motor.setTargetPosition(HIGH_HEIGHT);
//                super.notify(RobotEvent.LiftReachedHigh);
                break;
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




















