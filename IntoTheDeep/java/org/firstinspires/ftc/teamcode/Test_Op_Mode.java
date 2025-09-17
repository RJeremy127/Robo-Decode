
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.Movement;
import org.firstinspires.ftc.teamcode.control.Odometry;
import org.firstinspires.ftc.teamcode.datatypes.InputState;
import org.firstinspires.ftc.teamcode.datatypes.Pair;
import org.firstinspires.ftc.teamcode.rendertypes.BoundingBox;
import org.firstinspires.ftc.teamcode.rendertypes.Display;
import org.firstinspires.ftc.teamcode.rendertypes.GameMap;
import org.firstinspires.ftc.teamcode.util.HardwareMapper;

@TeleOp(name="Test_Op_Mode", group="Linear OpMode")


public class Test_Op_Mode extends LinearOpMode {


    private GameMap field;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor[] motors;
    private Movement movement;
    private DcMotor rightSlideMotor, leftSlideMotor;
    private Servo testservo, clawWristServo, clawServo;
    private Servo axleServoL, axleServoR;



    private Odometry otto;
    private Display disp;
    private GameMap map;

    private BoundingBox bbTest = new BoundingBox(new Pair[]{
            new Pair(-3, -6),
            new Pair(3, -6),
            new Pair(3, 6),
            new Pair(-3, 6),


    });

    @Override
    public void runOpMode() {
        motors = HardwareMapper.getMotors(hardwareMap);
        //slideMotor = hardwareMap.get(DcMotor.class, "slide");
        rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlide");
        leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlide");
        axleServoL = hardwareMap.get(Servo.class, "axleServoL");
        axleServoR = hardwareMap.get(Servo.class, "axleServoR");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawWristServo = hardwareMap.get(Servo.class, "clawWristServo");

        axleServoL.setDirection(Servo.Direction.REVERSE);

        leftSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftSlideMotor.setTargetPosition(0);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightSlideMotor.setTargetPosition(0);
        rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE); //reverse one of the motors


        movement = new Movement(motors);
        otto = new Odometry(new DcMotor[] {motors[0], motors[1], motors[2]});
        disp = new Display(25, 25, telemetry);
        map = new GameMap(new Pair(144, 144));

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        InputState pgp1 = new InputState(gamepad1); // previous gamepad1, used to save past input states to test for button changes
        InputState pgp2 = new InputState(gamepad2);
        boolean canDrive = false;
        boolean runDisplay = false;

        boolean bucketScoring = true;

//        Gamepad.RumbleEffect rumbler = new Gamepad.RumbleEffect.Builder()
//                .addStep(0.0, 1.0, 500)
//                .addStep(0.0, 0.0, 300)
//                .addStep(1.0, 0.0, 250)
//                .addStep(0.0, 0.0, 250)
//                .addStep(1.0, 0.0, 250).build();
//
//        Gamepad.RumbleEffect.Builder rippleBuilder = new Gamepad.RumbleEffect.Builder();
//
//        for (int i = 0; i < 100; i++) {
//            // Example logic for step values; you can modify this based on your needs.
//
//            // Add step to the builder
//            rippleBuilder.addStep(i/100.0, 1-(i/100.0), 30);
//        }
//        for (int i = 0; i < 100; i++) {
//            // Example logic for step values; you can modify this based on your needs.
//            // Add step to the builder
//            rippleBuilder.addStep(1-(i/100.0), (i/100.0), 30);
//        }
//
//// Build the final RumbleEffect
//        Gamepad.RumbleEffect ripple = rippleBuilder.build();

        double gowthams = 0.5;
        double slideGowthams = 1;


        double axleAngle = (axleServoL.getPosition() + axleServoR.getPosition())/2;
        axleServoL.setPosition(0.5);
        axleServoR.setPosition(0.5);
        double clawServoPos = 0.12;
        clawServo.setPosition(clawServoPos);

        double clawWristServoPos = 0.5;
        clawWristServo.setPosition(clawWristServoPos);

        int leftSlidePos = 0;
        int rightSlidePos = 0;

        int leftSlidePosOffset = 0;
        int rightSlidePosOffset = 0;




        otto.resetEncoders();
        disp.fill('.');

