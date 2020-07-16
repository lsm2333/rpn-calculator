package model.calculator;

import enums.RpnOperator;
import exception.CalculatorException;
import model.mementos.Originator;
import model.others.ExtendStack;
import utils.MathUtil;
import utils.RpnOperatorUtil;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <B>Description:</B> use a recursiveCalculate method to implement rpn calculator <br>
 * <B>Create on:</B> 2020-05-17 17:25 <br>
 *
 * @author shengming.lin
 */
public class RpnCalculator extends Originator implements Calculator {

    /**
     * reg expression of blank
     */
    public static final String BLANK = "\\s";

    /**
     * queue to hold the input token, one token is polled each time
     */
    private Queue<String> inputQueue = new LinkedList<>();

    /**
     * Initializes a rpn calculator with self introduce
     */
    public RpnCalculator() {
        this.selfIntro();
    }

    @Override
    public ExtendStack<BigDecimal> calculate(String input) throws CalculatorException {
        if (input == null) {
            throw new CalculatorException("Input cannot be null.");
        }
        //add into input queue
        String[] inputSplit = input.split(BLANK);
        for (String token : inputSplit) {
            inputQueue.add(token);
        }
        recursiveCalculate(inputQueue, 0);
        return getState();
    }

    @Override
    public ExtendStack<BigDecimal> getResultStack() {
        return getState();
    }

    /**
     * <B>Description:</B> recursiveCalculate calculate, the recursiveCalculate stops when input is empty <br>
     * <B>Create on:</B> 2020-05-17 17:24 <br>
     * <p>
     * add number to result stack directly, and pop one/two number(s) when a token is operator
     *
     * @param input queue of token
     * @param index the index of current token, used to find out exception location
     * @return result stack
     * @author shengming.lin
     */
    private ExtendStack<BigDecimal> recursiveCalculate(Queue<String> input, int index) throws CalculatorException {
        ExtendStack<BigDecimal> state = (ExtendStack<BigDecimal>) getState().clone();
        if (input == null || input.isEmpty()) {
            return state;
        }
        String firstToken = input.poll();
        BigDecimal tryParseBigDecimal = MathUtil.tryParseBigDecimal(firstToken);
        // 1. if firstToken is number, just add to result stack
        if (tryParseBigDecimal != null) {
            state.add(tryParseBigDecimal);
            this.setState(state);
            RpnOperatorUtil.record(this.save());
            return recursiveCalculate(input, ++index);
        }
        // 2. the token is operator, try to execute the operator
        RpnOperator operator = RpnOperator.getEnum(firstToken);
        // 3. calculate according to the required number of different operator
        RpnOperatorUtil.calculateByOperandsNumber(this, operator, index);
        return recursiveCalculate(input, ++index);
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
        this.getState().clear();
        this.inputQueue.clear();
    }

}