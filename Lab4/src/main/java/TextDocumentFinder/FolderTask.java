package main.java.TextDocumentFinder;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class FolderTask extends RecursiveTask<Map<String, Map<String, Integer>>> {
    private final Folder folder;
    private final Set<String> keywords;

    FolderTask(Folder folder, Set<String> keywords) {
        super();
        this.folder = folder;
        this.keywords = keywords;
    }

    @Override
    protected Map<String, Map<String, Integer>> compute() {
        List<RecursiveTask<Map<String, Map<String, Integer>>>> forks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderTask task = new FolderTask(subFolder, keywords);
            forks.add(task);
            task.fork();
        }

        for (Document document : folder.getDocuments()) {
            DocumentTask task = new DocumentTask(document, keywords);
            forks.add(task);
            task.fork();
        }

        Map<String, Map<String, Integer>> result = new HashMap<>();

        for (RecursiveTask<Map<String, Map<String, Integer>>> fork : forks) {
            Map<String, Map<String, Integer>> taskResult = fork.join();
            result.putAll(taskResult);
        }

        return result;
    }
}