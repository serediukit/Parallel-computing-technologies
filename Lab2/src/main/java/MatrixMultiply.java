package main.java;

import java.util.concurrent.Executor;
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
}
