package components;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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

        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.CONTRAST_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(6, 12, 6, 12)
        ));

        // Font
        setFont(StylesConfig.defaultButton);
    }
}
