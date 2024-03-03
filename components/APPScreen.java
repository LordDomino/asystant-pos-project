package components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import configs.ColorConfig;
import java.awt.GridBagLayout;

public abstract class APPScreen extends JPanel {
    
    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.DEFAULT_BG;

    public GridBagConstraints gbc = new GridBagConstraints();

    public APPScreen() {
        super();
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
    }

    public APPScreen(LayoutManager layout) {
        super(layout);
    }

    protected abstract void prepare();

    protected abstract void prepareComponents();

    protected abstract void addComponents();

    protected abstract void finalizePrepare();

    protected void compile() {
        this.prepare();
        this.prepareComponents();
        this.addComponents();
        this.finalizePrepare();
    }

    public void switchPanel(JPanel fromView, JPanel toView) {
        remove(fromView);
        add(toView, gbc);
    }
}
