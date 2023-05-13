package org.firstinspires.ftc.teamcode.api.sensor.camera;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public interface IObservable {
    Recognition getLatestObservation();
    String getConsensus();
    void giveRecognition(Recognition recognition);
}
