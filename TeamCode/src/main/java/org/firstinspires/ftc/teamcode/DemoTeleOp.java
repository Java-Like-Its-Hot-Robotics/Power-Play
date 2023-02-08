package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;

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
import org.firstinspires.ftc.teamcode.api.sensor.OctopusDualMotor;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusMotor;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusServo;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;

@TeleOp(name = "Main TeleOp")
public class DemoTeleOp extends LinearOpMode {

//    private final ElapsedTime runtime = new ElapsedTime();

    BotConfig robot = new DefaultBotConfig();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        //CONTROLLER
        AbstractControllerListener controllerListener = new DefaultControllerListener(gamepad1)
                .bind(RobotEvent.LiftRaiseToLow,        A)
                .bind(RobotEvent.LiftRaiseToMedium,     B)
                .bind(RobotEvent.LiftRaiseToHigh,       Y)
                .bind(RobotEvent.LiftRaiseToPickup, DOWN)
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
        OctopusDualMotor octopusMotor = new OctopusDualMotor(robot.octopusMotor, robot.octopusMotor2);

        //DISPATCHER
        AbstractEventDispatcher ed = new EventDispatcherFactory()
                .global(true) //this is necessary, as the non-global version is buggy/annoying
                .build()
                .register(controllerListener)
                .register(driveMode)
//                .register(touchSensor)
                .register(octopusServo)
                .register(octopusMotor);

        ed.init();

        waitForStart();

        ed.start();
        final int CARRY_POS = 150;
        final int PICKUP_POS = -10;
        final int LOW_HEIGHT = 250;
        final int MED_HEIGHT = 500;
        final int HIGH_HEIGHT = 750;
        final double openPos = robot.octopusServo.getPosition();
        final double closedPos = openPos + 0.3;

        boolean isExpanded = false;

        while(opModeIsActive()) {
            driveMode.drive();
            ed.updateWhileStarted();
            telemetry.addData("Encoder Pos",String.valueOf(robot.octopusMotor.getCurrentPosition()));
            telemetry.update();

//            if(gamepad1.a) {
//                robot.octopusMotor.setTargetPosition(LOW_HEIGHT);
//            }
//            else if (gamepad1.b) {
//                robot.octopusMotor.setTargetPosition(MED_HEIGHT);
//            }
//            else if (gamepad1.y) {
//                robot.octopusMotor.setTargetPosition(HIGH_HEIGHT);
//            }
//            else if (gamepad1.dpad_down) {
//                robot.octopusMotor.setTargetPosition(PICKUP_POS);
//            }
//            else if (gamepad1.x) {
//                if (isExpanded) {
//                    robot.octopusServo.setPosition(closedPos);
//                    telemetry.addData(String.valueOf(robot.octopusServo.getPosition()), "");
//                    isExpanded = false;
//                }
//                else {
//                    robot.octopusServo.setPosition(openPos);
//                    telemetry.addData(String.valueOf(robot.octopusServo.getPosition()), "");
//                    isExpanded = true;
//                }
//                telemetry.update();
//            }
        }

        //end of opmode; loop exited
//        ed.kill();
//        ed.stop();

    }



}
