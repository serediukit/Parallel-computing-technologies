package main.java.TextDocumentFinder;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Document {
    private final String path;
    private final List<String> lines;

    public Document(String path, List<String> lines) {
        this.path = path;
        this.lines = lines;
    }

    public String getPath() {
        return path;
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

        return new Document(path, lines);
    }
}
