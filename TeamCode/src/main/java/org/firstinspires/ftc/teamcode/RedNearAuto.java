package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "RedNearAuto")
public class RedNearAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // Setup
        Hardware.init(hardwareMap);
        Hardware.resetDriveEncoders();

        EncoderWheelController wheelController = new EncoderWheelController(0.6f);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Motors", Hardware.frontLeftDriveMotor.getMode());
        telemetry.update();

        waitForStart();
        runtime.reset();

        wheelController.setForwardMovement(300);
        while (wheelController.tick(opModeIsActive())) {
            wheelController.addTelemetry(telemetry);
            telemetry.update();
        }

        sleep(1000);
    }
}
