package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Calculator {

    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String PREFIX = "//";
    private static final String NEW_LINE = "\n";
    private static final String ESCAPE_NEWLINE = "\\n";
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

        input = input.replace(ESCAPE_NEWLINE, NEW_LINE);

        String delimiter = DEFAULT_DELIMITER;
        String line = input;

        if(input.startsWith(PREFIX)) {
            DelimiterInfo info = extraDelimiter(input);

            delimiter = DEFAULT_DELIMITER + "|" + Pattern.quote(info.getDelimiter());
            line = info.getNumberString();
        }

        List<Integer> numbers = parseValue(delimiter, line);
        return new Calculator(numbers);
    }

    private static DelimiterInfo extraDelimiter(String input) {
        int escapeIndex = input.indexOf(NEW_LINE);

        DelimiterInfo customDelimiter = getDelimiterInfo(input, escapeIndex);
        if (customDelimiter != null) {
            return customDelimiter;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    private static DelimiterInfo getDelimiterInfo(String input, int escapeIndex) {
        if(escapeIndex != -1) {
            String customDelimiter = input.substring(PREFIX.length(), escapeIndex);
            String numbers = input.substring(escapeIndex + NEW_LINE.length());
            return new DelimiterInfo(customDelimiter, numbers, false);
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

}
