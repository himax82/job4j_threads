package wait;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int cnt = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            cnt++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (cnt < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}