package main.java;

import java.util.Random;

public class Main {
    private static int[][] generateRandomMatrix(int rows, int columns) {
        Random rnd = new Random();
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                matrix[i][j] = rnd.nextInt(11);
        return matrix;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int MSIZE = 1000;
        int THREADS_COUNT = 20;
        int[][] matrixA = generateRandomMatrix(MSIZE, MSIZE);
        int[][] matrixB = generateRandomMatrix(MSIZE, MSIZE);

//        System.out.println("Matrix A:");
//        printMatrix(matrixA);
//
//        System.out.println("Matrix B:");
//        printMatrix(matrixB);

        long startTime = System.currentTimeMillis();
        Result result = MatrixMultiply.multiply(matrixA, matrixB, THREADS_COUNT);
        long endTime = System.currentTimeMillis();
        System.out.println("Result:");
        //result.printMatrix();

        long wastedTime = endTime - startTime;
        long seconds = (wastedTime / 1000) % 60;
        long milliseconds = wastedTime % 1000;

        String formattedTime = String.format("%02d:%03d", seconds, milliseconds);
        System.out.println("Wasted time: " + formattedTime);
    }
}
