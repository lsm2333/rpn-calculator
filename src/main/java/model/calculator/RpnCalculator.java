package model.calculator;

import enums.RpnOperator;
import exception.CalculatorException;
import model.others.ExtendStack;
import utils.MathUtil;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <B>Description:</B> use a recursiveCalculate method to implement rpn calculator <br>
 * <B>Create on:</B> 2020-05-17 17:25 <br>
 *
 * @author shengming.lin
 */
public class RpnCalculator implements Calculator {

    /**
     * reg expression of blank
     */
    public static final String BLANK = "\\s";

    /**
     * stack to hold the output number
     */
    private ExtendStack<Double> resultStack = new ExtendStack<>();

    /**
     * queue to hold the input token, one token is polled each time
     */
    private Queue<String> inputQueue = new LinkedList<>();

    /**
     * a stack for undo purpose
     */
    private ExtendStack<String> undoStack = new ExtendStack<>();

    /**
     * Initializes a rpn calculator with self introduce
     */
    public RpnCalculator() {
        this.selfIntro();
    }

    @Override
    public ExtendStack<Double> calculate(String input) throws CalculatorException {
        if (input == null) {
            throw new CalculatorException("Input cannot be null.");
        }
        //add into input queue
        String[] inputSplit = input.split(BLANK);
        for (String token : inputSplit) {
            inputQueue.add(token);
        }
        recursiveCalculate(inputQueue, resultStack, 0);
        return resultStack;
    }

    @Override
    public ExtendStack<Double> getResultStack() {
        return resultStack;
    }

    /**
     * <B>Description:</B> recursiveCalculate calculate, the recursiveCalculate stops when input is empty <br>
     * <B>Create on:</B> 2020-05-17 17:24 <br>
     * <p>
     * add number to result stack directly, and pop one/two number(s) when a token is operator
     *
     * @param input  queue of token
     * @param result result stack of calculation result
     * @param index  the index of current token, used to find out exception location
     * @return result stack
     * @author shengming.lin
     */
    private ExtendStack<Double> recursiveCalculate(Queue<String> input, ExtendStack<Double> result, int index) throws CalculatorException {
        if (input == null || input.isEmpty()) {
            return result;
        }
        String firstToken = input.poll();
        Double tryParseDouble = MathUtil.tryParseDouble(firstToken);
        // if firstToken is number, just add to result stack
        if (tryParseDouble != null) {
            result.add(tryParseDouble);
            return recursiveCalculate(input, result, ++index);
        }
        RpnOperator operator = RpnOperator.getEnum(firstToken);
        int operandsNumber = operator.getOperandsNumber();
        if (result.size() < operandsNumber) {
            throw new CalculatorException(String.format("operator %s (position: %d): insucient parameters", firstToken, ++index));
        }
        switch (operandsNumber) {
            //hard code here
            case 0: {
                //clear
                if (RpnOperator.CLEAR.equals(operator)) {
                    result.clear();
                    undoStack.clear();
                }
                //undo
                if (RpnOperator.UNDO.equals(operator)) {
                    undo(result);
                }
                return recursiveCalculate(input, result, ++index);
            }
            case 1: {
                Double pop = result.pop();
                Double calculateResult = operator.calculate(pop, null);
                result.add(calculateResult);
                undoStack.add(String.valueOf(pop));
                undoStack.add(firstToken);
                return recursiveCalculate(input, result, ++index);
            }
            case 2: {
                Double pop1 = result.pop();
                Double pop2 = result.pop();
                Double calculateResult = operator.calculate(pop1, pop2);
                result.add(calculateResult);
                undoStack.add(String.valueOf(pop2));
                undoStack.add(String.valueOf(pop1));
                undoStack.add(firstToken);
                return recursiveCalculate(input, result, ++index);
            }
            default: {
                throw new CalculatorException("wrong operandsNumber for operator: " + firstToken);
            }
        }
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
    private void undo(ExtendStack<Double> result) throws CalculatorException {
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

    /**
     * <B>Description:</B> simply clear queue and stack <br>
     * <B>Create on:</B> 2020-05-17 14:47 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    public void clearStack() {
        this.resultStack.clear();
        this.undoStack.clear();
        this.inputQueue.clear();
    }

}