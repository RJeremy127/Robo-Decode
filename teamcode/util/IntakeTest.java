package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "IntakeTest")
public class IntakeTest extends LinearOpMode {

    // Declare OpMode members.


    private DcMotor intake;
    private DcMotor[] motors;
    double max;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        intake = hardwareMap.get(DcMotor.class, "intake");
        motors = HardwareMapper.getMotors(hardwareMap);


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips


        // Wait for the game to start (driver presses START)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double move = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
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



            // Send calculated power to wheels
            if (gamepad1.square) {
                intake.setPower(-.8);
            }
            else {
                intake.setPower(0);
            }


            // Show the elapsed game time and wheel power.
            //telemetry.addData("Intake Power", );
            telemetry.update();
        }
    }
}
