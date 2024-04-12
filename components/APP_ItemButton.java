package components;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import configs.ColorConfig;
import configs.StylesConfig;

public class APP_ItemButton extends JButton {
    
    public APP_ItemButton(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        // Colors
        setBackground(ColorConfig.ACCENT_BUTTON_BG);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        setMargin(new Insets(10, 5, 10, 5));

        // Font
        setFont(StylesConfig.defaultButton);
    }
}