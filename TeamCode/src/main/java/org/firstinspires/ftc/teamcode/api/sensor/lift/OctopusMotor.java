package org.firstinspires.ftc.teamcode.api.sensor.lift;

import static org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusLevelManager.Levels.*;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;
import org.firstinspires.ftc.teamcode.api.sensor.ILevelTracker;
import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

@SuppressWarnings("FieldCanBeLocal")
public class OctopusMotor extends DiscreteEventListener {
    private final ILevelTracker levelTracker;
    private final IPositionable<Integer> motors;

    /**
     * Constructor for use when two motors are configured
     * @param motorL The first motor
     * @param motorR The second motor
     */
    public OctopusMotor(DcMotor motorL, DcMotor motorR) {
        motors = new DualMotors(motorL, motorR);
        levelTracker = new OctopusLevelManager();
    }

    /**
     * Constructor for use when only one motor is present
     * @param motor The motor to use
     */
    public OctopusMotor(DcMotor motor) {
        motors = new SoloMotor(motor);
        levelTracker = new OctopusLevelManager();
    }


    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch(robotEvent) {
            case ConeGuideLightSensorDetected:
                motors.setPosition(PICKUP_POS.getValue());
                break;
            case LiftRaiseToCarry:
                setMotorsToCallback(
                        CARRY_POS.getValue(),
                        RobotEvent.LiftReachedCarry
                );
                break;
            case LiftRaiseToLow:
                setMotorsToCallback(
                        LOW_HEIGHT.getValue(),
                        RobotEvent.LiftReachedLow
                );
                break;
            case LiftRaiseToMedium:
                setMotorsToCallback(
                        MED_HEIGHT.getValue(),
                        RobotEvent.LiftReachedMedium
                );
                break;
            case LiftRaiseToHigh:
                setMotorsToCallback(
                        HIGH_HEIGHT.getValue(),
                        RobotEvent.LiftReachedHigh
                );
                break;
            case LiftRaiseToPickup:
                setMotorsToCallback(
                        PICKUP_POS.getValue(),
                        RobotEvent.LiftReachedPickup
                );
                break;
            case OctoTouchSensorPressed:
                //stop the motor where it is
                super.notify(RobotEvent.ManualOctoServoToggle);
                break;
            case LiftGoStageUp:
                if (!preventRepeatFor(100)) break;
                setMotorsToCallback(
                        levelTracker.nextLevel(),
                        RobotEvent.LiftReachedNextLevel
                );
                break;
            case LiftGoStageDown:
                if (!preventRepeatFor(100)) break;
                setMotorsToCallback(
                        levelTracker.prevLevel(),
                        RobotEvent.LiftReachedPrevLevel
                );
                break;
            case DebugOctoMotorDown:
                if (!preventRepeatFor(250)) break;
                motors.setPosition(motors.getPosition()-5);
                break;
            case DebugOctoMotorUp:
                if (!preventRepeatFor(250)) break;
                motors.setPosition(motors.getPosition()+5);
                break;
        }
    }

    /**
     * Set the motor to a position and notify of a callback once the motors
     * reach that position.
     * @param pos The position to set the motors to
     * @param robotEvent The event to notify after reaching the specified position
     */
    private void setMotorsToCallback(int pos, RobotEvent robotEvent) {
        motors.setPosition(pos);
        super.notifyWhen(robotEvent, () -> motors.isAt(pos));
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




















