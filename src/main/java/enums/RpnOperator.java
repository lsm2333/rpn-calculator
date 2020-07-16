package enums;

import exception.CalculatorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;
import static utils.MathUtil.factorial;
import static utils.MathUtil.reFactorial;

@Getter
@AllArgsConstructor
public enum RpnOperator {

    /**
     * a meaningless operator just to satisfy three operands number requirement
     */
    THREE_OPERANDS("to", 3, "3位操作符") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return more[1].add(more[0]).add(first);
        }
    },

    ADDITION("+", 2, "加法") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return more[0].add(first);
        }
    },

    SUBTRACTION("-", 2, "减法") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return more[0].subtract(first);
        }
    },

    MULTIPLICATION("*", 2, "乘法") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return more[0].multiply(first);
        }
    },

    DIVISION("/", 2, "除法") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) throws CalculatorException {
            if (first.compareTo(BigDecimal.ZERO) == 0)
                throw new CalculatorException("Cannot divide by 0.");
            return more[0].divide(first);
        }
    },

    SQUAREROOT("sqrt", 1, "开方") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(sqrt(doubleValue));
        }
    },

    POWER("pow", 1, "平方") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(pow(doubleValue, 2.0));
        }
    },

    FACTORIAL("n!", 1, "阶乘") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return factorial(first);
        }
    },

    REVERSE_FACTORIAL("rv-n!", 1, "逆阶乘") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            return reFactorial(first);
        }
    },

    COS("cos", 1, "余弦") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(cos(doubleValue));
        }
    },

    ACOS("acos", 1, "反余弦") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(acos(doubleValue));
        }
    },

    TAN("tan", 1, "正切") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(tan(doubleValue));
        }
    },

    ATAN("atan", 1, "反正切") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) {
            double doubleValue = first.doubleValue();
            return BigDecimal.valueOf(atan(doubleValue));
        }
    },

    UNDO("undo", 0, "撤回") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", 0, "清除") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    EXIT("exit", 0, "退出") {
        @Override
        public BigDecimal calculate(BigDecimal first, BigDecimal... more) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    };

    private String symbol;
    private int operandsNumber;
    private String chinese;

    public abstract BigDecimal calculate(BigDecimal first, BigDecimal... more) throws CalculatorException;

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