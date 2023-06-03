import mpi.MPI;
import mpi.Request;

import java.util.Arrays;

public class NonBlockingMain {
    private static final int NRA = 62;
    private static final int NCA = 15;
    private static final int NCB = 7;
    private static final int MASTER = 0;

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

                MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, dest, 0);
                MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, dest, 1);
                MPI.COMM_WORLD.Isend(a, offset[0], rows[0], MPI.OBJECT, dest, 2);
                MPI.COMM_WORLD.Isend(b, 0, NCA, MPI.OBJECT, dest, 3);

                offset[0] = offset[0] + rows[0];
            }

            for(int source = 1; source <= numWorkers; source++) {
                Request offsetRecv = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, source, 4);
                Request rowsRecv = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, source, 5);
                offsetRecv.Wait();
                rowsRecv.Wait();

                Request cRecv = MPI.COMM_WORLD.Irecv(c, offset[0], rows[0], MPI.OBJECT, source, 6);
                cRecv.Wait();

                System.out.println("Received results from task " + taskId);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("****");
            System.out.println("Result Matrix:");

            for(int i = 0; i < NRA; i++) {
                for (double ci : c[i]) {
                    System.out.printf("%6.2f ", ci);
                }
                System.out.println();
            }

            System.out.println("\n********");
            System.out.println("Time: " + (endTime - startTime));
            System.out.println("Done.");
        } else {
            Request offsetRecv = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, MASTER, 0);
            Request rowsRecv = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, MASTER, 1);
            offsetRecv.Wait();
            rowsRecv.Wait();

            MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, MASTER, 4);
            MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, MASTER, 5);

            Request aRecv = MPI.COMM_WORLD.Irecv(a, 0, rows[0], MPI.OBJECT, MASTER, 2);
            Request bRecv = MPI.COMM_WORLD.Irecv(b, 0, NCA, MPI.OBJECT, MASTER, 3);
            aRecv.Wait();
            bRecv.Wait();

            for (int k = 0; k < NCB; k++) {
                for (int i = 0; i < rows[0]; i++) {
                    for (int j = 0; j < NCA; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Isend(c, 0, rows[0], MPI.OBJECT, MASTER, 6);
        }

        MPI.Finalize();
    }
}