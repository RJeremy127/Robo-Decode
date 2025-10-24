package org.firstinspires.ftc.robotcontroller;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "DriveWithCatapult")
public class Poop extends LinearOpMode {
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor catapult = null;

    @Override
    public void runOpMode() {
        // Map motors
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        catapult = hardwareMap.get(DcMotor.class, "shoot");

        // Reverse right motor so both move forward together
        right.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // === Drive control ===
            double drive = -gamepad1.left_stick_y;  // Forward/backward
            double turn = gamepad1.left_stick_x;    // Left/right turn

            double leftPower = drive + turn;
            double rightPower = drive - turn;

            leftPower = Math.max(-1.0, Math.min(1.0, leftPower));
            rightPower = Math.max(-1.0, Math.min(1.0, rightPower));

            left.setPower(leftPower);
            right.setPower(rightPower);

            // === Catapult control ===
            if (gamepad1.x) {
                // X → Forward (shoot)
                catapult.setPower(1.0);
            } else if (gamepad1.b) {
                // O → Backward (reload)
                catapult.setPower(-1.0);
            } else {
                // Stop motor when no button pressed
                catapult.setPower(0.0);
            }

            // Telemetry feedback
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Catapult Power", catapult.getPower());
            telemetry.update();
        }
    }
}
