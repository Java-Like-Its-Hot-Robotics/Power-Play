package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/*
    This is NOT an opmode.

    This class can be used to define all the specific hardware for a single robot.
*/

public abstract class BotConfig {

    /* Defining drive motors */
    public DcMotor leftFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightFront = null;
    public DcMotor rightBack = null;
    public DcMotorEx octopusMotor = null;
    public DcMotorEx octopusMotor2 = null;
    public Servo octopusServo = null;
    public BNO055IMU imu = null;
    public RevTouchSensor octoTouchSensor = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public BotConfig(){

    }

    /**
     * Used for mocking
     * @param leftFront
     * @param leftBack
     * @param rightFront
     * @param rightBack
     * @param octopusServo
     * @param imu
     */
    public BotConfig(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack, Servo octopusServo, BNO055IMU imu) {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.octopusServo = octopusServo;
        this.imu = imu;
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;


        //MOTOR SETUP
        leftFront  = hwMap.get(DcMotor.class, "FrontLeft");
        leftBack   = hwMap.get(DcMotor.class, "BackLeft");
        rightFront = hwMap.get(DcMotor.class, "FrontRight");
        rightBack  = hwMap.get(DcMotor.class, "BackRight");
        //// Keep motor direction constant
        leftFront.setDirection (DcMotor.Direction.FORWARD);
        leftBack.setDirection  (DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection (DcMotor.Direction.REVERSE);
        //OCTOPUS SETUP
        ////Motors
        octopusMotor = hwMap.get(DcMotorEx.class, "octopusMotor");
        octopusMotor2 = hwMap.get(DcMotorEx.class, "octopusMotor2");
        //default PID coefficients are p=10, i=0.050003, d=0, f=0
        PIDFCoefficients velCoefficients = new PIDFCoefficients(
                10,
                3,
                2,
                75, //feed forward
                MotorControlAlgorithm.PIDF
        );
        PIDFCoefficients posCoefficients = new PIDFCoefficients(
                10, 0, 0, 0, MotorControlAlgorithm.PIDF
        );
//        octopusMotor.setVelocityPIDFCoefficients(10, 0, 0, 50);
//        octopusMotor2.setVelocityPIDFCoefficients(10, 0, 0, 50);
        octopusMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, velCoefficients);
        octopusMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, posCoefficients);
        octopusMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, velCoefficients);
        octopusMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, posCoefficients);

        octopusMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        octopusMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ////Servo
        octopusServo = hwMap.get(Servo.class, "octopusServo");
        ////Sensors
        octoTouchSensor = hwMap.get(RevTouchSensor.class, "octopusTouchSensor");
        //IMU SETUP
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

    }

    /**
     *
     * @return the Orientation with INTRINSIC, ZYX, and DEGREES units
     */
    public Orientation getAngles() {
//        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, 100, 100, 1,1);
    }

    public void stopDrive(){
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    public void drive(double forwardInp, double strafe, double rotateLeft, double rotateRight){
        double y = -forwardInp;
        double x = strafe * 1.1; // Counteract imperfect strafing
//        double rx = strafe;
        double rotate = rotateLeft - rotateRight;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double dampening = 0.45;
        double frontLeftPower  = (y + x + rotate) * dampening;
        double backLeftPower   = (y - x + rotate) * dampening;
        double frontRightPower = (y - x - rotate) * dampening;
        double backRightPower  = (y + x - rotate) * dampening;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);

    }


}
