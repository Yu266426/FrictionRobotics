package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    public static DcMotor frontLeftDriveMotor, frontRightDriveMotor, backLeftDriveMotor, backRightDriveMotor;

    public static DcMotor leftSlideMotor, rightSlideMotor;

    public static DcMotor frontIntakeMotor;
    public static CRServo centralIntakeServo;

    public static Servo rightGripperServo, leftGripperServo;

    public static Servo droneLauncherServo;

    public static void init(HardwareMap hardwareMap) {
        // Set up drive motors
        frontLeftDriveMotor = hardwareMap.get(DcMotor.class, "frontLeftDriveMotor");
        frontRightDriveMotor = hardwareMap.get(DcMotor.class, "frontRightDriveMotor");
        backLeftDriveMotor = hardwareMap.get(DcMotor.class, "backLeftDriveMotor");
        backRightDriveMotor = hardwareMap.get(DcMotor.class, "backRightDriveMotor");
        frontLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        resetDriveEncoders();

        leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlideMotor");
        rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlideMotor");
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontIntakeMotor = hardwareMap.get(DcMotor.class, "frontIntakeMotor");
        centralIntakeServo = hardwareMap.get(CRServo.class, "centralIntakeServo");

        rightGripperServo = hardwareMap.get(Servo.class, "rightGripperServo");
        leftGripperServo = hardwareMap.get(Servo.class, "leftGripperServo");
        rightGripperServo.setDirection(Servo.Direction.REVERSE);

//        droneLauncherServo = hardwareMap.get(Servo.class, "droneLauncherServo");
    }

    public static void resetDriveEncoders() {
        DcMotor.RunMode prevMode = frontLeftDriveMotor.getMode();

        // Reset Wheel Encoders
        frontLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Revert to previous mode
        frontLeftDriveMotor.setMode(prevMode);
        frontRightDriveMotor.setMode(prevMode);
        backLeftDriveMotor.setMode(prevMode);
        backRightDriveMotor.setMode(prevMode);
    }

    public static void setDriveMode(DcMotor.RunMode runMode) {
        frontLeftDriveMotor.setMode(runMode);
        frontRightDriveMotor.setMode(runMode);
        backLeftDriveMotor.setMode(runMode);
        backRightDriveMotor.setMode(runMode);
    }
}
