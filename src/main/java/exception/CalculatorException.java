package exception;

/**
 * <B>Description:</B> self customized exception for calculator <br>
 * <B>Create on:</B> 2020-05-16 18:55 <br>
 *
 * @author shengming.lin
 */
public class CalculatorException extends Exception {
    public CalculatorException(String message) {
        super(message);
    }
}