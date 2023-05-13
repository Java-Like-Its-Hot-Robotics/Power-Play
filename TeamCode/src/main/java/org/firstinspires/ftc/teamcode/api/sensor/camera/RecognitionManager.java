package org.firstinspires.ftc.teamcode.api.sensor.camera;


import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.HashMap;
import java.util.Map;

public class RecognitionManager implements IObservable {
    Map<String, ConfidenceProportion> stats;

    public RecognitionManager() {
        stats = new HashMap<>();
    }

    @Override
    public Recognition getLatestObservation() {
        return null;
    }

    @Override
    public String getConsensus() {
        ConfidenceProportion highestConf = new ConfidenceProportion();
        String highestLabel = "";
        for(String key : stats.keySet()) {
            if(stats.get(key).getConf() > highestConf.getConf()) {
                highestLabel = key;
            }
        }
        return highestLabel;
    }

    @Override
    public void giveRecognition(Recognition recognition) {
        String label = recognition.getLabel();
        float confidence = recognition.getConfidence();

        if (!stats.containsKey(label)) {
            stats.put(label, new ConfidenceProportion());
        } else { //already present
            ConfidenceProportion oldConfidence = stats.get(label);
            oldConfidence.addOccurrence(recognition);
            updateAllProps();
//            stats.put(label,
//                    (oldConfidence + confidence) / 2
//            );
        }
    }

    private void updateAllProps() {
        for (ConfidenceProportion proportion : stats.values()) {
            proportion.updateConf();
        }
    }
}




















