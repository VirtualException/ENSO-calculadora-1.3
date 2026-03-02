/**
 * @name        Calculator View interface
 * @package     calculator
 * @file        View.java
 * @description Interface for calculator views, agnostic to specific UI framework.
 */

package calculator;


public interface View {
    
    /**
     * Display the result on the calculator display
     * @param result The result to display
     */
    void displayResult(Double result);
    
    /**
     * Get the current value from the display
     * @return The value currently displayed
     */
    Double getDisplayValue();
    
    /**
     * Append text to the display
     * @param text The text to append
     */
    void appendToDisplay(String text);
    
    /**
     * Clear the display
     */
    void clearDisplay();
    
    /**
     * Replace the current display content
     * @param text The text to replace with
     */
    void setDisplay(String text);
    
    /**
     * Set a listener for button actions
     * @param listener The action listener
     */
    void setActionListener(EventHandler listener);
}
