package org.firstinspires.ftc.teamcode;

//class EncoderWheel {
//
//}

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class EncoderWheelController {
    private final float encoderCountPerCentimeterForward = 100;
    private final float encoderCountPerCentimeterSideways = 100;

    private final float encoderCountPer360 = 100;

    private final float motorPower;

    private float frontLeftWheelPos = 0, frontRightWheelPos = 0, backLeftWheelPos = 0, backRightWheelPos = 0;

    EncoderWheelController(float motorPower) {
        this.motorPower = motorPower;

        Hardware.resetDriveEncoders();
        Hardware.setDriveMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void updateMotorPositions() {
        Hardware.frontLeftDriveMotor.setTargetPosition(Math.round(frontLeftWheelPos));
        Hardware.frontRightDriveMotor.setTargetPosition(Math.round(frontRightWheelPos));
        Hardware.backLeftDriveMotor.setTargetPosition(Math.round(backLeftWheelPos));
        Hardware.backRightDriveMotor.setTargetPosition(Math.round(backRightWheelPos));
    }

    private void runMotors() {
        Hardware.frontLeftDriveMotor.setPower(this.motorPower);
        Hardware.frontRightDriveMotor.setPower(this.motorPower);
        Hardware.backLeftDriveMotor.setPower(this.motorPower);
        Hardware.backRightDriveMotor.setPower(this.motorPower);
    }

    private void stopMotors() {
        Hardware.frontLeftDriveMotor.setPower(0);
        Hardware.frontRightDriveMotor.setPower(0);
        Hardware.backLeftDriveMotor.setPower(0);
        Hardware.backRightDriveMotor.setPower(0);
    }

    public boolean tick(boolean opModeActive) {
        // If the op mode is inactive, then stop
        if (!opModeActive) {
            return false;
        }

        int numMotorsFinished = 0;
        if (!Hardware.frontLeftDriveMotor.isBusy()) {
            numMotorsFinished += 1;
        }
        if (!Hardware.frontRightDriveMotor.isBusy()) {
            numMotorsFinished += 1;
        }
        if (!Hardware.backLeftDriveMotor.isBusy()) {
            numMotorsFinished += 1;
        }
        if (!Hardware.backRightDriveMotor.isBusy()) {
            numMotorsFinished += 1;
        }

        // If less than 2 motors are done, keep moving
        if (numMotorsFinished <= 2) {
            runMotors();
            return true;
        }

        stopMotors();
        return false;
    }

    public void addTelemetry(Telemetry telemetry) {
        telemetry.addData("Front Left", "%4d | %4d", this.frontLeftWheelPos, Hardware.frontLeftDriveMotor.getCurrentPosition());
        telemetry.addData("Front Right", "%4d | %4d", this.frontRightWheelPos, Hardware.frontRightDriveMotor.getCurrentPosition());
        telemetry.addData("Back Left", "%4d | %4d", this.backLeftWheelPos, Hardware.backLeftDriveMotor.getCurrentPosition());
        telemetry.addData("Back Right", "%4d | %4d", this.backRightWheelPos, Hardware.backRightDriveMotor.getCurrentPosition());
    }

    public void setForwardMovement(float cmDistance) {
        this.frontLeftWheelPos += cmDistance * this.encoderCountPerCentimeterForward;
        this.frontRightWheelPos += cmDistance * this.encoderCountPerCentimeterForward;
        this.backLeftWheelPos += cmDistance * this.encoderCountPerCentimeterForward;
        this.backRightWheelPos += cmDistance * this.encoderCountPerCentimeterForward;

        updateMotorPositions();
    }

    public void setSidewaysMovement(float cmDistance) {
        this.frontLeftWheelPos += cmDistance * this.encoderCountPerCentimeterSideways;
        this.frontRightWheelPos += cmDistance * this.encoderCountPerCentimeterSideways;
        this.backLeftWheelPos -= cmDistance * this.encoderCountPerCentimeterSideways;
        this.backRightWheelPos -= cmDistance * this.encoderCountPerCentimeterSideways;

        updateMotorPositions();
    }
}
