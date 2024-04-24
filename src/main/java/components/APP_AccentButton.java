package main.java.components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.configs.ColorConfig;
import main.java.configs.StylesConfig;

public class APP_AccentButton extends JButton {

    public APP_AccentButton(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        // Colors
        setBackground(ColorConfig.ACCENT_BUTTON_BG);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(true);

        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.ACCENT_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(4, 16, 4, 16)
        ));

        // Font
        setFont(StylesConfig.BUTTON);
    }

    public APP_AccentButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize();
    }

    public void fireValueChanged() {}
}
