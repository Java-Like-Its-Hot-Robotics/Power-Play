package org.firstinspires.ftc.teamcode.api.controller;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//FIXME: Refactor into a more unified event dispatcher and turn this into something that raises
//       Events that are related to keypresses, and the AbstractEventDispatcher determines what
//       It should send to its subscribers (MEDIATOR PATTERN)
public abstract class AbstractControllerListener extends ContinuousEventListener {
    private Multimap<ModifierBinding, RobotEvent> bindings;
    private Gamepad gamepad;

    public AbstractControllerListener(Gamepad gamepad) {
        super("Controller Manager");
        bindings = MultimapBuilder.hashKeys().arrayListValues().build();  //MultimapBuilder.ListMultimapBuilder<KeyEvent, Binding>() {
        this.gamepad = gamepad;
    }
    private AbstractControllerListener() {
        super("Controller Manager Thread");
    }

    public AbstractControllerListener bind(RobotEvent robotEvent, ControllerKey key) {
        ModifierBinding modifierKey = new ModifierBinding(key);
        bindings.put(modifierKey, robotEvent);
        return this;
    }
    public AbstractControllerListener bind(RobotEvent robotEvent, ModifierBinding key) {
        bindings.put(key, robotEvent);
        return this;
    }

    public void handleEvent(RobotEvent robotEvent) {
        //Controller doesn't need to do anything with this
    }

    protected Gamepad getGamepad() {
        return gamepad;
    }
    protected Multimap<ModifierBinding, RobotEvent> getBindings() {
        return bindings;
    }

    protected List<ModifierBinding> getPressedKeys() {
        Multiset<ModifierBinding> allKeys = bindings.keys();
        List<ModifierBinding> pressedList = new ArrayList<>();
        //collect all pressed keys
        for(ModifierBinding binding : allKeys) {
            if (binding.isPressed(gamepad)) {
                pressedList.add(binding);
            }
        }
        return pressedList;
    }

    public int getGamepadId() {
        return gamepad.getGamepadId();
    }
}
