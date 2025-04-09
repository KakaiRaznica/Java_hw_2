package edu.phystech.hw2.analyzer;

import java.util.List;

public class NegativeTextAnalyzer extends KeywordAnalyzer {
    public static final List<String> NEGATIVE_KEYWORDS = List.of(":(", "=(", ":|");
    public NegativeTextAnalyzer() {
        super(NEGATIVE_KEYWORDS, Label.NEGATIVE);
    }

}