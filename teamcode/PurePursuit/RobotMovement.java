package org.firstinspires.ftc.teamcode.PurePursuit;

import static org.firstinspires.ftc.teamcode.util.Actuation.otto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.control.Odometry;
import org.firstinspires.ftc.teamcode.datatypes.CurvePoint;
import org.firstinspires.ftc.teamcode.datatypes.Pair;
import org.firstinspires.ftc.teamcode.datatypes.Point;
import org.firstinspires.ftc.teamcode.datatypes.Pose;
import org.firstinspires.ftc.teamcode.util.Actuation;
import org.firstinspires.ftc.teamcode.util.MathFunctions;

import java.util.ArrayList;

public class RobotMovement {

    private static int lastFoundIndex = 0;
    public static Pose robotPose = new Pose(0,0,0);
    public static Pose initPos = new Pose(0,0,0);
    public static Telemetry telemetry;

    public static void setup(Pose startPos, Telemetry t) {
        robotPose = startPos;
        initPos = startPos;
        telemetry = t;
    }

    public static double[] goToPosition(Pose targetPose, double movementSpeed, double turnSpeed) {
        double deltaX = targetPose.getX() - robotPose.getX();
        double deltaY = targetPose.getY() - robotPose.getY();
        double dist = Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
        double deltaAngle = MathFunctions.angleWrap(targetPose.getR()-robotPose.getR());
        double dX = 0,dY = 0;
        if (Math.abs(dist) > 1.5) {
            double movementXPower = deltaX/dist; // Normalizes the movement Power
            double movementYPower = deltaY/dist;
            dX = movementXPower*movementSpeed;
            dY = movementYPower*movementSpeed;
        }
        double deltaHeading = 0;
        if (deltaAngle > Math.toRadians(3)) {
            deltaHeading = Math.max(((deltaAngle/(Math.PI))*turnSpeed), turnSpeed);
        }
        else if (deltaAngle<Math.toRadians(-3)){
            deltaHeading = Math.min(((deltaAngle/(Math.PI))*turnSpeed), -turnSpeed);
        }
        telemetry.addData("deltaHeading", deltaAngle);
        telemetry.addData("Pose", robotPose);
        telemetry.update();
        Actuation.drive(dY, dX, deltaHeading);
        return new double[]{dX, dY, deltaHeading};
    }

    public static double[] goToPosition(Pose targetPose, Pose robotPose, double movementSpeed) {
        double deltaX = targetPose.getX() - robotPose.getX();
        double deltaY = targetPose.getY() - robotPose.getY();
        double distance = MathFunctions.distance(new Point(targetPose.getX(), targetPose.getY()), new Point(robotPose.getX(), robotPose.getY()));
        double dX = 0, dY = 0;
        if (distance != 0) {
            double movementXPower = deltaX/distance; // Normalizes the movement Power
            double movementYPower = deltaY/distance;
            dX = movementXPower*movementSpeed;
            dY = movementYPower*movementSpeed;
        }
        Actuation.drive(dX, dY, 0);
        return new double[]{dX, dY, 0};
    }

