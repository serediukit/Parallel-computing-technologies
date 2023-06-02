package main.java.SimilarWordsFinder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TextFileGenerator {
    public static void generateFiles() {
        String filePath0 = "src\\main\\java\\SimilarWordsFinder\\texts\\text0.txt";
        String filePath1 = "src\\main\\java\\SimilarWordsFinder\\texts\\text1.txt";
        String[] content = generateRandomString();

        try {
            BufferedWriter writer0 = new BufferedWriter(new FileWriter(filePath0));
            writer0.write(content[0]);
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(filePath1));
            writer1.write(content[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] generateRandomString() {
        Random random = new Random();
        int wordCount = random.nextInt(10001) + 10000; // Random count of words between 10,000 and 20,000

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            int wordLength = random.nextInt(11) + 5; // Random word length between 5 and 15
            String word = generateRandomWord(wordLength);
            stringBuilder1.append(word).append(" ");
            stringBuilder2.append(random.nextInt(2) == 0 ? word : generateRandomWord(wordLength)).append(" ");
        }

        return new String[] {stringBuilder1.toString().trim(), stringBuilder2.toString().trim()};
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