package org.firstinspires.ftc.teamcode;

public class LinearSlideController {
    private float power;
    private final float powerAmount;
    private final float defaultPower, highPowerMultiplier;

    private boolean lift;

    LinearSlideController(float defaultPower, float powerAmount, float highPowerMultiplier) {
        this.defaultPower = defaultPower;
        this.powerAmount = powerAmount;
        this.highPowerMultiplier = highPowerMultiplier;

        this.lift = false;
    }

    private void restrainPower() {
        this.power = Math.max(-1, Math.min(1, this.power)); // Clamp between -1 and 1

        if (Hardware.leftSlideMotor.getCurrentPosition() > 7750) {
            this.power = Math.min(this.power, 0);
        }
        if (Hardware.leftSlideMotor.getCurrentPosition() < 10) {
            this.power = Math.max(this.power, 0);
        }
    }

    public void handleInput(boolean up, boolean highPowerUp, boolean down, boolean highPowerDown) {
        this.power = this.defaultPower;

        if (up) {
            this.power += this.powerAmount;
        } else if (highPowerUp) {
            this.power += this.powerAmount * highPowerMultiplier;
        }

        if (down) {
            this.power = -this.powerAmount;
        } else if (highPowerDown) {
            this.power = -this.powerAmount * highPowerMultiplier;
        }

        if (this.lift) {
            this.power = -1.0f;
        }

        this.restrainPower();
    }

    public void toggleLift() {
        this.lift = !this.lift;
    }

    public void handleInput(float input, boolean highPower) {
        this.power = this.defaultPower + input * this.powerAmount * (highPower ? this.highPowerMultiplier : 1.0f);

        this.restrainPower();
    }

    public float getPower() {
        return this.power;
    }
}
