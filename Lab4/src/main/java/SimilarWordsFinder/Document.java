package main.java.SimilarWordsFinder;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Document {
    private final List<String> lines;

    public Document(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return new LinkedList<>(lines);
    }

    public static Document fromFile(File file) throws IOException {
        String path = file.getPath();
        List<String> lines = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Document(lines);
    }
}
