package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.hardware.rev.RevTouchSensor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import kotlin.Unit;


public class TouchSensor<T extends Enum<T>> extends ContinuousEventListener<T> {
    private final RevTouchSensor touchSensor;
    private boolean isHeld = false;

    public TouchSensor(RevTouchSensor touchSensor) {
        super("Touch Sensor Thread");
        this.touchSensor = touchSensor;
        addEventHandler((RobotEventI<T> event) -> {
            if (event instanceof RobotEvent) {
                switch((RobotEvent) event) {
                    case LiftReachedPickup:
                        isHeld = false;
                        break;
                    default:
                        break;
                }
            }
            return Unit.INSTANCE;
        });
    }

//    @Override
//    public void handleEvent(RobotEventI<T> robotEventI) {
//        switch(robotEventI) {
//            case LiftReachedPickup:
//                isHeld = false;
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void eventStep() {
        //We don't want to keep sending the status when it is held
        //TODO: can this be generalized so that its easier to make these
        //      latching classes?
        if(!isHeld && touchSensor.isPressed()) {
            //TODO: generalize so that the event raised is generalized to the instance
            //      rather than the whole class
            super.notify((RobotEventI<T>) RobotEvent.OctoTouchSensorPressed);
            isHeld = true;
        }
    }

    public boolean isHeld() {
        return isHeld;
    }
}
