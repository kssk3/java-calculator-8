package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final String REGEX = "//(.)\n(.*)";
    private static final String DEFAULT_DELIMITER = ",|:";
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
        input = input.replace("\\n", "\n");
        Pattern pattern = Pattern.compile(REGEX, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        String delimiter = DEFAULT_DELIMITER;
        String line = input;

        if (matcher.matches()) {
            delimiter += "|" + Pattern.quote(matcher.group(1));
            line = matcher.group(2);
        }
        List<Integer> numbers = parseValue(delimiter, line);
        return new Calculator(numbers);
    }

    private static List<Integer> parseValue(String delimiter, String line) {
        List<Integer> result = new ArrayList<>();

        String[] tokens = line.split(delimiter);
        for (String token : tokens) {
            // 구분 문자로 tokens 값을 나눌 경우, 구분 문자는 제거되고
            // 구분 문자 사이의 값들만 배열에 포함됨
            // ex1) "1,2,3" => ["1", "2", "3"]
            // ex2) "1,,3" => ["1", "", "3"]  (연속된 구분자 사이는 빈 문자열)
            // ex3) "132343" (구분자가 3일 때) => ["1", "2", "", "4"]
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
