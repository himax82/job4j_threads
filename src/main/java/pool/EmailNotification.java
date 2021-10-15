package pool;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool;

    public EmailNotification(ExecutorService pool) {
        this.pool = pool;
    }

    public Map<String, String> emailTo(User user) {
        return Map.of(
                "subject", String.format("Notification %s to email %s.", user.getName(), user.getEmail()),
        "body", String.format("Add a new event to %s.", user.getName()),
        "email", user.getEmail());
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification(Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        ));
        User user = new User("max", "max@mail.com");
        emailNotification.pool.submit(() -> {
            Map<String, String> map = emailNotification.emailTo(user);
            emailNotification.send(map.get("subject"), map.get("body"), map.get("email"));
        });
        emailNotification.close();
    }

}
