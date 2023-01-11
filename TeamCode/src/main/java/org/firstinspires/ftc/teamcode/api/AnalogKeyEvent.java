package org.firstinspires.ftc.teamcode.api;

public class AnalogKeyEvent extends KeyEvent {

    public AnalogKeyEvent(ControllerKey key) {
        super(key);
    }

    @Override
    public boolean isPressed() {
        return false;
    }

}
