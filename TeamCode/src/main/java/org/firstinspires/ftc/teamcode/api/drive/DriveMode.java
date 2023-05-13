package org.firstinspires.ftc.teamcode.api.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public abstract class DriveMode<T extends Enum<T>> extends ContinuousEventListener<T> {
    protected Gamepad gamepad;
    protected BotConfig config;

    public DriveMode(Gamepad gamepad, BotConfig config) {
        super("Drive Mode");
        this.config = config;
        this.gamepad = gamepad;
    }
    private DriveMode() {
        super("Drive Mode");
    }


    abstract void drive();

    protected Gamepad getGamepad() {
        return gamepad;
    }

    protected BotConfig getConfig() {
        return config;
    }
}
