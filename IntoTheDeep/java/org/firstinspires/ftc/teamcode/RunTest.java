package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareMapper;

@TeleOp(name = "RunTest")
@Disabled
public class RunTest extends LinearOpMode {
    private DcMotor[] motors;
    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);
        double max;
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double move = gamepad1.left_stick_y;
            double strafe = gamepad1.right_stick_x;
            double leftFrontPower  = move + strafe;
            double rightFrontPower = move - strafe;
            double leftBackPower   = move - strafe;
            double rightBackPower  = move + strafe;


            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            motors[3].setPower(leftFrontPower);
            motors[2].setPower(rightFrontPower);
            motors[1].setPower(leftBackPower);
            motors[0].setPower(rightBackPower);
        }

    }
}
