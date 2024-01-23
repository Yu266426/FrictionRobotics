package org.firstinspires.ftc.teamcode;

public class GripperController {
    private final float closedPos, openPos;

    private float pos;

    public GripperController(float closedPos, float openPos) {
        this.closedPos = closedPos;
        this.openPos = openPos;

        pos = openPos;
    }

    public void getInput(boolean closed) {
        if (closed) {
            pos = closedPos;
        } else {
            pos = openPos;
        }
    }

    public float getPos() {
        return pos;
    }
}
