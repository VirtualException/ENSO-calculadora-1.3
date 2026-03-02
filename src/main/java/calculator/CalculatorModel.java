/**
 * @name        Calculator Model class
 * @package     calculator
 * @file        CalculatorModel.java
 * @description Core logic of the caculator binary an unary operations.
 * It allows chaining of operations before pressing the equal button.
 */

package calculator;

import static java.lang.Double.NaN;
import calculator.operators.UnaryOperatorFactory;
import calculator.domain.UnaryOperatorModes;
import calculator.operators.BinaryOperatorFactory;
import calculator.domain.BinaryOperatorModes;

public class CalculatorModel {

    private Double num1 = 0.0;
    private Double num2 = 0.0;
    private BinaryOperatorModes pendingMode = BinaryOperatorModes.UNSET;

    /**
     * Executes the pending binary operation.
     */
    private Double calculatePendingOperation() {
        if (pendingMode == BinaryOperatorModes.UNSET) {
            return num2; // nothing to compute
        }
        var operator = BinaryOperatorFactory.create(pendingMode);
        return operator.execute(num1, num2);
    }

    /**
     * Called when a binary operator button is pressed
     */
    public Double calculateBinary(BinaryOperatorModes newMode, Double num) {
        if (pendingMode == BinaryOperatorModes.UNSET) {
            // No previous operation pending
            num1 = num;
            pendingMode = newMode;
            return null; // no result to show yet
        } else {
            // Execute previous operation
            num2 = num;
            num1 = calculatePendingOperation();
            pendingMode = newMode;
            return num1;
        }
    }

    /**
     * Called when EQUALS button is pressed
     */
    public Double calculateEqual(Double num) {
        if (pendingMode == BinaryOperatorModes.UNSET) {
            return num; // nothing pending, return current input
        }
        num2 = num;
        Double result = calculatePendingOperation();
        pendingMode = BinaryOperatorModes.UNSET; // reset
        return result;
    }

    /**
     * Reset calculator state
     */
    public Double reset() {
        num1 = 0.0;
        num2 = 0.0;
        pendingMode = BinaryOperatorModes.UNSET;
        return NaN;
    }

    /**
     * Unary operations
     */
    public Double calculateUnary(UnaryOperatorModes newMode, Double num) {
        return UnaryOperatorFactory.create(newMode).execute(num);
    }
}


