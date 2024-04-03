package gui.windowPanels;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import components.APP_Panel;

public class WP_ItemMenu extends APP_Panel {
    
    public JButton menuItem1 = new JButton("<html>ABASDSDAsd SSDAS  DASSD C</html>");
    public JButton menuItem2 = new JButton("Item 2");
    public JButton menuItem3 = new JButton("Item 3");

    public WP_ItemMenu() {
        super(new GridLayout(0, 2, 5, 5));
        compile();
    }

    public void prepareComponents() {
        // menuItem1.setPreferredSize(new Dimension(75, 75));
        // menuItem2.setPreferredSize(new Dimension(75, 75));
        // menuItem3.setPreferredSize(new Dimension(75, 75));
    }

    public void prepare() {}

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
