package model.calculator;

import enums.RpnOperator;
import exception.CalculatorException;
import model.others.ExtendStack;
import utils.MathUtil;
import utils.RpnOperatorUtil;

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
        // 1. if firstToken is number, just add to result stack
        if (tryParseDouble != null) {
            result.add(tryParseDouble);
            return recursiveCalculate(input, result, ++index);
        }
        // 2. the token is operator, try to execute the operator
        RpnOperator operator = RpnOperator.getEnum(firstToken);
        // 3. calculate according to the required number of different operator
        RpnOperatorUtil.calculateByOperandsNumber(result, undoStack, operator, index);
        return recursiveCalculate(input, result, ++index);
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
        undoStack.clear();
        this.inputQueue.clear();
    }

}