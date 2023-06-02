package main.java.TextAnalysis;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Analizer analizer = new Analizer();

        for (int i = 0; i < 20; i++) {
            analizer.addText(new Text("text" + i + ".txt"));
        }

        analizer.Analize(10);

        analizer.printResult();
    }
}
