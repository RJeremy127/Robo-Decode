package org.firstinspires.ftc.teamcode.datatypes;

import androidx.annotation.NonNull;

public class Pose {
    //listed in order x, y, r
    private double[] pose;

    public Pose(double[] pose) {
        this.pose = pose;
    }

    public Pose(Pair pair, double r) {
        this.pose = new double[3];
        this.pose[0] = pair.getX();
        this.pose[1] = pair.getY();
        this.pose[2] = r;
    }
    public Pose(double x, double y, double r) {
        this.pose = new double[] {x, y, r};
    }

    public double[] getPose() {
        return pose;
    }
    public double getX() {
        return pose[0];
    }
    public double getY() {
        return pose[1];
    }
    public double getR() {
        return pose[2];
    }

    public void setPose(double[] pose) {
        this.pose = pose;
    }
    public Point getPoint() {
        return new Point(pose[0], pose[1]);
    }

    public void setR(double rot) {
        this.pose[2] = rot;
    }

    public void add(double[] pose_delta) {
        for (int i = 0; i<this.pose.length; i++) {
            pose[i] += pose_delta[i];
        }
    }

    public void add(Matrix pose_delta) {
        for (int i = 0; i<this.pose.length; i++) {
            pose[i] += pose_delta.getMatrix()[i][0];
        }
    }
    public void subtract(double[] pose_delta) {
        for (int i = 0; i<this.pose.length; i++) {
            pose[i] -= pose_delta[i];
        }
    }

    @NonNull
    @Override
    public String toString() {

        return "X: " + String.format("%.2f", this.getX()) +
                " Y: " + String.format("%.2f", this.getY()) +
                " Heading: " + String.format("%.2f", this.getR());
    }

}
