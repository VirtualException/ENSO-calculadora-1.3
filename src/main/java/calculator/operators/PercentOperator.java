/**
 * @name        Percentage operator
 * @package     calculator.operators
 * @file        PercentOperator.java
 * @description 
 */

package calculator.operators;

public class PercentOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return num / 100;
    }
}
