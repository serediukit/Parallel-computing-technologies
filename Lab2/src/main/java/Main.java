package main.java;

import java.util.Random;
import java.util.Scanner;

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
        int MSIZE = 1600;
        int THREADS_COUNT = 20;
        int BLOCK_SIZE = (int)Math.sqrt(MSIZE);
        int[][] matrixA = generateRandomMatrix(MSIZE, MSIZE);
        int[][] matrixB = generateRandomMatrix(MSIZE, MSIZE);

        System.out.println("-----МЕНЮ-----");
        System.out.println("1. Стрічковий");
        System.out.println("2. Фокса");
        System.out.println("--------------");
        System.out.println("Ваш вибір: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        long startTime = System.currentTimeMillis();
        Result result;

        switch (choice) {
            case 1 -> {
                System.out.println("Срічковий алгоритм");
                result = MatrixMultiply.multiply(matrixA, matrixB, THREADS_COUNT);
            }
            case 2 -> {
                System.out.println("Алгоритм Фокса");
                result = MatrixMultiply.multiplyFox(matrixA, matrixB, BLOCK_SIZE);
            }
            default -> result = MatrixMultiply.multiply(matrixA, matrixB, THREADS_COUNT);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Matrix A:");
        printMatrix(matrixA);

        System.out.println("Matrix B:");
        printMatrix(matrixB);


        System.out.println("Result:");
        result.printMatrix();

        long wastedTime = endTime - startTime;
        long seconds = (wastedTime / 1000) % 60;
        long milliseconds = wastedTime % 1000;

        String formattedTime = String.format("%02d:%03d", seconds, milliseconds);
        System.out.println("\nWasted time: " + formattedTime);
    }
}
