package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Drive {

    private DcMotor[] motors;

    public Drive(DcMotor[] motors) {
        this.motors = motors;
    }

    public void moveForward(double power) {
        motors[Constants.FRONT_LEFT].setPower(power);
        motors[Constants.BACK_LEFT].setPower(power);
        motors[Constants.FRONT_RIGHT].setPower(power);
        motors[Constants.BACK_RIGHT].setPower(power);
    }
    public void moveBackward(double power) {
        moveForward(-power);
    }


    public void Left(double power) {
        motors[Constants.FRONT_LEFT].setPower(power);
        motors[Constants.BACK_LEFT].setPower(-power);
        motors[Constants.FRONT_RIGHT].setPower(power);
        motors[Constants.BACK_RIGHT].setPower(-power);
    }

    public void Right(double power) {
        motors[Constants.FRONT_LEFT].setPower(-power);
        motors[Constants.BACK_LEFT].setPower(power);
        motors[Constants.FRONT_RIGHT].setPower(-power);
        motors[Constants.BACK_RIGHT].setPower(power);
    }

    // Stop all motors
    public void stop() {
        for (DcMotor motor : motors) {
            motor.setPower(0);
        }
    }
}