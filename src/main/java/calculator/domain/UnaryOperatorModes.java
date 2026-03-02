/**
 * @name        Unary Operator Mode enumeration
 * @package     calculator.domain
 * @file        UnaryOperatorModes.java
 * @description Enumeration with allowed unary (single operand) operator modes
 */


package calculator.domain;

public enum UnaryOperatorModes {
    SQUARE,     // Square
    SQRT,       // Square root
    INV,        // Inverstion (1/x)
    COS,        // Cosine of an angle in Degrees
    SIN,        // Sine  of an angle in Degrees
    TAN,        // Tangent  of an angle in Degrees
    LOG,        // Base-10 logarithm
    LN,         // Natural logarithm (base e)
    PERCENT,    // Percentage
    ABS,        // Absolute value
    BIN,        // Conversion from Decimal to Binary
    NEGATE      // Change of sign
}

