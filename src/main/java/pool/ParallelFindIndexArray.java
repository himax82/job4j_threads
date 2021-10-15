package pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFindIndexArray extends RecursiveTask<Integer> {

    private final int[] array;
    private final int value;
    private final int index;

    public ParallelFindIndexArray(int[] array, int value, int index) {
        this.array = array;
        this.value = value;
        this.index = index;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            return searchTen(array);
        }
        int mid = (array.length) / 2;
        ParallelFindIndexArray leftSort = new ParallelFindIndexArray(Arrays.copyOfRange(array, 0, mid), value, index);
        ParallelFindIndexArray rightSort = new ParallelFindIndexArray(Arrays.copyOfRange(array, mid, array.length), value, index + mid);
        leftSort.fork();
        rightSort.fork();
        int i = leftSort.join();
        int j = rightSort.join();
        return i + j;
    }

    public static int find(int[] array, int value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFindIndexArray(array, value, 0));
    }

    private int searchTen(int[] ar) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == (value)) {
                return i + index;
            }
        }
        return 0;
    }
}
