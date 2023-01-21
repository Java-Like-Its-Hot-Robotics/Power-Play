package org.firstinspires.ftc.teamcode.api.sensor;

import com.qualcomm.hardware.rev.RevColorSensorV3;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

@SuppressWarnings("FieldCanBeLocal")
public class LightSensor extends ContinuousEventListener {
    private RevColorSensorV3 colorSensor;

    public LightSensor(RevColorSensorV3 colorSensor) {
        super("Color Sensor Thread");
        this.colorSensor = colorSensor;
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        //color sensors don't do anything with events
    }

    @Override
    public void eventStep() {
        //TODO: write light sensor event generation
    }
}
