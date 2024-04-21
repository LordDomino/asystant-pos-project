package main.java.components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.configs.ColorConfig;
import main.java.configs.StylesConfig;

public class APP_ContrastButton extends JButton {

    public APP_ContrastButton(String text) {
        super(text);
        initialize();
    }
    
    public void initialize() {
        // Colors
        setBackground(ColorConfig.CONTRAST_BUTTON_BG);
        setForeground(ColorConfig.CONTRAST_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(false);

        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.CONTRAST_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(4, 16, 4, 16)
        ));

        // Font
        setFont(StylesConfig.BUTTON);
    }
}
