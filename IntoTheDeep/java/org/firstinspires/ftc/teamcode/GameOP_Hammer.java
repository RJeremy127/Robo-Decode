package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.Random;


@TeleOp(name="Game: Hammer 0", group="Linear OpMode")

public class GameOP_Hammer extends LinearOpMode {

    public static final double GAMETIME = 120;

    private double gowtham_speed = 0.6;

    private double hammerpos = 0;
    private final double HAMMER_MAX = .96; //todo DO NOT SEt tthis To 000000 !!!!!!
    private final double HAMMER_MIN = .4; //todo DO NOT set this TO 11111!!!!!!!
    private static final int DISABLED_TIME = 5;

    private static int shields = 0;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private Servo hammer = null;

    private TouchSensor touch_l;
    private TouchSensor touch_r;
    private boolean disabled = false;


    //disabled time

    private int shield = 0;
    private double disabled_until = 0;

    private int score = 10;

    @Override
    public void runOpMode() {


        leftDrive = hardwareMap.get(DcMotor.class, "left");
        rightDrive = hardwareMap.get(DcMotor.class, "right");
        hammer = hardwareMap.get(Servo.class, "hammer");
        touch_r = hardwareMap.get(TouchSensor.class, "touch_r");
        touch_l = hardwareMap.get(TouchSensor.class, "touch_l");

        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < GAMETIME)) {
            if ((touch_r.isPressed() || touch_l.isPressed()) && !disabled) {
                if (shield > 0) {
                    shield --;
                }
                else {
                    disabled_until = (runtime.seconds() + DISABLED_TIME);
                    score -= 5;
                }
            }
            disabled = (disabled_until > runtime.seconds());

            if (!disabled) {
                double drive = gamepad1.left_stick_y;
                double turn = gamepad1.right_stick_x;
                double rightPower = (drive + turn) * gowtham_speed;
                double leftPower = (drive - turn) * gowtham_speed;

                leftDrive.setPower(leftPower);
                rightDrive.setPower(rightPower);

                //hammer.setPosition(gamepad1.right_trigger * (HAMMER_MAX - HAMMER_MIN) + HAMMER_MIN);
                if (gamepad1.right_bumper) {
                    hammerpos += .5;
                } else if (gamepad1.left_bumper) {
                    hammerpos -= .5;
                }

                if (hammerpos < HAMMER_MIN) {
                    hammerpos = HAMMER_MIN;
                } else if (hammerpos > HAMMER_MAX) {
                    hammerpos = HAMMER_MAX;
                }

                hammer.setPosition(hammerpos);

            }
            else {
                leftDrive.setPower(0);
                rightDrive.setPower(0);
            }

            // Show the elapsed game time and wheel power.
            telemetry.addData("Run Time", runtime.seconds());
            telemetry.addData("Time Remaining", GAMETIME - runtime.seconds());
            telemetry.addData("Gowtham Speed", gowtham_speed);
            telemetry.addData("Hammer Position", hammer.getPosition() + ", " + hammerpos);
            telemetry.addData("Disabled", disabled);
            telemetry.addData("Score: ", score);
            telemetry.addData("speed :", score);
            telemetry.update();
        }
    }
}
