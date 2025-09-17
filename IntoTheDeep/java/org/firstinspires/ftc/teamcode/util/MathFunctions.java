package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.datatypes.Pair;
import org.firstinspires.ftc.teamcode.datatypes.Point;

public class MathFunctions {
    public static double angleWrap(double angle) {
        while (angle > Math.PI) {
            angle-=2*Math.PI;
        }
        while (angle < -Math.PI) {
            angle+=2*Math.PI;
        }
        return angle;
    }
    public static double clip(double val, double up, double low) {
        if (val>up) {
            return up/10;
        }
        if (val<low) {
            return low/10;
        }
        return val/10;
    }
    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2) + Math.pow(p1.getY()-p2.getY(), 2));
    }
}
