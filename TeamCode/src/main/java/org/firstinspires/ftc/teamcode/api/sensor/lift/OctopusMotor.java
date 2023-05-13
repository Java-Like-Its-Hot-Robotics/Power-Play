package org.firstinspires.ftc.teamcode.api.sensor.lift;

import static org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusLevelManager.Levels.*;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;
import org.firstinspires.ftc.teamcode.api.sensor.ILevelTracker;
import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

import kotlin.Unit;

@SuppressWarnings("FieldCanBeLocal")
public class OctopusMotor<T extends Enum<T>> extends DiscreteEventListener<T> {
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
        addEventHandler((RobotEventI<T> event) -> {
            if(event instanceof RobotEvent) {
                switch((RobotEvent) event) {
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
                    case LiftGoStageUp:
                        if (preventRepeatFor(350)) break;
                        setMotorsToCallback(
                            levelTracker.nextLevel(),
                            RobotEvent.LiftReachedNextLevel
                        );
                        break;
                    case LiftGoStageDown:
                        if (preventRepeatFor(350)) break;
                        setMotorsToCallback(
                            levelTracker.prevLevel(),
                            RobotEvent.LiftReachedPrevLevel
                        );
                        break;
                    case DebugOctoMotorDown:
                        if (preventRepeatFor(250)) break;
                        motors.setPosition(motors.getPosition()-25);
                        break;
                    case DebugOctoMotorUp:
                        if (preventRepeatFor(250)) break;
                        motors.setPosition(motors.getPosition()+25);
                        break;
                }
            }
            return Unit.INSTANCE;
        });
        //TODO: add autonomous event handler
    }

    /**
     * Constructor for use when only one motor is present
     * @param motor The motor to use
     */
    public OctopusMotor(DcMotor motor) {
        motors = new SoloMotor(motor);
        levelTracker = new OctopusLevelManager();
    }


//    @Override
//    public void handleEvent(RobotEventI robotEventI) {
//        switch(robotEventI) {
//            case ConeGuideLightSensorDetected:
//                motors.setPosition(PICKUP_POS.getValue());
//                break;
//            case LiftRaiseToCarry:
//                setMotorsToCallback(
//                        CARRY_POS.getValue(),
//                        RobotEventI.LiftReachedCarry
//                );
//                break;
//            case LiftRaiseToLow:
//                setMotorsToCallback(
//                        LOW_HEIGHT.getValue(),
//                        RobotEventI.LiftReachedLow
//                );
//                break;
//            case LiftRaiseToMedium:
//                setMotorsToCallback(
//                        MED_HEIGHT.getValue(),
//                        RobotEventI.LiftReachedMedium
//                );
//                break;
//            case LiftRaiseToHigh:
//                setMotorsToCallback(
//                        HIGH_HEIGHT.getValue(),
//                        RobotEventI.LiftReachedHigh
//                );
//                break;
//            case LiftRaiseToPickup:
//                setMotorsToCallback(
//                        PICKUP_POS.getValue(),
//                        RobotEventI.LiftReachedPickup
//                );
//                break;
//            case LiftGoStageUp:
//                if (preventRepeatFor(500)) break;
//                setMotorsToCallback(
//                        levelTracker.nextLevel(),
//                        RobotEventI.LiftReachedNextLevel
//                );
//                break;
//            case LiftGoStageDown:
//                if (preventRepeatFor(500)) break;
//                setMotorsToCallback(
//                        levelTracker.prevLevel(),
//                        RobotEventI.LiftReachedPrevLevel
//                );
//                break;
//            case DebugOctoMotorDown:
//                if (preventRepeatFor(250)) break;
//                motors.setPosition(motors.getPosition()-25);
//                break;
//            case DebugOctoMotorUp:
//                if (preventRepeatFor(250)) break;
//                motors.setPosition(motors.getPosition()+25);
//                break;
//        }
//    }

    /**
     * Set the motor to a position and notify of a callback once the motors
     * reach that position.
     * @param pos The position to set the motors to
     * @param robotEventI The event to notify after reaching the specified position
     */
    private void setMotorsToCallback(int pos, RobotEventI robotEventI) {
        motors.setPosition(pos);
        super.notifyWhen(robotEventI, () -> motors.isAt(pos));
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




















