/**
 * @name        Binary Operator Interface
 * @package     calculator.operators
 * @file        BinaryOperator.java
 * @description Strategy interface for binary (two operand) operations
 */

package calculator.operators;

public interface BinaryOperator {
    /**
     * Execute the operation on the two given numbers
     * @param num1 The first operand
     * @param num2 The second operand
     * @return The result of the operation
     */
    Double execute(Double num1, Double num2);
}
