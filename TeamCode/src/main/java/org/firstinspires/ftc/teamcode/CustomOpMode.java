package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier.Notifications;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Servo Test")
public class CustomOpMode extends LinearOpMode {

    BotConfig robot = new BotConfig();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                robot.servo.setPosition(robot.servo.getPosition() + 0.01);
            }
            else if (gamepad1.b) {
                robot.servo.setPosition(robot.servo.getPosition() - 0.01);
            }
        }
    }
}
