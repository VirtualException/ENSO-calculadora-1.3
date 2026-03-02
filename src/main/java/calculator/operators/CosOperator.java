/**
 * @name        Cosine of an angle in Degrees
 * @package     calculator.operators
 * @file        CosOperator.java
 * @description 
 */

package calculator.operators;

public class CosOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.cos(Math.toRadians(num));
    }
}
