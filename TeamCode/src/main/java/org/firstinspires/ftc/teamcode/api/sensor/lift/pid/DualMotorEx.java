package org.firstinspires.ftc.teamcode.api.sensor.lift.pid;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

public class DualMotorEx implements IPositionable<Integer> {
    DcMotorEx motorL;
    DcMotorEx motorR;

    public DualMotorEx(DcMotorEx motorL, DcMotorEx motorR) {
        this.motorL = motorL;
        this.motorR = motorR;

        motorL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        motorL.setVelocity(0);
        motorR.setVelocity(0);

        motorL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motorR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public Integer getPosition() {
        return (  motorL.getCurrentPosition()
                + motorL.getCurrentPosition())
                / 2;
    }

    /**
     * WARNING: NO-OP
     */
    @Override
    public void setPosition(Integer position) {}

    @Override
    public void setVelocity(double velocity) {
        motorL.setVelocity(velocity);
        motorR.setVelocity(velocity);
    }

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
