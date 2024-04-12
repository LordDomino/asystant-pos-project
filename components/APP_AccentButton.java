package components;

import javax.swing.JButton;
import javax.swing.JFrame;

import configs.ColorConfig;
import configs.StylesConfig;

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
        setBorderPainted(false);

        // Font
        setFont(StylesConfig.defaultButton);
    }

    public APP_AccentButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize();
    }
}
