package main.java.SimilarWordsFinder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class FinderMain {
    public static void main(String[] args) {
        TextFileGenerator.generateFiles();

        try {
            Folder folder = Folder.fromDirectory(new File("src\\main\\java\\SimilarWordsFinder\\texts\\"));

            ForkJoinPool forkJoinPool = new ForkJoinPool();
            Set<String> result = forkJoinPool.invoke(new FolderTask(folder));
            System.out.println(Arrays.toString(result.toArray()));
        } catch (IOException ignored) {}
    }
}
