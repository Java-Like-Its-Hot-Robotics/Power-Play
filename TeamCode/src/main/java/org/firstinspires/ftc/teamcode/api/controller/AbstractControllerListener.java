package org.firstinspires.ftc.teamcode.api.controller;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import java.util.ArrayList;
import java.util.List;

//FIXME: Refactor into a more unified event dispatcher and turn this into something that raises
//       Events that are related to keypresses, and the AbstractEventDispatcher determines what
//       It should send to its subscribers (MEDIATOR PATTERN)
public abstract class AbstractControllerListener<T extends Enum<T>> extends ContinuousEventListener<T> {
    private Multimap<ModifierBinding, RobotEventI<T>> bindings;
    private Gamepad gamepad;

    public AbstractControllerListener(Gamepad gamepad) {
        super("Controller Manager");
        bindings = MultimapBuilder.hashKeys().arrayListValues().build();  //MultimapBuilder.ListMultimapBuilder<KeyEvent, Binding>() {
        this.gamepad = gamepad;
    }
    private AbstractControllerListener() {
        super("Controller Manager Thread");
    }

    public AbstractControllerListener<T> bind(RobotEventI<T> robotEventI, ControllerKey key) {
        ModifierBinding modifierKey = new ModifierBinding(key);
        bindings.put(modifierKey, robotEventI);
        return this;
    }
    public AbstractControllerListener<T> bind(RobotEventI<T> robotEventI, ModifierBinding key) {
        bindings.put(key, robotEventI);
        return this;
    }

    public void handleEvent(RobotEventI<T> robotEventI) {
        //Controller doesn't need to do anything with this
        getEventHandler().apply(robotEventI);
    }

    protected Gamepad getGamepad() {
        return gamepad;
    }
    protected Multimap<ModifierBinding, RobotEventI<T>> getBindings() {
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
