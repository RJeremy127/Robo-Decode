package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.datatypes.Pose;

import static org.firstinspires.ftc.teamcode.util.Constants.BACK_LEFT;
import static org.firstinspires.ftc.teamcode.util.Constants.BACK_RIGHT;
import static org.firstinspires.ftc.teamcode.util.Constants.FRONT_LEFT;
import static org.firstinspires.ftc.teamcode.util.Constants.FRONT_RIGHT;
public class Movement {


    private DcMotor[] motors;
    private Pose deltaPose;
    public Movement(DcMotor[] motors) {
        this.motors = motors;
    }

    public void move(Pose deltaPose) {
        double axial = deltaPose.getY();  // Note: pushing stick forward gives negative value
        double lateral =  deltaPose.getX();
        double yaw =  deltaPose.getR();

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;

        double backLeftPower   = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;

        double[] powers = new double[] {backRightPower, backLeftPower, frontRightPower, frontLeftPower};
        double max = Math.max(Math.abs(powers[FRONT_LEFT]), Math.abs(powers[FRONT_RIGHT]));
        max = Math.max(max, Math.abs(powers[BACK_LEFT]));
        max = Math.max(max, Math.abs(powers[BACK_RIGHT]));

        //normalize the powers while preserving ratio if out of range
        if (max > 1.0) {
            for (int i = 0; i<powers.length; i++) {
                powers[i] /= max;
            }
        }
        // set motor powers
        for (int i = 0; i<powers.length; i++)  {
            motors[i].setPower(powers[i]);
        }
    }

    public void move(Pose deltaPose, double gowtham) {
        double axial = deltaPose.getY();  // Note: pushing stick forward gives negative value
        double lateral =  deltaPose.getX();
        double yaw =  deltaPose.getR();

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;

        double backLeftPower   = axial - lateral + yaw;
        double backRightPower = axial + lateral - yaw;

        double[] powers = new double[] {backRightPower, backLeftPower, frontRightPower, frontLeftPower};
        double max = Math.max(Math.abs(powers[FRONT_LEFT]), Math.abs(powers[FRONT_RIGHT]));
        max = Math.max(max, Math.abs(powers[BACK_LEFT]));
        max = Math.max(max, Math.abs(powers[BACK_RIGHT]));

        //normalize the powers while preserving ratio if out of range
        if (max > 1.0) {
            for (int i = 0; i<powers.length; i++) {
                powers[i] /= max;
            }
        }
        // set motor powers
        for (int i = 0; i<powers.length; i++)  {
            motors[i].setPower(powers[i]*gowtham);
        }
    }
}
