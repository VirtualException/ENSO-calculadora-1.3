/**
 * @name        Convertion from decimal to binary
 * @package     calculator.operators
 * @file        BinOperator.java
 * @description 
 */

package calculator.operators;

public class BinOperator implements UnaryOperator {
    @Override
    public Double execute(Double num) {

        if (num == null || Double.isNaN(num) || Double.isInfinite(num) || num < 0)
            return 0.0;

        // Convertemos a parte enteira a binario
        long n = num.longValue();
        String binStr = Long.toBinaryString(n);
        return Double.valueOf(binStr);
    }
}
