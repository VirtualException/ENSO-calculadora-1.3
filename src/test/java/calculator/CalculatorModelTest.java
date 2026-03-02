package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import calculator.domain.BinaryOperatorModes;
import calculator.domain.UnaryOperatorModes;
import static calculator.domain.BinaryOperatorModes.*;

class CalculatorModelTest {

    // ---------------- Binary Operator Tests ----------------

    @Test
    void calculateBinaryUnsetTest() {  

        // Check basic binary operation
        CalculatorModel calculator = new CalculatorModel();
        // Simulate pressing the num1 operator with the num1 number
        calculator.calculateBinary(UNSET, 3.0);
        // Simulate pressing the num2 number
        assertNull(calculator.calculateBinary(UNSET, 2.5));
    }    


    @ParameterizedTest(name = "{index} => mode={0}, num1={1}, num2={2}, expected={3}")
    @CsvSource({
        "ADD, 3.0, 2.5, 5.5",
        "MINUS, 3.1415, 1.1, 2.0415",
        "MULTIPLY, 3.2, -2.0, -6.4",
        "DIVIDE, 6.4, -2.0, -3.2",
        "DIVIDE, 6.4, 0.0, Infinity",
    })
    void calculateBinaryTest(BinaryOperatorModes mode, double num1, double num2, double expected) {  

        // Check basic binary operation
        CalculatorModel calculator = new CalculatorModel();
        // Simulate pressing the num1 operator with the num1 number
        calculator.calculateBinary(mode, num1);
        // Simulate pressing the num2 number
        assertEquals(expected, calculator.calculateBinary(UNSET, num2), 1e-10);

        // Simulate pressing equals after num2 number
        calculator = new CalculatorModel();
        // Simulate pressing the num1 operator with the num1 number
        calculator.calculateBinary(mode, num1);
        // Simulate pressing the num2 number
        assertEquals(expected, calculator.calculateEqual(num2), 1e-10);           

        // Chain comput. simulation: simulate pressing same operator after the num2 number
        calculator = new CalculatorModel();
        calculator.calculateBinary(mode, num1);
        assertEquals(expected, calculator.calculateBinary(mode, num2), 1e-10);    
    }

    @ParameterizedTest
    @CsvSource({
        "ADD, 6.4, 2.0, NaN"
    })
    void resetTest(BinaryOperatorModes mode, double num1, double num2, double expected) {
        CalculatorModel calculator = new CalculatorModel();
        calculator.calculateBinary(mode, num1);
        calculator.calculateBinary(mode, num2);
        assertEquals(expected, calculator.reset(), 1e-10);
    }

    // ---------------- Unary Operator Tests ----------------

    @ParameterizedTest(name = "{0}({1}) => expected {2}")
    @CsvSource({
        "SQUARE, 3.0, 9.0",
        "SQUARE, -4.0, 16.0",
        "SQRT, 25.0, 5.0",
        "SQRT, -1.0, NaN",
        "INV, 10.0, 0.1",
        "INV, 0.0, Infinity",
        "INV, 0.1, 10.0",
        "SIN, 30.0, 0.5",
        "SIN, -30.0, -0.5",
        "SIN, 390.0, 0.5",
        "COS, 60.0, 0.5",
        "COS, -60.0, 0.5",
        "COS, 420.0, 0.5",
        "TAN, 45.0, 1.0",
        "TAN, -45.0, -1.0",
        "TAN, 90.0, NaN",
        "TAN, -90.0, NaN",
        "TAN, 270.0, NaN",
        "LOG, 100.0, 2.0",
        "LOG, 1.0, 0.0",
        "LOG, 0.0, -Infinity",
        "LOG, -1.0, NaN",
        "LN, 2.718281828459045, 1.0",
        "LN, 1.0, 0.0",
        "LN, 0.0, -Infinity",
        "LN, -1.0, NaN",
        "PERCENT, 75.0, 0.75",
        "PERCENT, 150.0, 1.5",
        "PERCENT, 0.50, 0.005",
        "ABS, -3.0, 3.0",
        "ABS, 3.0, 3.0",
        "NEGATE, 5.0, -5.0",
        "NEGATE, -5.0, 5.0",
        "BIN, 10, 1010",
        "BIN, -10, 0",
        "BIN, NaN, 0",
        "BIN, Infinity, 0",
        "BIN, -Infinity, 0",
    })
    void calculateUnaryParameterizedTest(UnaryOperatorModes mode, double input, double expected) {
        CalculatorModel calculator = new CalculatorModel();
        assertEquals(expected, calculator.calculateUnary(mode, input), 1e-10);
    }
}