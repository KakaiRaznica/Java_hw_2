package edu.phystech.hw2.analyzer;


import java.util.List;

public abstract class KeywordAnalyzer implements TextAnalyzer {
    protected List<String> ban_words;
    private final Label label;

    protected KeywordAnalyzer(List<String> keywords, Label label) {
        this.ban_words = List.copyOf(keywords);
        this.label = label;
    }

    public Label processText(String text) {
        for (String word : ban_words) {
            if (text.contains(word + " ") || text.endsWith(word)) {
                return label;
            }
        }
        return Label.OK;
    }
}
