package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final String REGEX = "//(.)\n(.*)";
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String EXCEPTION_MESSAGE = "구분 문자와 양수만 입력 가능합니다.";
    private static final int DEFAULT_VALUE = 0;

    private List<Integer> values;

    public Calculator() {
        this.values = new ArrayList<>();
    }

    public int sum() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int process(String input) {
        input = input.replace("\\n", "\n");
        Pattern pattern = Pattern.compile(REGEX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        String delimiter = DEFAULT_DELIMITER;
        String line = input;

        if (matcher.matches()) {
            delimiter += "|" + Pattern.quote(matcher.group(1));
            line = matcher.group(2);
        }
        parseValue(delimiter, line);
        return sum();
    }

    private void parseValue(String delimiter, String line) {
        String[] tokens = line.split(delimiter);
        for (String token : tokens) {
            try{
                Integer result = Integer.parseInt(token);
                if(result <= 0){
                    throw new IllegalArgumentException();
                }
                values.add(result);
            }catch (NumberFormatException e){
                throw new IllegalArgumentException(EXCEPTION_MESSAGE);
            }
        }
    }

}
