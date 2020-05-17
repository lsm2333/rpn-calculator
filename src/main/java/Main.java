import enums.CalculatorEnum;
import enums.RpnOperator;
import exception.CalculatorException;
import factory.CalculatorFactory;
import model.others.ExtendStack;
import model.calculator.Calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = null;

        System.out.println(String.format("Choose a calculator, supported options: %s", CalculatorEnum.getNames()));
        while (scanner.hasNextLine()) {
            String calculatorName = scanner.nextLine();
            try {
                calculator = new CalculatorFactory().getByName(calculatorName);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                System.out.println(String.format("Choose a calculator, supported options: %s", CalculatorEnum.getNames()));
                continue;
            }
            break;
        }

        System.out.println("Enter an expression now");
        while (true) {
            // determine if further string is input
            String inputString = null;
            if (scanner.hasNextLine()) {
                inputString = scanner.nextLine();
            }
            // exit command makes program stop
            if (RpnOperator.EXIT.getSymbol().equals(inputString)) {
                break;
            }
            try {
                ExtendStack<Double> stack = calculator.calculate(inputString);
                stack.printStack();
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}