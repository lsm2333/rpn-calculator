package utils;

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
     * @param
     * @return
     * @author shengming.lin
     */
    public static BigDecimal factorial(BigDecimal number) {
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
     * @param
     * @return
     * @author shengming.lin
     */
    public static BigDecimal reFactorial(BigDecimal fResult) {
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
     * @param
     * @return
     * @author shengming.lin
     */
    public static BigDecimal tryParseBigDecimal(String str) {
        try {
            BigDecimal b = new BigDecimal(str);
            return b.setScale(15, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
