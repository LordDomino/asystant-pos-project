package components;

import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import configs.ColorConfig;
import configs.StylesConfig;

public class APP_DefaultButton extends JButton {

    public APP_DefaultButton(String text) {
        super(text);
        initialize();
    }


    public void initialize() {
        // Colors
        setBackground(ColorConfig.DEFAULT_BUTTON_BG);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(true);
        
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(6, 12, 6, 12)
        ));

        // Font
        setFont(StylesConfig.defaultButton);
    }

    public APP_DefaultButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize();
    }
}
