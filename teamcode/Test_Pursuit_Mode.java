
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PurePursuit.RobotMovement;
import org.firstinspires.ftc.teamcode.control.Movement;
import org.firstinspires.ftc.teamcode.control.Odometry;
import org.firstinspires.ftc.teamcode.datatypes.Pose;
import org.firstinspires.ftc.teamcode.util.HardwareMapper;
import org.firstinspires.ftc.teamcode.util.MathFunctions;

import java.util.Arrays;

@Autonomous(name="Test_Persuit_Mode", group="Linear OpMode")


public class Test_Pursuit_Mode extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor[] motors;
    private Movement movement;
    private DcMotor rightSlide;
    private DcMotor leftSlide;
    private Servo clawServo;
    private Servo clawWristServo;
    private Servo axleServoL;
    private Servo axleServoR;

    //send encoders to odometry in order              [leftDeadwheel,  rightDeadwheel, backDeadwheel]
    private Odometry otto;
    Pose targetPose = new Pose(new double[]{72, 0, Math.toRadians(0)});
    //BASKET AUTO PARK: 72, 0, 0  0.3
    //FAR AUTO PARK: 10, 0, 0    0.3
    //send encoders to odometry in order              [leftDeadwheel,  rightDeadwheel, backDeadwheel]


    @Override
    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);


        movement = new Movement(motors);
        otto = new Odometry(new DcMotor[] {motors[0], motors[1], motors[2]});


        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        double s = 0;
        double gowthams_speed_hehe = 0.1;

        otto.resetEncoders();
        //todo OP MODE CODEEEEEEEEEs
    while (opModeIsActive()) { // THIS IS THE MAIN LOOP

//
        if (MathFunctions.distance(otto.getPose().getPoint(), targetPose.getPoint()) > 0.6 || Math.abs(MathFunctions.angleWrap(targetPose.getR()-otto.getPose().getR())) > Math.toRadians(3)) {
            double max;
            telemetry.addData("Dist", MathFunctions.distance(otto.getPose().getPoint(), targetPose.getPoint()));
            double[] move = RobotMovement.goToPosition(targetPose, 0.5, 0.35);
            telemetry.addData("Move Array", Arrays.toString(move));


            double axial   = move[1];  // Note: pushing stick forward gives negative value
            double lateral =  move[0];
            double yaw     =  move[2];


            double leftFrontPower  = axial + lateral+yaw;
            double rightFrontPower = axial - lateral-yaw;
            double leftBackPower   = axial - lateral+yaw;
            double rightBackPower  = axial + lateral-yaw;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;
            }

            // Send calculated power to wheels
            motors[3].setPower(leftFrontPower);
            motors[2].setPower(rightFrontPower);
            motors[1].setPower(leftBackPower);
            motors[0].setPower(rightBackPower);
        } else {
            motors[3].setPower(0);
            motors[2].setPower(0);
            motors[1].setPower(0);
            motors[0].setPower(0);
        }

        telemetry.addData("s ", s);
        telemetry.addData("touch x", gamepad1.touchpad_finger_1_x);
        telemetry.addData("touch y", gamepad1.touchpad_finger_1_y);
        telemetry.addData("touch2 x", gamepad1.touchpad_finger_2_x);
        telemetry.addData("touch2 y", gamepad1.touchpad_finger_2_y);
        telemetry.addData("gowthams Speed", gowthams_speed_hehe);
        telemetry.addData("odometry:", otto.getPose());
        telemetry.update();

        otto.updateOdometry();
        }
    }



    private static void temp_old_drive(Gamepad gamepad1, Gamepad gamepad2, double gowthams_speed_hehe, DcMotor[] motors) {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        if (Math.abs(axial)*0.8 >Math.abs(lateral)) {
            lateral=0;
        } else if (Math.abs(lateral)*0.8 > Math.abs(axial)) {
            axial=0;
        }
        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }


        // Send calculated power to wheels
        motors[3].setPower(leftFrontPower*gowthams_speed_hehe);
        motors[2].setPower(rightFrontPower*gowthams_speed_hehe);
        motors[1].setPower(leftBackPower*gowthams_speed_hehe);
        motors[0].setPower(rightBackPower*gowthams_speed_hehe);
    }
}
