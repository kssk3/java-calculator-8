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
        Assertions.assertEquals(6, calculator.getValue());
    }

    class Calculator {

        private static final String REGEX = "//(.)\n(.*)";
        private static final String DEFAULT_DELIMITER = ",|:";

        private static final int DEFAULT_VALUE = 0;

        private int value;

        public Calculator() {
            this.value = DEFAULT_VALUE;
        }

        public void run() {
            process();
        }

        public int getValue() {
            return value;
        }

        private void process() {
            // "//;\n1;2;3" ; => 구분 문자
            String input = "1,2,3";
            String delimiter = DEFAULT_DELIMITER;

            String[] numbers = input.split(delimiter);
            for (String number : numbers) {
                value += Integer.parseInt(number);
            }
        }
    }
}