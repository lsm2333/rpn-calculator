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

    ADDITION("+", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand + firstOperand;
        }
    },

    SUBTRACTION("-", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand - firstOperand;
        }
    },

    MULTIPLICATION("*", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand * firstOperand;
        }
    },

    DIVISION("/", 2) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            if (firstOperand == 0)
                throw new CalculatorException("Cannot divide by 0.");
            return secondOperand / firstOperand;
        }
    },

    SQUAREROOT("sqrt", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return sqrt(firstOperand);
        }
    },

    POWER("pow", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return pow(firstOperand, 2.0);
        }
    },

    UNDO("undo", 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    EXIT("exit", 0) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    FACTORIAL("n!", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return factorial(firstOperand);
        }
    },

    REVERSE_FACTORIAL("rv-n!", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return reFactorial(firstOperand);
        }
    },

    COS("cos", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return cos(firstOperand);
        }
    },

    ACOS("acos", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return acos(firstOperand);
        }
    },

    ATAN("atan", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return atan(firstOperand);
        }
    },

    TAN("tan", 1) {
        @Override
        public Double calculate(Double firstOperand, Double secondOperand) {
            return tan(firstOperand);
        }
    };

    private String symbol;
    private int operandsNumber;
    // using map for a constant lookup cost
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

    public abstract Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException;

    @Override
    public String toString() {
        return symbol;
    }
}