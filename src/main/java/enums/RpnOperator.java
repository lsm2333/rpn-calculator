package enums;

import exception.CalculatorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;
import static utils.MathUtil.factorial;
import static utils.MathUtil.reFactorial;

@Getter
@AllArgsConstructor
public enum RpnOperator {

    ADDITION("+", 2, "加法") {
        @Override
        public Double calculate(Double first, Double second) {
            return second + first;
        }
    },

    SUBTRACTION("-", 2, "减法") {
        @Override
        public Double calculate(Double first, Double second) {
            return second - first;
        }
    },

    MULTIPLICATION("*", 2, "乘法") {
        @Override
        public Double calculate(Double first, Double second) {
            return second * first;
        }
    },

    DIVISION("/", 2, "除法") {
        @Override
        public Double calculate(Double first, Double second) throws CalculatorException {
            if (first == 0)
                throw new CalculatorException("Cannot divide by 0.");
            return second / first;
        }
    },

    SQUAREROOT("sqrt", 1, "开方") {
        @Override
        public Double calculate(Double first, Double second) {
            return sqrt(first);
        }
    },

    POWER("pow", 1, "平方") {
        @Override
        public Double calculate(Double first, Double second) {
            return pow(first, 2.0);
        }
    },

    FACTORIAL("n!", 1, "阶乘") {
        @Override
        public Double calculate(Double first, Double second) {
            return factorial(first);
        }
    },

    REVERSE_FACTORIAL("rv-n!", 1, "逆阶乘") {
        @Override
        public Double calculate(Double first, Double second) {
            return reFactorial(first);
        }
    },

    COS("cos", 1, "余弦") {
        @Override
        public Double calculate(Double first, Double second) {
            return cos(first);
        }
    },

    ACOS("acos", 1, "反余弦") {
        @Override
        public Double calculate(Double first, Double second) {
            return acos(first);
        }
    },

    TAN("tan", 1, "正切") {
        @Override
        public Double calculate(Double first, Double second) {
            return tan(first);
        }
    },

    ATAN("atan", 1, "反正切") {
        @Override
        public Double calculate(Double first, Double second) {
            return atan(first);
        }
    },

    UNDO("undo", 0, "撤回") {
        @Override
        public Double calculate(Double first, Double second) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", 0, "清除") {
        @Override
        public Double calculate(Double first, Double second) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    EXIT("exit", 0, "退出") {
        @Override
        public Double calculate(Double first, Double second) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    };

    private String symbol;
    private int operandsNumber;
    private String chinese;

    public abstract Double calculate(Double first, Double second) throws CalculatorException;

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