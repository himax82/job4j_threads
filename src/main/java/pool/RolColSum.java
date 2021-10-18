package pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            result[i] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] all = new Sums[matrix.length];
        Sums[] getOdd = getOdd(matrix).get();
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i += 2) {
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            all[i] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        for (int i = 1; i < matrix.length; i += 2) {
            all[i] = getOdd[i];
        }
        return all;
    }

    public static CompletableFuture<Sums[]> getOdd(int[][] data) {
        return CompletableFuture.supplyAsync(() -> {
            Sums[] sums = new Sums[data.length];
            int rowSum = 0;
            int colSum = 0;
            for (int i = 1; i < data.length; i += 2) {
                for (int j = 0; j < data.length; j++) {
                    rowSum += data[i][j];
                    colSum += data[j][i];
                }
                sums[i] = new Sums(rowSum, colSum);
                rowSum = 0;
                colSum = 0;
            }
            return sums;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] ar = new int[20000][20000];
        for (int i = 0; i < 20000; i++) {
            for (int j = 0; j < 20000; j++) {
                ar[i][j] = (int) (Math.random() * 1000000);
            }
        }
        long start1 = System.currentTimeMillis();
        Sums[] sums1 = sum(ar);
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);
        long start2 = System.currentTimeMillis();
        Sums[] sums2 = asyncSum(ar);
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }
}
