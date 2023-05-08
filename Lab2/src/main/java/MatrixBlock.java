package main.java;

public class MatrixBlock implements Runnable{
    private final int[][] A;
    private final int[][] B;
    private final int[][][][] C;
    private final int row;
    private final int column;

    public MatrixBlock(int[][] a, int[][] b, int[][][][] c, int row, int column) {
        this.A = a;
        this.B = b;
        this.C = c;
        this.row = row;
        this.column = column;
    }

    @Override
    public void run() {
        int[][] tempC = multiply(A, B);
        addMatrix(C[row][column], tempC);
    }

    private int[][] multiply(int[][] a, int[][] b) {
        int[][] res = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < a[0].length; k++)
                    sum += a[i][k] * b[k][j];
                res[i][j] = sum;
            }

        return res;
    }

    private void addMatrix(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++)
            for ( int j = 0; j < a[0].length; j++)
                a[i][j] += b[i][j];
    }
}
