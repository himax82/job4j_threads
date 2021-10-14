package wait;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void queueTestAddAndPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>();
        Thread threadOffer = new Thread(
                () -> {
                    simple.offer(1);
                    simple.offer(2);
                    simple.offer(3);
                }
        );
        Thread threadPoll = new Thread(
                () -> {
                    try {
                        simple.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        threadOffer.start();
        threadPoll.start();
        threadOffer.join();
        threadPoll.join();
        ArrayList<Integer> list = new ArrayList<>(simple.getQueue());
        assertThat(list, is(List.of(2, 3)));
    }

}