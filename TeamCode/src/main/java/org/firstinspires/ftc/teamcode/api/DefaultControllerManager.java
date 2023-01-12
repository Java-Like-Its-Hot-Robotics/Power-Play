package org.firstinspires.ftc.teamcode.api;


import static org.firstinspires.ftc.teamcode.api.ControllerKey.*;

import com.qualcomm.robotcore.hardware.Gamepad;

public class DefaultControllerManager extends AbstractControllerManager {
    public DefaultControllerManager(Gamepad gamepad) {
        super(gamepad);
        super   .bind(RobotEvent.Debug1, X)
                .bind(RobotEvent.Debug2, Y);
    }

}
