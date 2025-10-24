package org.firstinspires.ftc.teamcode.vision.Limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcontroller.Constants;
import org.firstinspires.ftc.robotcontroller.internal.HardwareMapper;
import org.firstinspires.ftc.robotcontroller.Drive;

@TeleOp(name = "colortrack")
public class ColorTrack extends OpMode {
    private Limelight3A limelight;
    private DcMotor[] motors;
    private Drive drive;

    @Override
    public void init() {
        motors = HardwareMapper.getMotors(hardwareMap);
        drive = new Drive(motors);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(4);
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        LLStatus status = limelight.getStatus();

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx();
            double ty = result.getTy();
            double ta = result.getTa();
            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);

            if (tx > 5) {
                drive.Right(0.2);
            } else if (tx < -5) {
                drive.Left(0.2);
            } else {
                drive.moveForward(.2);
            }
        } else {
            telemetry.addLine("No Target Found");
            drive.stop();
        }

        telemetry.update();
    }
}