package org.firstinspires.ftc.teamcode.datatypes;

import androidx.annotation.NonNull;

public class Matrix {
    private double[][] matrix;



    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public int getHeight() {
        return matrix.length;
    }
    public int getWidth() {
        return matrix[0].length;
    }
    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }


    public Matrix multiply(Matrix m2) {
        if (!(this.getWidth() == m2.getHeight())) {
            return null; // cannot multiply these matrices
        }

        double[][] m1 = this.matrix; // rename it locally so its easier to read the loop
        double[][] product = new double[this.getHeight()][m2.getWidth()];

        double accumulation =0;
        for (int r = 0; r<this.getHeight(); r++) {
            for (int x = 0; x<m2.getWidth(); x++) {
                accumulation = 0;
                for (int y = 0; y<m2.getHeight(); y++) {
                    accumulation += this.matrix[r][y]*m2.getMatrix()[y][x];
                }
                product[r][x] = accumulation;
            }
        }
        return new Matrix(product);
    }

    public Matrix add(Matrix m2) {
        if (!((this.getHeight()==m2.getHeight()) && (this.getWidth() == m2.getWidth()))) {
            return null; // cannot add these matrices
        }
        double[][] sum = new double[this.getHeight()][this.getWidth()];
        for (int h = 0; h<this.getHeight(); h++) {
            for (int w = 0; w<this.getWidth(); w++) {
                sum[h][w] = this.getMatrix()[h][w] + m2.getMatrix()[h][w];
            }
        }
        return new Matrix(sum);
    }

    public Matrix subtract(Matrix m2) {
        if (!((this.getHeight()==m2.getHeight()) && (this.getWidth() == m2.getWidth()))) {
            return null; // cannot add these matrices
        }
        double[][] difference = new double[this.getHeight()][this.getWidth()];
        for (int h = 0; h<this.getHeight(); h++) {
            for (int w = 0; w<this.getWidth(); w++) {
                difference[h][w] = this.getMatrix()[h][w] - m2.getMatrix()[h][w];
            }
        }
        return new Matrix(difference);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (double[] row : this.matrix) {
            str.append("|");
            for (double d : row) {
                str.append(d).append(", ");
            }
            str.delete(str.length()-2, str.length());//trim trailing ", "
            str.append("|\n");
        }
        return str.toString();
    }
}


