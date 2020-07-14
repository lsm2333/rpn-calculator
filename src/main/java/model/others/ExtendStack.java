package model.others;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Stack;

/**
 * <B>Description:</B> extends Stack with customized method <br>
 * <B>Create on:</B> 2020-05-16 14:46 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class ExtendStack<E> extends Stack<E> {

    public ExtendStack() {
        super();
    }

    public ExtendStack(E... e) {
        super();
        addAll(Arrays.asList(e));
    }

    /**
     * <B>Description:</B> print elements in stack with specific format <br>
     * <B>Create on:</B> 2020-05-16 14:59 <br>
     *
     * @param
     * @return
     * @author shengming.lin
     */
    public void printStack() {
        DecimalFormat fmt = new DecimalFormat("0.##########");
        fmt.setRoundingMode(RoundingMode.FLOOR);
        System.out.print("stack: ");

        ListIterator<E> eListIterator = listIterator();
        while (eListIterator.hasNext()) {
            System.out.print(fmt.format(eListIterator.next()));
            System.out.print(" ");
        }
        System.out.printf("%n");
    }
}
