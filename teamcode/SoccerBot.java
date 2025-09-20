package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="SoccerBot", group="Linear OpMode")
public class SoccerBot extends LinearOpMode {
    public DcMotor right, left;

    @Override
   public void runOpMode() {
        right = hardwareMap.get(DcMotor.class, "right");
        left = hardwareMap.get(DcMotor.class, "left");
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double move = gamepad1.left_stick_y*.8;
            double turn = gamepad1.right_stick_x*.8;
            right.setPower(move+turn);
            left.setPower((move-turn));
        }
   }
}
