package org.firstinspires.ftc.teamcode.vision.Limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcontroller.internal.HardwareMapper;

@TeleOp(name = "LimelightTrackingTeleOp")
public class RunTest extends LinearOpMode {
    private Limelight3A limelight;
    private DcMotor[] motors;
    private DcMotorEx intake;

    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        intake = hardwareMap.get(DcMotorEx.class,  "intake");
        limelight.pipelineSwitch(5);

        int currentPipeline = 5;
        boolean squarePressed = false;
        boolean circlePressed = false;
        boolean trianglePressed = false;
        boolean autoApproaching = false;

        double max;
        double threshold = 0.2;

        double kP = 0.025;
        double minAssist = 0.08;
        double maxAssist = 0.5;
        double deadzone = 5.0;
        double assistStrength = 1.0;

        double limelightHeight = 235.0;
        double targetStopDistance = 0.0;
        double approachSpeed = 0.4;

        double intakePower = 0.0;
        double intakeIncrement = 0.01;


        telemetry.addData("Aim Assist", assistStrength * 100 + "%");
        telemetry.update();

        waitForStart();
        limelight.start();

        while (opModeIsActive()) {
            if (gamepad1.square && !squarePressed) {
                currentPipeline = 4;
                limelight.pipelineSwitch(4);
                squarePressed = true;
            } else if (!gamepad1.square) {
                squarePressed = false;
            }

            if (gamepad1.circle && !circlePressed) {
                currentPipeline = 5;
                limelight.pipelineSwitch(5);
                circlePressed = true;
            } else if (!gamepad1.circle) {
                circlePressed = false;
            }

            if (gamepad1.triangle && !trianglePressed) {
                autoApproaching = !autoApproaching;
                trianglePressed = true;
            } else if (!gamepad1.triangle) {
                trianglePressed = false;
            }
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

            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double ty = result.getTy();
                double ta = result.getTa();

                double distance = Math.abs(limelightHeight / Math.tan(Math.toRadians(ty))) + 30.0;

                telemetry.addData("Target X", tx);
                telemetry.addData("Target Y", ty);
                telemetry.addData("Distance (mm)", String.format("%.1f", distance));
                telemetry.addData("Pipeline", currentPipeline);
                telemetry.addData("Auto Approach", autoApproaching ? "ON" : "OFF");

                if (autoApproaching) {
                    move = approachSpeed;

                    if (Math.abs(tx) > deadzone) {
                        double limelightTurn = tx * kP * assistStrength;

                        if (Math.abs(limelightTurn) > 0.01 && Math.abs(limelightTurn) < minAssist) {
                            limelightTurn = Math.signum(limelightTurn) * minAssist;
                        }

                        limelightTurn = Math.max(-maxAssist, Math.min(maxAssist, limelightTurn));

                        turn = limelightTurn;

                        telemetry.addData("Tracking", String.format("%.3f", limelightTurn));
                    } else {
                        turn = 0;
                        telemetry.addData("Tracking", "CENTERED");
                    }

                    strafe = 0;

                    telemetry.addData("Status", "DRIVING TO TARGET");
                } else if (!autoApproaching) {
                    if (Math.abs(tx) > deadzone) {
                        double limelightTurn = tx * kP * assistStrength;

                        if (Math.abs(limelightTurn) > 0.01 && Math.abs(limelightTurn) < minAssist) {
                            limelightTurn = Math.signum(limelightTurn) * minAssist;
                        }

                        limelightTurn = Math.max(-maxAssist, Math.min(maxAssist, limelightTurn));

                        turn += limelightTurn;

                        telemetry.addData("Assist Power", String.format("%.3f", limelightTurn));
                    } else {
                        telemetry.addData("Assist Power", "LOCKED ON");
                    }
                }
            } else {
                telemetry.addLine("No Target Found");
                telemetry.addData("Pipeline", currentPipeline);
                telemetry.addData("Auto Approach", autoApproaching ? "ON" : "OFF");

                if (autoApproaching) {
                    move = 0;
                    autoApproaching = false;
                    telemetry.addData("Status", "TARGET LOST - STOPPED");
                }
            }


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

            telemetry.update();
        }
    }
}