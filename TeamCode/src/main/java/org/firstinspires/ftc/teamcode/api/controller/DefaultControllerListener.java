package org.firstinspires.ftc.teamcode.api.controller;


import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

import com.google.common.collect.Multimap;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

import java.util.Collection;
import java.util.List;

public class DefaultControllerListener extends AbstractControllerListener {
    public DefaultControllerListener(Gamepad gamepad) {
        super(gamepad);
        super   .bind(RobotEvent.ManualDrop, X)
                .bind(RobotEvent.LiftRaiseToLow, A)
                .bind(RobotEvent.LiftRaiseToMedium, B)
                .bind(RobotEvent.LiftRaiseToHigh, Y);
    }

    public void eventStep() {
        try {
            Gamepad oldGamepad = new Gamepad();
            Gamepad gamepad = super.getGamepad();
            Multimap<ControllerKey, RobotEvent> bindings = super.getBindings();

            oldGamepad.copy(gamepad);
            //Check if any controls have changed to prevent needless events
            List<ControllerKey> keys = compareGamepad(gamepad, oldGamepad);
            for (ControllerKey key : keys) {
                //find all events associated with the changed keys
                Collection<RobotEvent> boundEvents = bindings.get(key);
                for(RobotEvent boundEvent : boundEvents) {
                    super.getMediator().notify(boundEvent);
                }
                oldGamepad.copy(gamepad);
            }
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }
    }

}
