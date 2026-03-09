/**
 * @name        Swing implementation of Calculator View interface
 * @package     calculator
 * @file        SwingView.java
 * @description 
 */

package calculator;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;

import static calculator.domain.BinaryOperatorModes.*;
import static calculator.domain.UnaryOperatorModes.*;

public class SwingView implements View {

    private static final Logger LOGGER = Logger.getLogger(SwingView.class.getName());
    private static final String CONFIG_FILE = "config.properties";
    
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JPanel[] subPanels;
    private final JTextField text;

    private final JButton[] butNums;
    private final JButton butAdd, butMinus, butMultiply, butDivide,
            butEqual, butCancel, butSqrt, butSquare, butInv, butCos, 
            butSin, butTan, butPower, butLog, butPercent, butAbs, butBin, 
            butln, butNegate, butDecimal;

    private EventHandler eventHandler;

    private Font numberFont;
    private Font functionFont;
    private Font textFont;
    private final ImageIcon image;

    private final DecimalFormat decimalFormat;
    private boolean startNewInput = true;
    
    // Configuración
    private Properties config;

    public enum ButtonType { NUMBER, FUNCTION }

    public SwingView() throws IOException {
        Locale.setDefault(Locale.US);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("0.###############", symbols);
        decimalFormat.setGroupingUsed(false);
        
        // Cargar configuración
        loadConfiguration();

        frame = new JFrame(getConfigProperty("window.title", "Calculator"));
        image = loadIcon();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        subPanels = new JPanel[9];
        for (int i = 0; i < 9; i++) {
            subPanels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        }

        // Inicializar fontes desde a configuración
        textFont = getFontFromConfig("display", new Font("Segoe UI", Font.BOLD, 24));
        numberFont = getFontFromConfig("number", new Font("Segoe UI", Font.BOLD, 18));
        functionFont = getFontFromConfig("function", new Font("Segoe UI", Font.PLAIN, 18));

        // --- JTextField para display ---
        text = new JTextField();
        text.setFont(textFont);
        text.setEditable(false);
        text.setHorizontalAlignment(JTextField.RIGHT);
        text.setColumns(15);
        text.setBackground(getColorFromConfig("display.background.color", Color.WHITE));
        text.setOpaque(true);
        
        // Configurar bordo do display
        int borderSize = getIntConfig("display.border.size", 5);
        Color borderColor = getColorFromConfig("display.border.color", new Color(238, 238, 238));
        text.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(borderColor, borderSize),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Botóns numéricos
        butNums = new JButton[10];
        for (int i = 0; i < 10; i++) {
            butNums[i] = createButton(String.valueOf(i), ButtonType.NUMBER);
        }

        // Botóns de función
        butAdd = createButton("+", ButtonType.FUNCTION);
        butMinus = createButton("-", ButtonType.FUNCTION);
        butMultiply = createButton("*", ButtonType.FUNCTION);
        butDivide = createButton("/", ButtonType.FUNCTION);
        butEqual = createButton("=", ButtonType.FUNCTION);
        butCancel = createButton("C", ButtonType.FUNCTION);
        butSqrt = createButton("sqrt", ButtonType.FUNCTION);
        butSquare = createButton("x^2", ButtonType.FUNCTION);
        butInv = createButton("1/x", ButtonType.FUNCTION);
        butCos = createButton("cos", ButtonType.FUNCTION);
        butSin = createButton("sin", ButtonType.FUNCTION);
        butTan = createButton("tan", ButtonType.FUNCTION);
        butln = createButton("ln", ButtonType.FUNCTION);
        butPower = createButton("x^y", ButtonType.FUNCTION);
        butLog = createButton("log", ButtonType.FUNCTION);
        butPercent = createButton("%", ButtonType.FUNCTION);
        butAbs = createButton("abs", ButtonType.FUNCTION);
        butBin = createButton("bin", ButtonType.FUNCTION);
        butNegate = createButton("+/-", ButtonType.NUMBER);
        butDecimal = createButton(".", ButtonType.NUMBER);

        setupLayout();
    }

