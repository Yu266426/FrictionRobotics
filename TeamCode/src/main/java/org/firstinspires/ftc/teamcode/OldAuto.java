package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "2023 Auto")
public class OldAuto extends LinearOpMode {
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private DcMotor leftSlideMotor, rightSlideMotor;

    private Servo leftWristServo, rightWristServo, leftHandServo, rightHandServo;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlideMotor");
        rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlideMotor");
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftWristServo = hardwareMap.get(Servo.class, "leftWristServo");  // Cable "Claw Arm 1"
        rightWristServo = hardwareMap.get(Servo.class, "rightWristServo");
        leftHandServo = hardwareMap.get(Servo.class, "leftHandServo");
        rightHandServo = hardwareMap.get(Servo.class, "rightHandServo");

        leftWristServo.setDirection(Servo.Direction.REVERSE);
        rightHandServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        TransRotWheelController transRotWheelController = new TransRotWheelController(0.5f, 0.5f);

        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 0.5) {
            leftSlideMotor.setPower(0.3);
            rightSlideMotor.setPower(0.3);
        }
        leftSlideMotor.setPower(0.0);
        rightSlideMotor.setPower(0.0);

        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 7.0) {
            transRotWheelController.handleInput(0.0f, 0.4f, 0.0f);

            frontLeftMotor.setPower(transRotWheelController.getFrontLeftWheelPower());
            frontRightMotor.setPower(transRotWheelController.getFrontRightWheelPower());
            backLeftMotor.setPower(transRotWheelController.getBackLeftWheelPower());
            backRightMotor.setPower(transRotWheelController.getBackRightWheelPower());

            telemetry.addData("Path", "Leg 1: " + runtime.seconds() + " seconds");
            telemetry.update();
        }

        frontLeftMotor.setPower(0.0);
        frontRightMotor.setPower(0.0);
        backLeftMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        telemetry.addData("Runtime", "Complete");
        telemetry.update();

        sleep(1000);
    }
}
