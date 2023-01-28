package org.firstinspires.ftc.teamcode.api.controller;


import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

import java.util.Collection;
import java.util.List;

public class DefaultControllerListener extends AbstractControllerListener {
    public DefaultControllerListener(Gamepad gamepad) {
        super(gamepad);
//        super   .bind(RobotEvent.ManualDrop, X)
//                .bind(RobotEvent.LiftRaiseToLow, A)
//                .bind(RobotEvent.LiftRaiseToMedium, B)
//                .bind(RobotEvent.LiftRaiseToHigh, Y);
    }

    public void eventStep() {
        try {
            Gamepad gamepad = super.getGamepad();
            Multimap<ModifierBinding, RobotEvent> bindings = super.getBindings();

            //find the pressed keys
            List<ModifierBinding> keys = getPressedKeys();
            for (ModifierBinding key : keys) {
                //find all events associated with the changed keys
                Collection<RobotEvent> boundEvents = bindings.get(key);
                for(RobotEvent boundEvent : boundEvents) {
                    super.getMediator().notify(boundEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
