package concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        first.join();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        second.join();
        System.out.println("Work the end!");
    }
}