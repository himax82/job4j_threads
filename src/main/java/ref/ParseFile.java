package ref;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) {
        String result = "";
       try (InputStream i = new FileInputStream(file);
       BufferedInputStream bufferedInputStream = new BufferedInputStream(i)) {
           StringBuilder output = new StringBuilder();
           int data;
           while ((data = bufferedInputStream.read()) > 0) {
               if (filter.test(data)) {
                   output.append((char) data);
               }
           }
           result = output.toString();
       } catch (IOException e) {
           e.printStackTrace();
       }
        return result;
    }

    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
    }
}