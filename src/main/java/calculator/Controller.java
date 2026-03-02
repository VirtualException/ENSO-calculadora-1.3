/**
 * @name        Calculator Controller class
 * @package     calculator
 * @file        Controller.java
 * @description Controller class that implements EventHandler interface to call suitable
 * model methods, and updates the View accordingly.
 */


package calculator;

import calculator.domain.BinaryOperatorModes;
import calculator.domain.UnaryOperatorModes;

public class Controller implements EventHandler {
    
    private final CalculatorModel model;
    private final View view;
    private StringBuilder displayBuffer;
    private boolean resetingInput = false;

    public Controller(CalculatorModel model, View view) {
        this.model = model;
        this.view = view;
        this.displayBuffer = new StringBuilder();
        view.setActionListener(this);
    }
    
    @Override
    public void onNumberPressed(int number) {

        // After a user presses equals and gets a result, 
        // the next number press should start a new input
        if (resetingInput) {
            displayBuffer = new StringBuilder();
            view.clearDisplay();
            resetingInput = false;
        }

        displayBuffer.append(number);
        view.setDisplay(displayBuffer.toString());
    }
    
    @Override
    public void onDecimalPressed() {

        // After a user presses equals and gets a result, 
        // the next decimal press should start a new input
        if (resetingInput) {
            displayBuffer = new StringBuilder();
            view.clearDisplay();
            resetingInput = false;
        }
       
        // Prevent multiple decimal points in the current number
        if (!displayBuffer.toString().contains(".")) {
            // Handle leading decimal point by prepending a "0"
            if (displayBuffer.length() == 0) {
                displayBuffer.append("0");
            }
            displayBuffer.append(".");
            view.setDisplay(displayBuffer.toString());
        }
    }
    
    @Override
    public void onBinaryOperatorPressed(BinaryOperatorModes mode) {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateBinary(mode, num);

            displayBuffer = new StringBuilder();
            if (result != null) {
                view.setDisplay(formatResult(result));
                displayBuffer.append(result);
            }
            resetingInput = true;
        }
    }
    
    @Override
    public void onUnaryOperatorPressed(UnaryOperatorModes mode) {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateUnary(mode, num);

            displayBuffer = new StringBuilder();
            view.setDisplay(formatResult(result));
            displayBuffer.append(result);
            resetingInput = true;
        }
    }
    
    @Override
    public void onEqualsPressed() {

        if (displayBuffer.length() > 0) {

            Double num = view.getDisplayValue();
            Double result = model.calculateEqual(num);

            displayBuffer = new StringBuilder();
            view.setDisplay(formatResult(result));
            displayBuffer.append(result);
            resetingInput = true;
        }
    }
    
    @Override
    public void onClearPressed() {
        displayBuffer = new StringBuilder();
        model.reset();
        view.clearDisplay();
        resetingInput = false;
    }
    
    private String formatResult(Double result) {
        if (Double.isNaN(result)) {
            return "NaN";
        }
        else if (Double.isInfinite(result)) {
            if (result > 0) {
                return "Inf";
            } else {
                return "-Inf";
            }
        }
        else {
            String formatted = String.format(java.util.Locale.US, "%.10f", result);
            return formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }
}
