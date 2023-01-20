package org.firstinspires.ftc.teamcode.api.controller;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
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
    private Multimap<ControllerKey, RobotEvent> bindings;
    private Gamepad gamepad;

    public AbstractControllerListener(Gamepad gamepad) {
        super("Controller Manager Thread");
        bindings = MultimapBuilder.hashKeys().arrayListValues().build();  //MultimapBuilder.ListMultimapBuilder<KeyEvent, Binding>() {
        this.gamepad = gamepad;

//        super.startDispatch();
    }
    private AbstractControllerListener() {
        super("Controller Manager Thread");
    }

    public AbstractControllerListener bind(RobotEvent robotEvent, ControllerKey key) {
        bindings.put(key, robotEvent);
        return this;
    }

//    public void eventStep() {
//        //Update the drive so those inputs can take effect
//        super.getMediator().notify(RobotEvent.DriveUpdate);
//        try {
//            Gamepad oldGamepad = new Gamepad();
//            oldGamepad.copy(gamepad);
//            //Check if any controls have changed to prevent needless events
//            List<ControllerKey> keys = compareGamepad(gamepad, oldGamepad);
//            for (ControllerKey key : keys) {
//                //find all events associated with the changed keys
//                Collection<RobotEvent> boundEvents = bindings.get(key);
//                for(RobotEvent boundEvent : boundEvents) {
//                    super.getMediator().notify(boundEvent);
//                }
//                oldGamepad.copy(gamepad);
//            }
//        } catch (RobotCoreException e) {
//            e.printStackTrace();
//        }
//    }

    public void handleEvent(RobotEvent robotEvent) {
        //Controller doesn't need to do anything with this
    }

    protected Gamepad getGamepad() {
        return gamepad;
    }
    protected Multimap<ControllerKey, RobotEvent> getBindings() {
        return bindings;
    }

    public int getGamepadId() {
        return gamepad.getGamepadId();
    }

    protected List<ControllerKey> compareGamepad(Gamepad g1) {
        List<ControllerKey> keys = new ArrayList<>();
        if (g1.a) keys.add(A);
        if (g1.b) keys.add(B);
        if (g1.x) keys.add(X);
        if (g1.y) keys.add(Y);
        if (g1.left_bumper)  keys.add(LB);
        if (g1.right_bumper) keys.add(RB);
        if (g1.dpad_up)      keys.add(UP);
        if (g1.dpad_down)    keys.add(DOWN);
        if (g1.dpad_left)    keys.add(LEFT);
        if (g1.dpad_right)   keys.add(RIGHT);
        if (g1.left_stick_button)  keys.add(LS_IN);
        if (g1.right_stick_button) keys.add(RS_IN);
//        keys.add()
        return keys;
    }
}
