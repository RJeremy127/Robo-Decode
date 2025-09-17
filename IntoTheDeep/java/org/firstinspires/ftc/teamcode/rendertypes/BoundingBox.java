package org.firstinspires.ftc.teamcode.rendertypes;

import org.firstinspires.ftc.teamcode.datatypes.Pair;

import java.util.ArrayList;

public class BoundingBox {
    private Pair[] vertices;
    private final Pair[] localVertices; // refrence for the object
    //local are kept constant as a reference to avoid accumulating error when rotating

    private double rotation;
    public BoundingBox(Pair[] vertices) {

        this.localVertices = vertices;
        this.vertices = new Pair[vertices.length];
        for (int i = 0; i<vertices.length; i++) {
            vertices[i] = localVertices[i].copy();
        }
    }

    public BoundingBox(Pair[] vertices, Pair boxScale) {
        this.localVertices = vertices;
        this.vertices = new Pair[vertices.length];
        for (int i = 0; i<vertices.length; i++) {
            vertices[i].rasterize(boxScale.getY(), boxScale.getX());
            vertices[i] = localVertices[i].copy();
        }
    }

    public void rotate(double radians) {
        for (int i = 0; i<vertices.length; i++) {
            vertices[i] = localVertices[i].getRotated(radians);
        }
    }

    public void locallyRotate(double radians) {
        for (Pair vertex : vertices) {
            vertex.rotate(radians-rotation);
        }
        rotation += radians-rotation;
    }





    public ArrayList<Pixel> render() {
        ArrayList<Pixel> pixels = new ArrayList<>();
        pixels.addAll(Line.render(vertices[vertices.length-1], vertices[0]));
        for (int i = 1; i<vertices.length; i++) {
            pixels.addAll(Line.render(vertices[i-1], vertices[i]));
        }
        return pixels;
    }


}
