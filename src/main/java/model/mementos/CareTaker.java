package model.mementos;

import exception.CalculatorException;
import model.others.ExtendStack;

import java.util.LinkedList;
import java.util.List;

/**
 * <B>Description:</B> agent for memento undo/redo/add/get operation, but unable to modify memento <br>
 * <B>Create on:</B> 2020-07-16 15:57 <br>
 *
 * @author shengming.lin
 */
public class CareTaker {
    private List<Memento> mementoList = new LinkedList<>();
    private int currentIndex = 0;

    /**
     * <B>Description:</B> add state to memento list, and refresh current index <br>
     * <B>Create on:</B> 2020-07-16 16:06 <br>
     *
     * @param state represented by memento class
     * @return
     * @author shengming.lin
     */
    public void add(Memento state) {
        // remove the memento after current index
        for (int i = mementoList.size() - 1; i > currentIndex; i--) {
            this.mementoList.remove(i);
        }
        this.mementoList.add(state);
        currentIndex = this.mementoList.size() - 1;
    }

    public Memento get(int index) {
        return this.mementoList.get(index);
    }

    /**
     * <B>Description:</B> roll back the result by fetching last memento <br>
     * <B>Create on:</B> 2020-07-16 16:07 <br>
     *
     * @return
     * @author shengming.lin
     */
    public Memento undo() throws CalculatorException {
        this.currentIndex = --this.currentIndex;
        // if there is no step to roll back, clear the memento list
        if (this.currentIndex == -1) {
            this.mementoList.clear();
            return new CalculatorMemento(new ExtendStack());
        }
        // if current index is minus, throw exception
        if (this.currentIndex < -1) {
            this.currentIndex = 0;
            throw new CalculatorException("Invalid undo operation, unable to roll back");
        }
        Memento result = this.mementoList.get(this.currentIndex);
        return result;
    }

    /**
     * <B>Description:</B> step forward to next memento <br>
     * <B>Create on:</B> 2020-07-16 16:07 <br>
     *
     * @return
     * @author shengming.lin
     */
    public Memento redo() throws CalculatorException {
        if (this.currentIndex + 1 >= this.mementoList.size()) {
            throw new CalculatorException("Invalid redo operation, unable to step forward");
        }
        this.currentIndex = ++this.currentIndex;
        Memento result = this.mementoList.get(this.currentIndex);
        return result;
    }

    /**
     * <B>Description:</B> clear memento list <br>
     * <B>Create on:</B> 2020-07-16 16:20 <br>
     *
     * @return
     * @author shengming.lin
     */
    public void clear() {
        this.mementoList.clear();
    }
}