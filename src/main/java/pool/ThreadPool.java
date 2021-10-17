package pool;

import wait.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TransferQueue;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread());
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void run() throws InterruptedException {
        while (!tasks.isEmpty()) {
            for (Thread t : threads) {
                if (t.getState() == Thread.State.TERMINATED || t.getState() == Thread.State.NEW) {
                    t = new Thread(tasks.poll());
                    t.start();
                }
            }
        }
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            threadPool.work(() -> System.out.println("Задача № " + finalI));
        }
        threadPool.run();
    }
}