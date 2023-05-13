package org.firstinspires.ftc.teamcode.auto.interactive;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.listener.continuous.ContinuousEventListener;

import kotlin.Unit;

public class InteractiveAuto<T extends Enum<T>> extends ContinuousEventListener<T> {
    private final Telemetry telemetry;
    private final PathManager pathManager;
    private String currentPath;
    private static String currentMode = "Edit";
    private Value currentValue = Value.Power;
    private final int adjustment = 10;
    public enum Mode {
        Edit, Run;

        @Override
        public String toString() {
            String ret = "";
            switch(this) {
                case Edit: ret = "Edit";
                case Run : ret = "Run";
            }
            return ret;
        }
        public static String swapMode(String str) {
            switch(str) {
                case "Edit": return "Run";
                case "Run" : return "Edit";
            }
            return null;
        }
        public static Mode fromString(String str) {
            if(str.equals("Run")) {
                return Run;
            }
            return Edit;
        }
    }
    public enum Value {
        ForwardPower,
        StrafePower,
        RotateLeftPower,
        RotateRightPower,
        Power;

        public Value increment() {
            switch(this) {
                case ForwardPower: return StrafePower;
                case StrafePower: return RotateLeftPower;
                case RotateLeftPower: return RotateRightPower;
                case RotateRightPower : return Power;
                case Power: return ForwardPower;
            }
            return ForwardPower;
        }
        public Value decrement() {
            switch(this) {
                case ForwardPower: return Power;
                case StrafePower: return ForwardPower;
                case RotateLeftPower: return StrafePower;
                case RotateRightPower: return RotateLeftPower;
                case Power: return RotateRightPower;
            }
            return ForwardPower;
        }
    }

    public InteractiveAuto(Telemetry telemetry, BotConfig robot) {
        super("Interactive Auto");
        this.telemetry = telemetry;
        pathManager = new PathManager(robot);

        //TODO implement interactivity
        addEventHandler((RobotEventI<T> event) -> {
            if (event instanceof InteractiveAutoEvent) {
                //Edit Mode
                if(currentMode.equals("Edit")) {
                    switch ((InteractiveAutoEvent) event) {
                        case IncrementValue:
                            if(preventRepeatFor(75)) break;
                            pathManager.modifyPath(currentPath, currentValue, adjustment);
                            break;
                        case DecrementValue:
                            if(preventRepeatFor(75)) break;
                            pathManager.modifyPath(currentPath, currentValue, -adjustment);
                            break;
                        case IncrementBig:
                            if(preventRepeatFor(75)) break;
                            pathManager.modifyPath(currentPath, currentValue, adjustment*10);
                            break;
                        case DecrementBig:
                            if(preventRepeatFor(75)) break;
                            pathManager.modifyPath(currentPath, currentValue, -adjustment*10);
                            break;
                        case ChangeFocusedValue:
                            if(preventRepeatFor(500)) break;
                            incrementValue();
                            break;

                    }
                }
                //Run Mode
                else {
                    switch((InteractiveAutoEvent) event) {
                        case RunStep:
                            if(preventRepeatFor(500)) break;
                            pathManager.runPath(currentPath);
//                            super.notifyWhen(
//                                (RobotEventI<T>) InteractiveAutoEvent.StopStep,
//                                pathManager.pathDone(currentPath));

                            break;
                        case StopStep:
                            pathManager.stopPath();
                            break;
                    }
                }
                //All Modes
                switch((InteractiveAutoEvent) event) {
                    case AdvanceStep:
                        if(preventRepeatFor(500)) break;
                        currentPath = pathManager.nextPath();
                        break;
                    case BackStep:
                        if(preventRepeatFor(500)) break;
                        currentPath = pathManager.prevPath();
                        break;
                    case ToggleMode:
                        if(preventRepeatFor(500)) break;
                        toggleMode();
                        break;
                }
            }
            return Unit.INSTANCE;
        });
    }

    @Override
    public void eventStep() {
        telemetry.addData("Interactive Auto Status", "");
        telemetry.addData("", toString());
        telemetry.update();
    }

    public boolean inEdit() {
        return currentMode.equals("Edit");
    }

    private void toggleMode() {
        InteractiveAuto.currentMode = Mode.swapMode(currentMode);
    }

//    public InteractiveAuto<T> addPath(String pathName, double forwardPower, int duration) {
//        pathManager.addPath(pathName, forwardPower, duration);
//        currentPath = pathManager.getCurrentPath().getName();
//        return this;
//    }
    public InteractiveAuto<T> addPath
        (String pathName,
         double frontLeft,
         double frontRight,
         double backLeft,
         double backRight,
         double power
         )
    {
        pathManager.addPath(
            pathName, frontLeft, frontRight,
            backLeft, backRight, power);
        currentPath = pathManager.getCurrentPath().getName();
        return this;
    }

    public String toString() {
        AutoPath path = pathManager.getPath(currentPath);
        String ret =
            "Current Mode: " + currentMode + "\n"
          + (Mode.fromString(currentMode) == Mode.Edit ?
                "Editing: " + currentValue + "\n"
              + "Adjustment: ±" + adjustment + "\n"
                :
                "\n"
            )
          + path.toString(currentValue);
        return ret;
    }

    private void incrementValue() {
        currentValue = currentValue.increment();
    }
    private void decrementValue() {
        currentValue = currentValue.decrement();
    }
}
