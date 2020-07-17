package model;

import stack.ExtendStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExtendStackTest {

    @Test
    public void testPrintStack() {
        System.out.println("current test is testPrintStack");
        ExtendStack<Double> extendStack = new ExtendStack<>(1D, 2D);
        extendStack.printStack();
    }

}
