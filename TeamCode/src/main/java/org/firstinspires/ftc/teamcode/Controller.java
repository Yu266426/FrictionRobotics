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
        Hardware.droneLauncherServo.setPosition(0.5);

        runtime.reset();

        TransRotWheelController transRotWheelController = new TransRotWheelController(0.5f, 0.5f);
        LinearSlideController slideController = new LinearSlideController(0.0f, 0.8f, 1.5f);
        GripperController gripperController = new GripperController(0.0f, 0.05f);

        Toggle intakeToggle = new Toggle();

        boolean intakeTowardsGripper = true;
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
             */

            // Drive Motors
            float x_input = gamepad1.left_stick_x;
            float y_input = -gamepad1.left_stick_y;
            float rot_input = gamepad1.right_stick_x;
            transRotWheelController.handleInput(x_input, y_input, rot_input);


            // Controller 2
            /*
                Linear slide: left stick y (left trigger for slightly more power)
                Linear slide lift: Toggled by `a`

                Open Gripper: right trigger

                Launch plane: `y`
             */

            // Linear Slide
//            slideController.handleInput(gamepad1.y, gamepad1.x, gamepad1.a, gamepad1.b); // up, UP, down,DOWN
            slideController.handleInput(-gamepad2.left_stick_y, gamepad2.left_trigger > 0.8);

            liftToggle.toggle(gamepad2.a);
            if (liftToggle.justToggled()) {
                slideController.toggleLift();
            }

            // Gripper
            gripperController.getInput(gamepad2.right_trigger < 0.8);

            // Drone Launcher
            if (gamepad2.y) {
                Hardware.droneLauncherServo.setPosition(0.2);
            } else {
                Hardware.droneLauncherServo.setPosition(0.5);
            }


            // All Controllers
            /*
                Central intake towards front: dpad up
                Central intake towards gripper: dpad down
                All intakes towards gripper: dpad right
             */
            // Intake
            intakeToggle.toggle(gamepad1.dpad_left || gamepad2.dpad_left);
            if (intakeToggle.justToggled()) {
                if (intakeToggle.on()) {
                    centralIntakeToggle.turnOn();
                    intakeTowardsGripper = true;
                } else {
                    centralIntakeToggle.turnOff();
                }
            }

            boolean justSwitched = false;
            if (gamepad1.dpad_down || gamepad2.dpad_down) {
                if (intakeTowardsGripper) {
                    intakeTowardsGripper = false;
                    justSwitched = true;
                }
            } else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                if (!intakeTowardsGripper) {
                    intakeTowardsGripper = true;
                    justSwitched = true;
                }
            }

            if (!justSwitched) {
                centralIntakeToggle.toggle(gamepad1.dpad_down || gamepad2.dpad_down || gamepad1.dpad_up || gamepad2.dpad_up);
            }


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
            // Emergency reverse pixel
            if (gamepad1.dpad_right || gamepad2.dpad_right) {
                Hardware.frontIntakeMotor.setPower(-1.0);
            } else if (intakeToggle.on()) {
                Hardware.frontIntakeMotor.setPower(1.0);
            } else {
                Hardware.frontIntakeMotor.setPower(0.0);
            }

            if (centralIntakeToggle.on()) {
                if (intakeTowardsGripper) {
                    Hardware.centralIntakeServo.setPower(1.0);
                } else {
                    Hardware.centralIntakeServo.setPower(-1.0);

                }
            } else {
                Hardware.centralIntakeServo.setPower(0.0);
            }

            // Telemetry
//            telemetry.addData("Input", "x: " + x_input + " | y: " + y_input + " | rot: " + rot_input);
//            telemetry.addData("flw: ", transRotWheelController.getFrontLeftWheelPower());
//            telemetry.addData("frw: ", transRotWheelController.getFrontRightWheelPower());
//            telemetry.addData("blw: ", transRotWheelController.getBackLeftWheelPower());
//            telemetry.addData("brw: ", transRotWheelController.getBackRightWheelPower());
//            telemetry.addData("flw: ", Hardware.frontLeftDriveMotor.getCurrentPosition());
//            telemetry.addData("frw: ", Hardware.frontRightDriveMotor.getCurrentPosition());
//            telemetry.addData("blw: ", Hardware.backLeftDriveMotor.getCurrentPosition());
//            telemetry.addData("brw: ", Hardware.backRightDriveMotor.getCurrentPosition());
            telemetry.addData("Slide Power:", slideController.getPower());
            telemetry.addData("Lifting", liftToggle.on());
            telemetry.addData("Left Encod", Hardware.leftSlideMotor.getCurrentPosition());
            telemetry.addData("Servo", gripperController.getPos());
            telemetry.addData("Intake:", intakeToggle.on());
            telemetry.addData("Central Intake:", centralIntakeToggle.on());
            telemetry.update();
        }
    }
}