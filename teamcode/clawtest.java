package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.HardwareMapper;

@TeleOp(name="ClawTest", group="Linear OpMode")
@Disabled
//public class clawtest extends LinearOpMode {
//    private Servo claw;
//
//    public void runOpMode() {
//        double servoPos = 0;
//        claw = hardwareMap.get(Servo.class,"claw");
//
//        waitForStart();
//        while (opModeIsActive()) {
//            if (gamepad1.cross) {
//                servoPos+=0.01;
//                //Telemetry.addData("A", gamepad1.a);
//            }
//            else if (gamepad1.square) {
//                servoPos-=.01;
//
//            }
//
//            servoPos = servoPos>1 ? 1:servoPos;
//            servoPos = servoPos<0 ? 0:servoPos;
//
//            claw.setPosition(servoPos);
//            telemetry.addData("Status", "Initialized");
//            telemetry.addData("servopos", servoPos);
//            telemetry.update();
//        }
//    }
//
//}


public class clawtest extends LinearOpMode {
    private Servo claw;
    public void runOpMode() {
        double servoPos = .7;

        claw = hardwareMap.get(Servo.class,"claw");
//        claw.setDirection(Servo.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.cross) {
                servoPos += 0.01;
                sleep(30);
            }
            else if (gamepad1.circle) {
                servoPos -= 0.01;
                sleep(30);
            }
            servoPos = servoPos>1 ? 1:servoPos;
            servoPos = servoPos<0 ? 0:servoPos;
            claw.setPosition(servoPos);
            telemetry.addData("Status", "Running");
            telemetry.addData("Servo Position", servoPos);
            telemetry.update();
        }
    }

    public static void sleep(double ms) {
        try {
            Thread.sleep((long)ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

