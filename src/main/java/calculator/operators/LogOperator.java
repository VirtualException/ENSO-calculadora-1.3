/**
 * @name        Common logarithm function (with base 10)
 * @package     calculator.operators
 * @file        LogOperator.java
 * @description 
 */

package calculator.operators;

import static java.lang.Math.log10;

public class LogOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return log10(num);
    }
}
