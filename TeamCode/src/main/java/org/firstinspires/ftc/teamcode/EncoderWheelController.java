package org.firstinspires.ftc.teamcode;

//class EncoderWheel {
//
//}

import com.qualcomm.robotcore.hardware.DcMotor;

public class EncoderWheelController {
    private int frontLeftWheelPos, frontRightWheelPos, backLeftWheelPos, backRightWheelPos;

    EncoderWheelController() {
        // Reset Wheel Encoders
        Hardware.frontLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.frontRightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.backLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Hardware.backLeftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        
    }


}
