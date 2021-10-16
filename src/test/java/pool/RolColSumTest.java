package pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSumRowSyn() {
        int[][] myArray = {{18, 28, 18}, {28, 45, 90}, {45, 3, 14}};
        RolColSum.Sums[] sums = RolColSum.sum(myArray);
        assertThat(sums[0].getRowSum(), is(64));
    }

    @Test
    public void whenSumColSyn() {
        int[][] myArray = {{18, 28, 18}, {28, 45, 90}, {45, 3, 14}};
        RolColSum.Sums[] sums = RolColSum.sum(myArray);
        assertThat(sums[0].getColSum(), is(91));
    }

    @Test
    public void whenSumRowASyn() throws ExecutionException, InterruptedException {
        int[][] myArray = {{18, 28, 18}, {28, 45, 90}, {45, 3, 14}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(myArray);
        assertThat(sums[1].getRowSum(), is(163));
    }

    @Test
    public void whenSumColASyn() throws ExecutionException, InterruptedException {
        int[][] myArray = {{18, 28, 18}, {28, 45, 90}, {45, 3, 14}};
        RolColSum.Sums[] sums = RolColSum.asyncSum(myArray);
        assertThat(sums[1].getColSum(), is(76));
    }
}