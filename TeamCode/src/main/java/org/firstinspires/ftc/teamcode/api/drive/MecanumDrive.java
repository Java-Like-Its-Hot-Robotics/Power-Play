package org.firstinspires.ftc.teamcode.api.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

public class MecanumDrive extends DriveMode {
    @SuppressWarnings("FieldCanBeLocal")
    private final double TOLERANCE = 0.1;
    private static double dampening = 0.45;

    public MecanumDrive(Gamepad gamepad, BotConfig botConfig) {
        super(gamepad, botConfig);
    }
    @Override
    public void drive() {
        Gamepad gamepad = super.getGamepad();
        BotConfig robot = super.getConfig();
        double y = -gamepad.left_stick_y *0.9; // Remember, this is reversed!
        double x = gamepad.left_stick_x;
        double rotate = gamepad.right_trigger - gamepad.left_trigger;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double frontLeftPower  = (y + x + rotate) * dampening;
        double backLeftPower   = (y - x + rotate) * dampening;
        double frontRightPower = (y - x - rotate) * dampening;
        double backRightPower  = (y + x - rotate) * dampening;

        robot.leftFront.setPower(frontLeftPower);
        robot.leftBack.setPower(backLeftPower);
        robot.rightFront.setPower(frontRightPower);
        robot.rightBack.setPower(backRightPower);
    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch (robotEvent) {
            //When the opmode is in the start loop, it should start checking the controller
            //for inputs and translate them into driving controls
            case OpmodeStart:
                drive();
                break;
            case LiftReachedPickup:
                dampening = 0.45;
                break;
            case LiftReachedLow:
                dampening = 0.3;
                break;
            case LiftReachedMedium:
                dampening = 0.25;
                break;
            case LiftReachedHigh:
                dampening = 0.20;
                break;
            default: break;
        }
    }
}
