package main.java.components;

import main.java.configs.ColorConfig;

public class APPButtonContrast extends APP_DefaultButton {
    public APPButtonContrast(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        setBackground(ColorConfig.CONTRAST_BUTTON_BG);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(false);
    }
}
