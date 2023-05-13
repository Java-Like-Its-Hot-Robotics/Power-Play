package org.firstinspires.ftc.teamcode.api.sensor.camera;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


/**Effectively a Proxy class for RecognitionManager, but only holds a single object.
 * This means you should only use this if you plan to detect a single object.
 **/
public class SingleObjectDetectionManager implements IObservable {
    //maps the labels to an object that tracks its confidence
    //over multiple recognitions
    RecognitionManager observations;
    Recognition latest;

    public SingleObjectDetectionManager(RecognitionManager observations) {
        this.observations = observations;
    }

    @Override
    public Recognition getLatestObservation() {
        return latest;
    }

    @Override
    public String getConsensus() {
        return observations.getConsensus();
    }

    @Override
    public void giveRecognition(Recognition recognition) {
        observations.giveRecognition(recognition);
    }
}
