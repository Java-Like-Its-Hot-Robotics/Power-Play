package org.firstinspires.ftc.teamcode.api.controller;

import com.qualcomm.robotcore.hardware.Gamepad;

public enum ControllerKey {
    A, B, X, Y, LB, RB, LT, RT, UP, DOWN, LEFT, RIGHT, LS, RS, LS_IN, RS_IN;

    public boolean isPressed(Gamepad gamepad) {
        switch(this) {
            case A:
                return gamepad.a;
            case B:
                return gamepad.b;
            case X:
                return gamepad.x;
            case Y:
                return gamepad.y;
            case LB:
                return gamepad.left_bumper;
            case RB:
                return gamepad.right_bumper;
            case LT:
                return gamepad.left_trigger > 0;
            case RT:
                return gamepad.right_trigger > 0;
            case UP:
                return gamepad.dpad_up;
            case DOWN:
                return gamepad.dpad_down;
            case LEFT:
                return gamepad.dpad_left;
            case RIGHT:
                return gamepad.dpad_right;
            case LS:
            case RS:
                return gamepad.atRest();
            case LS_IN:
                return gamepad.left_stick_button;
            case RS_IN:
                return gamepad.right_stick_button;
        }
        return false;
    }
}
//TODO: KeyStatus
