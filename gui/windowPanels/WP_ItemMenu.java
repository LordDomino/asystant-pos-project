package gui.windowPanels;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import components.APP_Panel;
import configs.ColorConfig;

public class WP_ItemMenu extends APP_Panel {
    
    public JButton menuItem1 = new JButton("<html>Strawberry<p>Php25.00</html>");
    public JButton menuItem2 = new JButton("Item 2");
    public JButton menuItem3 = new JButton("<html>Strawberry<p>Php25.00<p>2pcs</html>");

    public WP_ItemMenu() {
        super(new GridLayout(0, 2, 5, 5));
        compile();
    }

    public void prepareComponents() {
        menuItem1.setMargin(new Insets(10, 10, 10, 10));
        menuItem2.setMargin(new Insets(10, 10, 10, 10));
        menuItem3.setMargin(new Insets(10, 10, 10, 10));
    }

    public void prepare() {
        setBackground(ColorConfig.DEFAULT_BG_MID);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(menuItem1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(menuItem2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(menuItem3, gbc);
    }

    public void finalizePrepare() {
        
    }
}
