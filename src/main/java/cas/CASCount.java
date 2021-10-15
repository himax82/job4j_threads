package cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer ref;
        int r;
        do {
            ref = count.get();
            r = ref == null ? 1 : ref + 1;
        } while (!count.compareAndSet(ref, r));
    }

    public int get() {
        return count.getAcquire();
    }
}
