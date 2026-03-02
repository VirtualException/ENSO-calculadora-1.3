/**
 * @name        Addition operator
 * @package     calculator.operators
 * @file        AddOperator.java
 * @description 
 */

package calculator.operators;

public class AddOperator implements BinaryOperator {
    @Override
    public Double execute(Double num1, Double num2) {
        return num1 + num2;
    }
}
