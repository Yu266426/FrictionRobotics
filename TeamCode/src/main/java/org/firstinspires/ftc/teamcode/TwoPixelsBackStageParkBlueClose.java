package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "TwoPixelsBackStageParkBlueClose")
public class TwoPixelsBackStageParkBlueClose extends LinearOpMode {

  private CRServo centralIntakeServo;
  private DcMotor frontIntakeMotor;
  private DcMotor backLeftDriveMotor;
  private DcMotor frontLeftDriveMotor;
  private DcMotor rightSlideMotor;
  private DcMotor leftSlideMotor;
  private Servo rightGripperServo;
  private Servo leftGripperServo;
  private DcMotor frontRightDriveMotor;
  private DcMotor backRightDriveMotor;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    centralIntakeServo = hardwareMap.get(CRServo.class, "centralIntakeServo");
    frontIntakeMotor = hardwareMap.get(DcMotor.class, "frontIntakeMotor");
    backLeftDriveMotor = hardwareMap.get(DcMotor.class, "backLeftDriveMotor");
    frontLeftDriveMotor = hardwareMap.get(DcMotor.class, "frontLeftDriveMotor");
    rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlideMotor");
    leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlideMotor");
    rightGripperServo = hardwareMap.get(Servo.class, "rightGripperServo");
    leftGripperServo = hardwareMap.get(Servo.class, "leftGripperServo");
    frontRightDriveMotor = hardwareMap.get(DcMotor.class, "frontRightDriveMotor");
    backRightDriveMotor = hardwareMap.get(DcMotor.class, "backRightDriveMotor");

    // Reversals
    backLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
    frontLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
    
    // Positive power means going up
    rightSlideMotor.setDirection(DcMotor.Direction.REVERSE);
    
    rightGripperServo.setDirection(Servo.Direction.REVERSE);
    // 0 is absolute closed, 1 is absolute open. 0.05 is pref. open, 0 is pref closed.
    
    
    // Initialization
    
    openGrip();
    
    intake(3500, 0.5);
    
    linearSlideDown();
    
    waitForStart();
    if (opModeIsActive()) {
      /*
      Preload pixels in initialization phase
      Drive forward
      Turn left 90 degrees
      Drive foward
      Deliever pixel
        Drive forward a little bit
        Open grip
        Slide down
        Close grip
        Slide up
        Open grip
        Drive back a little bit
      */
      
      drive(650, 0.8);
      
      // 550 milliseconds at 0.5 power is 90 degree turn
      sleep(500);
      
      turnLeft();
      
      sleep(500);
      
      // Drive to the Backdrop
      drive(800, 0.4);
      
      delieverPixel();
      
      intake(3000, 0.3);
      
      delieverPixel();
      
      linearSlideDown();
      
      // Back into parking spot
      drive(500, 0.25);
      
    }
  }
  
  public void delieverPixel() {
    openGrip();
    linearSlideDown();
    closeGrip();
    
    
    // 3650, 0.35
    linearSlide(4300, 0.5);
    
    int hugTime = 2000;
    
    drive(hugTime, 0.23);
    
    openGrip();
    
    sleep(200);
    
    drive(hugTime / 2, -0.23);
    
    drive(700, 0.16);
  }
  
  public void strafe(double deltaT, double power) {
    // positive power for left strafte
    
    frontLeftDriveMotor.setPower(-power);
    backLeftDriveMotor.setPower(power);
    
    frontRightDriveMotor.setPower(power);
    backRightDriveMotor.setPower(-power);
    
    sleep((long)deltaT);
    
    frontLeftDriveMotor.setPower(0);
    frontRightDriveMotor.setPower(0);
    backLeftDriveMotor.setPower(0);
    backRightDriveMotor.setPower(0);
    
  }
  
  public void intake(double deltaT, double power) {
    centralIntakeServo.setPower(power);
    frontIntakeMotor.setPower(power * 1.25);
    sleep((long) deltaT);
    centralIntakeServo.setPower(0);
    frontIntakeMotor.setPower(0);
    
  }
  
  public void openGrip() {
    leftGripperServo.setPosition(0.05);
    rightGripperServo.setPosition(0.05);
  }
  
  public void closeGrip() {
    leftGripperServo.setPosition(0);
    rightGripperServo.setPosition(0);
  }
  
  public void linearSlideDown() {
    leftSlideMotor.setPower(-0.6);
    rightSlideMotor.setPower(-0.6);
    while (leftSlideMotor.getCurrentPosition() > 70) {
      // do nothing
    }
    leftSlideMotor.setPower(0);
    rightSlideMotor.setPower(0);
  }
  
  public void linearSlide(double deltaT, double poWaH) {
    leftSlideMotor.setPower(poWaH);
    rightSlideMotor.setPower(poWaH);
    sleep((long) deltaT);
    leftSlideMotor.setPower(0);
    rightSlideMotor.setPower(0);
  }
  
  public void drive(double deltaT, double power) {
    frontLeftDriveMotor.setPower(power);
    frontRightDriveMotor.setPower(power * 1.02);
    backLeftDriveMotor.setPower(power);
    backRightDriveMotor.setPower(power * 1.02);
    sleep((long) deltaT);
    frontLeftDriveMotor.setPower(0);
    frontRightDriveMotor.setPower(0);
    backLeftDriveMotor.setPower(0);
    backRightDriveMotor.setPower(0);
  }
  
  public void turnLeft() {
    double power = 0.5;
    frontLeftDriveMotor.setPower(-power);
    backLeftDriveMotor.setPower(-power);
    frontRightDriveMotor.setPower(power);
    backRightDriveMotor.setPower(power);
    
    int initial = frontRightDriveMotor.getCurrentPosition();
    int difference = 810;
    while (Math.abs(frontRightDriveMotor.getCurrentPosition() - initial) < difference) {
      // do nothing
    }
    frontLeftDriveMotor.setPower(0);
    backLeftDriveMotor.setPower(0);
    frontRightDriveMotor.setPower(0);
    backRightDriveMotor.setPower(0);
  }
  
  public void turnRight() {
    double power = 0.5;
    frontLeftDriveMotor.setPower(power);
    backLeftDriveMotor.setPower(power);
    frontRightDriveMotor.setPower(-power);
    backRightDriveMotor.setPower(-power);
    
    int initial = frontLeftDriveMotor.getCurrentPosition();
    int difference = 810;
    while (Math.abs(frontLeftDriveMotor.getCurrentPosition() - initial) < difference) {
      // do nothing
    }
    frontLeftDriveMotor.setPower(0);
    backLeftDriveMotor.setPower(0);
    frontRightDriveMotor.setPower(0);
    backRightDriveMotor.setPower(0);
  }
  
}
