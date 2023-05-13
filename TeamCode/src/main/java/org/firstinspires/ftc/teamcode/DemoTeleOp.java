package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;
import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.api.controller.AbstractControllerListener;
import org.firstinspires.ftc.teamcode.api.controller.DefaultControllerListener;
import org.firstinspires.ftc.teamcode.api.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.RobotEventI;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.EventDispatcherFactory;
import org.firstinspires.ftc.teamcode.api.sensor.colorsensor.LightSensor;
import org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusMotor;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusServo;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;

import kotlin.Unit;

@TeleOp(name = "Main TeleOp")
public class DemoTeleOp extends LinearOpMode {

    //DO NOT COMMIT THIS LINE!
    private static final String VUFORIA_KEY =
            "AYTXQHr/////AAABmbQiw/AJFkk1ntp4RJ81wr5uC7kAREjOtZWcs+YdnQstFRm5dcXU7DQ9hJKeAtf/WjiTU+JPI6WrVS9ORvIa/+jDAdE/rNTOLOGvVOYzTrETOXVundNvBqWq3WjK1i5ZswYTh/d7zlwxC8YkHDF6XR8lx4iFUivINMAEX2Sq0qynoS6IO+dFl3iAcdPewo+vbmgiUEs5FW7qqaaOsameNN063ZQYxWgBAFlK3NQS7ejsNVPIqASJjVrMYBPcCoCuT6EkOzpzLc1rnVcGXAS5DFJdfpB3qauAmBVn6rV9MtO7XLDWt5pGGv1WS0rB3CJ97T6j+SDad2pO3EJ9doj6OC5mJG9QcwvPZQ8CvoBSHMBv";

    BotConfig robot = new DefaultBotConfig();
//VUFORIA STUFF////////
    private static final String TFOD_MODEL_FILE = "complex.tflite";
    private static final String[] LABELS = {
            "high_level", //ORDER MATTERS!
            "low_level",
            "medium_level"
    };

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


    @Override
    public void runOpMode() {
//        initVuforia();
//        initTfod();
//        tfod.activate();

        robot.init(hardwareMap);

        //CONTROLLER
        AbstractControllerListener<RobotEvent> controllerListener = new DefaultControllerListener<RobotEvent>(gamepad1)
                .bind(RobotEvent.LiftGoStageUp, A)
                .bind(RobotEvent.LiftGoStageDown, B)
                .bind(RobotEvent.ManualOctoServoToggle, X)
                //DEBUG
                .bind(RobotEvent.DebugOctoMotorDown, LB)
                .bind(RobotEvent.DebugOctoMotorUp, RB);
        //DRIVE MODE
        MecanumDrive<RobotEvent> driveMode = new MecanumDrive<>(gamepad1, robot);
        //SENSORS
        ////Touch Sensor
        TouchSensor<RobotEvent> touchSensor = new TouchSensor<>(robot.octoTouchSensor);
        ////Color Sensor
        LightSensor<RobotEvent> colorSensor = new LightSensor<RobotEvent>(robot.colorSensor)
                .addColorAndEvent(
                        (color) -> color.red > 0.003,
                        RobotEvent.ColorSensorDetectRed)
                .addColorAndEvent(
                        (color) -> color.blue > 0.003,
                        RobotEvent.ColorSensorDetectBlue);

        //Lift Management
        ////Octopus Servo
        OctopusServo<RobotEvent> octopusServo = new OctopusServo<>(robot.octopusServo);
        ////Octopus Motor
        OctopusMotor<RobotEvent> octopusMotor = new OctopusMotor<>(robot.octopusMotor, robot.octopusMotor2);


        //DISPATCHER
        AbstractEventDispatcher<RobotEvent> ed = new EventDispatcherFactory<RobotEvent>()
            .global(true) //this is necessary, as the non-global version is buggy/annoying
            .build()
            .register(controllerListener)
            .register(driveMode)
            .register(touchSensor)
            .register(colorSensor)
            .register(octopusServo)
            .register(octopusMotor);

        ed.notify(OpmodeInit);

        waitForStart();

        ed.notify(OpmodeStart);

        while(opModeIsActive()) {
            //these must be run every frame
//            driveMode.drive();
            ed.updateWhileStarted();


            telemetry.addData("Encoder Pos",String.valueOf(robot.octopusMotor.getCurrentPosition()));
            telemetry.addData("PID SP M1", robot.octopusMotor.getTargetPosition());
            telemetry.addData("PID SP M2", robot.octopusMotor2.getTargetPosition());
            telemetry.addData("PID MV M1", robot.octopusMotor.getCurrentPosition());
            telemetry.addData("PID MV M2", robot.octopusMotor2.getCurrentPosition());
            telemetry.addData("PID Vel Coefs", robot.octopusMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));
            telemetry.addData("PID Pos Coefs", robot.octopusMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
            telemetry.addData("PID Vel M1", robot.octopusMotor.getVelocity());
            telemetry.addData("PID Vel M2", robot.octopusMotor2.getVelocity());

            telemetry.addData("Touch Sensor Held", touchSensor.isHeld());
            telemetry.addData("Red", robot.colorSensor.getNormalizedColors().red);
            telemetry.addData("Green", robot.colorSensor.getNormalizedColors().green);
            telemetry.addData("Blue", robot.colorSensor.getNormalizedColors().blue);

            //tensorflow
//            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//            if (updatedRecognitions != null) {
//                telemetry.addData("# Objects Detected", updatedRecognitions.size());
//                for (Recognition recognition : updatedRecognitions) {
//                    telemetry.addData("Detected Image", recognition.getLabel());
//                    telemetry.addData("Confidence", recognition.getConfidence());
//                    telemetry.addData("Horizontal Angle", recognition.estimateAngleToObject(AngleUnit.DEGREES));
//                    telemetry.addData("Height", recognition.getBottom());
//                    telemetry.addData("Img Center", recognition.getImageHeight()/2);
//                }
//            } else {
//                telemetry.addData("# Objects Detected", 0);
//            }
            telemetry.update();

//            if (touchSensor.isHeld()) {
//                gamepad1.rumble(50);
//            }


        }
    }/**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfod.loadModelFromAsset(TFOD_MODEL_FILE, LABELS);
        // tfod.loadModelFromFile(TFOD_MODEL_FILE, LABELS);
    }

}
