package org.firstinspires.ftc.teamcode.auto.interactive;

import static org.firstinspires.ftc.teamcode.api.controller.ControllerKey.*;
import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.OpmodeInit;
import static org.firstinspires.ftc.teamcode.api.event.RobotEvent.OpmodeStart;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.BotConfig;
import org.firstinspires.ftc.teamcode.DefaultBotConfig;
import org.firstinspires.ftc.teamcode.api.controller.AbstractControllerListener;
import org.firstinspires.ftc.teamcode.api.controller.DefaultControllerListener;
import org.firstinspires.ftc.teamcode.api.controller.ModifierBinding;
import org.firstinspires.ftc.teamcode.api.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.api.event.RobotEvent;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.AbstractEventDispatcher;
import org.firstinspires.ftc.teamcode.api.event.dispatcher.EventDispatcherFactory;
import org.firstinspires.ftc.teamcode.api.sensor.OctopusServo;
import org.firstinspires.ftc.teamcode.api.sensor.TouchSensor;
import org.firstinspires.ftc.teamcode.api.sensor.colorsensor.LightSensor;
import org.firstinspires.ftc.teamcode.api.sensor.lift.OctopusMotor;

@TeleOp(name = "Interactive Autonomous")
public class RightPolePark extends LinearOpMode {

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
	public void runOpMode() throws InterruptedException {
		robot.init(hardwareMap);
		robot.leftFront.setTargetPosition(0);
		robot.leftBack.setTargetPosition(0);
		robot.rightFront.setTargetPosition(0);
		robot.rightBack.setTargetPosition(0);
		robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.leftFront.setPower(0.7);
		robot.leftBack.setPower(0.7);
		robot.rightFront.setPower(0.7);
		robot.rightBack.setPower(0.7);
		//set up the event dispatch stuff
		AbstractControllerListener<InteractiveAutoEvent> controllerListener = new DefaultControllerListener<InteractiveAutoEvent>(gamepad1)
			//edit mode
			.bind(InteractiveAutoEvent.IncrementValue, A)
			.bind(InteractiveAutoEvent.DecrementValue, B)
			.bind(InteractiveAutoEvent.ChangeFocusedValue, Y)
			.bind(InteractiveAutoEvent.IncrementBig, new ModifierBinding(A, LT))
			.bind(InteractiveAutoEvent.DecrementBig, new ModifierBinding(B, LT))
			//run mode
			.bind(InteractiveAutoEvent.RunStep, A)
			.bind(InteractiveAutoEvent.StopStep, B)
			//all modes
			.bind(InteractiveAutoEvent.AdvanceStep, RB)
			.bind(InteractiveAutoEvent.BackStep, LB)
			.bind(InteractiveAutoEvent.ToggleMode, X);
		//DEBUG
		//DRIVE MODE
//		MecanumDrive<InteractiveAutoEvent> driveMode = new MecanumDrive<InteractiveAutoEvent>(gamepad1, robot)
//			.disableDriveCheck();
		//SENSORS
		////Touch Sensor
		TouchSensor<InteractiveAutoEvent> touchSensor = new TouchSensor<>(robot.octoTouchSensor);
		////Color Sensor
		LightSensor<InteractiveAutoEvent> colorSensor = new LightSensor<InteractiveAutoEvent>(robot.colorSensor)
			.addColorAndEvent(
				(color) -> color.blue > 0.001
					&& color.red > 0.001
					&& color.green > 0.001,
				InteractiveAutoEvent.ColorSensorDetectPole);

		//Lift Management
		////Octopus Servo
		OctopusServo<InteractiveAutoEvent> octopusServo = new OctopusServo<>(robot.octopusServo);
		////Octopus Motor
		OctopusMotor<InteractiveAutoEvent> octopusMotor = new OctopusMotor<>(robot.octopusMotor, robot.octopusMotor2);
		InteractiveAuto<InteractiveAutoEvent> interactiveAuto =
			new InteractiveAuto<InteractiveAutoEvent>(telemetry, robot)
				.addPath(
					"ForwardFromStart",
					1600,
					1600,
					1600,
					1600,
					0.4
				)
				.addPath(
					"TurnToPole",
					-100,
					100,
					-100,
					100,
					0.4
				)
				.addPath(
					"ApproachPole",
					300,
					300,
					300,
					300,
					0.4
				)
				.addPath(
					"Back Away",
					300,
					300,
					300,
					300,
					0.4
				)
				.addPath(
					"Rotate Back",
					100,
					-100,
					100,
					-100,
					0.4
				)
				.addPath(
					"(based on camera)",
					100,
					-100,
					100,
					-100,
					0.4
				)
			;


		AbstractEventDispatcher<InteractiveAutoEvent> ed = new EventDispatcherFactory<InteractiveAutoEvent>()
			.global(true) //this is necessary, as the non-global version is buggy/annoying
			.build()
			.register(controllerListener)
//			.register(driveMode)
			.register(touchSensor)
			.register(colorSensor)
			.register(octopusServo)
			.register(octopusMotor)
			.register(interactiveAuto);

		ed.notify(InteractiveAutoEvent.InitEvent);

		waitForStart();

		ed.notify(InteractiveAutoEvent.StartEvent);

		while (opModeIsActive()) {
			//these must be run every frame
//            driveMode.drive();
//			if(interactiveAuto.inEdit()) {
//				ed.notify(InteractiveAutoEvent.DriveInEditMode);
//			}
			ed.updateWhileStarted();
		}
	}
}
