package org.firstinspires.ftc.teamcode.test;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.api.AbstractControllerManager;
import org.firstinspires.ftc.teamcode.api.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.AbstractEventListenerManager;
import org.firstinspires.ftc.teamcode.api.DefaultControllerManager;
import org.firstinspires.ftc.teamcode.api.IRobotEventListener;
import org.firstinspires.ftc.teamcode.api.RobotEvent;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DefaultControllerManagerTest {
    @Test
    public void testOutput() {
        Gamepad gamepad = new Gamepad();
        AbstractControllerManager cm = new DefaultControllerManager(gamepad);
        Multimap<RobotEvent, IRobotEventListener> multimap = MultimapBuilder.hashKeys().arrayListValues().build();
        BlockingQueue<RobotEvent> queue = new ArrayBlockingQueue<RobotEvent>(10);
        AbstractEventListenerManager elm = new AbstractEventListenerManager(multimap){};
        AbstractEventDispatcher ed = new AbstractEventDispatcher(elm, queue) {
            @Override
            public void init() {
                notify(RobotEvent.Init);
            }

            @Override
            public void start() {
                notify(RobotEvent.Start);
            }

            @Override
            public void stop() {
                notify(RobotEvent.Stop);
            }
        };
        ed.register(cm, RobotEvent.Start);
        ed.notify(RobotEvent.Start);
        System.out.println("dwdadwa");
    }
}