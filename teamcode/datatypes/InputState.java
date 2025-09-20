package org.firstinspires.ftc.teamcode.datatypes;

import com.qualcomm.robotcore.hardware.Gamepad;

public class InputState { // this is used to store input states of controllers so you can save and compare states
    long timestamp;

    boolean a, b, x, y;
    boolean cross, circle, square, triangle;

    boolean left_bumper, right_bumper;

    float left_trigger, right_trigger;


    boolean dpad_up, dpad_down, dpad_left, dpad_right;

    boolean guide, share, options;

    Pair left_stick, right_stick;

    boolean touchpad_1, touchpad_2;
    Pair touchpad_finger_1, touchpad_finger_2;

    public Pair getTouchpad_finger_1() {return touchpad_finger_1;}


    public Pair getTouchpad_finger_2() {return touchpad_finger_2;}





    public boolean isOptions() {
        return options;
    }

    long nextRumbleApproxFinishTime;

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isA() {
        return a;
    }

    public boolean isB() {
        return b;
    }

    public boolean isX() {
        return x;
    }

    public boolean isY() {
        return y;
    }

    public boolean isCross() {
        return cross;
    }

    public boolean isCircle() {
        return circle;
    }

    public boolean isSquare() {
        return square;
    }

    public boolean isTriangle() {
        return triangle;
    }

    public boolean isLeft_bumper() {
        return left_bumper;
    }

    public boolean isRight_bumper() {
        return right_bumper;
    }

    public float getLeft_trigger() {
        return left_trigger;
    }

    public float getRight_trigger() {
        return right_trigger;
    }

    public boolean isDpad_up() {
        return dpad_up;
    }

    public boolean isDpad_down() {
        return dpad_down;
    }

    public boolean isDpad_left() {
        return dpad_left;
    }

    public boolean isDpad_right() {
        return dpad_right;
    }

    public boolean isGuide() {
        return guide;
    }

    public boolean isShare() {
        return share;
    }

    public Pair getLeft_stick() {
        return left_stick;
    }

    public Pair getRight_stick() {
        return right_stick;
    }

    public boolean isTouchpad_1() {
        return touchpad_1;
    }

    public boolean isTouchpad_2() {
        return touchpad_2;
    }

    public long getNextRumbleApproxFinishTime() {
        return nextRumbleApproxFinishTime;
    }

    public InputState(Gamepad gp) {
        timestamp = gp.timestamp;

        a = gp.a;
        cross = a;

        b = gp.b;
        circle = b;

        x = gp.x;
        square = x;

        y= gp.y;
        triangle = y;

        left_bumper = gp.left_bumper;
        right_bumper = gp.right_bumper;

        left_trigger = gp.left_trigger;
        right_trigger = gp.right_trigger;

        dpad_up = gp.dpad_up;
        dpad_down = gp.dpad_down;
        dpad_left = gp.dpad_left;
        dpad_right = gp.dpad_right;

        guide = gp.guide;
        share = gp.share;
        options = gp.options;

        left_stick = new Pair(gp.left_stick_x, gp.left_stick_y);
        right_stick = new Pair(gp.left_stick_x, gp.left_stick_y);

        touchpad_finger_1 = new Pair(gp.touchpad_finger_1_x, gp.touchpad_finger_1_y);
        touchpad_finger_2 = new Pair(gp.touchpad_finger_2_x, gp.touchpad_finger_2_y);

        touchpad_1 = gp.touchpad_finger_1;
        touchpad_2 = gp.touchpad_finger_2;
     }


    public void updateState(Gamepad gp) {
        timestamp = gp.timestamp;

        a = gp.a;
        cross = a;

        b = gp.b;
        circle = b;

        x = gp.x;
        square = x;

        y= gp.y;
        triangle = y;

        left_bumper = gp.left_bumper;
        right_bumper = gp.right_bumper;

        left_trigger = gp.left_trigger;
        right_trigger = gp.right_trigger;

        dpad_up = gp.dpad_up;
        dpad_down = gp.dpad_down;
        dpad_left = gp.dpad_left;
        dpad_right = gp.dpad_right;

        guide = gp.guide;
        share = gp.share;
        options = gp.options;

        left_stick = new Pair(gp.left_stick_x, gp.left_stick_y);
        right_stick = new Pair(gp.left_stick_x, gp.left_stick_y);

        touchpad_finger_1 = new Pair(gp.touchpad_finger_1_x, gp.touchpad_finger_1_y);
        touchpad_finger_2 = new Pair(gp.touchpad_finger_2_x, gp.touchpad_finger_2_y);

    }
}
