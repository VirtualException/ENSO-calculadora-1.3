/**
 * @name        Square root function
 * @package     calculator.operators
 * @file        SqrtOperator.java
 * @description 
 */

package calculator.operators;

public class SqrtOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.sqrt(num);
    }
}
