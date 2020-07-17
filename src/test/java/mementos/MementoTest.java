package mementos;

import stack.ExtendStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class MementoTest {

    @Test
    public void testMementoPattern() {
        try {
            Originator originator = new Originator();
            CareTaker careTaker = new CareTaker();

            originator.setState(new ExtendStack<>(BigDecimal.valueOf(1D)));
            careTaker.add(originator.save());
            log("add", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(1D)));

            originator.setState(new ExtendStack<>(BigDecimal.valueOf(2D)));
            careTaker.add(originator.save());
            log("add", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(2D)));

            originator.restore(careTaker.undo());
            log("undo", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(1D)));

            originator.setState(new ExtendStack(BigDecimal.valueOf(3D)));
            careTaker.add(originator.save());
            log("add", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(3D)));

            originator.restore(careTaker.undo());
            log("undo", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(1D)));

            originator.restore(careTaker.redo());
            log("redo", originator.getState(), new ExtendStack<>(BigDecimal.valueOf(3D)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log(String command, ExtendStack actualState, ExtendStack expectedState) {
        System.out.println(String.format("After %s, Current State: %s", command, actualState));
        Assert.assertEquals(expectedState, actualState);
    }
}