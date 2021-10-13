package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wget implements Runnable {

    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String out = "temp_" + url.substring(url.lastIndexOf("/") + 1);
        double delay = (1024 / (double) speed) * 1000;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(out)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long timeStart = System.currentTimeMillis();
            int count = 1;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long timeEnd = System.currentTimeMillis();
                long sleep = (long) delay - (timeEnd - timeStart);
                if (sleep > 0) {
                    Thread.sleep(sleep);
                }
                System.out.print("\rLoading: " + count++ + " kb. Time sleep - " + sleep);
                timeStart = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateArgs(String[] args) {
        boolean url;
        boolean in;
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(args[0]);
        url = m.find();
        try {
            Integer.parseInt(args[1]);
            in = true;
        } catch (NumberFormatException e) {
            in = false;
        }
        return url && in;
    }

    public static void main(String[] args) throws InterruptedException {
        if (validateArgs(args)) {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        } else {
            throw new IllegalArgumentException("Аргументы введенны не верно!");
        }
    }
}
