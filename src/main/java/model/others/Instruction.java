package model.others;

import enums.RpnOperator;
import exception.CalculatorException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Instruction {
    RpnOperator rpnOperator;
    Double value;

    public String getReverseInstruction() throws CalculatorException {
        if (rpnOperator.getOperandsNumber() < 1)
            throw new CalculatorException(String.format("invalid operation for rpnOperator %s", rpnOperator.getSymbol()));

        return (rpnOperator.getOperandsNumber() < 2) ?
                String.format("%s", rpnOperator.getOpposite()) :
                String.format("%f %s %f", value, rpnOperator.getOpposite(), value);
    }
}