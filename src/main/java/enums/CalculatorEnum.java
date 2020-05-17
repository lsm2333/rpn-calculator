package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.calculator.RpnCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <B>Description:</B> enum of calculator <br>
 * <B>Create on:</B> 2020-05-16 17:52 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum CalculatorEnum {
    RPN("rpn", RpnCalculator.class.getName());

    String name;
    String className;

    // using map for a constant lookup cost
    private static final Map<String, CalculatorEnum> lookup = new HashMap<>();
    private static final List<String> names = new ArrayList<>();

    static {
        for (CalculatorEnum o : values()) {
            lookup.put(o.getName(), o);
            names.add(o.getName());
        }
    }

    public static CalculatorEnum getEnum(String name) {
        return lookup.get(name);
    }

    public static List<String> getNames() {
        return names;
    }
}
