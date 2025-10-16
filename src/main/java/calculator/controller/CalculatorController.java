package calculator.controller;

import calculator.domain.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private InputView inputView = new InputView();
    private OutputView outputView = new OutputView();
    private Calculator calculator = new Calculator();

    public void run() {
        outputView.start();
        int value = calculator.process(inputView.readLine());
        outputView.print(value);
    }
}
