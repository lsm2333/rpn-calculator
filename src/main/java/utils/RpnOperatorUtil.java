package utils;

import enums.RpnOperator;
import exception.CalculatorException;
import model.others.ExtendStack;

import java.util.EmptyStackException;

/**
 * <B>Description:</B> util of rpn operator <br>
 * <B>Create on:</B> 2020-07-13 22:36 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class RpnOperatorUtil {

    /**
     * <B>Description:</B> examine number of operators  <br>
     * <B>Create on:</B> 2020-07-13 22:03 <br>
     * if result size is smaller than required operandsNumber, throw an exception
     *
     * @param resultSize the size of the result stack
     * @param index the index of the current token
     * @param operandsNumber the required number of the operands for current operator
     * @param operator current operator
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
    public static void calculateByOperandsNumber(ExtendStack<Double> result, ExtendStack<String> undoStack, String firstToken, RpnOperator operator, int index) throws CalculatorException {
        int operandsNumber = operator.getOperandsNumber();
        RpnOperatorUtil.checkOperandsNumber(result.size(), operandsNumber, index, firstToken);
        switch (operandsNumber) {
            case 0: {
                if (RpnOperator.CLEAR.equals(operator)) {
                    clear(result, undoStack);
                }
                if (RpnOperator.UNDO.equals(operator)) {
                    undo(result, undoStack);
                }
                break;
            }
            case 1: {
                oneOperandCalculate(result, firstToken, operator, undoStack);
                break;
            }
            case 2: {
                twoOperandCalculate(result, firstToken, operator, undoStack);
                break;
            }
            default: {
                throw new CalculatorException("wrong operandsNumber for operator: " + firstToken);
            }
        }
    }

    /**
     * <B>Description:</B> deal with two operand calculation <br>
     * <B>Create on:</B> 2020-07-13 22:27 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    private static void twoOperandCalculate(ExtendStack<Double> result, String firstToken, RpnOperator operator, ExtendStack<String> undoStack) throws CalculatorException {
        Double pop1 = result.pop();
        Double pop2 = result.pop();
        Double calculateResult = operator.calculate(pop1, pop2);
        result.add(calculateResult);
        undoStack.add(String.valueOf(pop2));
        undoStack.add(String.valueOf(pop1));
        undoStack.add(firstToken);
    }

    /**
     * <B>Description:</B> deal with one operand calculation <br>
     * <B>Create on:</B> 2020-07-13 22:27 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    private static void oneOperandCalculate(ExtendStack<Double> result, String firstToken, RpnOperator operator, ExtendStack<String> undoStack) throws CalculatorException {
        Double pop = result.pop();
        Double calculateResult = operator.calculate(pop, null);
        result.add(calculateResult);
        undoStack.add(String.valueOf(pop));
        undoStack.add(firstToken);
    }

    /**
     * <B>Description:</B> clear the result and the undo stack <br>
     * <B>Create on:</B> 2020-07-13 22:19 <br>
     *
     * @param
     * @author shengming.lin
     */
    private static void clear(ExtendStack<Double> result, ExtendStack<String> undoStack) {
        result.clear();
        undoStack.clear();
    }

    /**
     * <B>Description:</B> undo the last command <br>
     * <B>Create on:</B> 2020-05-17 16:12 <br>
     * every undo will pop the last token from undo-stack, and try to roll back numbers from undo-stack
     *
     * @param
     * @return
     * @author shengming.lin
     */
    private static void undo(ExtendStack<Double> result, ExtendStack<String> undoStack) throws CalculatorException {
        String lastToken = null;
        try {
            lastToken = undoStack.pop();
        } catch (EmptyStackException e) {
        }
        if (lastToken == null && !result.isEmpty()) {
            result.pop();
        } else {
            RpnOperator lastOperator = RpnOperator.getEnum(lastToken);
            int lastOperatorNumber = lastOperator.getOperandsNumber();
            switch (lastOperatorNumber) {
                case 1: {
                    result.pop();
                    result.add(Double.valueOf(undoStack.pop()));
                    break;
                }
                case 2: {
                    result.pop();
                    String pop1 = undoStack.pop();
                    String pop2 = undoStack.pop();
                    result.add(Double.valueOf(pop2));
                    result.add(Double.valueOf(pop1));
                    break;
                }
                default: {
                    throw new CalculatorException("wrong operandsNumber for operator: " + lastToken);
                }
            }
        }
    }
}
