package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.hardware.rev.RevTouchSensor;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;


public class TouchSensor extends ContinuousEventListener {
    private RevTouchSensor touchSensor;
    private boolean isHeld = false;

    public TouchSensor(RevTouchSensor touchSensor) {
        super("Touch Sensor Thread");
        this.touchSensor = touchSensor;
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {}

    @Override
    public void eventStep() {
        //We don't want to keep sending the status when it is held
        //TODO: can this be generalized so that its easier to make these
        //      latching classes?
//        if(touchSensor.isPressed() || touchSensor.getValue() == 1) {
//
//            super.notify(RobotEvent.ManualPickup);
//        }
//        if(!isHeld && touchSensor.isPressed()) {
//            //TODO: generalize so that the event raised is generalized to the instance
//            //      rather than the whole class
//            super.notify(RobotEvent.OctoTouchSensorPressed);
//            isHeld = true;
//        } else if (isHeld && !touchSensor.isPressed()){
//            //reset the held status
//            isHeld = false;
//        }
    }
}
