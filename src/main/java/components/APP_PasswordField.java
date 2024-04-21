package main.java.components;

import javax.swing.JPasswordField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import main.java.configs.ColorConfig;

public class APP_PasswordField extends JPasswordField {
    
    public APP_PasswordField() {
        super();
        initialize();
    }

    public APP_PasswordField(int columns) {
        super(columns);
        initialize();
    }

    private void initialize() {
        setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.ACCENT_3),
            new LineBorder(ColorConfig.BG, 2)
        ));
    }
}
