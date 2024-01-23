package org.firstinspires.ftc.teamcode;

public class Toggle {
    private boolean isToggling;
    private boolean isToggled;
    private boolean isJustToggled;

    Toggle() {
        isToggling = false;
        isToggled = false;
        isJustToggled = false;
    }

    public void toggle(boolean value) {
        if (value) {
            if (!isToggling) {
                isToggled = !isToggled;
                isJustToggled = true;
                isToggling = true;
            } else {
                isJustToggled = false;
            }
        } else {
            isToggling = false;
        }
    }

    public void turnOff() {
        this.isToggled = false;
        this.isJustToggled = false;
        this.isToggling = false;
    }

    public void turnOn() {
        this.isToggled = true;
        this.isJustToggled = false;
        this.isToggling = false;
    }

    public boolean on() {
        return isToggled;
    }

    public boolean justToggled() {
        return isJustToggled;
    }
}
