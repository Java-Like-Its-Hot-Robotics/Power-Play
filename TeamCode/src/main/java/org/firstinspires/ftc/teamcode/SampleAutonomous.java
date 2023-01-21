package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.util.ElapsedTime;

public class SampleAutonomous extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();
    BotConfig robot = new BotConfig(){};

    @Override
    public void runOpMode(){
        // Initialize the hardware in BotConfig
        robot.init(hardwareMap);
        // Wait for the driver to press run
        waitForStart();

        while (opModeIsActive()) {
            robot.drive(0, -1, 0, 0);
            sleep(5000);
            robot.stopDrive();
        }
    }

}