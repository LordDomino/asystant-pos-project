package components;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import configs.ColorConfig;

public class APP_TextField extends JTextField {

    public APP_TextField() {
        initialize();
    } 

    public APP_TextField(int columns) {
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
