package components;

import javax.swing.JButton;
import javax.swing.JFrame;

import configs.ColorConfig;
import configs.StylesConfig;

public class APP_DefaultButton extends JButton {

    public APP_DefaultButton(String text) {
        super(text);
        initialize(true);
    }

    public APP_DefaultButton(String text, boolean paintBorder) {
        super(text);
        initialize(paintBorder);
    }

    public void initialize(boolean paintBorder) {
        // Colors
        setBackground(ColorConfig.DEFAULT_BUTTON_BG);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(paintBorder);

        // Font
        setFont(StylesConfig.defaultButton);
    }

    public APP_DefaultButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize(true);
    }
}
