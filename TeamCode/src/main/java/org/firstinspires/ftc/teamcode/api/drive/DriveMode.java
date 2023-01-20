package org.firstinspires.ftc.teamcode.api.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.api.event.listener.discrete.DiscreteEventListener;

public abstract class DriveMode extends DiscreteEventListener {
    protected Gamepad gamepad;
    protected BotConfig config;

    public DriveMode(Gamepad gamepad, BotConfig config) {
        this.config = config;
        this.gamepad = gamepad;
    }
    private DriveMode() {}

    abstract void drive();

    protected Gamepad getGamepad() {
        return gamepad;
    }

    protected BotConfig getConfig() {
        return config;
    }
}
