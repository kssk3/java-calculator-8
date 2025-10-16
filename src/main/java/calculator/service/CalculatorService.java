package calculator.service;

import calculator.domain.Calculator;

public class CalculatorService {

    public int value(String input) {
        Calculator calculator = Calculator.process(input);
        return calculator.sum();
    }
}
