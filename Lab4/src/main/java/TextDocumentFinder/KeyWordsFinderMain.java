package main.java.TextDocumentFinder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class KeyWordsFinderMain {
    public static void main(String[] args) {
        TextFileGenerator.generateFiles(20);

        try {
            Folder folder = Folder.fromDirectory(new File("src\\main\\java\\SimilarWordsFinder\\texts\\"));
            Set<String> keywords = generateKeywordsSet();
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            FolderTask task = new FolderTask(folder, keywords);
            Map<String, Map<String, Integer>> result = forkJoinPool.invoke(task);
            result.forEach((key, value) -> System.out.println(key + " " + value));
        } catch (IOException ignored) {}
    }

    private static Set<String> generateKeywordsSet() {
        Random random = new Random();
        String[] set = new String[5];
        for (int i = 0; i < set.length; i++) {
            set[i] = "keyword" + random.nextInt(10000);
        }
        return new HashSet<>(Arrays.stream(set).toList());
    }
}
