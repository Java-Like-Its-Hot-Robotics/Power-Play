package org.firstinspires.ftc.teamcode.api;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BotConfig;

public abstract class DriveMode {
    Gamepad gamepad;
    BotConfig config;

    public DriveMode(Gamepad gamepad, BotConfig config) {
        this.config = config;
        this.gamepad = gamepad;
    }
    private DriveMode() {}

    abstract void drive();
}
