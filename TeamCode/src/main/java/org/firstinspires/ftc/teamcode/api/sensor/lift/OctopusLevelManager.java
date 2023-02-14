package org.firstinspires.ftc.teamcode.api.sensor.lift;

import static org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusLevelManager.Levels.*;

import org.firstinspires.ftc.teamcode.api.sensor.ILevelTracker;

public class OctopusLevelManager implements ILevelTracker {
    public enum Levels {
        PICKUP_POS (0),
        CARRY_POS  (150),
        LOW_HEIGHT (375),
        MED_HEIGHT (595),
        HIGH_HEIGHT(850);

        private final int height;
        Levels(int i) {
            height = i;
        }

        public int getValue() {
            return height;
        }
    }

    //this field should be static so it can remain known between
    //opmodes
    private static Levels currentLevel = PICKUP_POS;


    @Override
    public int currentLevel() {
        return currentLevel.getValue();
    }

    @Override
    public int nextLevel() {
        switch (currentLevel) {
            case PICKUP_POS:
                currentLevel = CARRY_POS;
                return CARRY_POS.getValue();
            case CARRY_POS:
                currentLevel = LOW_HEIGHT;
                return LOW_HEIGHT.getValue();
            case LOW_HEIGHT:
                currentLevel = MED_HEIGHT;
                return MED_HEIGHT.getValue();
            case MED_HEIGHT:
            case HIGH_HEIGHT:
                currentLevel = HIGH_HEIGHT;
                return HIGH_HEIGHT.getValue();
        }
        return 0;
    }

    @Override
    public int prevLevel() {
        switch (currentLevel) {
            case HIGH_HEIGHT:
                currentLevel = MED_HEIGHT;
                return MED_HEIGHT.getValue();
            case MED_HEIGHT:
                currentLevel = LOW_HEIGHT;
                return LOW_HEIGHT.getValue();
            case LOW_HEIGHT:
                currentLevel = CARRY_POS;
                return CARRY_POS.getValue();
            case CARRY_POS:
            case PICKUP_POS:
                currentLevel = PICKUP_POS;
                return PICKUP_POS.getValue();
        }
        return 0;
    }
}
