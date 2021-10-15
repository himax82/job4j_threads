package cas;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void when200incrTwoThreads() {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        count.increment();
                    }
                }
        );
        thread1.start();
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        count.increment();
                    }
                }
        );
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(count.get(), is(200));
    }

}