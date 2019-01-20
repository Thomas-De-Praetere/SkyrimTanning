package com.thomas.skyrim.tanning.util;

/**
 * This class was created by thoma on 11-May-18.
 */
public class Solver {
    private final double[][] matrix;
    private final int rows;
    private final int columns;


    public Solver(int columns, double... v) {
        matrix = new double[v.length / columns][columns];
        int i = 0, j = 0;
        for (int k = 0; k < v.length; k++) {
            double v1 = v[k];
            if (j >= columns) {
                i++;
                j = 0;
            }
            matrix[i][j] = v1;
            j++;
        }
        rows = v.length / columns;
        this.columns = columns;
    }

    public double[][] reduce() {
        int rowIndex = 0;
        while (rowIndex < rows - 1) {
            sort(rowIndex);
            reduce(rowIndex);
            rowIndex++;
        }
        rowIndex = rows - 1;
        while (rowIndex >= 1) {
            reverseReduce(rowIndex);
            rowIndex--;
        }
        normalise();
        return matrix;
    }

    private void sort(int column) {
        if (matrix[column][column] != 0.0) return;
        for (int i = column + 1; i < rows; i++) {
            if (matrix[i][column] > 0.0) {
                double[] dummy = matrix[column];
                matrix[column] = matrix[i];
                matrix[i] = dummy;
                return;
            }
        }
    }

    private void reduce(int row) {
        for (int i = row + 1; i < rows; i++) {
            subtract(row, i, matrix[i][row] / matrix[row][row]);
        }
    }

    private void subtract(int row, int i, double term) {
        for (int j = 0; j < matrix[i].length; j++) {
            matrix[i][j] = matrix[i][j] - term * matrix[row][j];
        }
    }

    private void reverseReduce(int row) {
        for (int i = row - 1; i >= 0; i--) {
            subtract(row, i, matrix[i][row] / matrix[row][row]);
        }
    }

    private void normalise() {
        for (int row = 0; row < rows; row++) {
            double divisor = matrix[row][row];
            for (int column = 0; column < columns; column++) {
                matrix[row][column] = matrix[row][column] / divisor;
            }
        }
    }
}
