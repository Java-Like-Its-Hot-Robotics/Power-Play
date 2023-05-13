package org.firstinspires.ftc.teamcode.api.controller;


import com.google.common.collect.Multimap;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;

import java.util.Collection;
import java.util.List;

public class DefaultControllerListener<T extends Enum<T>> extends AbstractControllerListener<T> {
    public DefaultControllerListener(Gamepad gamepad) {
        super(gamepad);
//        super   .bind(RobotEventI.ManualDrop, X)
//                .bind(RobotEventI.LiftRaiseToLow, A)
//                .bind(RobotEventI.LiftRaiseToMedium, B)
//                .bind(RobotEventI.LiftRaiseToHigh, Y);
    }

    public void eventStep() {
        try {
            Gamepad gamepad = super.getGamepad();
            Multimap<ModifierBinding, RobotEventI<T>> bindings = super.getBindings();

            //find the pressed keys
            List<ModifierBinding> keys = getPressedKeys();
            for (ModifierBinding key : keys) {
                //find all events associated with the changed keys
                Collection<RobotEventI<T>> boundEvents = bindings.get(key);
                for(RobotEventI<T> boundEvent : boundEvents) {
                    super.getMediator().notify(boundEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
