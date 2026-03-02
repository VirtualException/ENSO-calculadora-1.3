/**
 * @name        Sine of an angle in Degrees
 * @package     calculator.operators
 * @file        SinOperator.java
 * @description 
 */

package calculator.operators;

public class SinOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        return Math.sin(Math.toRadians(num));
    }
}
