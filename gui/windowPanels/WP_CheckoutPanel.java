package gui.windowPanels;

import java.awt.Color;

import javax.swing.JLabel;

import components.APP_Panel;

public class WP_CheckoutPanel extends APP_Panel {

    protected JLabel header = new JLabel("Checkout");
    
    public WP_CheckoutPanel() {
        super();
        compile();
    }

    public void prepareComponents() {}

    public void prepare() {
        setBackground(Color.CYAN);
    }

    public void addComponents() {
        add(header);
    }

    public void finalizePrepare() {}
}
