package main.java.TextDocumentFinder;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Map<String, Map<String, Integer>>> {
    private final Document document;
    private final Set<String> keywords;

    DocumentTask(Document document, Set<String> keywords) {
        super();
        this.document = document;
        this.keywords = keywords;
    }

    @Override
    protected Map<String, Map<String, Integer>> compute() {
        Map<String, Integer> map = new HashMap<>();
        List<String> lines = document.getLines();
        HashMap<String, Map<String, Integer>> result = new HashMap<>();

        for(String line : lines) {
            String[] words = line.trim().split("(\\s|\\p{Punct})+");

            for(String word : words) {
                if (keywords.contains(word)) {
                    if(map.containsKey(word)) {
                        map.put(word, map.get(word) + 1);
                    } else {
                        map.put(word, 1);
                    }
                }
            }
        }

        result.put(document.getPath(), map);

        return result;
    }
}