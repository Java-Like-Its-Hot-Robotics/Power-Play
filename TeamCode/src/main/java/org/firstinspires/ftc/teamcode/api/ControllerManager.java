package org.firstinspires.ftc.teamcode.api;

import com.google.common.collect.Multimap;
import com.qualcomm.robotcore.hardware.Gamepad;
import static org.firstinspires.ftc.teamcode.api.ControllerKey.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class ControllerManager {
    private Multimap<KeyEvent, Binding> bindings;
    private Gamepad gamepad;

    public ControllerManager bind(Binding binding, KeyEvent key) {
        bindings.put(key, binding);
        return this;
    }
    public void unbind(Binding binding, KeyEvent key) {
        bindings.remove(key, binding);
    }
    public void check() {
        Iterator<KeyEvent> keyEventIterator = bindings.keys().iterator();

        //For each key event, call all of its bound handlers with the key event in question and
        //the gamepad state
        while(keyEventIterator.hasNext()) {
            KeyEvent nextKeyEvent = keyEventIterator.next();
            Iterator<Binding> bindingIterator = bindings.get(nextKeyEvent).iterator();
            while(bindingIterator.hasNext()) {
                Binding nextBinding = bindingIterator.next();
                nextBinding.handleKey(nextKeyEvent, gamepad);
            }

        }
    }

    public int getGamepadId() {
        return gamepad.getGamepadId();
    }


//    private List<KeyEvent> gamepadToEvents() {
//        List<KeyEvent> events = new ArrayList<>();
//        events.add(new AnalogKeyEvent(LS));
//        events.add(new AnalogKeyEvent(RS));
//        events.add(new DigitalKeyEvent(A));
//        events.add(new DigitalKeyEvent(B));
//        events.add(new DigitalKeyEvent(X));
//        events.add(new DigitalKeyEvent(Y));
//        events.add(new DigitalKeyEvent(RS));
//        events.add(new DigitalKeyEvent(LS));
//        events.add(new DigitalKeyEvent(LS_IN));
//        events.add(new DigitalKeyEvent(RS_IN, gamepad.right_stick_button));
//        events.add(new DigitalKeyEvent(DOWN,  gamepad.dpad_down));
//        events.add(new DigitalKeyEvent(LEFT,  gamepad.dpad_left));
//        events.add(new DigitalKeyEvent(UP,    gamepad.dpad_up));
//        events.add(new DigitalKeyEvent(RIGHT, gamepad.dpad_right));
//        return events;
//    }
}
