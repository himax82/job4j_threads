package cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount() {
        count.set(0);
    }

    public void increment() {
        Integer ref;
        int newValue;
        do {
            ref = count.get();
            newValue = ref + 1;
        } while (!count.compareAndSet(ref, newValue));
    }

    public int get() {
        return count.getAcquire();
    }
}
