package components;

import javax.swing.JButton;

import configs.ColorConfig;
import configs.StylesConfig;

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

        // Font
        setFont(StylesConfig.defaultButton);
    }
}
