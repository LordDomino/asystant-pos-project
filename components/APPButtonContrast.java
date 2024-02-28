package components;

import configs.ColorConfig;

public class APPButtonContrast extends APPButton {
    public APPButtonContrast(String text) {
        super(text);
    }

    public void initialize() {
        setBackground(ColorConfig.DEFAULT_BUTTON_BG_CONTRAST);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG_TEXT);
        setFocusPainted(false);
        setBorderPainted(false);
    }
}
