package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.api.controller.AbstractControllerListener;
import org.firstinspires.ftc.teamcode.api.controller.DefaultControllerListener;
import org.firstinspires.ftc.teamcode.api.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.EventDispatcherFactory;
import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;
import org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusLevelManager;
import org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusMotor;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusServo;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;
import org.firstinspires.ftc.teamcode.api.sensor.lift.pid.DualMotorEx;
import org.firstinspires.ftc.teamcode.api.sensor.lift.pid.MotorPID;
import org.firstinspires.ftc.teamcode.api.sensor.lift.pid.SoloMotorEx;

@TeleOp(name = "Main TeleOp")
public class DemoTeleOp extends LinearOpMode {

//    private final ElapsedTime runtime = new ElapsedTime();

    BotConfig robot = new DefaultBotConfig();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //CONTROLLER
        AbstractControllerListener controllerListener = new DefaultControllerListener(gamepad1)
//                .bind(RobotEvent.LiftRaiseToLow,        A)
//                .bind(RobotEvent.LiftRaiseToMedium,     B)
//                .bind(RobotEvent.LiftRaiseToHigh,       Y)
                .bind(RobotEvent.LiftGoStageUp, A)
                .bind(RobotEvent.LiftGoStageDown, B)
//                .bind(RobotEvent.LiftRaiseToPickup, DOWN)
                .bind(RobotEvent.ManualOctoServoToggle, X)
                //DEBUG
                .bind(RobotEvent.DebugOctoMotorDown, LB)
                .bind(RobotEvent.DebugOctoMotorUp, RB);
        //DRIVE MODE
        MecanumDrive driveMode = new MecanumDrive(gamepad1, robot);
        //SENSORS
        TouchSensor touchSensor = new TouchSensor(robot.octoTouchSensor);
        //Lift Management
        ////Octopus Servo
        OctopusServo octopusServo = new OctopusServo(robot.octopusServo);
        ////Octopus Motor
        OctopusMotor octopusMotor = new OctopusMotor(robot.octopusMotor, robot.octopusMotor2);


        //DISPATCHER
        AbstractEventDispatcher ed = new EventDispatcherFactory()
                .global(true) //this is necessary, as the non-global version is buggy/annoying
                .build()
                .register(controllerListener)
                .register(driveMode)
                .register(touchSensor)
                .register(octopusServo)
                .register(octopusMotor);

        ed.init();

        waitForStart();

        ed.start();

        while(opModeIsActive()) {
            //these must be run every frame
            driveMode.drive();
            ed.updateWhileStarted();

            telemetry.addData("Encoder Pos",String.valueOf(robot.octopusMotor.getCurrentPosition()));
            telemetry.addData("PID SP M1", robot.octopusMotor.getTargetPosition());
            telemetry.addData("PID SP M2", robot.octopusMotor2.getTargetPosition());
            telemetry.addData("PID MV M1", robot.octopusMotor.getCurrentPosition());
            telemetry.addData("PID MV M2", robot.octopusMotor2.getCurrentPosition());
            telemetry.addData("PID Vel Coefs", robot.octopusMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
            telemetry.addData("PID Pos Coefs", robot.octopusMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
            telemetry.addData("PID Vel M1", robot.octopusMotor.getVelocity());
            telemetry.addData("PID Vel M2", robot.octopusMotor2.getVelocity());

            //PID TESTING
            telemetry.addData("Touch Sensor Held", touchSensor.isHeld());
            telemetry.update();

            if (touchSensor.isHeld()) {
                gamepad1.rumble(50);
            }


        }
    }



}
