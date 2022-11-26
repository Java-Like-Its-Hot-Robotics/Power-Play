package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    This is NOT an opmode.

    This class can be used to define all the specific hardware for a single robot.
*/

public class BotConfig {

    /* Defining drive motors */
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;

    /* Defining Sensors */
    public TouchSensor octopusTouch = null;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public BotConfig() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFront = hwMap.get(DcMotor.class, "FrontLeft");
        leftBack = hwMap.get(DcMotor.class, "BackLeft");
        rightFront = hwMap.get(DcMotor.class, "FrontRight");
        rightBack = hwMap.get(DcMotor.class, "BackRight");

        // Keep motor direction constant
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);


    }

    public void stopDrive() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    public void drive(float foward, float strafe, float rotateLeft, float rotateRight) {
        double turn = -rotateLeft + rotateRight;
        double denominator = Math.max(Math.abs(strafe) + Math.abs(foward) + Math.abs(turn), 1);
        double frontLeftPower = (strafe + foward + turn) / denominator;
        double backLeftPower = (strafe - foward + turn) / denominator;
        double frontRightPower = (strafe - foward - turn) / denominator;
        double backRightPower = (strafe + foward - turn) / denominator;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }

    public boolean hasCone() {
        return octopusTouch.isPressed();
    }
}