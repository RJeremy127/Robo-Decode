package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "SlidesTest")
@Disabled
public class SlidesTest extends LinearOpMode {
    private DcMotor slide;

    @Override
    public void runOpMode() {
        slide = hardwareMap.get(DcMotor.class, "slide");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.circle) {
                slide.setPower(.2);
            }
            else if (gamepad1.cross) {
                slide.setPower(.2);
            }
        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
}
