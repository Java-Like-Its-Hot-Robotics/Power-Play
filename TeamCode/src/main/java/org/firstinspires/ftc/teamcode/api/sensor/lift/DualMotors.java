package org.firstinspires.ftc.teamcode.api.sensor.lift;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

public class DualMotors implements IPositionable<Integer> {
    DcMotor motorL;
    DcMotor motorR;

    public DualMotors(DcMotor motorL, DcMotor motorR) {
        this.motorL = motorL;
        this.motorR = motorR;

        motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setTargetPosition(0);
        motorR.setTargetPosition(0);

        motorL.setPower(0.8);
        motorR.setPower(0.8);

        motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public Integer getPosition() {
        return (  motorL.getCurrentPosition()
                + motorL.getCurrentPosition())
                / 2;
    }

    @Override
    public void setPosition(Integer position) {
        motorL.setTargetPosition(position);
        motorR.setTargetPosition(position);
    }

    /**
     * WARNING: NO-OP
     */
    @Override
    public void setVelocity(double velocity) {}

    @Override
    public boolean isAt(Integer position) {
        return motorL.getCurrentPosition() == position
                && motorR.getCurrentPosition() == position;
    }

    @Override
    public Integer getTargetPosition() {
       return motorL.getTargetPosition();
    }
}
