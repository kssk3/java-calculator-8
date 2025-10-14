package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Calculator {

    private static final int INIT_VALUE = 0;

    private int value;

    public Calculator() {
        this.value = INIT_VALUE;
    }

    public void run() {
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();
        String[] splitedValue = input.split("");
        process(splitedValue);
    }

    private void process(String[] splitedValue) {
    }

    public int getValue() {
        return this.value;
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        cal.run();
    }
}
