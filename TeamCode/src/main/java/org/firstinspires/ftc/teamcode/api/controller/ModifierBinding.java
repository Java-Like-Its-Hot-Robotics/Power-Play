package org.firstinspires.ftc.teamcode.api.controller;


import com.qualcomm.robotcore.hardware.Gamepad;

public class ModifierBinding {
    private ControllerKey key;
    private ControllerKey modifier;

    private ModifierBinding() {}

    public ModifierBinding(ControllerKey key, ControllerKey modifier) {
        if(key.equals(modifier)) throw new IllegalArgumentException();
        this.key = key;
        this.modifier = modifier;
    }

    public ModifierBinding(ControllerKey key) {
        this.key = key;
    }

    public boolean isPressed(Gamepad gamepad) {
        if(modifier == null) {
            return key.isPressed(gamepad);
        } else {
            return key.isPressed(gamepad) && modifier.isPressed(gamepad);
        }
    }

}
