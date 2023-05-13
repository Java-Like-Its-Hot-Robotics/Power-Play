package org.firstinspires.ftc.teamcode.auto.interactive;

import org.firstinspires.ftc.teamcode.BotConfig;

import java.util.concurrent.Callable;

public class AutoPath {
    private String name;
    private double forwardPower;
    private double strafePower;
    private double rotateLeft;
    private double rotateRight;
    private int duration;
    private long pathStartedAtTime;
    private double power;

    public AutoPath(String name, double forwardPower, double strafePower, double rotateLeft, double rotateRight, double power) {
        this.name = name;
        this.forwardPower = forwardPower;
        this.strafePower = strafePower;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.power = power;
    }

    public AutoPath(String name, double forwardPower, int duration) {
        this.name = name;
        this.forwardPower = forwardPower;
        this.duration = duration;
    }

    private AutoPath() {}

    public void modify(InteractiveAuto.Value value, double adjustment) {
        switch(value) {
            case ForwardPower:
                forwardPower+=adjustment;
                break;
            case StrafePower:
                strafePower+=adjustment;
                break;
            case RotateLeftPower:
                rotateLeft+=adjustment;
                break;
            case RotateRightPower:
                rotateRight+=adjustment;
                break;
            case Power:
                power+=adjustment;
                break;
        }
    }

    public String toString(InteractiveAuto.Value highlightValue) {
        String name     = "Path Name: "     + this.name         + "\n";
        String fwPower  = "Front Left: " + this.forwardPower + "\n";
        String strPower = "Front Right: "  + this.strafePower  + "\n";
        String rtLeft   = "Back Left: "   + this.rotateLeft   + "\n";
        String rtRight  = "Back Right: "  + this.rotateRight  + "\n";
        String power = "Power: "      + this.power + "\n";

        switch(highlightValue) {
            case ForwardPower:
                fwPower = "*" + fwPower;
                break;
            case StrafePower:
                strPower = "*" + strPower;
                break;
            case RotateLeftPower:
                rtLeft = "*" + rtLeft;
                break;
            case RotateRightPower:
                rtRight = "*" + rtRight;
                break;
            case Power:
                power = "*" + power;
                break;
        }
        return name + fwPower + strPower + rtLeft + rtRight + power;// + duration;
    }

    public String getName() {
        return name;
    }

    public void run(BotConfig robot) {
        pathStartedAtTime = System.currentTimeMillis();
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        robot.driveWithEncoder(
            (int) forwardPower,
            (int) strafePower,
            (int) rotateLeft,
            (int) rotateRight
        );
    }

    public Callable<Boolean> pathDone() {
        pathStartedAtTime = System.currentTimeMillis();
        return () ->
            pathStartedAtTime + duration < System.currentTimeMillis();
    }
}