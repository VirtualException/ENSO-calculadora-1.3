/**
 * @name        Natural logarithm function (with base e)
 * @package     calculator.operators
 * @file        LnOperator.java
 * @description 
 */

package calculator.operators;

import static java.lang.Math.log;

public class LnOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return log(num);
    }
}
