package calculator.controller;

import calculator.domain.Calculator;
import calculator.service.CalculatorService;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private InputView inputView = new InputView();
    private OutputView outputView = new OutputView();
    private CalculatorService calculatorService = new CalculatorService();

    public void run() {
        outputView.start();
        int value = calculatorService.value(inputView.readLine());
        outputView.print(value);
    }
}
