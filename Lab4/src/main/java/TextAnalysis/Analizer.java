package main.java.TextAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Analizer {
    private List<Text> textList = new ArrayList<>();
    private Result result;

    public Analizer() {
    }

    public void addText(Text text) {
        textList.add(text);
    }

    public void Analize(int poolsCount) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(poolsCount);
        AnalizeRecursiveTask task = new AnalizeRecursiveTask(textList, 0, textList.size());
        result = forkJoinPool.invoke(task);
    }

    public void printResult() {
        System.out.println("Word Length Statistics:");
        System.out.println("Minimum Length: " + result.getMinLength());
        System.out.println("Maximum Length: " + result.getMaxLength());
        System.out.println("Average Length: " + result.getAverageLength());
        System.out.println();
    }
}
