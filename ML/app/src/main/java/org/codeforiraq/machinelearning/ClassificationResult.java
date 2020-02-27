package org.codeforiraq.machinelearning;

public class ClassificationResult {

    public final String title;
    public final float confidence;

    public ClassificationResult(String title, float confidence) {
        this.title = title;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return title + " " + String.format("(%.1f%%) ", confidence * 100.0f);
    }
}
