package main.java.TextAnalysis;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class AnalizeRecursiveTask extends RecursiveTask<Result> {
    private static final int THRESHOLD = 5;

    private final List<Text> texts;
    private final int start;
    private final int end;

    public AnalizeRecursiveTask(List<Text> texts, int start, int end) {
        this.texts = texts;
        this.start = start;
        this.end = end;
    }

    @Override
    public Result compute() {
        if (end - start <= THRESHOLD) {
            return analyzeRange(start, end);
        }

        int mid = start + (end - start) / 2;
        AnalizeRecursiveTask leftTask = new AnalizeRecursiveTask(texts, start, mid);
        AnalizeRecursiveTask rightTask = new AnalizeRecursiveTask(texts, mid, end);

        leftTask.fork();
        Result rightResult = rightTask.compute();
        Result leftResult = leftTask.join();

        return mergeResults(leftResult, rightResult);
    }

    private Result analyzeRange(int start, int end) {
        Result statistics = new Result();
        for (int i = start; i < end; i++) {
            String text = texts.get(i).getText();
            String[] words = text.split("\\s+");
            for (String word : words) {
                int length = word.length();
                statistics.addSample(length);
            }
        }
        return statistics;
    }

    private Result mergeResults(Result left, Result right) {
        left.merge(right);
        return left;
    }
}
