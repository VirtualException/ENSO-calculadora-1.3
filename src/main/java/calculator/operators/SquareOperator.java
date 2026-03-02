/**
 * @name        Square function
 * @package     calculator.operators
 * @file        SquareOperator.java
 * @description 
 */

package calculator.operators;

public class SquareOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return num * num;
    }
}
