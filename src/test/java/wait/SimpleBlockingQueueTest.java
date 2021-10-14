package wait;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.getQueue().isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenFetchAllThenGetNoAll() throws InterruptedException {
        final ArrayList<Integer> list = new ArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> {
                    IntStream.range(5, 9).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!(queue.getQueue().size() == 1) || !Thread.currentThread().isInterrupted()) {
                        try {
                            list.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(queue.poll(), is(8));
    }
}