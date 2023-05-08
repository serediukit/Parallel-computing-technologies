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
        int MSIZE = 100;
        int THREADS_COUNT = 20;
        int BLOCK_SIZE = (int)Math.sqrt(MSIZE);

        System.out.println("TESTING? Y/n");
        Scanner text_sc = new Scanner(System.in);
        char c = text_sc.next().charAt(0);;

        if (c == 'Y' || c == 'y') {
            System.out.println("+----------+-------+----+------------+--------+");
            System.out.println("| TESTNUMB | MSIZE | TC | ALGHORYTM  | TIME   |");
            System.out.println("+----------+-------+----+------------+--------+");
            for (int i = 1; i <= 10; i++) {
                MSIZE = i * i * 100;
                int[][] matrixA = generateRandomMatrix(MSIZE, MSIZE);
                int[][] matrixB = generateRandomMatrix(MSIZE, MSIZE);
                System.out.print("| Test #" + i);
                if (i == 10) System.out.print(" | " + MSIZE + " | " + THREADS_COUNT + " | Стрічковий | ");
                else if ( i <= 3 ) System.out.print("  | " + MSIZE + "   | " + THREADS_COUNT + " | Стрічковий | ");
                else System.out.print("  | " + MSIZE + "  | " + THREADS_COUNT + " | Стрічковий | ");
                long startTime = System.currentTimeMillis();
                Result result = MatrixMultiply.multiply(matrixA, matrixB, THREADS_COUNT);
                long endTime = System.currentTimeMillis();
                long wastedTime = endTime - startTime;
                long seconds = wastedTime / 1000;
                long milliseconds = wastedTime % 1000;

                String formattedTime = String.format("%02d:%03d", seconds, milliseconds);
                System.out.print(formattedTime);

                System.out.println(" |");

                System.out.print("|          |       |    | Фокса      | ");
                startTime = System.currentTimeMillis();
                Result result2 = MatrixMultiply.multiplyFox(matrixA, matrixB, THREADS_COUNT);
                endTime = System.currentTimeMillis();
                wastedTime = endTime - startTime;
                seconds = wastedTime / 1000;
                milliseconds = wastedTime % 1000;

                formattedTime = String.format("%02d:%03d", seconds, milliseconds);
                System.out.print(formattedTime);

                System.out.println(" |");

                System.out.println("+----------+-------+----+------------+--------+");
            }
        } else {
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
                default -> {
                    System.out.println("Не правильний ввід. Вихід...");
                    System.exit(0);
                }
            }

            long endTime = System.currentTimeMillis();

//            System.out.println("Matrix A:");
//            printMatrix(matrixA);
//
//            System.out.println("Matrix B:");
//            printMatrix(matrixB);
//
//
//            System.out.println("Result:");
//            result.printMatrix();

            long wastedTime = endTime - startTime;
            long seconds = (wastedTime / 1000) % 60;
            long milliseconds = wastedTime % 1000;

            String formattedTime = String.format("%02d:%03d", seconds, milliseconds);
            System.out.println("\nWasted time: " + formattedTime);
        }
    }
}
