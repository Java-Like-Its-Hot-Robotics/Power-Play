package org.firstinspires.ftc.teamcode.api.sensor.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

public class SoloMotor implements IPositionable<Integer> {
    private DcMotor motor;

    public SoloMotor(DcMotor motor) {
        this.motor = motor;

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
    public boolean isAt(Integer position) {
        return motor.getCurrentPosition() == position;
    }

    @Override
    public Integer getTargetPosition() {
        return motor.getTargetPosition();
    }
}
