package calculator.controller;

import calculator.domain.Calculator;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final CalculatorService calculatorService = new CalculatorService();

    public void run() {
        outputView.start();
        int value = calculatorService.calculate(inputView.readLine());
        outputView.print(value);
    }
}
