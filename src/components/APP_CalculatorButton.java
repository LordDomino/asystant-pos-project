package components;

import java.awt.Insets;

import configs.ColorConfig;
import configs.StylesConfig;

public class APP_CalculatorButton extends APP_DefaultButton {
    public APP_CalculatorButton(String text) {
        super(text);
        setFont(StylesConfig.CALCULATOR_FONT);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG);
        setMargin(new Insets(5, 10, 5, 10));
    }
}
