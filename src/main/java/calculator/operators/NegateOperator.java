/**
 * @name        Negation operator (change of sign)
 * @package     calculator.operators
 * @file        NegateOperator.java
 * @description 
 */

package calculator.operators;

public class NegateOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return -num;
    }
}
