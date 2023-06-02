package main.java.TextAnalysis;

public class Result {
    private int minLength;
    private int maxLength;
    private long sumLengths;
    private long sampleCount;

    public Result() {
        minLength = Integer.MAX_VALUE;
        maxLength = Integer.MIN_VALUE;
        sumLengths = 0;
        sampleCount = 0;
    }

    public void addSample(int length) {
        if (length < minLength) {
            minLength = length;
        }
        if (length > maxLength) {
            maxLength = length;
        }
        sumLengths += length;
        sampleCount++;
    }

    public void merge(Result other) {
        if (other.minLength < minLength) {
            minLength = other.minLength;
        }
        if (other.maxLength > maxLength) {
            maxLength = other.maxLength;
        }
        sumLengths += other.sumLengths;
        sampleCount += other.sampleCount;
    }

    public int getMinLength() {
        return minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public double getAverageLength() {
        if (sampleCount == 0) {
            return 0.0;
        }
        return (double) sumLengths / sampleCount;
    }
}
