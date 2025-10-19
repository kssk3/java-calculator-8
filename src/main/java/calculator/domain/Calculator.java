package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Calculator {

    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String PREFIX = "//";
    private static final String ESCAPE_NEWLINE = "\\n";
    private static final String NEW_LINE = "\n";
    private static final String EXCEPTION_MESSAGE = "구분 문자와 양수만 입력 가능합니다.";

    private List<Integer> values;

    public Calculator(List<Integer> numbers) {
        this.values = numbers;
    }

    public int sum() {
        return values.stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public static Calculator process(String input) {
        if(input == null || input.isEmpty()) {
            return new Calculator(new ArrayList<>());
        }

        String delimiter = DEFAULT_DELIMITER;
        String line = input;

        if(input.startsWith(PREFIX)) {
            DelimiterInfo info = extraDelimiter(input);
            if (info.usedNewLine) {
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }

            delimiter = DEFAULT_DELIMITER + "|" + Pattern.quote(info.delimiter);
            line = info.numberString;
        }

        List<Integer> numbers = parseValue(delimiter, line);
        return new Calculator(numbers);
    }

    private static DelimiterInfo extraDelimiter(String input) {
        int escapeIndex = input.indexOf(ESCAPE_NEWLINE);

        DelimiterInfo customDelimiter = getDelimiterInfo(input, escapeIndex);
        if (customDelimiter != null) {
            return customDelimiter;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    private static DelimiterInfo getDelimiterInfo(String input, int escapeIndex) {
        if(escapeIndex != -1) {
            String customDelimiter = input.substring(PREFIX.length(), escapeIndex);
            String numbers = input.substring(escapeIndex + ESCAPE_NEWLINE.length());
            return new DelimiterInfo(customDelimiter, numbers, false);
        }

        int newLineIndex = input.indexOf(NEW_LINE);
        if (newLineIndex != -1) {
            String customDelimiter = input.substring(PREFIX.length(), newLineIndex);
            String numbers = input.substring(newLineIndex + NEW_LINE.length());
            return new DelimiterInfo(customDelimiter, numbers, true);
        }
        return null;
    }

    private static List<Integer> parseValue(String delimiter, String line) {
        List<Integer> result = new ArrayList<>();

        String[] tokens = line.split(delimiter);
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            try {
                result.add(Integer.parseInt(token));
                if (result.getLast() <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }
        }

        return result;
    }
    
    private static class DelimiterInfo{
        private final String delimiter;
        private final String numberString;
        private final boolean usedNewLine;

        public DelimiterInfo(String delimiter, String numberString, boolean usedNewLine) {
            this.delimiter = delimiter;
            this.numberString = numberString;
            this.usedNewLine = usedNewLine;
        }
    }
}
