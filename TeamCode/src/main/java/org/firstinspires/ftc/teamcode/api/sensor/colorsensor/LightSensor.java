package org.firstinspires.ftc.teamcode.api.sensor.colorsensor;


import androidx.annotation.NonNull;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class LightSensor<T extends Enum<T>> extends ContinuousEventListener<T> {
    private RevColorSensorV3 colorSensor;
    private Map<Predicate<NormalizedRGBA>, RobotEventI<T>> taggedColors;

    public LightSensor(RevColorSensorV3 colorSensor) {
        super("Color Sensor Thread");
        this.colorSensor = colorSensor;
        taggedColors = new HashMap<>();
    }

    @Override
    public void handleEvent(@NonNull RobotEventI<T> robotEventI) {
        //color sensors don't do anything with events
    }

    @Override
    public void eventStep() {
        //TODO: write light sensor event generation
        NormalizedRGBA sensedColor = colorSensor.getNormalizedColors();
        for(Predicate<NormalizedRGBA> colorPredicate : taggedColors.keySet()) {
            if(colorPredicate.test(sensedColor)) {
                //get the corresponding event
                super.notify(taggedColors.get(colorPredicate));
            }
        }
    }

    public LightSensor<T> addColorAndEvent(Predicate<NormalizedRGBA> color, RobotEventI robotEventI) {
        taggedColors.put(color, robotEventI);
        return this;
    }

}
