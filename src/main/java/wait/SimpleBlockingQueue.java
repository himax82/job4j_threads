package wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final static int QUEUELENGTH = 10;
    private int count;

    public synchronized void offer(T value) {
            while (count == QUEUELENGTH) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(value);
            count++;
            this.notify();
    }

    public synchronized T poll() throws InterruptedException {
            while (queue.isEmpty()) {
                    this.wait();
            }
            count--;
            this.notify();
            return queue.poll();
        }
    }
