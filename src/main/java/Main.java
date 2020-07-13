import enums.CalculatorEnum;
import enums.RpnOperator;
import exception.CalculatorException;
import model.calculator.Calculator;
import model.others.ExtendStack;
import utils.CalculatorScannerUtil;

import java.util.Scanner;

/**
 * <B>Description:</B> entry class <br>
 * <B>Create on:</B> 2020-07-13 20:54 <br>
 *
 * @author shengming.lin
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Firstly choose a calculator implementation
        System.out.println(String.format("Choose a calculator, supported options: %s", CalculatorEnum.getNames()));
        Calculator calculator = CalculatorScannerUtil.getCalculator(scanner);

        // 2. Secondly enter an expression
        System.out.println("Enter an expression now");
        while (true) {
            // 2.1 determine if further string is input
            String inputString = null;
            if (scanner.hasNextLine()) {
                inputString = scanner.nextLine();
            }
            // 2.2 exit command makes program stop
            if (RpnOperator.EXIT.getSymbol().equals(inputString)) {
                break;
            }
            try {
                ExtendStack<Double> stack = calculator.calculate(inputString);
                stack.printStack();
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
                ExtendStack<Double> resultStack = calculator.getResultStack();
                if (resultStack != null) {
                    resultStack.printStack();
                }
            }
        }
    }

}