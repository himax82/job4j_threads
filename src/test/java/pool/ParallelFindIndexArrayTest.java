package pool;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelFindIndexArrayTest {

    @Ignore
    @Test
    public void whenSearchIndexSize40() {
        int[] ar = new int[40];
        for (int i = 0; i < 40; i++) {
            ar[i] = 1;
        }
        ar[19] = 2;
        ar[21] = 3;
        ar[39] = 4;
        ar[1] = 8;
        ar[0] = 5;
        assertThat(ParallelFindIndexArray.find(ar, 2), is(19));
        assertThat(ParallelFindIndexArray.find(ar, 3), is(21));
        assertThat(ParallelFindIndexArray.find(ar, 4), is(39));
        assertThat(ParallelFindIndexArray.find(ar, 8), is(1));
        assertThat(ParallelFindIndexArray.find(ar, 5), is(0));
    }
}