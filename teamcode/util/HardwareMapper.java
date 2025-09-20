package org.firstinspires.ftc.teamcode.util;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.util.Constants.BACK_LEFT;
import static org.firstinspires.ftc.teamcode.util.Constants.BACK_RIGHT;
import static org.firstinspires.ftc.teamcode.util.Constants.FRONT_LEFT;
import static org.firstinspires.ftc.teamcode.util.Constants.FRONT_RIGHT;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareMapper {
    public static DcMotor[] getMotors(HardwareMap hardwareMap) {
        DcMotor[] motors = new DcMotor[4];
        motors[FRONT_LEFT]  = hardwareMap.get(DcMotor.class, "frontLeft");
        motors[BACK_LEFT]  = hardwareMap.get(DcMotor.class, "backLeft");
        motors[FRONT_RIGHT] = hardwareMap.get(DcMotor.class, "frontRight");
        motors[BACK_RIGHT] = hardwareMap.get(DcMotor.class, "backRight");

        // preconfigure the motor directions
        motors[FRONT_LEFT].setDirection(DcMotor.Direction.REVERSE);
        motors[BACK_LEFT].setDirection(DcMotor.Direction.REVERSE);
        motors[FRONT_RIGHT].setDirection(DcMotor.Direction.FORWARD);
        motors[BACK_RIGHT].setDirection(DcMotor.Direction.FORWARD);
        return motors;
    }

}
