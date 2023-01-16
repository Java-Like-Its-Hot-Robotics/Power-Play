package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.api.controller.AbstractControllerListener;
import org.firstinspires.ftc.teamcode.api.controller.DefaultControllerListener;
import org.firstinspires.ftc.teamcode.api.drive.DriveMode;
import org.firstinspires.ftc.teamcode.api.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.EventDispatcherFactory;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusMotor;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusServo;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;

@TeleOp(name = "Main TeleOp")
public class DemoTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    BotConfig robot = new DefaultBotConfig();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //CONTROLLER
        AbstractControllerListener controllerListener = new DefaultControllerListener(gamepad1);
        //DRIVE MODE
        DriveMode driveMode = new MecanumDrive(gamepad1, new MockBotConfig());
        //SENSORS
        TouchSensor touchSensor = new TouchSensor(robot.octoTouchSensor);
        //Lift Management
        ////Octopus Servo
        OctopusServo octopusServo = new OctopusServo(robot.octopusServo);
        ////Octopus Motor
        OctopusMotor octopusMotor = new OctopusMotor(robot.octopusMotor);

        //DISPATCHER
        AbstractEventDispatcher ed = new EventDispatcherFactory()
                .global(true)
                .build()
                .register(controllerListener)
                .register(driveMode)
                .register(touchSensor)
                .register(octopusServo)
                .register(octopusMotor);

        ed.notify(RobotEvent.OpmodeInit);

        waitForStart();

        while(opModeIsActive()) {
            ed.notify(RobotEvent.OpmodeStart);
        }

    }



}
