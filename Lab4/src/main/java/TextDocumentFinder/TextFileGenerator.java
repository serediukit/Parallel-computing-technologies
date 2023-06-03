package main.java.TextDocumentFinder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TextFileGenerator {
    public static void generateFiles(int count) {
        while (count-- > 0) {
            String filePath = "src\\main\\java\\TextDocumentFinder\\texts\\text" + count + ".txt";
            String content = generateRandomString();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateRandomString() {
        Random random = new Random();
        int wordCount = random.nextInt(10001) + 10000; // Random count of words between 10,000 and 20,000

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            int wordLength = random.nextInt(11) + 5; // Random word length between 5 and 15
            String word = random.nextInt(10) == 0 ? ("keyword" + i) : generateRandomWord(wordLength);
            stringBuilder.append(word).append(" ");
        }

        return stringBuilder.toString().trim();
    }

    private static String generateRandomWord(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a'); // Random lowercase character
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}