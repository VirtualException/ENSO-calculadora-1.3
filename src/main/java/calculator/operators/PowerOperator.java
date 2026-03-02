/**
 * @name        Exponentiation operator
 * @package     calculator.operators
 * @file        PowerOperator.java
 * @description 
 */

package calculator.operators;

import static java.lang.Math.pow;

public class PowerOperator implements BinaryOperator {
    @Override
    public Double execute(Double num1, Double num2) {
        return pow(num1, num2);
    }
}
