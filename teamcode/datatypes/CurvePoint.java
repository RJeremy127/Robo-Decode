package org.firstinspires.ftc.teamcode.datatypes;

public class CurvePoint {
    private Point loc;
    private double moveSpeed;
    private double turnSpeed;
    private double followDistance;
    private double targetHeading;
    //private double pointLen;
    //private double slowDownAmtTurnRadians;
    //private double SlowDownTurnAmt;

    public CurvePoint (Point pt, double moveSpeed, double turnSpeed, double followDistance, double targetHeading) {
        loc = pt;
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
        this.targetHeading = targetHeading;
    }

    public CurvePoint (CurvePoint other) {
        loc = other.loc;
        moveSpeed = other.moveSpeed;
        turnSpeed = other.turnSpeed;
        followDistance = other.followDistance;
        targetHeading = other.targetHeading;
    }

    public Point toPoint() {
        return loc;
    }

    public void setPoint(Point p) {
        loc = p;
    }
    public Pose toPose() {
        return new Pose(loc.getX(), loc.getY(), targetHeading);
    }
    public double getFollowDistance() {
        return followDistance;
    }
    public double getTargetHeading() {
        return targetHeading;
    }
    public double getMoveSpeed() {
        return moveSpeed;
    }
    public double getTurnSpeed() {
        return turnSpeed;
    }
}
