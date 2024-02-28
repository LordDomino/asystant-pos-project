package components;

import javax.swing.JButton;

import configs.ColorConfig;

public class APPButton extends JButton {
    public APPButton(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        setBackground(ColorConfig.DEFAULT_BUTTON_BG);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG_TEXT);
        setFocusPainted(false);
        setBorderPainted(false);
    }
}
