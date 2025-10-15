package calculator;

import static org.junit.jupiter.api.Assertions.*;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void test() {
        Calculator calculator = new Calculator();
        calculator.run();
        assertEquals(5, calculator.getValue());
    }

    class Calculator {

        private static final String REGEX = "//(.)\n(.*)";
        private static final String DEFAULT_DELIMITER = ",|:";

        private static final String EXCEPTION_MESSAGE = "구분 문자와 양수만 입력 가능합니다.";
        private static final int DEFAULT_VALUE = 0;

        private int value;

        public Calculator() {
            value = DEFAULT_VALUE;
        }

        public int getValue() {
            return value;
        }

        public void run() {
            process();
        }

        private void process() {
            // "//;\n1;2;3" ; => 구분 문자
            String input = "//;\n1;2;3";

            String delimiter = DEFAULT_DELIMITER;
            String numbers = "";

            Pattern pattern = Pattern.compile(REGEX, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(input);

            if(matcher.matches()){
                delimiter = matcher.group(1);
                numbers = matcher.group(2);
            }

            String[] tokens = numbers.split(delimiter);
            for (String token : tokens) {
                value +=  Integer.parseInt(token);
            }
        }


    }
}