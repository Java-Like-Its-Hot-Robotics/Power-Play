package org.firstinspires.ftc.teamcode.api.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;

public class MecanumDrive extends DriveMode {
    @SuppressWarnings("FieldCanBeLocal")
    private final double TOLERANCE = 0.1;

    public MecanumDrive(Gamepad gamepad, BotConfig botConfig) {
        super(gamepad, botConfig);
    }
    @Override
    public void drive() {
        Gamepad gamepad = super.getGamepad();
        BotConfig robot = super.getConfig();
        double straif    =  gamepad.left_stick_y; //0.5
        double forward   = -gamepad.left_stick_x; //-0,5
        double turnLeft  = gamepad.left_trigger; //0
        double turnRight = gamepad.right_trigger; //0
        double turn = turnRight - turnLeft;
        double angleRad = robot.getAngles().firstAngle * -Math.PI/180;

        double temp = straif; //0.5
        straif = straif*Math.cos(angleRad) - forward*Math.sin(angleRad);
        forward = temp*Math.sin(angleRad) + forward*Math.cos(angleRad);

        if (Math.abs(straif) <= TOLERANCE) {
            straif = 0;
        }
        if (Math.abs(forward) <= TOLERANCE) {
            forward = 0;
        }

        double m1D = (forward-straif+turn)/3.0;
        double m2D = (forward+straif+turn)/3.0;
        double m3D = (forward+straif-turn)/3.0;
        double m4D = (forward-straif-turn)/3.0;


        // set power for wheels
        robot.leftFront.setPower(m1D);
        robot.rightBack.setPower(m4D);
        robot.leftBack.setPower(m2D);
        robot.rightFront.setPower(m3D);

    }

    @Override
    public void handleEvent(RobotEvent robotEvent) {
        switch (robotEvent) {
            //When the opmode is in the start loop, it should start checking the controller
            //for inputs and translate them into driving controls
            case OpmodeStart:
                drive();
                break;
            default: break;
        }
    }
}
