package org.firstinspires.ftc.teamcode.api.sensor.lift.pid;


import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.api.sensor.IPositionable;

public class MotorPID {

    IPositionable<Integer> motor;

    public MotorPID(IPositionable<Integer> motor) {
        this.motor = motor;
    }

    private double kp = 1;
    private double ki = 0.00;
    private double kd = 3;

    private double sp = 0; //setpoint
//    private double mv = 0; //measured value

    private double output = 0;

    private final double[] error = {
            0, //e(t-2)
            0, //e(t-1)
            0  //e(t)
    };

    public double step(double dt) {
//        double currTime = System.currentTimeMillis();
//        double dt = currTime - oldTime;
        error[2] = error[1];
        error[1] = error[0];
        error[0] = getSP() - getMV();

        double a0 = kp + ki*dt + kd/dt;
        double a1 = -kp - 2*kd/dt;
        double a2 = kd/dt;

        output = output + a0*error[1] + a1*error[1] + a2*error[2];
        return output;
    }

    public double getSP() {
        return sp;
    }

    public void setSP(double sp) {
        this.sp = sp;
    }

    private double getMV() {
        return motor.getPosition();
    }

    public double getOutput() {
        return output;
    }

    public String debugConstants(int selected, double modify) {
        selected %= 4;
        selected = Math.abs(selected);
        String ret = "";
        switch(selected) {
            case 0: //kp
                kp += modify;
                ret = "kp @ "+ kp;
                break;
            case 1: //ki
                ki += modify;
                ret = "ki @ "+ ki;
                break;
            case 2: //kd
                kd += modify;
                ret = "kd @ "+ kd;
                break;
            case 3: //SP
                setSP(sp + modify*1000);
                ret = "sp @ " + sp;
                break;
            default:
                break;
        }
        return ret;
    }

    /*
A0 := Kp + Ki*dt + Kd/dt
A1 := -Kp - 2*Kd/dt
A2 := Kd/dt
error[2] := 0 // e(t-2)
error[1] := 0 // e(t-1)
error[0] := 0 // e(t)
output := u0  // Usually the current value of the actuator

loop:
    error[2] := error[1]
    error[1] := error[0]
    error[0] := setpoint − measured_value
    output := output + A0 * error[0] + A1 * error[1] + A2 * error[2]
    wait(dt)
    goto loop
     */
}
