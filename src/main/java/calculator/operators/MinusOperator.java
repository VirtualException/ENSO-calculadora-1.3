/**
 * @name        Subtraction operator
 * @package     calculator.operators
 * @file        MinusOperator.java
 * @description 
 */

package calculator.operators;

public class MinusOperator implements BinaryOperator {
    @Override
    public Double execute(Double num1, Double num2) {
        return num1 - num2;
    }
}
