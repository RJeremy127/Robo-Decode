package org.firstinspires.ftc.teamcode.rendertypes;

import android.print.PrintAttributes;

import org.firstinspires.ftc.teamcode.datatypes.Pair;

import java.util.ArrayList;

//all actual units are in cm
public class GameMap {
    private static final Pair FIELD_DIMENSIONS = new Pair(144, 144);// DO IT IN IN (give half of the full since 0, 0 is at center)
    private char[][] map;
    private Pair resolution;
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<BoundingBox> bboxes = new ArrayList<>();


    public GameMap(Pair resolution) {
        this.resolution = resolution;
        this.map = new char[(int)resolution.getX()][(int)resolution.getY()];

        this.lines.add(new Line(new Pair(0, 0), new Pair(144, 0)));
        this.lines.add(new Line(new Pair(0, 0), new Pair(0, 144)));
        this.lines.add(new Line(new Pair(144, 0), new Pair(0, 0)));
        this.lines.add(new Line(new Pair(0, 144), new Pair(0, 0)));
        this.lines.add(new Line(new Pair(30, 24), new Pair(42, 24)));
        this.lines.add(new Line(new Pair(30, 48), new Pair(42, 48)));
        this.lines.add(new Line(new Pair(30, 24), new Pair(30, 48)));
        this.lines.add(new Line(new Pair(42, 24), new Pair(42, 48)));
        for (Line l : lines) {
            l.normalize(FIELD_DIMENSIONS);
        }

    }

    public void renderBuffer(Pair res) {
        for (Line line : lines) {
            drawPixels(line.getRasterized(res).render());// rasterize to new size then render the line
        }
    }
    public void renderBuffer() {
        for (Line line : lines) {
            drawPixels(line.getRasterized(FIELD_DIMENSIONS).render());// rasterize to new size then render the line
        }
    }

    public void drawPixels(ArrayList<Pixel> pixels) {
        for (Pixel pixel : pixels) {
            try {
                map[pixel.getY()][pixel.getX()] = pixel.getValue();
            } catch (Exception ignored) {
            }
        }
    }




    public char[][] sampleImage(Pair realCenter, int height, int width) {
        realCenter.add(new Pair(FIELD_DIMENSIONS.getX()/2, FIELD_DIMENSIONS.getY()/2));
        realCenter.normalize(FIELD_DIMENSIONS);
        realCenter.rasterize(resolution);


        int cornerY = (int)realCenter.getY()-(height/2);
        int cornerX = (int)realCenter.getX()-(width/2);
        char[][] snip = new char[height][width];
        for (int y = 0; y<height; y++) {
            for (int x = 0; x<width; x++) {
                try {
                    snip[y][x] = map[cornerY + y][cornerX + x];
                } catch (Exception ignored) {
                }
            }
        }
        return snip;
    }
//    public char[][] sampleImage(Pair offset, int height, int width) {
//        char[][] snip = new char[height][width];
//        for (int y = 0; y<height; y++) {
//            for (int x = 0; x<width; x++) {
//                snip[y][x] = map[(int)offset.getY() + y][(int)offset.getX()+x];
//            }
//        }
//        return snip;
//    }
}
