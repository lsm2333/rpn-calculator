package factory;

import enums.CalculatorEnum;
import model.calculator.Calculator;

/**
 * <B>Description:</B> a factory class to return a calculator instance <br>
 * <B>Create on:</B> 2020-05-16 17:08 <br>
 *
 * @author shengming.lin
 * @version 1.0
 */
public class CalculatorFactory {

    public Calculator getByName(String calculatorName) throws Exception {
        CalculatorEnum calculatorEnum = CalculatorEnum.getEnum(calculatorName);
        if (calculatorEnum == null) {
            throw new Exception("unknown calculator:" + calculatorName);
        }
        return (Calculator) Class.forName(calculatorEnum.getClassName()).newInstance();
    }
}
