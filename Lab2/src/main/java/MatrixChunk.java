package main.java;

public class MatrixChunk implements Runnable{
    private final int[][] a;
    private final int[][] b;
    private final int[][] result;
    private final int startRow;
    private final int endRow;

    public MatrixChunk(int[][] a, int[][] b, int[][] result, int startRow, int endRow) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    @Override
    public void run() {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < b[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < a[0].length; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
    }
}
