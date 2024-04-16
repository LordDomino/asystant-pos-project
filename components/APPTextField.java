package components;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import configs.ColorConfig;

public class APPTextField extends JTextField {

    public APPTextField() {
        initialize();
    } 

    public APPTextField(int columns) {
        super(columns);
        initialize();
    }

    public void initialize() {
        setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BG_CONTRAST),
            new LineBorder(ColorConfig.DEFAULT_BG, 2)
        ));
    }
}
