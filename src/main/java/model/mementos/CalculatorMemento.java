package model.mementos;

import model.others.ExtendStack;

/**
 * <B>Description:</B> memonto implementation for calculator <br>
 * <B>Create on:</B> 2020-07-16 11:30 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class CalculatorMemento implements Memento {
    private ExtendStack extendStack;

    public CalculatorMemento(ExtendStack extendStack) {
        this.extendStack = extendStack;
    }

    @Override
    public ExtendStack getState() {
        return extendStack;
    }
}
