package components;

import javax.swing.JButton;
import javax.swing.JFrame;

import configs.ColorConfig;
import configs.StylesConfig;

public class APPButton extends JButton {

    public APPButton(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        // Colors
        setBackground(ColorConfig.DEFAULT_BUTTON_BG);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG_TEXT);
        setFocusPainted(false);
        setBorderPainted(false);

        // Font
        setFont(StylesConfig.defaultButton);
    }

    public APPButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize();
    }
}
