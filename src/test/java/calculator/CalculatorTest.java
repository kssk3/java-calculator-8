package calculator;

import static org.junit.jupiter.api.Assertions.*;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void test() {
        Calculator calculator = new Calculator();
        calculator.run();
        Assertions.assertEquals(1, calculator.getValue());
    }

    class Calculator {

        private static final String DELIMITER = "[,:]";
        private static final int DEFAULT_VALUE = 0;

        private List<Integer> numbers;
        private int value;

        public Calculator() {
            this.numbers = new ArrayList<>();
            this.value = DEFAULT_VALUE;
        }

        public void run() {
            String test = "1,2:3,4";
            String[] splited = test.split(DELIMITER);
            for (String s : splited) {
                this.value += Integer.parseInt(s);
            }
        }

        public int getValue() {
            return value;
        }
    }
}