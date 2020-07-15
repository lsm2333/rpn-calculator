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
    public static void calculateByOperandsNumber(ExtendStack<Double> result, ExtendStack<String> undoStack, RpnOperator operator, int index) throws CalculatorException {
        int operandsNumber = operator.getOperandsNumber();
        RpnOperatorUtil.checkOperandsNumber(result.size(), operandsNumber, index, operator.getSymbol());
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
            default: {
                popAndCalculateByOperandsNumber(result, operator, undoStack, operandsNumber);
            }
        }
    }

    /**
     * <B>Description:</B> deal with operand calculation according to operandsNumber<br>
     * <B>Create on:</B> 2020-07-13 22:27 <br>
     *
     * @param result         the result stack contains the operands and result
     * @param operator       operator of the calculation
     * @param undoStack      the stack for undo purpose
     * @param operandsNumber the required operands number of operator
     * @return
     * @throws CalculatorException see more detail in {@link RpnOperator#calculate(java.lang.Double, java.lang.Double...)}
     * @author shengming.lin
     */
    private static void popAndCalculateByOperandsNumber(ExtendStack<Double> result, RpnOperator operator, ExtendStack<String> undoStack, int operandsNumber) throws CalculatorException {
        // 1. pop from result stack
        Double[] more = new Double[operandsNumber];
        Double first = result.pop();
        for (int i = 0; i < operandsNumber - 1; i++) {
            more[i] = result.pop();
        }
        // 2. call calculate method to execute popped operands
        Double calculateResult = operator.calculate(first, more);
        // 3. add result into result stack
        result.add(calculateResult);
        // 4. add both operands and operator to undo stack
        for (int i = operandsNumber - 2; i >= 0; i--) {
            undoStack.add(String.valueOf(more[i]));
        }
        undoStack.add(String.valueOf(first));
        undoStack.add(operator.getSymbol());
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
     * @param result    the result stack
     * @param undoStack the undo stack
     * @return
     * @throws CalculatorException when a wrong operandsNumber is defined
     * @author shengming.lin
     */
    private static void undo(ExtendStack<Double> result, ExtendStack<String> undoStack) throws CalculatorException {
        String lastToken = null;
        try {
            lastToken = undoStack.pop();
        } catch (EmptyStackException e) {
            //allow to undo an empty stack here
        }
        // if there is no operator/operands in undoStack, rollback the result stack
        if (lastToken == null && !result.isEmpty()) {
            result.pop();
        } else {
            RpnOperator lastOperator = RpnOperator.getEnum(lastToken);
            rollbackByOperandNumber(result, undoStack, lastOperator.getOperandsNumber());
        }
    }

    /**
     * <B>Description:</B> rollback from undo stack according to operand number <br>
     * <B>Create on:</B> 2020-07-15 20:58 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    private static void rollbackByOperandNumber(ExtendStack<Double> result, ExtendStack<String> undoStack, int operandNumber) {
        result.pop();
        String[] popArray = new String[operandNumber];
        for (int i = 0; i < operandNumber; i++) {
            popArray[i] = undoStack.pop();
        }
        for (int i = operandNumber - 1; i >= 0; i--) {
            result.add(Double.valueOf(popArray[i]));
        }
    }
}
