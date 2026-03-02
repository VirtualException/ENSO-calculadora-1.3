/**
 * @name        Tangent of an angle in degrees
 * @package     calculator.operators
 * @file        TanOperator.java
 * @description 
 */

package calculator.operators;

import static java.lang.Double.NaN;

public class TanOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {
        double eps = 1e-10;
        if (Math.abs(num % 180) < eps) {
            return 0.0;
        }
        else if (Math.abs(num % 90) < eps) {
            return NaN;
        }
        return Math.tan(Math.toRadians(num));
    }
}
