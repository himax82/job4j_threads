package cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        if (count.getAcquire() == null) {
            count.set(1);
        } else {
            int i = count.getAcquire();
            i++;
            count.set(i);
        }
    }

    public int get() {
        return count.getAcquire();
    }
}
