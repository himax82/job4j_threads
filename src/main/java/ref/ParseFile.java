package ref;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    public static final Predicate<Integer> CONTENT  = integer -> integer > 0;
    public static final Predicate<Integer> CONTENTWITHOUTUNICODE  = integer -> integer < 0x80;
    private final File file;

    public ParseFile(final File file) {
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
}