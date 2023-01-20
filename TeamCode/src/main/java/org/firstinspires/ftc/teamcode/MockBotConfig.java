package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mock.MockDcMotor;
import org.firstinspires.ftc.teamcode.mock.MockIMU;
import org.firstinspires.ftc.teamcode.mock.MockServo;

public class MockBotConfig extends BotConfig {

    public MockBotConfig() {
        super(new MockDcMotor(), new MockDcMotor(),new MockDcMotor(),new MockDcMotor(),
                new MockServo(), new MockIMU());
    }
}