    public static double [] turnToPosition(Pose targetPose, double turnSpeed) {
        double deltaAngle = MathFunctions.angleWrap(targetPose.getR()-robotPose.getR());
        double deltaHeading = 0;
        if (deltaAngle > Math.toRadians(3)) {
            deltaHeading = Math.max(((deltaAngle/(Math.PI))*turnSpeed), turnSpeed);
        }
        else if (deltaHeading<Math.toRadians(-3)){
            deltaHeading = Math.min(((deltaAngle/(Math.PI))*turnSpeed), -turnSpeed);
        }
        return new double[]{0,0,deltaHeading};
    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Pose robotPose, double followRadius) {
        CurvePoint followMe = pathPoints.get(lastFoundIndex);
        for (int i = lastFoundIndex; i<pathPoints.size()-1;i++) {
            CurvePoint startPoint = pathPoints.get(i);
            CurvePoint endPoint = pathPoints.get(i+1);

            Point [] intersections = getIntersections(followRadius, robotPose.getPoint(), startPoint.toPoint(), endPoint.toPoint());

            /*double minAngle = Integer.MAX_VALUE;
            for (int j = 0; j<intersections.length;j++) {
                double absoluteAngle = Math.atan2(intersections[j].getY()-GraphicsLineCircle.WorldPosY, intersections[j].getX()-GraphicsLineCircle.WorldPosX);
                double deltaAngle = Math.abs(MathFunctions.angleWrap(absoluteAngle-Math.toRadians(GraphicsLineCircle.WorldHeading)));
                System.out.println("Point: "+intersections[j] + " dAngle: " + deltaAngle);
                if (minAngle > deltaAngle) {
                    minAngle = deltaAngle;
                    followMe.setPoint(intersections[j]);
                }
            }*/
            double minCurveDist = Integer.MAX_VALUE;
            for (int j = 0; j<intersections.length;j++) {
                double intersectCurveDist = MathFunctions.distance(intersections[j], pathPoints.get(lastFoundIndex+1).toPoint());
                if (minCurveDist>intersectCurveDist) {
                    minCurveDist = intersectCurveDist;
                    followMe.setPoint(intersections[j]);
                }
            }
            System.out.println("Last Index: " + lastFoundIndex);
            System.out.println("Len: " + intersections.length);
            double centerCurveDist = MathFunctions.distance(robotPose.getPoint(), pathPoints.get(lastFoundIndex+1).toPoint());
            if (centerCurveDist>minCurveDist) {
                lastFoundIndex = i;
                break;
            }
            else {
                if (lastFoundIndex == pathPoints.size()-2) {
                    followMe = pathPoints.get(pathPoints.size()-1);
                }
                lastFoundIndex++;
            }
        }
        return followMe;
    }
    public static Point[] getIntersections(double r, Point center, Point p1, Point p2) {
        double h = center.getX();
        double k = center.getY();
        if (p1.getX() == p2.getX()) {
            p2 = new Point(p2.getX()+.0001, p2.getY());
            System.out.println(p2);
        }
        double m = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
        double b = p1.getY()-m*p1.getX();
        double xpoint1;
        double xpoint2;
        double ypoint1;
        double ypoint2;
        double minX = Math.min(p1.getX(), p2.getX());
        double maxX = Math.max(p1.getX(), p2.getX());

        if (-1*b*b-2*b*h*m+2*b*k-h*h*m*m+2*h*k*m-k*k+m*m*r*r+r*r < 0) {
            Point [] intersections = {};
            return intersections;
        }
        else if (Math.sqrt(-1*b*b-2*b*h*m+2*b*k-h*h*m*m+2*h*k*m-k*k+m*m*r*r+r*r) == 0) {
            xpoint1 = (Math.sqrt(-1*b*b-2*b*h*m+2*b*k-h*h*m*m+2*h*k*m-k*k+m*m*r*r+r*r) - b*m+h+k*m)/(m*m+1);
            ypoint1 = (m*xpoint1+b);
            if (xpoint1<=maxX && xpoint1 >= minX) {
                Point [] intersections = new Point[1];
                intersections[0] = new Point(xpoint1, ypoint1);
                return intersections;
            }
            Point [] intersections = {};
            return intersections;
        }
        else {
            xpoint1 = (Math.sqrt(-1*b*b-2*b*h*m+2*b*k-h*h*m*m+2*h*k*m-k*k+m*m*r*r+r*r) - b*m+h+k*m)/(m*m+1);
            ypoint1 = (m*xpoint1+b);
            xpoint2 = (-1*Math.sqrt(-1*b*b-2*b*h*m+2*b*k-h*h*m*m+2*h*k*m-k*k+m*m*r*r+r*r) - b*m+h+k*m)/(m*m+1);
            ypoint2 = (m*xpoint2+b);
            if (xpoint1<=maxX && xpoint1 >= minX && xpoint2<=maxX && xpoint2 >= minX) {
                Point [] intersections = new Point[2];
                intersections[0] = new Point(xpoint1, ypoint1);
                intersections[1] = new Point(xpoint2, ypoint2);
                return intersections;
            }
            if (xpoint1<=maxX && xpoint1 >= minX) {
                Point [] intersections = new Point[1];
                intersections[0] = new Point(xpoint1, ypoint1);
                return intersections;
            }
            if (xpoint2<=maxX && xpoint2 >= minX) {
                Point [] intersections = new Point[1];
                intersections[0] = new Point(xpoint2, ypoint2);
                return intersections;
            }
            Point [] intersections = {};
            return intersections;
        }
    }
    public static CurvePoint followCurve(ArrayList<CurvePoint> allPoints, Pose robotPose) { // can be void return type
        CurvePoint followMe = getFollowPointPath(allPoints, robotPose, allPoints.get(0).getFollowDistance());
        if (lastFoundIndex != allPoints.size()-1 || MathFunctions.distance(robotPose.getPoint(), allPoints.get(allPoints.size()-1).toPoint()) >.6 || Math.abs(MathFunctions.angleWrap(allPoints.get(lastFoundIndex == allPoints.size()-1? allPoints.size()-1:lastFoundIndex+1).getTargetHeading()-robotPose.getR()))>Math.toRadians(2)) {
            //goToPosition(followMe.toPose(), allPoints.get(lastFoundIndex+1).getMoveSpeed());
        }
        return followMe;
    }

}
