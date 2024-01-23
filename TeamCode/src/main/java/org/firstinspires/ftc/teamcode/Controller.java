package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Controller")
public class Controller extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Hardware.init(hardwareMap);

        runtime.reset();

        TransRotWheelController transRotWheelController = new TransRotWheelController(0.5f, 0.5f);
        LinearSlideController slideController = new LinearSlideController(0.0f, 0.8f, 1.5f);
        GripperController gripperController = new GripperController(0.0f, 0.2f);

        Toggle intakeToggle = new Toggle();
        Toggle centralIntakeToggle = new Toggle();
        Toggle liftToggle = new Toggle();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            // Handle Input

            // Controller 1
            /*
                Movement: left stick
                Rotation: right stick x

                Front intake (and central by extension): dpad left
             */

            // Drive Motors
            float x_input = gamepad1.left_stick_x;
            float y_input = -gamepad1.left_stick_y;
            float rot_input = gamepad1.right_stick_x;
            transRotWheelController.handleInput(x_input, y_input, rot_input);

            // Intake
            intakeToggle.toggle(gamepad1.dpad_left);
            if (intakeToggle.justToggled()) {
                if (intakeToggle.on()) {
                    centralIntakeToggle.turnOn();
                } else {
                    centralIntakeToggle.turnOff();
                }
            }

            // Controller 2
            /*
                Linear slide: left stick y (left trigger for slightly more power)
                Linear slide lift: Toggled by `a`

                Central intake: dpad down

                Open Gripper: right trigger
             */

            // Linear Slide
//            slideController.handleInput(gamepad1.y, gamepad1.x, gamepad1.a, gamepad1.b); // up, UP, down,DOWN
            slideController.handleInput(-gamepad2.left_stick_y, gamepad2.left_trigger > 0.8);

            liftToggle.toggle(gamepad2.a);
            if (liftToggle.justToggled()) {
                slideController.toggleLift();
            }

            // Intake
            centralIntakeToggle.toggle(gamepad2.dpad_down);

            // Gripper
            gripperController.getInput(gamepad2.right_trigger < 0.8);


            // Set Actuators
            // Drive Motors
            Hardware.frontLeftDriveMotor.setPower(transRotWheelController.getFrontLeftWheelPower());
            Hardware.frontRightDriveMotor.setPower(transRotWheelController.getFrontRightWheelPower());
            Hardware.backLeftDriveMotor.setPower(transRotWheelController.getBackLeftWheelPower());
            Hardware.backRightDriveMotor.setPower(transRotWheelController.getBackRightWheelPower());

            // Linear Slide
            Hardware.leftSlideMotor.setPower(slideController.getPower());
            Hardware.rightSlideMotor.setPower(slideController.getPower());

            // Gripper
            Hardware.leftGripperServo.setPosition(gripperController.getPos());
            Hardware.rightGripperServo.setPosition(gripperController.getPos());

            // Intake
            if (intakeToggle.on()) {
                Hardware.frontIntakeMotor.setPower(0.5);
            } else {
                Hardware.frontIntakeMotor.setPower(0.0);
            }

            if (centralIntakeToggle.on()) {
                Hardware.centralIntakeServo.setPower(0.8);
            } else {
                Hardware.centralIntakeServo.setPower(0.0);
            }

            // Telemetry
//            telemetry.addData("Input", "x: " + x_input + " | y: " + y_input + " | rot: " + rot_input);
//            telemetry.addData("flw: ", transRotWheelController.getFrontLeftWheelPower());
//            telemetry.addData("frw: ", transRotWheelController.getFrontRightWheelPower());
//            telemetry.addData("blw: ", transRotWheelController.getBackLeftWheelPower());
//            telemetry.addData("brw: ", transRotWheelController.getBackRightWheelPower());
//            telemetry.addData("Slide Power:", slideController.getPower());
//            telemetry.addData("Left Encod", Hardware.leftSlideMotor.getCurrentPosition());
            telemetry.addData("Servo", gripperController.getPos());
            telemetry.addData("Intake:", intakeToggle.on());
            telemetry.addData("Central Intake:", centralIntakeToggle.on());
            telemetry.update();
        }
    }
}