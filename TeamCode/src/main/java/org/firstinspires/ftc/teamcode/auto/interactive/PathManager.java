package org.firstinspires.ftc.teamcode.auto.interactive;

import org.firstinspires.ftc.teamcode.BotConfig;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class PathManager {
    private BotConfig robot;
    private ConcurrentLinkedDeque<AutoPath> paths;

    public PathManager(BotConfig robot) {
        this.robot = robot;
        paths = new ConcurrentLinkedDeque<AutoPath>();
    }


    public void modifyPath(String path, InteractiveAuto.Value value, int adjustment) {
        switch(value) {
            case ForwardPower:
            case StrafePower:
            case RotateLeftPower:
            case RotateRightPower:
                getPath(path).modify(value, adjustment);
                break;
            case Power:
                getPath(path).modify(value, (double) adjustment/100);
                break;
        }
    }

    public void runPath(String pathName) {
        getPath(pathName).run(robot);
    }

    public AutoPath getPath(String pathName) {
        for (AutoPath somePath : paths) {
            if (somePath.getName().equals(pathName)) {
                return somePath;
            }
        }
        return new AutoPath("Placeholder path", 0, 0);
    }

    public void stopPath() {
        robot.stopDrive();
    }

    public String nextPath() {
        //remove head
        AutoPath head = paths.pollFirst();
        //move it to the back
        paths.offerLast(head);
        //get the new head but don't remove it
        AutoPath next = paths.peekFirst();
        if (next != null) {
            return next.getName();
        }
        return "";
    }

    public String prevPath() {
        //remove tail
        AutoPath head = paths.pollLast();
        //move it to the front
        paths.offerFirst(head);
        //get the new head but don't remove it
        //note this needs to still be first
        //because its more akin to a rolling tape
        //and paths would be skipped otherwise
        AutoPath next = paths.peekFirst();
        if (next != null) {
            return next.getName();
        }
        return "";
    }

    public void addPath(String pathName, double forwardPower, int duration) {
        AutoPath newPath = new AutoPath(pathName, forwardPower, duration);
        paths.addLast(newPath);
    }

    public void addPath(String pathName,    double forwardPower,
                        double strafePower, double rotateLeft,
                        double rotateRight, double power) {
        AutoPath newPath = new AutoPath(
            pathName, forwardPower, strafePower,
            rotateLeft, rotateRight, power);
        paths.addLast(newPath);
    }

    public Callable<Boolean> pathDone(String pathName) {
        return getPath(pathName).pathDone();
    }

    public AutoPath getCurrentPath() {
        return paths.peekFirst();
    }
}