    /**
     * Carga a configuración desde o ficheiro properties
     */
    private void loadConfiguration() {
        config = new Properties();
        try (InputStream input = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                LOGGER.warning("Non se puido atopar config.properties. Usando valores por defecto.");
                return;
            }
            config.load(input);
            LOGGER.info("Configuración cargada correctamente");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro cargando ficheiro de configuración", e);
        }
    }

    /**
     * Obtén unha propiedade de configuración como String
     */
    private String getConfigProperty(String key, String defaultValue) {
        return config != null ? config.getProperty(key, defaultValue) : defaultValue;
    }

    /**
     * Obtén unha propiedade de configuración como enteiro
     */
    private int getIntConfig(String key, int defaultValue) {
        if (config == null) return defaultValue;
        try {
            return Integer.parseInt(config.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            LOGGER.warning("Formato numérico inválido para " + key + ". Usando default: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Obtén unha propiedade de configuración como booleano
     */
    private boolean getBooleanConfig(String key, boolean defaultValue) {
        if (config == null) return defaultValue;
        String value = config.getProperty(key, String.valueOf(defaultValue)).toLowerCase();
        return value.equals("true") || value.equals("yes") || value.equals("1");
    }

    /**
     * Obtén unha cor desde a configuración (formato RGB: r,g,b)
     */
    private Color getColorFromConfig(String key, Color defaultColor) {
        if (config == null) return defaultColor;
        
        String value = config.getProperty(key);
        if (value == null) {
            return defaultColor;
        }
        
        try {
            String[] rgb = value.split(",");
            if (rgb.length == 3) {
                int r = Integer.parseInt(rgb[0].trim());
                int g = Integer.parseInt(rgb[1].trim());
                int b = Integer.parseInt(rgb[2].trim());
                return new Color(r, g, b);
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Formato de cor inválido para " + key + ". Usando default.");
        }
        return defaultColor;
    }

    /**
     * Obtén unha fonte desde a configuración
     */
    private Font getFontFromConfig(String prefix, Font defaultFont) {
        if (config == null) return defaultFont;
        
        String name = getConfigProperty(prefix + ".font.name", defaultFont.getName());
        String styleStr = getConfigProperty(prefix + ".font.style", "plain").toLowerCase();
        int size = getIntConfig(prefix + ".font.size", defaultFont.getSize());
        
        int style = Font.PLAIN;
        if (styleStr.contains("bold")) style |= Font.BOLD;
        if (styleStr.contains("italic")) style |= Font.ITALIC;
        
        return new Font(name, style, size);
    }

    private JButton createButton(String label, ButtonType type) {
        JButton b = new JButton(label);
        b.setFont(type == ButtonType.NUMBER ? numberFont : functionFont);
        b.setPreferredSize(new java.awt.Dimension(80, 40));
        
        // Establecer cor de fondo desde configuración
        if (type == ButtonType.NUMBER) {
            b.setBackground(getColorFromConfig("number.button.background.color", Color.WHITE));
        } else {
            b.setBackground(getColorFromConfig("function.button.background.color", new Color(220, 255, 255)));
        }
        
        b.setFocusPainted(false);
        b.setBorderPainted(true);
        b.setOpaque(true);
        return b;
    }

    private void setupLayout() {
        // --- Display panel ---
        JPanel displayPanel = new JPanel(new java.awt.BorderLayout());
        displayPanel.add(text, java.awt.BorderLayout.CENTER);
        mainPanel.add(displayPanel);

        // --- Row 1 ---
        subPanels[1].add(butNums[1]);
        subPanels[1].add(butNums[2]);
        subPanels[1].add(butNums[3]);
        subPanels[1].add(Box.createHorizontalStrut(15));
        subPanels[1].add(butAdd);
        subPanels[1].add(butMinus);
        mainPanel.add(subPanels[1]);

        // --- Row 2 ---
        subPanels[2].add(butNums[4]);
        subPanels[2].add(butNums[5]);
        subPanels[2].add(butNums[6]);
        subPanels[2].add(Box.createHorizontalStrut(15));
        subPanels[2].add(butMultiply);
        subPanels[2].add(butDivide);
        mainPanel.add(subPanels[2]);

        // --- Row 3 ---
        subPanels[3].add(butNums[7]);
        subPanels[3].add(butNums[8]);
        subPanels[3].add(butNums[9]);
        subPanels[3].add(Box.createHorizontalStrut(15));
        subPanels[3].add(butEqual);
        subPanels[3].add(butCancel);
        mainPanel.add(subPanels[3]);

        // --- Row 4 ---
        subPanels[4].add(butNegate);
        subPanels[4].add(butNums[0]);
        subPanels[4].add(butDecimal);
        mainPanel.add(subPanels[4]);

        // --- Extra separation ---
        mainPanel.add(Box.createVerticalStrut(10));

        // --- Row 5 ---
        subPanels[5].add(butInv);
        subPanels[5].add(butln);
        subPanels[5].add(butLog);
        mainPanel.add(subPanels[5]);

        // --- Row 6 ---
        subPanels[6].add(butSquare);
        subPanels[6].add(butSqrt);
        subPanels[6].add(butPower);
        mainPanel.add(subPanels[6]);

        // --- Row 7 ---
        subPanels[7].add(butCos);
        subPanels[7].add(butSin);
        subPanels[7].add(butTan);
        mainPanel.add(subPanels[7]);

        // --- Row 8 ---
        subPanels[8].add(butPercent);
        subPanels[8].add(butAbs);
        subPanels[8].add(butBin);
        mainPanel.add(subPanels[8]);
    }

    public void init() {
        frame.setSize(getIntConfig("window.width", 465), getIntConfig("window.height", 460));
        frame.setLocationRelativeTo(null);
        frame.setResizable(getBooleanConfig("window.resizable", false));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (image != null) frame.setIconImage(image.getImage());
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    @Override
    public void setActionListener(EventHandler handler) {
        this.eventHandler = handler;
        for (int i = 0; i < 10; i++) {
            final int index = i;
            butNums[i].addActionListener(e -> eventHandler.onNumberPressed(index));
        }

        // Binary operators
        butAdd.addActionListener(e -> eventHandler.onBinaryOperatorPressed(ADD));
        butMinus.addActionListener(e -> eventHandler.onBinaryOperatorPressed(MINUS));
        butMultiply.addActionListener(e -> eventHandler.onBinaryOperatorPressed(MULTIPLY));
        butDivide.addActionListener(e -> eventHandler.onBinaryOperatorPressed(DIVIDE));
        butPower.addActionListener(e -> eventHandler.onBinaryOperatorPressed(POWER));

        // Unary operators
        butSquare.addActionListener(e -> eventHandler.onUnaryOperatorPressed(SQUARE));
        butSqrt.addActionListener(e -> eventHandler.onUnaryOperatorPressed(SQRT));
        butInv.addActionListener(e -> eventHandler.onUnaryOperatorPressed(INV));
        butCos.addActionListener(e -> eventHandler.onUnaryOperatorPressed(COS));
        butSin.addActionListener(e -> eventHandler.onUnaryOperatorPressed(SIN));
        butTan.addActionListener(e -> eventHandler.onUnaryOperatorPressed(TAN));
        butLog.addActionListener(e -> eventHandler.onUnaryOperatorPressed(LOG));
        butln.addActionListener(e -> eventHandler.onUnaryOperatorPressed(LN));
        butPercent.addActionListener(e -> eventHandler.onUnaryOperatorPressed(PERCENT));
        butAbs.addActionListener(e -> eventHandler.onUnaryOperatorPressed(ABS));
        butBin.addActionListener(e -> eventHandler.onUnaryOperatorPressed(BIN));
        butNegate.addActionListener(e -> eventHandler.onUnaryOperatorPressed(NEGATE));

        // Other actions
        butDecimal.addActionListener(e -> eventHandler.onDecimalPressed());
        butEqual.addActionListener(e -> eventHandler.onEqualsPressed());
        butCancel.addActionListener(e -> eventHandler.onClearPressed());
    }

    @Override
    public void displayResult(Double result) {
        if (result == null || Double.isNaN(result) || Double.isInfinite(result)) {
            text.setText("Error");
        } else {
            text.setText(decimalFormat.format(result));
        }
        startNewInput = true;
    }

    @Override
    public Double getDisplayValue() {
        String textValue = text.getText().trim();

        if (textValue.isEmpty()) {
            return 0.0;
        }

        // Detectar cadeas especiais
        switch (textValue) {
            case "NaN":
                return Double.NaN;
            case "Inf":
                return Double.POSITIVE_INFINITY;
            case "-Inf":
                return Double.NEGATIVE_INFINITY;
        }

        // Eliminar punto final sen díxitos
        if (textValue.endsWith(".")) {
            textValue = textValue.substring(0, textValue.length() - 1);
        }

        try {
            return Double.parseDouble(textValue);
        } catch (NumberFormatException e) {
            // Se por calquera motivo non é un número válido, devolve 0.0
            return 0.0;
        }
    }

    @Override
    public void appendToDisplay(String value) {
        if (startNewInput) {
            text.setText(value);
            startNewInput = false;
        } else {
            text.setText(text.getText() + value);
        }
    }

    @Override
    public void clearDisplay() {
        text.setText("");
        startNewInput = true;
    }

    @Override
    public void setDisplay(String displayText) {
        text.setText(displayText);
        startNewInput = true;
    }

    private ImageIcon loadIcon() throws IOException {
        String iconPath = getConfigProperty("icon.path", "icon/icon.png");
        try (InputStream is = getClass().getResourceAsStream(iconPath)) {
            if (is == null) {
                System.err.println("Non se puido cargar a icona desde: " + iconPath);
                return null;
            }
            BufferedImage bufferedImage = ImageIO.read(is);
            return new ImageIcon(bufferedImage);
        } catch (Exception e) {
            System.err.println("Non se puido cargar a icona: " + e.getMessage());
            return null;
        }
    }
}