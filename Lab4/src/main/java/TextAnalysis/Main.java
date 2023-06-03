package main.java.TextAnalysis;

public class Main {
    public static void main(String[] args) {
        final int FILES_COUNT = 100;
        TextFileGenerator.generateFiles(FILES_COUNT);

        for (int poolsCount = 1; poolsCount <= 10; poolsCount++) {
            Analizer analizer = new Analizer();

            for (int i = 0; i < FILES_COUNT; i++) {
                analizer.addText(new Text("text" + i + ".txt"));
            }

            long startTime = System.currentTimeMillis();
            analizer.Analize(poolsCount);
            long endTime = System.currentTimeMillis();

            System.out.println("Pools count: " + poolsCount);
            System.out.printf("Time: %-6d ms%n", (endTime - startTime));

            analizer.printResult();
        }
    }
}
