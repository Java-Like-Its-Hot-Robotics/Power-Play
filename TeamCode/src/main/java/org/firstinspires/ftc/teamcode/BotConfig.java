package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
    public DcMotor octopusMotor = null;
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
        octopusMotor = hwMap.get(DcMotor.class, "octopusMotor");
//        octopusMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        octopusMotor.setTargetPosition(0);
        octopusMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        octopusMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //RUN TO POSITION will mess everything up!
        octopusMotor.setPower(0.6);
        octopusMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
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
        double forward   = -forwardInp;
        double turnLeft  = rotateLeft;
        double turnRight = rotateRight;
        double turn = turnRight - turnLeft;
        double angleRad = getAngles().firstAngle * -Math.PI/180;

        double temp = strafe;
        strafe = strafe*Math.cos(angleRad) - forward*Math.sin(angleRad);
        forward = temp*Math.sin(angleRad) + forward*Math.cos(angleRad);

        double m1D = (forward-strafe+turn)/3.0;
        double m2D = (forward+strafe+turn)/3.0;
        double m3D = (forward+strafe-turn)/3.0;
        double m4D = (forward-strafe-turn)/3.0;

        // set power for wheels
        leftFront.setPower(m1D);
        rightBack.setPower(m4D);
        leftBack.setPower(m2D);
        rightFront.setPower(m3D);

    }


}
