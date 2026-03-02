/**
 * @name        Absolute value operator
 * @package     calculator.operators
 * @file        AbsOperator.java
 * @description 
 */

package calculator.operators;

public class AbsOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.abs(num);
    }
}
