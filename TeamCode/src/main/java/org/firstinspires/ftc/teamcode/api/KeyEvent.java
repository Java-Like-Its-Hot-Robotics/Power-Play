package org.firstinspires.ftc.teamcode.api;


import java.util.ResourceBundle;

public abstract class KeyEvent {
    private ControllerKey key;
    private boolean pressed;

    public KeyEvent(ControllerKey key) {
        this.key = key;
        this.pressed = false;
    }

    public KeyEvent(ControllerKey key, boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }

    private KeyEvent() {}

    public boolean isPressed() {
        return pressed;
    }

}