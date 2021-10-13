package ref;

import java.io.*;

public class Save {

    public static void saveContent(File file, String content) {
        try (OutputStream o = new FileOutputStream(file);
             BufferedOutputStream outputStream = new BufferedOutputStream(o)) {
            for (int i = 0; i < content.length(); i += 1) {
                outputStream.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
