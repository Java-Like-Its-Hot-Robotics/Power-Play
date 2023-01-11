package org.firstinspires.ftc.teamcode.api;

import com.qualcomm.robotcore.hardware.Gamepad;

public interface Binding {
    void handleKey(KeyEvent status, final Gamepad gamepad);
}
