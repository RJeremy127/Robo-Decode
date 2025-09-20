
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;
import com.qualcomm.robotcore.hardware.Servo;//andy lau add :)

import org.firstinspires.ftc.teamcode.util.HardwareMapper;

@TeleOp(name="Andys_Op_Mode", group="Linear OpMode")
@Disabled

public class Andys_Op_Mode extends LinearOpMode {
    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor[] motors;
    private Servo myServo, intake;//andy lau add :)
    private boolean check = false;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        motors = HardwareMapper.getMotors(hardwareMap);
        myServo = hardwareMap.get(Servo.class, "deposit1");//andy lau add :)
        intake = hardwareMap.get(Servo.class, "intake");


        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) { // THIS IS THE MAIN LOOP
            double max;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = 0;//-gamepad1.left_stick_y + (-gamepad2.left_stick_y);  // Note: pushing stick forward gives negative value
            double lateral = 0;// gamepad1.left_stick_x + gamepad2.left_stick_x;
            double yaw     =  0;//(-gamepad1.right_stick_x) + (-gamepad2.right_stick_x);

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



            if (gamepad1.a) {
                // Move servo to one position,added limits in order to stop servo from over extending, setting a range
                //intake
                intake.setPosition(0.58);
            } else if (gamepad1.b) {
                // Move servo to another position, added limits in order to stop servo from over extending
                //retract
                intake.setPosition(0.43);
            }
            
            //intake.setPosition(gamepad1.left_stick_x);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("Servo Position", myServo.getPosition());//andy lau add :)
            telemetry.addData("Slide Servo Pos", intake.getPosition());
            telemetry.addData("A:", gamepad1.a);
            telemetry.addData("B:", gamepad1.b);
            telemetry.addData("Check", check);
            telemetry.addData("Servo", intake.toString());

            telemetry.update();

        }
    }}
