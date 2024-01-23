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

}
