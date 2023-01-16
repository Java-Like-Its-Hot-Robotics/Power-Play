package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier.Notifications;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Motor Test")
public class CustomOpMode extends LinearOpMode {

    BotConfig robot = new TestingBotConfig();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                robot.octopusMotor.setPower(0.1);
            }
            else if (gamepad1.b) {
                robot.octopusMotor.setPower(-0.1);
            }
            else {
                robot.octopusMotor.setPower(0);
            }
        }
    }
}
