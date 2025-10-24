package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.dataType.Point;

import java.util.List;

public class AprilTag {
    static private Limelight3A limelight;
    private IMU imu;

    public static YawPitchRollAngles getOrientation(IMU imu) {
        return imu.getRobotYawPitchRollAngles();
    }
    public static int getTagID(List<LLResultTypes.FiducialResult> results) {
        int id = 0;
        for (LLResultTypes.FiducialResult fiducial : results) {
            id = fiducial.getFiducialId();
        }
        return id;
    }

    public static double getDistance(double a1, double a2, double h1, double h2) {
        double angToGoalDeg = a1 + a2;
        double angleRadians = angToGoalDeg * (3.14159 / 180.0);
        return (h2 - h1) / Math.tan(angleRadians);
    }

    public static String[] getMotif(int tagId) {
        String[] motif = {};
          if (tagId == 21)  {
              motif = new String[]{"G", "P", "P"};
              return motif;
          }
          else if (tagId == 22) {
              motif = new String[]{"P", "G", "P"};
              return motif;
          }
          else if (tagId == 23) {
              motif = new String[] {"P", "P", "G"};
              return motif;
          }
          return motif;
    }

    public static Point getPosition(Pose3D botpose) {
        if (botpose != null) {
            double x = botpose.getPosition().x;
            double y = botpose.getPosition().y;
            Point point = new Point(x, y);
            return point;
        }
        else {
            return null;
        }
    }

}
