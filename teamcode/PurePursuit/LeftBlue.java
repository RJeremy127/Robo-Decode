package org.firstinspires.ftc.teamcode.PurePursuit;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.datatypes.Pose;
import org.firstinspires.ftc.teamcode.util.Actuation;

@Autonomous(name="LeftBlue")

public class LeftBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Actuation.setup(hardwareMap, new Pose(0,0,0), telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();


        Pose [] p = new Pose[]{new Pose(0, 0, Math.toRadians(90))};
        Route r = new Route(p);
        r.run(0.3, .16);
        Thread.sleep(10000);
    }
}
