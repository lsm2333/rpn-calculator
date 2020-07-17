package enums;

import exception.CalculatorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum RpnOperator {

    /**
     * a meaningless operator just to satisfy three operands number requirement
     */
    THREE_OPERANDS("to", 3, "3位操作符"),
    ADDITION("+", 2, "加法"),
    SUBTRACTION("-", 2, "减法"),
    MULTIPLICATION("*", 2, "乘法"),
    DIVISION("/", 2, "除法"),
    SQUAREROOT("sqrt", 1, "开方"),
    POWER("pow", 1, "平方"),
    FACTORIAL("n!", 1, "阶乘"),
    REVERSE_FACTORIAL("rv-n!", 1, "逆阶乘"),
    COS("cos", 1, "余弦"),
    ACOS("acos", 1, "反余弦"),
    TAN("tan", 1, "正切"),
    ATAN("atan", 1, "反正切"),
    UNDO("undo", 0, "撤回"),
    REDO("redo", 0, "重做"),
    CLEAR("clear", 0, "清除"),
    EXIT("exit", 0, "退出");

    private String symbol;
    private int operandsNumber;
    private String chinese;

    /**
     * using map for a constant lookup cost
     */
    private static final Map<String, RpnOperator> lookup = new HashMap<>();

    static {
        for (RpnOperator o : values()) {
            lookup.put(o.getSymbol(), o);
        }
    }

    public static RpnOperator getEnum(String value) throws CalculatorException {
        RpnOperator operator = lookup.get(value);
        if (operator == null) {
            throw new CalculatorException("unsupported operator: " + value);
        }
        return operator;
    }

    @Override
    public String toString() {
        return symbol;
    }
}