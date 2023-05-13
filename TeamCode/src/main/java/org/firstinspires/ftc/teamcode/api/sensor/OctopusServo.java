package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.*;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

import kotlin.Unit;

public class OctopusServo<T extends Enum<T>> extends DiscreteEventListener<T> {
    private final Servo servo;
    private final double DROP;
    private final double HOLD;
//    private static final double TOLERANCE = 0.02;
    private static final double TIMEOUT = 500; //milliseconds
    private boolean isExpanded;
    private static double pressTime = System.currentTimeMillis();

    public OctopusServo(Servo servo) {
        this.servo = servo;
        //TODO: make this a static value (with debugger)
        DROP = 0.9;//servo.getPosition();
        HOLD = 0.35; //0.4
        isExpanded = false;
        addEventHandler((RobotEventI<T> event) -> {
            if(event instanceof RobotEvent) {
                switch((RobotEvent) event) {
                    case LiftReachedPickup:
                    case ManualPickup:
                        servo.setPosition(HOLD);
                        isExpanded = true;

                        super.notify((RobotEventI<T>) OctoServoExpanded);
                        break;
                    case ManualDrop:
                        servo.setPosition(DROP);
                        isExpanded = false;

                        super.notify((RobotEventI<T>) OctoServoCompressed);
                        break;
                    case ManualOctoServoToggle:
                        if(preventRepeatFor(TIMEOUT)) break;
                        if (isExpanded) {
                            super.notify((RobotEventI<T>) ManualDrop);
                        }
                        else {
                            super.notify((RobotEventI<T>) ManualPickup);
                        }
                        break;
                    case OctoTouchSensorPressed:
                        //the touch sensor event has fired
                        //pickup what we are holding
                        super.notify((RobotEventI<T>) ManualPickup);
                        //go up to pickup
                        super.notify((RobotEventI<T>) LiftGoStageUp);

                    default: break;
                }
            }
            return Unit.INSTANCE;
        } );

    }

//    @Override public void handleEvent(RobotEventI robotEventI) {
//        switch(robotEventI) {
//            case LiftReachedPickup:
//            case ManualPickup:
//                servo.setPosition(HOLD);
//                isExpanded = true;
//
//                super.notify((RobotEventI<T>) OctoServoExpanded);
//                break;
//            case ManualDrop:
//                servo.setPosition(DROP);
//                isExpanded = false;
//
//                super.notify((RobotEventI<T>) OctoServoCompressed);
//                break;
//            case ManualOctoServoToggle:
//                if(preventRepeatFor(TIMEOUT)) break;
//                if (isExpanded) {
//                    super.notify((RobotEventI<T>) ManualDrop);
//                }
//                else {
//                    super.notify((RobotEventI<T>) ManualPickup);
//                }
//                break;
//            case OctoTouchSensorPressed:
//                //the touch sensor event has fired
//                //pickup what we are holding
//                super.notify(ManualPickup);
//                //go up to pickup
//                super.notify(LiftGoStageUp);
//
//            default: break;
//        }
//    }

}
