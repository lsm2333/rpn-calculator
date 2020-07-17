package utils;

import enums.RpnOperator;
import exception.CalculatorException;
import model.calculator.RpnCalculator;
import model.mementos.CareTaker;
import model.mementos.Memento;
import model.others.ExtendStack;

import java.math.BigDecimal;

import static java.lang.Math.*;
import static utils.MathUtil.factorial;
import static utils.MathUtil.reFactorial;

/**
 * <B>Description:</B> util of rpn operator <br>
 * <B>Create on:</B> 2020-07-13 22:36 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class RpnOperatorUtil {

    private static CareTaker careTaker = new CareTaker();

    /**
     * <B>Description:</B> examine number of operators  <br>
     * <B>Create on:</B> 2020-07-13 22:03 <br>
     * if result size is smaller than required operandsNumber, throw an exception
     *
     * @param resultSize     the size of the result stack
     * @param index          the index of the current token
     * @param operandsNumber the required number of the operands for current operator
     * @param operator       current operator
     * @return
     * @throws CalculatorException if result stack doesn't match required operand number
     * @author shengming.lin
     */
    public static void checkOperandsNumber(int resultSize, int operandsNumber, int index, String operator) throws CalculatorException {
        if (resultSize < operandsNumber) {
            throw new CalculatorException(String.format("operator %s (position: %d): insucient parameters", operator, ++index));
        }
    }

    /**
     * <B>Description:</B> calculate according to required number of operands <br>
     * <B>Create on:</B> 2020-07-13 22:33 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    public static void calculateByOperandsNumber(RpnCalculator rpnCalculator, RpnOperator operator, int index) throws CalculatorException {
        int operandsNumber = operator.getOperandsNumber();
        RpnOperatorUtil.checkOperandsNumber(rpnCalculator.getState().size(), operandsNumber, index, operator.getSymbol());
        switch (operandsNumber) {
            case 0: {
                if (RpnOperator.CLEAR.equals(operator)) {
                    clear(rpnCalculator);
                }
                if (RpnOperator.UNDO.equals(operator)) {
                    undo(rpnCalculator);
                }
                if (RpnOperator.REDO.equals(operator)) {
                    redo(rpnCalculator);
                }
                break;
            }
            default: {
                popAndCalculateByOperandsNumber(rpnCalculator, operator, operandsNumber);
            }
        }
    }

    /**
     * <B>Description:</B> redo the undo command <br>
     * <B>Create on:</B> 2020-05-17 16:12 <br>
     *
     * @param rpnCalculator calculator instance, which will restore state by calling redo
     * @return
     * @throws CalculatorException when a wrong operandsNumber is defined
     * @author shengming.lin
     */
    private static void redo(RpnCalculator rpnCalculator) throws CalculatorException {
        rpnCalculator.restore(careTaker.redo());
    }

    /**
     * <B>Description:</B> deal with operand calculation according to operandsNumber<br>
     * <B>Create on:</B> 2020-07-13 22:27 <br>
     *
     * @param operator       operator of the calculation
     * @param operandsNumber the required operands number of operator
     * @return
     * @throws CalculatorException see more detail in {@link RpnOperatorUtil#calculate(enums.RpnOperator, java.math.BigDecimal, java.math.BigDecimal...)}
     * @author shengming.lin
     */
    private static void popAndCalculateByOperandsNumber(RpnCalculator rpnCalculator, RpnOperator operator, int operandsNumber) throws CalculatorException {
        // 1. pop from result stack
        ExtendStack<BigDecimal> result = (ExtendStack<BigDecimal>) rpnCalculator.getState().clone();
        BigDecimal[] more = new BigDecimal[operandsNumber];
        BigDecimal first = result.pop();
        for (int i = 0; i < operandsNumber - 1; i++) {
            more[i] = result.pop();
        }
        // 2. call calculate method to execute popped operands
        BigDecimal calculateResult = calculate(operator, first, more);
//        BigDecimal calculateResult = operator.calculate(first, more);
        // 3. add result into result stack
        result.add(calculateResult.setScale(15, BigDecimal.ROUND_HALF_UP));
        // 4. record state of calculator
        rpnCalculator.setState(result);
        careTaker.add(rpnCalculator.save());
    }

    /**
     * <B>Description:</B> clear the result and the undo stack <br>
     * <B>Create on:</B> 2020-07-13 22:19 <br>
     *
     * @param
     * @param rpnCalculator
     * @author shengming.lin
     */
    private static void clear(RpnCalculator rpnCalculator) {
        rpnCalculator.getState().clear();
        careTaker.clear();
    }

    /**
     * <B>Description:</B> undo the last command <br>
     * <B>Create on:</B> 2020-05-17 16:12 <br>
     *
     * @param rpnCalculator calculator instance, which will restore state by calling undo
     * @return
     * @throws CalculatorException when a wrong operandsNumber is defined
     * @author shengming.lin
     */
    private static void undo(RpnCalculator rpnCalculator) throws CalculatorException {
        rpnCalculator.restore(careTaker.undo());
    }

    /**
     * <B>Description:</B> record memento into memento list <br>
     * <B>Create on:</B> 2020-07-16 21:43 <br>
     *
     * @param memento
     * @return
     * @author shengming.lin
     */
    public static void record(Memento memento) {
        careTaker.add(memento);
    }

    /**
     * <B>Description:</B> calculate according to different operators <br>
     * <B>Create on:</B> 2020-07-17 10:36 <br>
     *
     * @param operator
     * @param first    first operand
     * @return
     * @author shengming.lin
     */
    public static BigDecimal calculate(RpnOperator operator, BigDecimal first, BigDecimal... more) throws CalculatorException {
        switch (operator) {
            case THREE_OPERANDS:
                return more[1].add(more[0]).add(first);
            case ADDITION:
                return more[0].add(first);
            case SUBTRACTION:
                return more[0].subtract(first);
            case MULTIPLICATION:
                return more[0].multiply(first);
            case DIVISION: {
                if (first.compareTo(BigDecimal.ZERO) == 0)
                    throw new CalculatorException("Cannot divide by 0.");
                return more[0].divide(first);
            }
            case SQUAREROOT: {
                if (first.compareTo(BigDecimal.ZERO) < 0) {
                    throw new CalculatorException("Minus number is not supported.");
                }
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(sqrt(doubleValue));
            }
            case POWER: {
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(pow(doubleValue, 2.0));
            }
            case FACTORIAL:
                return factorial(first);
            case REVERSE_FACTORIAL:
                return reFactorial(first);
            case COS: {
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(cos(doubleValue));
            }
            case ACOS: {
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(acos(doubleValue));
            }
            case TAN: {
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(tan(doubleValue));
            }
            case ATAN: {
                double doubleValue = first.doubleValue();
                return BigDecimal.valueOf(atan(doubleValue));
            }
            case UNDO:
            case REDO:
            case CLEAR:
            case EXIT:
                throw new CalculatorException("Invalid operation");
            default:
                throw new CalculatorException("Not supported operator: " + operator);
        }
    }
}
