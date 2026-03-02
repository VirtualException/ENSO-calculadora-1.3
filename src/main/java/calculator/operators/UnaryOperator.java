/**
 * @name        Unary Operator Interface
 * @package     calculator.operators
 * @file        UnaryOperator.java
 * @description Strategy interface for unary (single operand) operations
 * Implementations define different mathematical operations that work on a single number.
 */

package calculator.operators;

public interface UnaryOperator {
    /**
     * Execute the operation on the given number
     * @param num The number to operate on
     * @return The result of the operation
     */
    Double execute(Double num);
}