    while (opModeIsActive()) { // todo THIS IS THE MAIN LOOP
        otto.updateOdometry();
        // grab input state at beginning of loop and put it into an object
        InputState gp1 = new InputState(gamepad1);
        InputState gp2 = new InputState(gamepad2);

        // todo THUMBSTICKS to drive
        if (canDrive) {
            if ((axleServoL.getPosition() > 0.69) || axleServoL.getPosition() < 0.45)  {
                temp_old_drive(gamepad1, gamepad2, 0.45, motors, telemetry);
            } else {
                temp_old_drive(gamepad1, gamepad2, gowthams, motors, telemetry);
            }
        }

        // todo OPTIONS to toggle driving GUIDE to toggle display
        if (gp1.isOptions() && !pgp1.isOptions()) {
            if (canDrive) {
                gamepad1.rumbleBlips(1);
            } else {
                gamepad1.rumbleBlips(2);
            }
            canDrive = !canDrive;
        }
        if (gp1.isShare() && !pgp1.isShare()) {
            if (runDisplay) {
                gamepad1.rumbleBlips(1);
            } else {
                gamepad1.rumbleBlips(2);
            }
            runDisplay = !runDisplay;
        }

        if (gp2.isGuide() && !pgp2.isGuide()) {
            if (bucketScoring) {
                gamepad1.rumbleBlips(1);
            } else {
                gamepad1.rumbleBlips(2);
            }
            bucketScoring = !bucketScoring;
        }
        // todo Crosspad thing
        if (gp1.isDpad_right() && !pgp1.isDpad_right()) { // open and close claw
            if (clawServo.getPosition() > 0.22) {
                clawServo.setPosition(0.11);
            } else {
                clawServo.setPosition(0.46);
            }
        }
        if (gp1.isDpad_left() && !pgp1.isDpad_left()) { // open and close claw
            clawWristServoPos = 0.5;
            clawWristServo.setPosition(clawWristServoPos);
        }

        if (gp1.isDpad_up()) { // rotate claw formard and back
            clawWristServoPos += 0.01;
            clawWristServo.setPosition(clawWristServoPos);
        } else if (gp1.isDpad_down()) {
            clawWristServoPos -= 0.01;
            clawWristServo.setPosition(clawWristServoPos);
        }
        clawWristServoPos = bound(clawWristServoPos, 0.146, 0.772);

        // todo REGULAR BUTTONS
        if (gp1.isSquare()) {
            axleAngle = 0.73;
            axleServoR.setPosition(axleAngle);
            axleServoL.setPosition(axleAngle);

            clawWristServoPos = 0.5;
            clawWristServo.setPosition(clawWristServoPos);
            clawServo.setPosition(0.46);

        }
        if (gp1.isCircle()) {
            axleAngle = 0.44;
            axleServoR.setPosition(axleAngle);
            axleServoL.setPosition(axleAngle);


            leftSlidePos = -1980;
            leftSlideMotor.setTargetPosition(leftSlidePos + leftSlidePosOffset);
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSlideMotor.setPower(slideGowthams);

            rightSlidePos = -1980;
            rightSlideMotor.setTargetPosition(rightSlidePos + rightSlidePosOffset);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setPower(slideGowthams);
        }

        if (gp1.isTriangle())  {
            leftSlidePos -= (int)(30);
            leftSlideMotor.setTargetPosition(leftSlidePos + leftSlidePosOffset);
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSlideMotor.setPower(slideGowthams);

            rightSlidePos -= (int)(30);
            rightSlideMotor.setTargetPosition(rightSlidePos + rightSlidePosOffset);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setPower(slideGowthams);
        } else if (gp1.isCross()) {
            leftSlidePos += (int)(30);
            leftSlideMotor.setTargetPosition(leftSlidePos + leftSlidePosOffset);
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSlideMotor.setPower(slideGowthams);

            rightSlidePos += (int)(30);
            rightSlideMotor.setTargetPosition(rightSlidePos + rightSlidePosOffset);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setPower(slideGowthams);
        } else if (pgp1.isTriangle() || pgp1.isCross()) {
            rightSlidePos = rightSlideMotor.getCurrentPosition();
            leftSlidePos = leftSlideMotor.getCurrentPosition();

            leftSlideMotor.setTargetPosition(leftSlidePos + leftSlidePosOffset);
            leftSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftSlideMotor.setPower(slideGowthams);

            rightSlideMotor.setTargetPosition(rightSlidePos + rightSlidePosOffset);
            rightSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightSlideMotor.setPower(slideGowthams);

        }
        leftSlidePos = (int)bound(leftSlidePos, -1980, 0);
        rightSlidePos = (int)bound(rightSlidePos, -1980, 0);

        // todo TRIGGERS
        if (gp1.getLeft_trigger() > 0.9) { // up
            axleAngle-= 0.0035;
            axleServoL.setPosition(axleAngle);
            axleServoR.setPosition(axleAngle);
        } else if (gp1.getRight_trigger() > 0.9) {
            axleAngle+=0.0035;
            axleServoL.setPosition(axleAngle);
            axleServoR.setPosition(axleAngle);
        } else if (pgp1.getRight_trigger() > 0.9 || pgp1.getLeft_trigger() > 0.9) {
            axleAngle = (axleServoL.getPosition() + axleServoR.getPosition())/2;
            axleServoL.setPosition(axleAngle);
            axleServoR.setPosition(axleAngle);
        }

        // 0.73 pick up
        // 0.67 lift
        //

        axleAngle = bound(axleAngle, 0.42, 0.74);




        // todo BUMPERS

        if (gp1.isLeft_bumper() && !pgp1.isLeft_bumper()) {

            if (!canDrive) {
                slideGowthams += 0.1;
            } else {
                gowthams-= 0.1;
            }
        } else if (gp1.isRight_bumper() && !pgp1.isRight_bumper()) {
            if (!canDrive) {
                slideGowthams -= 0.1;
            } else {
                gowthams+= 0.1;
            }

        }




        // todo TOUCHPAD
        if ((gp1.isTouchpad_1() && gp1.isTouchpad_2()) && (pgp1.isTouchpad_1() && pgp1.isTouchpad_2())) {
            double distance_delta = gp1.getTouchpad_finger_1().distance(gp1.getTouchpad_finger_2()) -
                    pgp1.getTouchpad_finger_1().distance(pgp1.getTouchpad_finger_2());;//distance between fingers
            gowthams += distance_delta/4;// the 4 is just to scale down the speed of change
        }


        // todo GP2
        if (gp2.isLeft_bumper() && !pgp2.isLeft_bumper()) {
            leftSlidePosOffset -= 10;
        } else if (gp2.getLeft_trigger() > 0.2 && !(pgp2.getLeft_trigger() > 0.2)) {
            leftSlidePosOffset += 10;
        }

        if (gp2.isRight_bumper() && !pgp2.isRight_bumper()) {
            rightSlidePosOffset -= 10;
        } else if (gp2.getRight_trigger() > 0.2 && !(pgp2.getRight_trigger() > 0.2)) {
            rightSlidePosOffset +=10;
        }

        if (gp2.isGuide() && !pgp2.isGuide()) {
            if (bucketScoring) {
                gamepad1.rumbleBlips(1);
            } else {
                gamepad1.rumbleBlips(2);
            }
            bucketScoring = !bucketScoring;
        }


        // todo TELEMETRY AND DISPLAY
        if (runDisplay) {
            map.renderBuffer();
            disp.fill(' ');
            disp.fill(map.sampleImage(new Pair(otto.getPose().getX(), otto.getPose().getY()), disp.getHeight(), disp.getWidth())); // SAMPLES FROM TOP LEFT CORNER BASED ON CENTER INPUT
            bbTest.rotate(otto.getPose().getR());
            disp.addPixels(bbTest.render());
            disp.update();
        } else {
            telemetry.addData("gowthams Speed", gowthams);
            telemetry.addData("Driving is ", ((canDrive) ? "enabled" : "disabled"));
            telemetry.addData("Scoring by ", ((bucketScoring) ? "bucket" : "specimen"));
            telemetry.addData("left motorpos", leftSlideMotor.getCurrentPosition() + " + " + leftSlidePosOffset);
            telemetry.addData("right motorpos", rightSlideMotor.getCurrentPosition() + " + " + rightSlidePosOffset);
//            telemetry.addData("left target motorpos", leftSlidePos);
//            telemetry.addData("right target motorpos", rightSlidePos);

            telemetry.addData("odometry ", otto.getPose());
            telemetry.addData("Axle Servo ", axleAngle);
            telemetry.addData("Claw Wrist Servo ", clawWristServo.getPosition());
            telemetry.addData("Claw Servo", clawServo.getPosition());

            telemetry.addData(" ", " ");
            telemetry.addData("PRESET POSITION REFERENCES", "");
            telemetry.addData("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", "");

            if (bucketScoring) {

            } else {
                telemetry.addData("Grab Sample : ", "0.00");
                telemetry.addData("Lift Sample : ", "0.00");
                telemetry.addData("[deposit sample to human player]", " ");
                telemetry.addData("Grab specimen ", "0.00");
                telemetry.addData("T1 hang specimen ", "0.00");
                telemetry.addData("T2 hang specimen", "0.00");
            }


//            telemetry.addData("raw left thumbstick", gamepad1.left_stick_x + ", " + gamepad1.left_stick_y);
//            telemetry.addData("raw right thumbstick", gamepad1.right_stick_x + ", " + gamepad1.right_stick_y);
           // telemetry.addData("encoder deltas", encoder_delta[0]+ ", " + encoder_delta[1] + ", " + encoder_delta[2]);
//            telemetry.addData("left odo", Odometry.ticksToIn(motors[0].getCurrentPosition()));
//            telemetry.addData("back odo", Odometry.ticksToIn(motors[1].getCurrentPosition()));
//            telemetry.addData("right odo", Odometry.ticksToIn(motors[2].getCurrentPosition()));
            telemetry.update();
        }


        gowthams = bound(gowthams, 0, 1);
        slideGowthams = bound(slideGowthams, 0, 1);
        pgp1 = gp1; // saving previous input state for gamepad1
        pgp2 = gp2;
        otto.updateOdometry();
        }
    }


    public static double bound(double i, double l, double u) {
        return (i < l) ? l : (i > u) ? u : i;
    }


    private void temp_old_drive(Gamepad gamepad1, Gamepad gamepad2, double gowthams_speed_hehe, DcMotor[] motors, Telemetry telemetry) {
        double max;

        //gamepad1.rumble(gowthams_speed_hehe * Math.abs(gamepad1.left_stick_x), gowthams_speed_hehe * Math.abs(gamepad1.left_stick_y), 30);

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x * (1-(gowthams_speed_hehe/2));





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
