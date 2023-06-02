package main.java.TextAnalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Text {
    private String text;

    public Text(String fileName) {
        setStringFromFile(fileName);
    }

    public String getText() {
        return text;
    }

    private void setStringFromFile(String fileName) {
        String filePath = "src\\main\\java\\TextAnalysis\\texts\\" + fileName;
         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
             StringBuilder stringBuilder = new StringBuilder();
             String line;

             while ((line = reader.readLine()) != null) {
                 stringBuilder.append(line).append(" ");
             }

             text = stringBuilder.toString();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
}
