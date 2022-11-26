package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DemoTeleOp extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    BotConfig robot = new BotConfig();

    @Override
    public void runOpMode(){

        robot.init(hardwareMap);

        robot.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.left_trigger, gamepad1.right_trigger);

        telemetry.addLine("Has Cone: " + robot.hasCone());
        telemetry.update();

    }

}
