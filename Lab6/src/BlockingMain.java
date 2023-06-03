import mpi.MPI;

import java.util.Arrays;

public class BlockingMain {
    private static final int NRA = 1000;
    private static final int NCA = 500;
    private static final int NCB = 1000;
    private static final int MASTER = 0;
    private static final int FROM_MASTER = 1;
    private static final int FROM_WORKER = 2;

    public static void main(String[] args) {
        double[][] a = new double[NRA][NCA];
        double[][] b = new double[NCA][NCB];
        double[][] c = new double[NRA][NCB];

        MPI.Init(args);
        int numTasks = MPI.COMM_WORLD.Size();
        int taskId = MPI.COMM_WORLD.Rank();

        if (numTasks < 2) {
            System.out.println("Need at least two MPI tasks. Quiting...");
            MPI.COMM_WORLD.Abort(1);
            System.exit(1);
        }

        int numWorkers = numTasks - 1;
        int[] offset = {0};
        int[] rows = {0};

        if (taskId == MASTER) {
            System.out.println("mpi_mm has started with " + numTasks + " tasks.");

            for(int i = 0; i < NRA; i++) {
                Arrays.fill(a[i], 10);
            }

            for(int i = 0; i < NCA; i++) {
                Arrays.fill(b[i], 10);
            }

            int aveRow = NRA / numWorkers;
            int extra = NRA % numWorkers;

            long startTime = System.currentTimeMillis();

            for(int dest = 1; dest <= numWorkers; dest++) {
                rows[0] = (dest <= extra) ? aveRow + 1 : aveRow;
                System.out.println("Sending " + rows[0] + " rows to task " + dest + " offset = " + offset[0]);

                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a, offset[0], rows[0], MPI.OBJECT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b, 0, NCA, MPI.OBJECT, dest, FROM_MASTER);

                offset[0] = offset[0] + rows[0];
            }

            for(int source = 1; source <= numWorkers; source++) {
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(c, offset[0], rows[0], MPI.OBJECT, source, FROM_WORKER);
                System.out.println("Received results from task " + taskId);
            }

            long endTime = System.currentTimeMillis();

//            System.out.println("****");
//            System.out.println("Result Matrix:");
//
//            for(int i = 0; i < NRA; i++) {
//                for (double ci : c[i]) {
//                    System.out.printf("%6.2f ", ci);
//                }
//                System.out.println();
//            }
//
//            System.out.println("\n********");
            System.out.println("Time: " + (endTime - startTime));
            System.out.println("Done.");
        } else {
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(a, 0, rows[0], MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, NCA, MPI.OBJECT, MASTER, FROM_MASTER);

            for (int k = 0; k < NCB; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < NCA; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(c, 0, rows[0], MPI.OBJECT, MASTER, FROM_WORKER);
        }

        MPI.Finalize();
    }
}