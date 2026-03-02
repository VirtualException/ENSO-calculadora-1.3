/**
 * @name        Binary Operator Factory class
 * @package     calculator.operators
 * @file        BinaryOperatorFactory.java
 * @description Factory for creating BinaryOperator instances
 */

package calculator.operators;

import calculator.domain.BinaryOperatorModes;

public class BinaryOperatorFactory {
    
    /**
     * Create an instance of a BinaryOperator based on the provided mode.
     * @param mode The mode of the binary operator to create
     * @return The created BinaryOperator instance
     * @throws IllegalArgumentException if the mode is not recognized
     */
    public static BinaryOperator create(BinaryOperatorModes mode) {
        return switch (mode) {
            case ADD -> new AddOperator();
            case MINUS -> new MinusOperator();
            case MULTIPLY -> new MultiplyOperator();
            case DIVIDE -> new DivideOperator();
            case POWER -> new PowerOperator();
            default -> throw new IllegalArgumentException("Unknown Binary Operator Mode: " + mode);
        };
    }
}
