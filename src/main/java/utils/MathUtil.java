package utils;

import exception.CalculatorException;

import java.math.BigDecimal;

/**
 * <B>Description:</B> implements some math functionality here <br>
 * <B>Create on:</B> 2020-05-16 21:58 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class MathUtil {

    /**
     * <B>Description:</B> factorial <br>
     * <B>Create on:</B> 2020-05-16 21:58 <br>
     * a recursive implementation
     *
     * @param number input to factorial
     * @return factorial result
     * @throws CalculatorException if number is null
     * @author shengming.lin
     */
    public static BigDecimal factorial(BigDecimal number) throws CalculatorException {
        if (number == null) {
            throw new CalculatorException("factorial input can't be null!");
        }
        if (number.compareTo(BigDecimal.ONE) <= 0)
            return BigDecimal.ONE;
        else
            return number.multiply(factorial(number.subtract(BigDecimal.ONE)));
    }

    /**
     * <B>Description:</B> reverse factorial <br>
     * <B>Create on:</B> 2020-05-16 21:58 <br>
     * a recursive implementation
     *
     * @param fResult number to re-factorial
     * @return re-factorial result
     * @throws CalculatorException if input is null
     * @author shengming.lin
     */
    public static BigDecimal reFactorial(BigDecimal fResult) throws CalculatorException {
        if (fResult == null) {
            throw new CalculatorException("reFactorial input can't be null!");
        }
        BigDecimal result = fResult;
        for (int i = 2; fResult.compareTo(BigDecimal.ONE) > 0; i++) {
            fResult = fResult.divide(BigDecimal.valueOf(i));
            result = BigDecimal.valueOf(i);
        }
        return result;
    }

    /**
     * <B>Description:</B> try to parse input as double, which will keep 15 decimal digits and in {@code BigDecimal.ROUND_HALF_DOWN} mode <br>
     * <B>Create on:</B> 2020-05-17 13:37 <br>
     *
     * @param str string to parse to bigDecimal
     * @return BigDecimal result parsed from string, null if string can't be parsed to BigDecimal
     * @throws CalculatorException if input is null
     * @author shengming.lin
     */
    public static BigDecimal tryParseBigDecimal(String str) throws CalculatorException {
        if (str == null) {
            throw new CalculatorException("tryParseBigDecimal input can't be null!");
        }
        try {
            BigDecimal b = new BigDecimal(str);
            return b.setScale(15, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
