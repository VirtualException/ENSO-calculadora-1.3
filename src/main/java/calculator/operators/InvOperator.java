/**
 * @name        Inverse operator (1/x function)
 * @package     calculator.operators
 * @file        InvOperator.java
 * @description 
 */

package calculator.operators;

public class InvOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return 1 / num;
    }
}
