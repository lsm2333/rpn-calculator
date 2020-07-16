package model;

import exception.CalculatorException;
import model.calculator.RpnCalculator;
import model.others.ExtendStack;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RpnCalculatorTest {

    private static RpnCalculator rpnCalculator;

    @BeforeClass
    public static void prepare() {
        rpnCalculator = new RpnCalculator();
    }

    @After
    public void clear() {
        rpnCalculator.clearStack();
    }

    @Test
    public void testCase1() {
        testCommonLogic("case1",
                new String[]{"5 2"},
                new ExtendStack<>(5D, 2D));
    }

    @Test
    public void testCase2() {
        testCommonLogic("case2",
                new String[]{"2 sqrt", "clear 9 sqrt"},
                new ExtendStack<>(1.4142135623730951D),
                new ExtendStack<>(3D));
    }

    @Test
    public void testCase3() {
        testCommonLogic("case3",
                new String[]{"5 2 -", "3 -", "clear"},
                new ExtendStack<>(3D),
                new ExtendStack<>(0D),
                new ExtendStack<>());
    }

    @Test
    public void testCase4() {
        testCommonLogic("case4",
                new String[]{"5 4 3 2", "undo undo *", "5 *", "undo"},
                new ExtendStack<>(5D, 4D, 3D, 2D),
                new ExtendStack<>(20D),
                new ExtendStack<>(100D),
                new ExtendStack<>(20D, 5D)
        );
    }

    @Test
    public void testCase5() {
        testCommonLogic("case5",
                new String[]{"7 12 2 /", "*", "4 /"},
                new ExtendStack<>(7D, 6D),
                new ExtendStack<>(42D),
                new ExtendStack<>(10.5D)
        );
    }

    @Test
    public void testCase6() {
        testCommonLogic("case6",
                new String[]{"1 2 3 4 5", "*", "clear 3 4 -"},
                new ExtendStack<>(1D, 2D, 3D, 4D, 5D),
                new ExtendStack<>(1D, 2D, 3D, 20D),
                new ExtendStack<>(-1D)
        );
    }

    @Test
    public void testCase7() {
        testCommonLogic("case7",
                new String[]{"1 2 3 4 5", "* * * *"},
                new ExtendStack<>(1D, 2D, 3D, 4D, 5D),
                new ExtendStack<>(120D)
        );
    }

    @Test(expected = CalculatorException.class)
    public void testCase8() throws CalculatorException {
        try {
            System.out.println("----------");
            System.out.println("current test case is: case8");
            String input = "1 2 3 * 5 + * * 6 5";
            System.out.println(input);
            rpnCalculator.calculate(input);
        } catch (CalculatorException e) {
            ExtendStack<BigDecimal> result = rpnCalculator.getResultStack();
            ExtendStack<BigDecimal> expected = new ExtendStack<>();
            expected.add(BigDecimal.valueOf(11D).setScale(15, BigDecimal.ROUND_HALF_UP));
            assertEquals("expectation not matched", expected, result);
            assertEquals("exception message is not matched", "operator * (position: 8): insucient parameters", e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void testCaseFactorial() {
        testCommonLogic("CaseFactorial",
                new String[]{"4 n!", "undo"},
                new ExtendStack<>(24D),
                new ExtendStack<>(4D)
        );
    }

    @Test
    public void testCaseCos() {
        testCommonLogic("CaseCos",
                new String[]{"1 cos", "undo"},
                new ExtendStack<>(0.5403023058681398D),
                new ExtendStack<>(1D)
        );
    }

    @Test
    public void testCaseAtan() {
        testCommonLogic("CaseAtan",
                new String[]{"1 atan", "undo"},
                new ExtendStack<>(0.7853981633974483D),
                new ExtendStack<>(1D)
        );
    }

    @Test(expected = CalculatorException.class)
    public void testSqrtMinus() throws CalculatorException {
        try {
            System.out.println("----------");
            System.out.println("current test case is: SqrtMinus");
            String input = "-1 sqrt";
            System.out.println(input);
            rpnCalculator.calculate(input);
        } catch (CalculatorException e) {
            assertEquals("exception message is not matched", "Minus number is not supported.", e.getMessage());
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Test
    public void testSelfIntro() {
        System.out.println("----------");
        System.out.println("current test case is: testSelfIntro");
        rpnCalculator.selfIntro();
    }

    @Test
    public void testHandleException() {
        System.out.println("current test case is: testHandleException");
        rpnCalculator.handleException(new CalculatorException("testHandleException"));
    }

    @Test
    public void testThreeOperandsOperator() {
        testCommonLogic("testThreeOperandsOperator",
                new String[]{"1 2 3 to", "undo"},
                new ExtendStack<>(6D),
                new ExtendStack<>(1D, 2D, 3D)
        );
    }

    @Test
    public void testRedo() {
        testCommonLogic("testRedo",
                new String[]{"1 2 3 undo", "redo"},
                new ExtendStack<>(1D, 2D),
                new ExtendStack<>(1D, 2D, 3D)
        );
    }

    /**
     * <B>Description:</B> common logic of test <br>
     * <B>Create on:</B> 2020-07-14 02:06 <br>
     *
     * @param caseName      the name of test case
     * @param inputArray    the input array
     * @param expectedArray the array contains the expected stack
     * @return
     * @author shengming.lin
     */
    private void testCommonLogic(String caseName, String[] inputArray, ExtendStack<Double>... expectedArray) {
        System.out.println("----------");
        System.out.println("current test case is: " + caseName);
        ExtendStack<BigDecimal> result;
        try {
            for (int i = 0; i < inputArray.length; i++) {
                System.out.println(inputArray[i]);
                result = rpnCalculator.calculate(inputArray[i]);
                rpnCalculator.getResultStack().printStack();
                ExtendStack<Double> expectedDouble = expectedArray[i];
                ExtendStack<BigDecimal> expected = new ExtendStack<>();
                for (Double aDouble : expectedDouble) {
                    expected.add(BigDecimal.valueOf(aDouble).setScale(15, BigDecimal.ROUND_HALF_UP));
                }
                assertEquals("expectation not matched", expected, result);
            }
        } catch (CalculatorException e) {
            e.printStackTrace();
        }
    }
}
