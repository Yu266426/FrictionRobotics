package org.firstinspires.ftc.teamcode;

class TransRotWheel {
    private final float translationScaleX, translationScaleY, rotationScale;
    private float powerScale, rotPowerScale;

    TransRotWheel(float translationScaleX, float translationScaleY, float rotationScale, float powerScale, float rotPowerScale) {
        this.translationScaleX = translationScaleX;
        this.translationScaleY = translationScaleY;
        this.rotationScale = rotationScale;

        this.powerScale = powerScale;
        this.rotPowerScale = rotPowerScale;
    }

    float getRawPower(float x, float y, float rotation) {
        float translation_power = x * this.translationScaleX * this.powerScale + y * this.translationScaleY * this.powerScale;

        float manhattanDist = Math.abs(x) + Math.abs(y);
        if (manhattanDist != 0) {
            translation_power /= manhattanDist;
        }

        // Square to Circle mapping
        float circle_x = (float) (x * Math.sqrt(1 - 0.5 * Math.pow(y, 2)));
        float circle_y = (float) (y * Math.sqrt(1 - 0.5 * Math.pow(x, 2)));
        float translation_scale = (float) Math.sqrt(Math.pow(circle_x, 2) + Math.pow(circle_y, 2));
        translation_power *= translation_scale;

        float rotation_power = rotation * this.rotationScale * this.rotPowerScale;

        return translation_power + rotation_power;  // Divide by largest wheel power to account for rotation
    }

    void setPower(float powerScale, float rotPowerScale) {
        this.powerScale = powerScale;
        this.rotPowerScale = rotPowerScale;
    }
}

public class TransRotWheelController {
    private final TransRotWheel frontLeftWheel, frontRightWheel, backLeftWheel, backRightWheel;

    private float frontLeftWheelPower, frontRightWheelPower, backLeftWheelPower, backRightWheelPower;

    TransRotWheelController(float powerScale, float rotPowerScale) {
        this.frontLeftWheel = new TransRotWheel(-1f, -1f, 1f, powerScale, rotPowerScale);
        this.frontRightWheel = new TransRotWheel(-1f, -1f, -1f, powerScale, rotPowerScale);
        this.backLeftWheel = new TransRotWheel(1f, -1f, 1f, powerScale, rotPowerScale);
        this.backRightWheel = new TransRotWheel(1f, -1f, -1f, powerScale, rotPowerScale);
    }

    void handleInput(float x, float y, float rotation) {
        this.frontLeftWheelPower = this.frontLeftWheel.getRawPower(x, y, rotation);
        this.frontRightWheelPower = this.frontRightWheel.getRawPower(x, y, rotation);
        this.backLeftWheelPower = this.backLeftWheel.getRawPower(x, y, rotation);
        this.backRightWheelPower = this.backRightWheel.getRawPower(x, y, rotation);

        float maxPower = Math.max(Math.abs(this.frontLeftWheelPower), Math.max(Math.abs(this.frontRightWheelPower), Math.max(Math.abs(this.backLeftWheelPower), Math.abs(this.backRightWheelPower))));

        if (maxPower > 1.0f) {
            this.frontLeftWheelPower /= maxPower;
            this.frontRightWheelPower /= maxPower;
            this.backLeftWheelPower /= maxPower;
            this.backRightWheelPower /= maxPower;
        }
    }

    public float getFrontLeftWheelPower() {
        return frontLeftWheelPower;
    }

    public float getFrontRightWheelPower() {
        return frontRightWheelPower;
    }

    public float getBackLeftWheelPower() {
        return backLeftWheelPower;
    }

    public float getBackRightWheelPower() {
        return backRightWheelPower;
    }

    public void setPower(float powerScale, float rotPowerScale) {
        this.frontLeftWheel.setPower(powerScale, rotPowerScale);
        this.frontRightWheel.setPower(powerScale, rotPowerScale);
        this.backLeftWheel.setPower(powerScale, rotPowerScale);
        this.backRightWheel.setPower(powerScale, rotPowerScale);
    }

    public void test(float x, float y, float rotation) {
        this.handleInput(x, y, rotation);
        System.out.println("_____________________________________________");
        System.out.println("Input | x: " + x + " | y: " + y + " | rot: " + rotation);
        System.out.println(this.getFrontLeftWheelPower() + " | " + this.getFrontRightWheelPower());
        System.out.println(this.getBackLeftWheelPower() + " | " + this.getBackRightWheelPower());
    }
}
