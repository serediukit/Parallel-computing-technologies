package main.java;

public class Result {
    private final int[][] matrix;

    public Result(int[][] m) {
        this.matrix = m;
    }

    public void printMatrix() {
        for (int[] row : matrix) {
            for (int cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }
}
