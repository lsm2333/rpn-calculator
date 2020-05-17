package model;

import exception.CalculatorException;
import model.calculator.Rpn2Calculator;
import model.others.ExtendStack;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class Rpn2CalculatorTest {

    private static Rpn2Calculator rpnCalculator;

    @BeforeClass
    public static void prepare() {
        rpnCalculator = new Rpn2Calculator();
    }

    @After
    public void clear() {
        rpnCalculator.clearStack();
    }

    @Test
    public void testCase1() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("5 2");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(5D);
        expected.add(2D);
        assertEquals("expectation not matched", expected, result);
    }

    @Test
    public void testCase2() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("2 sqrt");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(1.4142135623730951D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("clear 9 sqrt");
        expected.clear();
        expected.add(3D);
        assertEquals("expectation2 not matched", expected, result2);
    }

    @Test
    public void testCase3() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("5 2 -");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(3D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("3 -");
        expected.clear();
        expected.add(0D);
        assertEquals("expectation2 not matched", expected, result2);

        ExtendStack<Double> result3 = rpnCalculator.calculate("clear");
        expected.clear();
        assertEquals("expectation3 not matched", expected, result3);
    }

    @Test
    public void testCase4() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("5 4 3 2");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(5D);
        expected.add(4D);
        expected.add(3D);
        expected.add(2D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("undo undo *");
        expected.clear();
        expected.add(20D);
        assertEquals("expectation2 not matched", expected, result2);

        ExtendStack<Double> result3 = rpnCalculator.calculate("5 *");
        expected.clear();
        expected.add(100D);
        assertEquals("expectation3 not matched", expected, result3);

        ExtendStack<Double> result4 = rpnCalculator.calculate("undo");
        expected.clear();
        expected.add(20D);
        expected.add(5D);
        assertEquals("expectation4 not matched", expected, result4);
    }

    @Test
    public void testCase5() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("7 12 2 /");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(7D);
        expected.add(6D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("*");
        expected.clear();
        expected.add(42D);
        assertEquals("expectation2 not matched", expected, result2);

        ExtendStack<Double> result3 = rpnCalculator.calculate("4 /");
        expected.clear();
        expected.add(10.5D);
        assertEquals("expectation3 not matched", expected, result3);
    }

    @Test
    public void testCase6() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("1 2 3 4 5");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(1D);
        expected.add(2D);
        expected.add(3D);
        expected.add(4D);
        expected.add(5D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("*");
        expected.pop();
        expected.pop();
        expected.add(20D);
        assertEquals("expectation2 not matched", expected, result2);

        ExtendStack<Double> result3 = rpnCalculator.calculate("clear 3 4 -");
        expected.clear();
        expected.add(-1D);
        assertEquals("expectation3 not matched", expected, result3);
    }

    @Test
    public void testCase7() throws CalculatorException {
        ExtendStack<Double> result = rpnCalculator.calculate("1 2 3 4 5");

        ExtendStack<Double> expected = new ExtendStack<>();
        expected.add(1D);
        expected.add(2D);
        expected.add(3D);
        expected.add(4D);
        expected.add(5D);
        assertEquals("expectation not matched", expected, result);

        ExtendStack<Double> result2 = rpnCalculator.calculate("* * * *");
        expected.clear();
        expected.add(120D);
        assertEquals("expectation2 not matched", expected, result2);
    }

    @Test(expected = CalculatorException.class)
    public void testCase8() throws CalculatorException {
        try {
            rpnCalculator.calculate("1 2 3 * 5 + * * 6 5");
        } catch (CalculatorException e) {
            ExtendStack<Double> result = rpnCalculator.getResultStack();
            ExtendStack<Double> expected = new ExtendStack<>();
            expected.add(11D);
            assertEquals("expectation not matched", expected, result);
            throw e;
        }
    }
}
