package utils;

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
    public static Double factorial(Double number) {
        if (number <= 1D)
            return 1D;
        else
            return number * factorial(number - 1);
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
    public static Double reFactorial(Double fResult) {
        Double result = fResult;
        for (int i = 2; fResult > 1; i++) {
            fResult /= i;
            result = Double.valueOf(i);
        }
        return result;
    }

    /**
     * <B>Description:</B> try to parse input as double <br>
     * <B>Create on:</B> 2020-05-17 13:37 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    public static Double tryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
