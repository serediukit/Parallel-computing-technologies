package main.java.SimilarWordsFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Set<String>> {
    private final Document document;

    DocumentTask(Document document) {
        super();
        this.document = document;
    }
    @Override
    protected Set<String> compute() {
        List<String> lines = document.getLines();
        Set<String> result = new HashSet<>();
        for(var line : lines) {
            String[] words = line.trim().split("(\\s|\\p{Punct})+");

            for(var word : words) {
                result.add(word.toLowerCase());
            }
        }

        return result;
    }
}