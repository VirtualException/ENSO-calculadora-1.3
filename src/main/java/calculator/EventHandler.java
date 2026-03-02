/**
 * @name        Calculator Event Handler interface
 * @package     calculator
 * @file        EventHandler.java
 * @description Interface that defines event handling methods for calculator 
 * user interactions.
 */

package calculator;

import calculator.domain.BinaryOperatorModes;
import calculator.domain.UnaryOperatorModes;

public interface EventHandler {
    
    /**
     * Handle a number button press
     * @param number The number pressed (0-9)
     */
    void onNumberPressed(int number);
    
    /**
     * Handle a decimal point button press
     */
    void onDecimalPressed();
    
    /**
     * Handle a binary operator button press
     * @param operator The operator (add, minus, multiply, divide, power)
     */
    void onBinaryOperatorPressed(BinaryOperatorModes mode);
    
    /**
     * Handle a unary operator button press
     * @param operator The operator (square, sqrt, sin, cos, etc)
     */
    void onUnaryOperatorPressed(UnaryOperatorModes mode);
    
    /**
     * Handle the equals button press
     */
    void onEqualsPressed();
    
    /**
     * Handle the clear/reset button press
     */
    void onClearPressed();
}
