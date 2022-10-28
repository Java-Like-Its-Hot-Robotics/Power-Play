package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DemoTeleOp", group="Linear Opmode")
public class DemoTeleOp extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    BotConfig robot = new BotConfig();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {



            robot.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.left_trigger, gamepad1.right_trigger);
        }
    }

}
