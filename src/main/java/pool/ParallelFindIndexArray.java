package pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndexArray extends RecursiveTask<Integer> {

    private final int[] array;
    private final int value;
    private int start;
    private int finish;

    public ParallelFindIndexArray(int[] array, int value, int start, int finish) {
        this.array = array;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        if (finish - start <= 10) {
            return search();
        }
        int mid = (finish + start) / 2;
        ParallelFindIndexArray leftSort = new ParallelFindIndexArray(array, value, start, mid);
        ParallelFindIndexArray rightSort = new ParallelFindIndexArray(array, value, mid + 1, finish);
        leftSort.fork();
        rightSort.fork();
        int i = leftSort.join();
        int j = rightSort.join();
        return Math.max(i, j);
    }

    public static int find(int[] array, int value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndexArray(array, value, 0, array.length - 1));
    }

    private int search() {
        for (int i = start; i <= finish; i++) {
            if (array[i] == (value)) {
                return i;
            }
        }
        return -1;
    }
}
