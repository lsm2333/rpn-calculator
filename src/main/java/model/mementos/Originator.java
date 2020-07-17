package model.mementos;

import model.others.ExtendStack;

import java.math.BigDecimal;

/**
 * <B>Description:</B> the character with state, and restore state from memento <br>
 * <B>Create on:</B> 2020-07-16 21:08 <br>
 *
 * @param
 * @return
 * @author shengming.lin
 */
public class Originator {
    private ExtendStack<BigDecimal> state = new ExtendStack<>();

    public void setState(ExtendStack<BigDecimal> state) {
        ExtendStack<BigDecimal> clone = (ExtendStack<BigDecimal>) state.clone();
        this.state = clone;
    }

    public ExtendStack<BigDecimal> getState() {
        return (ExtendStack<BigDecimal>) state.clone();
    }

    public Memento save() {
        return new CalculatorMemento(state);
    }

    public void restore(Memento Memento) {
        state = Memento.getState();
    }

    public void clear() {
        this.state.clear();
    }
}