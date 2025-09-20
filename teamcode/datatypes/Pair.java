package org.firstinspires.ftc.teamcode.datatypes;

import androidx.annotation.NonNull;

public class Pair {
    public double x, y;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void rotate(double radians) {
        x = x*Math.cos(radians) - y*Math.sin(radians);
        y = x*Math.sin(radians) + y*Math.cos(radians);
    }
    public Pair getRotated(double radians) {
        return new Pair(
                x*Math.cos(radians) - y*Math.sin(radians),
                x*Math.sin(radians) + y*Math.cos(radians));
    }


    public void add(Pair p2) {
        this.x += p2.getX();
        this.y += p2.getY();
    }

    public void normalize(double height, double width) {
        x = x/width;
        y = y/height;
    }
    public void normalize(Pair dim) {
        normalize(dim.getY(), dim.getX());
    }

    public void rasterize(double height, double width) {
        x = Math.round(x*width);
        y = Math.round(y*height);
    }
    public void rasterize(Pair dim) {
        rasterize(dim.getY(), dim.getX());
    }

    public Pair getRasterized(double height, double width) {
        return new Pair(Math.round(x*width), Math.round(y*height));
    }
    public Pair getRasterized(Pair dim) {
        return getRasterized(dim.getY(), dim.getX());
    }

    public double distance(Pair p) {
        return Math.sqrt((Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2)));
    }

    public Pair copy() {
        return new Pair(x, y);
    }

    @NonNull
    @Override
    public String toString() {

        return "X: " + String.format("%.2f", this.getX()) +
                " Y: " + String.format("%.2f", this.getY());
    }

}
