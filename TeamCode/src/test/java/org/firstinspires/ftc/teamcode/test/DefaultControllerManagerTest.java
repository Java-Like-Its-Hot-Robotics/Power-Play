package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MockBotConfig;
import org.firstinspires.ftc.teamcode.api.controller.AbstractControllerListener;
import org.firstinspires.ftc.teamcode.api.drive.DriveMode;
import org.firstinspires.ftc.teamcode.api.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.controller.DefaultControllerListener;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.EventDispatcherFactory;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;
import org.junit.Test;

public class DefaultControllerManagerTest {
    @Test
    public void testOutput() {
        Gamepad gamepad = new Gamepad();

        AbstractControllerListener controllerListener = new DefaultControllerListener(gamepad);
        DriveMode driveMode = new MecanumDrive(gamepad, new MockBotConfig());
        AbstractEventDispatcher ed = new EventDispatcherFactory()
                .global(true)
                .build();

        ed.register(controllerListener)
          .register(driveMode);

        ed.notify(RobotEvent.OpmodeStart);
        ed.updateWhileStarted();
        System.out.println("End of test program");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}