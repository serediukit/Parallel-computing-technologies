package main.java.TextAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Analizer {
    private List<Text> textList = new ArrayList<>();

    public Analizer() {
    }

    public void addText(Text text) {
        textList.add(text);
    }

    public void Analize(int poolsCount) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(poolsCount);

    }
}
