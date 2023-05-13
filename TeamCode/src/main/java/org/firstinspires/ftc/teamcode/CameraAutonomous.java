package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

    import org.firstinspires.ftc.robotcore.external.ClassFactory;
    import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
    import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
    import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
    import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
    import org.firstinspires.ftc.teamcode.api.sensor.camera.RecognitionManager;
    import org.firstinspires.ftc.teamcode.api.sensor.camera.SingleObjectDetectionManager;

    import java.util.List;

@Autonomous
public class CameraAutonomous extends LinearOpMode {
    BotConfig robot = new DefaultBotConfig();

    //DO NOT COMMIT THIS LINE!
    private static final String VUFORIA_KEY =
            "AYTXQHr/////AAABmbQiw/AJFkk1ntp4RJ81wr5uC7kAREjOtZWcs+YdnQstFRm5dcXU7DQ9hJKeAtf/WjiTU+JPI6WrVS9ORvIa/+jDAdE/rNTOLOGvVOYzTrETOXVundNvBqWq3WjK1i5ZswYTh/d7zlwxC8YkHDF6XR8lx4iFUivINMAEX2Sq0qynoS6IO+dFl3iAcdPewo+vbmgiUEs5FW7qqaaOsameNN063ZQYxWgBAFlK3NQS7ejsNVPIqASJjVrMYBPcCoCuT6EkOzpzLc1rnVcGXAS5DFJdfpB3qauAmBVn6rV9MtO7XLDWt5pGGv1WS0rB3CJ97T6j+SDad2pO3EJ9doj6OC5mJG9QcwvPZQ8CvoBSHMBv";

    //VUFORIA STUFF////////
    private static final String TFOD_MODEL_FILE = "complex.tflite";
    private static final String[] LABELS = {
            "high_level", //ORDER MATTERS!
            "low_level",
            "medium_level"
    };

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private SingleObjectDetectionManager detectionManager;

    @Override
    public void runOpMode() {
        telemetry.addData("Camera Activation", "Initiated");
        telemetry.update();
        initVuforia();
        initTfod();
        tfod.activate();
        telemetry.addData("Camera Activation", "Complete");
        telemetry.update();

        // Initialize the hardware in BotConfig
        robot.init(hardwareMap);
        detectionManager = new SingleObjectDetectionManager(new RecognitionManager());
        // Wait for the driver to press run
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            boolean objDetected = sample(50);
            telemetry.addData("Sampling", "Complete");
            int drivetime = 1000;
            //in case we didn't detect an object, move forward and try again
            if (!objDetected) {
                telemetry.addData("Sampling", "Failed, Retrying closer up");
                drivetime-=100;
                robot.drive(-1,0,0,0);
                sleep(100);
                robot.stopDrive();
                sample(50);
            }
            telemetry.update();
            tfod.deactivate();
            //END DETECTION

            //Go in the necessary direction based on what the
            // cone shows
            switch(detectionManager.getConsensus()) {
                case "low_level":
                    robot.drive(0, -1, 0, 0);
                    sleep(1850);
                    break;
                case "medium_level":
                    //already here, just stop
                    break;
                case "high_level":
                    robot.drive(0, 1, 0, 0);
                    sleep(1850);
                    break;
                default:
                    telemetry.addData("Status", "Nothing found!");
                    telemetry.update();
            }
            robot.stopDrive();
            sleep(500);
            robot.drive(-1, 0,0,0);
            sleep(drivetime);
            robot.stopDrive();
            return;
        }
    }


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

    private boolean sample(int nTimes) {
        boolean objDetected = false;
        for (int i = 0; i < nTimes; i++) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Objects Detected", updatedRecognitions.size());
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("Detected Image", recognition.getLabel());
                    telemetry.addData("Confidence", recognition.getConfidence());
                    detectionManager.giveRecognition(recognition);
                    objDetected = true;
                }
                telemetry.addData("Current Max", detectionManager.getConsensus());
                telemetry.update();
            } else {
                i--;
            }
        }
        return objDetected;
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
