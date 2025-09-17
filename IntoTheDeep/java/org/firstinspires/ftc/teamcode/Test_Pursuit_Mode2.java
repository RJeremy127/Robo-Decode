
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
import org.firstinspires.ftc.teamcode.datatypes.Pair;
import org.firstinspires.ftc.teamcode.datatypes.Pose;
import org.firstinspires.ftc.teamcode.util.HardwareMapper;
import org.firstinspires.ftc.teamcode.util.MathFunctions;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name="Test_Persuit_Mode2", group="Linear OpMode")


public class Test_Pursuit_Mode2 extends LinearOpMode {



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
    ArrayList<Pose> path;
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


        path = new ArrayList<>();
        path.add(new Pose(10, 10, 0));
        path.add(new Pose(0, 0, 0));


        while (opModeIsActive()) {
            Pose target = path.get(0);
            otto.updateOdometry();

            double dx = target.getX() - otto.getPose().getX();
            double dy = target.getY() - otto.getPose().getY();
            double dr = target.getR() - otto.getPose().getR();
            double nv = Math.sqrt(dx*dx + dy*dy); // normalization value
            if (nv < 1) {
                path.remove(0);
            }
            Pair leftStick = new Pair(dx/nv, dr/nv);







            telemetry.addData("odometry:", otto.getPose());
            telemetry.update();
        }
    }



    private static void temp_old_drive(Pair leftStick, Pair rightStick, double gowthams, DcMotor[] motors) {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -leftStick.getY(); // Note: pushing stick forward gives negative value
        double lateral =  leftStick.getX();
        double yaw     =  rightStick.getX();

//        if (Math.abs(axial)*0.8 >Math.abs(lateral)) {
//            lateral=0;
//        } else if (Math.abs(lateral)*0.8 > Math.abs(axial)) {
//            axial=0;
//        }
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
        motors[3].setPower(leftFrontPower*gowthams);
        motors[2].setPower(rightFrontPower*gowthams);
        motors[1].setPower(leftBackPower*gowthams);
        motors[0].setPower(rightBackPower*gowthams);
    }
}
