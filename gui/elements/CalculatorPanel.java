package gui.elements;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import components.Customizable;
import configs.ColorConfig;

public class CalculatorPanel extends JPanel implements Customizable {

    public CalculatorPanel() {
        super(new GridBagLayout());
        compile();
    }

    public void prepare() {
        setBackground(ColorConfig.DEFAULT_BG);
    }

    public void prepareComponents() {}

    public void addComponents() {}

    public void finalizePrepare() {}

    public void compile() {
        prepare();
        prepareComponents();
        addComponents();
        finalizePrepare();
    }
}
