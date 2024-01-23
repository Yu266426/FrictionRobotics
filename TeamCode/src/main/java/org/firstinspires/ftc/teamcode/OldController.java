package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


class GrabberController {
    private float wristPos, handPos;
    private float wristSpeed;
    private final float minWristPos, maxWristPos, closedHandPos, openHandPos;

    GrabberController(float defaultWristPos, float minWristPos, float maxWristPos, float wristSpeed, float defaultHandPos, float openHandPos) {
        this.wristPos = defaultWristPos;
        this.wristSpeed = wristSpeed;

        this.minWristPos = minWristPos;
        this.maxWristPos = maxWristPos;

        this.handPos = defaultHandPos;
        this.closedHandPos = defaultHandPos;
        this.openHandPos = openHandPos;
    }

    public void handleInput(boolean wristUp, boolean wristDown, float handInput) {
        if (wristUp) {
            this.wristPos += this.wristSpeed;
        }
        if (wristDown) {
            this.wristPos -= this.wristSpeed;
        }
        this.wristPos = Math.min(this.maxWristPos, Math.max(this.minWristPos, this.wristPos));  // Clamp pos values

        this.handPos = closedHandPos + (openHandPos - closedHandPos) * handInput;
    }

    public float getWristPos() {
        return this.wristPos;
    }

    public float getHandPos() {
        return this.handPos;
    }

    public void setWristSpeed(float wristSpeed) {
        this.wristSpeed = wristSpeed;
    }

    public void test(boolean wristUp, boolean wristDown, float handInput) {
        this.handleInput(wristUp, wristDown, handInput);
        System.out.println("Wrist: " + this.getWristPos() + " | Hand: " + this.getHandPos());
    }
}

@TeleOp(name = "Controller")
public class OldController extends LinearOpMode {
    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private DcMotor leftSlideMotor, rightSlideMotor;

    private Servo leftWristServo, rightWristServo, leftHandServo, rightHandServo;

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
        LinearSlideController slideController = new LinearSlideController(0.2f, 0.3f, 2f);

        // Left servo (1 max, 0.5 down)
        // Right servo (1 max, 0.5 down)
        GrabberController grabberController = new GrabberController(0.5f, 0.0f, 0.5f, 0.002f, 0.5f, 1.0f);

        Toggle liftToggle = new Toggle();

        while (opModeIsActive()) {
            // Handle Input
            // Drive Motors
            float x_input = gamepad1.left_stick_x;
            float y_input = -gamepad1.left_stick_y;
            float rot_input = gamepad1.right_stick_x;
            transRotWheelController.handleInput(x_input, y_input, rot_input);

            // Linear Slide
            slideController.handleInput(gamepad2.y, gamepad2.x, gamepad2.a, gamepad2.b);

            liftToggle.toggle(gamepad2.right_trigger > 0.8);
            if (liftToggle.on()) {
                slideController.toggleLift();
            }
//            slideController.handleInput(gamepad2.right_stick_y);

            // Grabber
            grabberController.handleInput(gamepad2.right_bumper, gamepad2.left_bumper, gamepad2.left_trigger);

            // Set Actuators
            // Drive Motors
            frontLeftMotor.setPower(transRotWheelController.getFrontLeftWheelPower());
            frontRightMotor.setPower(transRotWheelController.getFrontRightWheelPower());
            backLeftMotor.setPower(transRotWheelController.getBackLeftWheelPower());
            backRightMotor.setPower(transRotWheelController.getBackRightWheelPower());

            // Linear Slide
            leftSlideMotor.setPower(slideController.getPower());
            rightSlideMotor.setPower(slideController.getPower());

            // Grabber
            leftWristServo.setPosition(grabberController.getWristPos());
//            rightWristServo.setPosition(grabberController.getWristPos());

            leftHandServo.setPosition(grabberController.getHandPos());
            rightHandServo.setPosition(1.5 - grabberController.getHandPos());

            // Telemetry
//            telemetry.addData("Input", "x: " + x_input + " | y: " + y_input + " | rot: " + rot_input);
//            telemetry.addData("flw: ", transRotWheelController.getFrontLeftWheelPower());
//            telemetry.addData("frw: ", transRotWheelController.getFrontRightWheelPower());
//            telemetry.addData("blw: ", transRotWheelController.getBackLeftWheelPower());
//            telemetry.addData("brw: ", transRotWheelController.getBackRightWheelPower());
            telemetry.addData("Slide Power", slideController.getPower());
            telemetry.addData("Slide Input", gamepad2.y + " " + gamepad2.a);
            telemetry.addData("Slide Pos: ", leftSlideMotor.getCurrentPosition() + " | " + rightSlideMotor.getCurrentPosition());
            // telemetry.addData("Hand Servo", grabberController.getHandPos() + " " + (1.5f - grabberController.getHandPos()));
            telemetry.update();
        }

        // Reset?
        leftSlideMotor.setPower(0);
        rightSlideMotor.setPower(0);
    }
}