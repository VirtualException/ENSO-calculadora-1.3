/**
 * @name        Calculator Application
 * @package     calculator
 * @file        CalculatorApp.java
 * @description Main application class that bootstraps the MVC components
 * and starts the calculator application.
 */

package calculator;

public class CalculatorApp {
    
    public static void main(String[] args) throws Exception {
        // Create the Model
        CalculatorModel model = new CalculatorModel();
        
        // Create the View
        SwingView view = new SwingView();
        
        // Create the Controller (linking View and Model)
        Controller controller = new Controller(model, view);
        
        // Initialize and show the UI
        view.init();
        
        System.out.println("Calculator application started successfully!");
    }
}
