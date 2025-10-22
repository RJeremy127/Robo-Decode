package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.control.Odometry;
import org.firstinspires.ftc.teamcode.util.HardwareMapper;

import java.util.Arrays;

@TeleOp(name = "RunTest")
public class RunTest extends LinearOpMode {
    private DcMotor[] motors;
    private Odometry otto;
    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);
        otto = new Odometry(new DcMotor[] {motors[0], motors[1], motors[2]});
        double max;
        double threshold = .3;
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        otto.resetEncoders();

        while (opModeIsActive()) {
            otto.updateOdometry();
            double move = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            if (Math.abs(move) < threshold) {
                move = 0;
            }
            if (Math.abs(strafe) < threshold) {
                strafe = 0;
            }
            if (Math.abs(turn) < 0) {
                turn = 0;
            }
            double leftFrontPower  = move + strafe+turn;
            double rightFrontPower = move - strafe-turn;
            double leftBackPower   = move - strafe+turn;
            double rightBackPower  = move + strafe-turn;


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

            telemetry.addData("odometry ", otto.getPose());
            telemetry.addData("Degreees", Math.toDegrees(otto.getPose().getR()));
            telemetry.addData("Pose Delta", otto.pose_delta);
            telemetry.addData("Sensor Deltas", Arrays.toString(otto.deltas));
            telemetry.addData("X delta", otto.deltas[0]+otto.deltas[1]);
            telemetry.addData("Back Enc", otto.backEnc);
            otto.updateOdometry();
            telemetry.update();
        }

    }
}

