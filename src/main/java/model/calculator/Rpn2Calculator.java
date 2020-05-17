package model.calculator;

import enums.RpnOperator;
import exception.CalculatorException;
import lombok.Getter;
import model.others.ExtendStack;
import utils.MathUtil;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

public class Rpn2Calculator implements Calculator {

    private ExtendStack<Double> resultStack = new ExtendStack<>();
    private Queue<String> inputQueue = new LinkedList<>();
    private ExtendStack<String> undoStack = new ExtendStack<>();

    public Rpn2Calculator() {
        this.selfIntro();
    }

    @Override
    public ExtendStack<Double> calculate(String input) throws CalculatorException {
        if (input == null) {
            throw new CalculatorException("Input cannot be null.");
        }
        //add into stack
        String[] inputSplit = input.split("\\s");
        for (String token : inputSplit) {
            inputQueue.add(token);
        }
        recursive(inputQueue, resultStack);
        return resultStack;
    }

    @Override
    public ExtendStack<Double> getResultStack() {
        return resultStack;
    }

    private ExtendStack<Double> recursive(Queue<String> input, ExtendStack<Double> result) throws CalculatorException {
        if (input == null || input.isEmpty()) {
            return result;
        }
        String firstToken = input.poll();
        Double tryParseDouble = MathUtil.tryParseDouble(firstToken);
        //if firstToken is number
        if (tryParseDouble != null) {
            result.add(tryParseDouble);
            return recursive(input, result);
        }
        RpnOperator operator = RpnOperator.getEnum(firstToken);
        if (operator == null) {
            throw new CalculatorException("unsupported operator: " + firstToken);
        }
        int operandsNumber = operator.getOperandsNumber();
        if (result.size() < operandsNumber) {
            throw new CalculatorException(String.format("operator %s (position: %d): insucient parameters", firstToken, 15));
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
                return recursive(input, result);
            }
            case 1: {
                Double pop = result.pop();
                Double calculateResult = operator.calculate(pop, null);
                result.add(calculateResult);
                undoStack.add(String.valueOf(pop));
                undoStack.add(firstToken);
                return recursive(input, result);
            }
            case 2: {
                Double pop1 = result.pop();
                Double pop2 = result.pop();
                Double calculateResult = operator.calculate(pop1, pop2);
                result.add(calculateResult);
                undoStack.add(String.valueOf(pop2));
                undoStack.add(String.valueOf(pop1));
                undoStack.add(firstToken);
                return recursive(input, result);
            }
            default: {
                throw new CalculatorException("wrong operandsNumber for operator: " + firstToken);
            }
        }
    }

    /**
     * <B>Description:</B> undo the last command <br>
     * <B>Create on:</B> 2020-05-17 16:12 <br>
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