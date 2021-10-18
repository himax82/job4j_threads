package pool;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public Map<String, String> emailTo(User user) {
        pool.submit(() -> send(String.format(
                "Notification %s to email %s.", user.getName(), user.getEmail()),
                String.format("Add a new event to %s.", user.getName()),
                user.getEmail()
        ));
        return Map.of(
                "subject", String.format("Notification %s to email %s.", user.getName(), user.getEmail()),
        "body", String.format("Add a new event to %s.", user.getName()),
        "email", user.getEmail());
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        User user = new User("max", "max@mail.com");
        emailNotification.emailTo(user);
        emailNotification.close();
    }

}
