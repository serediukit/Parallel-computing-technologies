package main.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiply {
    public static Result multiply(int[][] matrixA, int[][] matrixB, int threadsCount) {
        int rows = matrixA.length;
        int columns = matrixB[0].length;
        int[][] result = new int[rows][columns];

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        int chunkSize = rows / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            int startRow = i * chunkSize, endRow;
            if (i == threadsCount - 1)
                endRow = rows;
            else
                endRow = (i + 1) * chunkSize;

            MatrixChunk matrixChunk = new MatrixChunk(matrixA, matrixB, result, startRow, endRow);
            executorService.execute(matrixChunk);
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) break;
        }

        return new Result(result);
    }

    public static Result multiplyFox(int[][] matrixA, int[][] matrixB, int blockSize) {
        int rows = matrixA.length;
        int columns = matrixB[0].length;
        int threadsCount = rows / blockSize;

        int[][] result = new int[rows][columns];
        setZeroMatrix(result);

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        int[][][][] blockA = getBlock(matrixA, blockSize);
        int[][][][] blockB = getBlock(matrixB, blockSize);
        int[][][][] blockC = getBlock(result, blockSize);

        for (int i = 0; i < threadsCount; i++) {
            for (int j = 0; j < threadsCount; j++) {
                for (int h = 0; h < threadsCount; h++) {
                    MatrixBlock matrixBlock = new MatrixBlock(blockA[i][(i + h) % threadsCount], blockB[(i + h) % threadsCount][j], blockC, i, j);
                    executorService.execute(matrixBlock);
                }
            }
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) break;
        }

        get2DMatrixFrom4D(result, blockC);

        return new Result(result);
    }

    private static int[][][][] getBlock(int[][] matrix, int blockSize) {
        int nBlocks = matrix.length / blockSize;
        int[][][][] block = new int[nBlocks][nBlocks][blockSize][blockSize];

        for (int i = 0; i < nBlocks; i++)
            for (int j = 0; j < nBlocks; j++)
                for (int h = 0; h < blockSize; h++)
                    for (int k = 0; k < blockSize; k++)
                        block[i][j][h][k] = matrix[i * blockSize + h][j * blockSize + k];

        return block;
    }

    private static void setZeroMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = 0;
    }

    private static void get2DMatrixFrom4D(int[][] dest, int[][][][] a) {
        int size = a[0][0].length;
        int count = a.length;
        for (int i = 0; i < count; i++)
            for (int j = 0; j < count; j++) {
                int[][] temp = a[i][j];
                int tempRow = i * size;
                int tempColumn = j * size;
                for (int h = 0; h < size; h++)
                    for (int k = 0; k < size; k++)
                        dest[tempRow + h][tempColumn + k] = temp[h][k];
            }
    }
}
