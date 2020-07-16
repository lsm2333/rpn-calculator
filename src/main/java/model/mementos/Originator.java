package model.mementos;

import model.others.ExtendStack;

public class Originator {
    private ExtendStack state;

    public void setState(ExtendStack state) {
        this.state = state;
    }

    public ExtendStack getState() {
        return state;
    }

    public Memento save() {
        return new CalculatorMemento(state);
    }

    public void restore(Memento Memento) {
        state = Memento.getState();
    }
}