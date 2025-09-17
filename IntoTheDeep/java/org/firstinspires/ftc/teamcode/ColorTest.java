package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;

@Autonomous(name = "ColorTest")
public class ColorTest extends LinearOpMode {
    public static ColorSensor color;

    public void runOpMode() {
        color = hardwareMap.get(ColorSensor.class, "color");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            int [] rgb = scan();
            telemetry.addData("RGB vals", Arrays.toString(rgb));
            String c = "";
            if (rgb[0]>2000 && rgb[1]>2000) {
                c = "yellow";
            }
            else if (rgb[0]>2000) {
                c = "red";
            }
            else if (rgb[2]>2000) {
                c = "blue";
            }
            telemetry.addData("Recorded color", c);
            telemetry.update();
        }
    }

    public int[] scan() {
        return new int[]{color.red(), color.green(), color.blue()};
    }

}
