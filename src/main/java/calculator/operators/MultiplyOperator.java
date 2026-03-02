/**
 * @name        Multiplication operator
 * @package     calculator.operators
 * @file        MultiplyOperator.java
 * @description 
 */

package calculator.operators;

public class MultiplyOperator implements BinaryOperator {
    @Override
    public Double execute(Double num1, Double num2) {
        return num1 * num2;
    }
}
