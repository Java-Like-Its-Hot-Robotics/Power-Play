package org.firstinspires.ftc.teamcode.api.sensor.colorsensor;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class LightSensor extends ContinuousEventListener {
    private RevColorSensorV3 colorSensor;
    private Map<NormalizedRGBA, RobotEvent> taggedColors;
    private NormalizedRGBA tolerances = new NormalizedRGBA();

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
        NormalizedRGBA sensedColors = colorSensor.getNormalizedColors();

    }

//    private boolean checkColorEqual(NormalizedRGBA c1, NormalizedRGBA c2) {
//        if (c1.red < c2.red + tolerances.red)
//    }

    public void addColorAndEvent() {}
}
