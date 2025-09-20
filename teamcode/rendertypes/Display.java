package org.firstinspires.ftc.teamcode.rendertypes;
import org.firstinspires.ftc.robotcore.external.Telemetry;


import org.firstinspires.ftc.teamcode.datatypes.Pair;

import java.util.ArrayList;

public class Display {
    private char[][] display;
    private Telemetry telemetry;
    //organized from top down, (0, 0) is top left corner



    public Display(int height, int width, Telemetry telemetry) {
        this.display = new char[height][width];
        this.telemetry = telemetry;
    }

    public int getHeight() {return this.display.length;}
    public int getWidth() {return this.display[0].length;}

    public void update() {
        telemetry.clearAll();
        for (int i = 0; i<getHeight(); i++) {
            String line = "";
            for (int j = 0; j<getWidth(); j++) {
                line += monoify(display[i][j]);
            }
            telemetry.addLine(line);
        }
        telemetry.update();
    }

    public void fill(char c) {
        for (int i = 0; i<getHeight(); i++) {
            for (int j = 0; j<getWidth(); j++) {
                display[i][j] = c;
            }
        }
    }

    public void fill(char[][] sample) {
        for (int i = 0; (i<getHeight() && i<sample.length); i++) {
            for (int j = 0; (j<getWidth() && i<sample[0].length); j++) {
                display[i][j] = sample[i][j];
            }
        }
    }



    public void addPixels(char[][] img, Pair offset) {
        for (int y = 0; y<img.length; y++) {
            for (int x = 0; x<img[0].length; x++) {
                display[(int)offset.getY()+y][(int)offset.getX()+x] = img[y][x];
            }
        }
    }

    public void addPixels(ArrayList<Pixel> pixels) {
        int hOff = getHeight()/2;
        int wOff = getWidth()/2;
        for (Pixel pixel : pixels) {
            try {
                display[pixel.getY()+hOff][pixel.getX()+wOff] = pixel.getValue();
            } catch (Exception e) {

            }
        }
    }

    public String monoify(char c) { // buffers the width of the characters to make it mono relative to line height of driver hub
        switch (c) {
            case ' ':
                return "     ";
            case '-':
                return "-----";
            case '|':
                return "  |  ";
            case '/':
                return "  /  ";
            case '\\':
                return "  \\  ";
        }

        return "  " + c + "  ";
    }

}
