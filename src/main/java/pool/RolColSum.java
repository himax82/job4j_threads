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
        Sums[] getCol = getCol(matrix).get();
        int rowSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
            }
            all[i] = new Sums(rowSum, 0);
            rowSum = 0;
        }
        for (int i = 0; i < matrix.length; i++) {
            all[i].setColSum(getCol[i].getColSum());
        }
        return all;
    }

    public static CompletableFuture<Sums[]> getCol(int[][] data) {
        return CompletableFuture.supplyAsync(() -> {
            Sums[] sums = new Sums[data.length];
            int colSum = 0;
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    colSum += data[j][i];
                }
                sums[i] = new Sums(0, colSum);
                colSum = 0;
            }
            return sums;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] ar = new int[10000][10000];
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
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
