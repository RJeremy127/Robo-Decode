package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.HardwareMapper;

@Autonomous
public class ColorTrack extends OpMode {

    private Limelight3A limelight;



    private DcMotor [] motors;

    @Override
    public void init() {
        motors = HardwareMapper.getMotors(hardwareMap);
        //LLmotor = hardwareMap.get(DcMotor.class, "LLmotor");
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
        telemetry.addData("Name", "%s",
                status.getName());
        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                status.getTemp(), status.getCpu(), (int) status.getFps());
        telemetry.addData("Pipeline", "Index: %d, Type: %s",
                status.getPipelineIndex(), status.getPipelineType());



        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa();

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target area offset", ta);

            if (tx > 3) {
                // LLmotor.setPower(0.1);
                double turn = .2;
                motors[3].setPower(turn);
                motors[2].setPower(-turn);
                motors[1].setPower(turn);
                motors[0].setPower(-turn);
            } else if (tx < -3) {
                double turn = .2;
                //LLmotor.setPower(-0.1);
                motors[3].setPower(-turn);
                motors[2].setPower(turn);
                motors[1].setPower(-turn);
                motors[0].setPower(turn);
            } else {
                //LLmotor.setPower(0);
                motors[3].setPower(0);
                motors[2].setPower(0);
                motors[1].setPower(0);
                motors[0].setPower(0);
            }
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
    }
}
