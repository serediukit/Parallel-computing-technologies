package main.java.SimilarWordsFinder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class FolderTask extends RecursiveTask<Set<String>> {
    private final Folder folder;
    FolderTask(Folder folder) {
        super();
        this.folder = folder;
    }
    @Override
    protected Set<String> compute() {
        List<RecursiveTask<Set<String>>> forks = new LinkedList<>();

        for (var subFolder : folder.getSubFolders()) {
            FolderTask task = new FolderTask(subFolder);
            forks.add(task);
            task.fork();
        }

        for (var document : folder.getDocuments()) {
            DocumentTask task = new DocumentTask(document);
            forks.add(task);
            task.fork();
        }

        Set<String> result = new HashSet<>();

        for (int i = 0; i < forks.size(); i++) {
            var taskResult = forks.get(i).join();

            if(result.isEmpty()) result.addAll(taskResult);
            else result.retainAll(taskResult);
        }

        return result;
    }
}