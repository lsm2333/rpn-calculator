import enums.RpnOperator;
import exception.CalculatorException;
import calculator.Calculator;
import calculator.RpnCalculator;
import stack.ExtendStack;

import java.math.BigDecimal;
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

        // 1. Firstly init a Calculator
        Calculator calculator = new RpnCalculator();

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
                // 2.3 call calculate method to get result stack
                ExtendStack<BigDecimal> stack = calculator.calculate(inputString);
                stack.printStack();
            } catch (CalculatorException e) {
                // 2.4 exception handler
                calculator.handleException(e);
            }
        }
    }
}