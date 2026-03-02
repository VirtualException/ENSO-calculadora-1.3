/**
 * @name        Division operator
 * @package     calculator.operators
 * @file        DivideOperator.java
 * @description 
 */

package calculator.operators;

public class DivideOperator implements BinaryOperator {
    @Override
    public Double execute(Double num1, Double num2) {
        return num1 / num2;
    }
}
