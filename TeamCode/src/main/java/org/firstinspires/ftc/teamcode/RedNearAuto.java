package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RedNearAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        // Setup
        Hardware.init(hardwareMap);

        EncoderWheelController wheelController = new EncoderWheelController(0.6f);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        wheelController.setForwardMovement(30);
        while (wheelController.tick(opModeIsActive())) {
            wheelController.addTelemetry(telemetry);
            telemetry.update();
        }

    }
}
