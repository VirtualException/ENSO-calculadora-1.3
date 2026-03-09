package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import calculator.domain.BinaryOperatorModes;

class ControllerTest {

    @Test
    void onSpecialNumberPressedEUpdatesDisplay() {
        FakeView view = new FakeView();
        Controller controller = new Controller(new CalculatorModel(), view);

        controller.onSpecialNumberPressed("E");

        assertEquals(Math.E, view.getDisplayValue(), 1e-10);
    }

    @Test
    void onSpecialNumberPressedPiCanBeUsedInBinaryOperation() {
        FakeView view = new FakeView();
        Controller controller = new Controller(new CalculatorModel(), view);

        controller.onSpecialNumberPressed("PI");
        controller.onBinaryOperatorPressed(BinaryOperatorModes.ADD);
        controller.onNumberPressed(2);
        controller.onEqualsPressed();

        assertEquals(Math.PI + 2.0, view.getDisplayValue(), 1e-10);
    }

    private static final class FakeView implements View {
        private String display = "";

        @Override
        public void displayResult(Double result) {
            display = result == null ? "" : result.toString();
        }

        @Override
        public Double getDisplayValue() {
            if (display == null || display.isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(display);
        }

        @Override
        public void appendToDisplay(String text) {
            display += text;
        }

        @Override
        public void clearDisplay() {
            display = "";
        }

        @Override
        public void setDisplay(String text) {
            display = text;
        }

        @Override
        public void setActionListener(EventHandler listener) {
            // Not needed for controller unit tests.
        }
    }
}
