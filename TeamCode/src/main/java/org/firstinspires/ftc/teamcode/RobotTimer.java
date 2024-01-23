package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class RobotTimer {
    private double lastTime;
    private double cooldown;
    private final boolean repeating;

    private boolean isDone;
    private boolean isJustDone;

    public RobotTimer(ElapsedTime runtime, double cooldown, boolean startDone, boolean repeating) {
        lastTime = startDone ? 0.0 : runtime.seconds();

        this.cooldown = cooldown;
        this.repeating = repeating;

        isDone = startDone;
        isJustDone = startDone;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public void start(ElapsedTime runtime) {
        lastTime = runtime.seconds();
        isDone = false;
        isJustDone = false;
    }

    public void tick(ElapsedTime runtime) {
        if (repeating) {
            isDone = false;
            isJustDone = false;

            if (lastTime + cooldown < runtime.seconds()) {
                isDone = true;
                isJustDone = true;
            }
        } else {
            if (lastTime + cooldown < runtime.seconds()) {
                if (isDone) {
                    isJustDone = false;
                } else {
                    isDone = true;
                    isJustDone = true;
                }
            }
        }
    }

    public boolean done() {
        return isDone;
    }

    public boolean justDone() {
        return isJustDone;
    }
}
