package gui.windowPanels;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import components.APP_Panel;

public class WP_ItemMenu extends APP_Panel {
    
    public JButton menuItem1 = new JButton("Item 1");
    public JButton menuItem2 = new JButton("Item 2");
    public JButton menuItem3 = new JButton("Item 3");

    public WP_ItemMenu() {
        super();
        compile();
    }

    public void prepareComponents() {}

    public void prepare() {}

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
    }

    public void finalizePrepare() {}
}
