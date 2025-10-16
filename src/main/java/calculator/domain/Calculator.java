package calculator.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final String REGEX = "//(.)\n(.*)";
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String EXCEPTION_MESSAGE = "구분 문자와 양수만 입력 가능합니다.";
    private static final int DEFAULT_VALUE = 0;

    private int value;

    public Calculator() {
        this.value = DEFAULT_VALUE;
    }

    public void process(String input) {
        Pattern pattern = Pattern.compile(REGEX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String delimiter = Pattern.quote(matcher.group(1));
            String line = matcher.group(2);
            parseValue(delimiter, line);
        } else {
            parseValue(DEFAULT_DELIMITER, input);
        }
    }

    private void parseValue(String delimiter, String line) {
        String[] tokens = line.split(delimiter);
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            value += Integer.parseInt(token);
        }
    }

    public int getValue() {
        return value;
    }
}
