package utils;

import enums.CalculatorEnum;
import factory.CalculatorFactory;
import model.calculator.Calculator;

import java.util.Scanner;

/**
 * <B>Description:</B> a helper util for calculator scanner <br>
 * <B>Create on:</B> 2020-07-13 20:51 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class CalculatorScannerUtil {

    /**
     * <B>Description:</B> get calculator from scanner <br>
     * <B>Create on:</B> 2020-07-13 20:47 <br>
     *
     * @param scanner the scanner of console IO
     * @return an implementation of {@link Calculator}
     * @author shengming.lin
     */
    public static Calculator getCalculator(Scanner scanner) {
        Calculator calculator = null;
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
        return calculator;
    }
}
