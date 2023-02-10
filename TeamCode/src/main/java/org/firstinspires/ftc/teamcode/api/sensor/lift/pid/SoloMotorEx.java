package org.firstinspires.ftc.teamcode.api.sensor.lift.pid;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

public class SoloMotorEx implements IPositionable<Integer> {
    DcMotorEx motor;

    public SoloMotorEx(DcMotorEx motor) {
        this.motor = motor;

        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        motor.setVelocity(1000);
        motor.setPower(0.8);

        motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    @Override
    public Integer getPosition() {
        return motor.getCurrentPosition();
    }

    @Override
    public void setPosition(Integer position) {
        motor.setTargetPosition(position);
    }

    @Override
    public void setVelocity(double velocity) {
        motor.setVelocity(velocity);
    }

    @Override
    public boolean isAt(Integer position) {
        return motor.getCurrentPosition() == position;
    }

    @Override
    public Integer getTargetPosition() {
        return motor.getTargetPosition();
    }
}
