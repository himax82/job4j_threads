package concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println("Work first!");
        }
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println("Work second!");
        }
        System.out.println("Work the end!");
    }
}