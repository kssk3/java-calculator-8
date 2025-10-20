package calculator.domain;

public class DelimiterInfo {

    private final String delimiter;
    private final String numberString;
    private final boolean usedNewLine;

    public DelimiterInfo(String delimiter, String numberString, boolean usedNewLine) {
        this.delimiter = delimiter;
        this.numberString = numberString;
        this.usedNewLine = usedNewLine;
    }

    public boolean isUsedNewLine() {
        return usedNewLine;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getNumberString() {
        return numberString;
    }
}
