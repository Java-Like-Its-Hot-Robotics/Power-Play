package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
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

public class TestingBotConfig extends BotConfig {

    /* Defining drive motors */
//    public RevRoboticsCoreHexMotor octopusMotor = null;
    public DcMotor octopusMotor = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public TestingBotConfig(){

    }


    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        octopusMotor = hwMap.get(DcMotor.class, "motor");//hwMap.get(DcMotor.class, "octopusMotor");

    }

    /**
     *
     * @return the Orientation with INTRINSIC, ZYX, and DEGREES units
     */
    public Orientation getAngles() {
        return new Orientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES, 100, 100, 1,1);//imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

//    public void drive(float foward, float strafe, float rotateLeft, float rotateRight){
//        double turn= -rotateLeft+ rotateRight;
//        double denominator= Math.max(Math.abs(strafe)+Math.abs(foward)+Math.abs(turn),1);
//        double frontLeftPower= (strafe+foward+turn)/denominator;
//        double backLeftPower= (strafe-foward+turn)/denominator;
//        double frontRightPower= (strafe-foward-turn)/denominator;
//        double backRightPower= (strafe+foward-turn)/denominator;
//
//        leftFront.setPower(frontLeftPower);
//        leftBack.setPower(backLeftPower);
//        rightFront.setPower(frontRightPower);
//        rightBack.setPower(backRightPower);
//    }


}
