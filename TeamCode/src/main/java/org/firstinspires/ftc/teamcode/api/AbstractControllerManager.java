package org.firstinspires.ftc.teamcode.api;

import static org.firstinspires.ftc.teamcode.api.ControllerKey.*;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//FIXME: Refactor into a more unified event dispatcher and turn this into something that raises
//       Events that are related to keypresses, and the AbstractEventDispatcher determines what
//       It should send to its subscribers (MEDIATOR PATTERN)
public abstract class AbstractControllerManager implements RobotEventListenerI {
    private Multimap<ControllerKey, RobotEvent> bindings;
    private Gamepad gamepad;
    private RobotEventMediatorI mediatorI;
    private Thread controlDispatchThread;

    public AbstractControllerManager(Gamepad gamepad) {
        bindings = MultimapBuilder.hashKeys().arrayListValues().build();  //MultimapBuilder.ListMultimapBuilder<KeyEvent, Binding>() {
        controlDispatchThread = new Thread
                ( this::controllerLoop
                , "Controller Notification -> Mediator Loop");
        controlDispatchThread.setDaemon(true);

        this.gamepad = gamepad;
    }
    private AbstractControllerManager() {}

    public AbstractControllerManager bind(RobotEvent robotEvent, ControllerKey key) {
        bindings.put(key, robotEvent);
        return this;
    }
    private void controllerLoop() {
        try {
            Gamepad oldGamepad = new Gamepad();
            oldGamepad.copy(gamepad);
            while(!controlDispatchThread.isInterrupted()) {
                //Check if any controls have changed to prevent needless events
                List<ControllerKey> keys = compareGamepad(gamepad, oldGamepad);
                for (ControllerKey key : keys) {
                    //find all events associated with the changed keys
                    Collection<RobotEvent> boundEvents = bindings.get(key);
                    for(RobotEvent boundEvent : boundEvents) {
                        mediatorI.notify(boundEvent);
                    }
                }
                oldGamepad.copy(gamepad);
            }
        } catch (RobotCoreException e) {
            e.printStackTrace();
        }
    }

    public void handleEvent(RobotEvent robotEvent, RobotEventMediatorI mediatorI) {
        //Controller doesn't need to do anything with this on anything but the initial ping
        if (robotEvent.equals(RobotEvent.Start)) {
            this.mediatorI = mediatorI;
            controlDispatchThread.start();
        }
    }

    public int getGamepadId() {
        return gamepad.getGamepadId();
    }

    private List<ControllerKey> compareGamepad(Gamepad g1, Gamepad g2) {
        List<ControllerKey> keys = new ArrayList<>();
        if (g1.a && !g2.a) keys.add(A);
        if (g1.b && !g2.b) keys.add(B);
        if (g1.x && !g2.x) keys.add(X);
        if (g1.y && !g2.y) keys.add(Y);
        if (g1.left_bumper  && !g2.left_bumper)  keys.add(LB);
        if (g1.right_bumper && !g2.right_bumper) keys.add(RB);
        if (g1.dpad_up      && !g2.dpad_up)      keys.add(UP);
        if (g1.dpad_down    && !g2.dpad_down)    keys.add(DOWN);
        if (g1.dpad_left    && !g2.dpad_left)    keys.add(LEFT);
        if (g1.dpad_right   && !g2.dpad_right)   keys.add(RIGHT);
        if (g1.left_stick_button  && !g2.left_stick_button)  keys.add(LS_IN);
        if (g1.right_stick_button && !g2.right_stick_button) keys.add(RS_IN);
//        if (g1.b != g2.b) keys.add(B);
//        if (g1.b != g2.b) keys.add(B);
//        if (g1.b != g2.b) keys.add(B);
        return keys;
    }
}
