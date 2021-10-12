package concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = new char[] {'|', '/', '-', '\\', '|', '/', '-', '\\'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\r load: " + process[i]);
            if (i == process.length - 1) {
                i = -1;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
