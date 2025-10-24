package org.firstinspires.ftc.teamcode.vision.Limelight;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcontroller.internal.HardwareMapper;

@TeleOp(name = "BasicDriveTeleOp")
public class BasicDrive extends LinearOpMode {
    private DcMotor[] motors;
    private DcMotorEx intake;

    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);
        intake = hardwareMap.get(DcMotorEx.class,  "intake");

        double max;
        double threshold = 0.2;
        double intakePower = 0.0;
        double intakeIncrement = 0.01;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double move = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            if (Math.abs(move) < threshold) {
                move = 0;
            }
            if (Math.abs(strafe) < threshold) {
                strafe = 0;
            }
            if (Math.abs(turn) < threshold) {
                turn = 0;
            }

            // Intake control
            if (gamepad1.left_bumper) {
                telemetry.addData("Status", "left trigger");
                intakePower += intakeIncrement;
                if (intakePower > 1.0) {
                    intakePower = 1.0;
                }
            } else if (gamepad1.right_bumper) {
                intakePower -= intakeIncrement;
                telemetry.addData("Status", "right trigger");
                if (intakePower < -1.0) {
                    intakePower = -1.0;
                }
            } else {

                if (intakePower > 0) {
                    intakePower -= intakeIncrement;
                    if (intakePower < 0) intakePower = 0;
                } else if (intakePower < 0) {
                    intakePower += intakeIncrement;
                    if (intakePower > 0) intakePower = 0;
                }
            }

            intake.setPower(intakePower);

            double leftFrontPower  = move + strafe + turn;
            double rightFrontPower = move - strafe - turn;
            double leftBackPower   = move - strafe + turn;
            double rightBackPower  = move + strafe - turn;

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
            motors[1].setPower(-leftBackPower);
            motors[0].setPower(rightBackPower);

            telemetry.addData("Status", "Running");
            telemetry.addData("Intake Power", String.format("%.0f%%", intakePower * 100));
            telemetry.update();
        }
    }
}