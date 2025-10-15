package calculator;

public class CalculatorController {

    private InputView inputView = new InputView();
    private OutputView outputView = new OutputView();
    private Calculator calculator = new Calculator();

    public void run() {
        String input = inputView.readLine();
        calculator.process(input);
    }
}
