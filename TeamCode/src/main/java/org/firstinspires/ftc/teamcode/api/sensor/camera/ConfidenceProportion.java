package org.firstinspires.ftc.teamcode.api.sensor.camera;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class ConfidenceProportion {
    private float conf;
    private int occurrences;
    private static int totalOccurrences;

    public ConfidenceProportion() {
        conf = 0;
        occurrences = 0;
    }

//    public ConfidenceProportion(float conf) {
//        this.conf = conf;
//        occurrences = 1;
//    }

    public void addOccurrence(Recognition recognition) {
        if(recognition == null)
            return;
        occurrences++;
        totalOccurrences++;
        int locTotal = totalOccurrences == 0 ? 1 : totalOccurrences;
        float avgNum = conf == 0 ? 1 : 2; //used for the first time we calculate conf
        conf = ((conf + recognition.getConfidence())/avgNum) * occurrences/locTotal;
    }

    public void updateConf() {
        conf = conf * occurrences/totalOccurrences;
    }

    public float getConf() {
        return conf;
    }
}
