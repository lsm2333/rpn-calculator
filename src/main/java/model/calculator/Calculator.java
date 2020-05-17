package model.calculator;

import exception.CalculatorException;
import model.others.ExtendStack;

/**
 * <B>Description:</B> calculator interface <br>
 * <B>Create on:</B> 2020-05-16 15:33 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public interface Calculator {

    /**
     * <B>Description:</B> accept input from command, and return stack as result <br>
     * <B>Create on:</B> 2020-05-16 17:02 <br>
     *
     * @param input string from command
     * @return stack containing results
     * @author shengming.lin
     */
    ExtendStack<Double> calculate(String input) throws CalculatorException;

    /**
     * <B>Description:</B> introduce itselft via console <br>
     * <B>Create on:</B> 2020-05-16 18:11 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    default void selfIntro() {
        System.out.println(String.format("Calculator [%s] is chosen", this.getClass().getSimpleName()));
    }

    /**
     * <B>Description:</B> get result stack <br>
     * <B>Create on:</B> 2020-05-17 17:17 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    ExtendStack<Double> getResultStack();
}
