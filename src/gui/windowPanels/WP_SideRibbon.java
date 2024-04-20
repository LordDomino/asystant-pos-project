package gui.windowPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import components.APP_Panel;
import components.APP_SideRibbonButton;

public class WP_SideRibbon extends APP_Panel {
    
    protected JPanel headerPanel = new JPanel(new GridBagLayout());

    protected JButton menuButton = new APP_SideRibbonButton("Menu");
    protected JButton homeButton = new APP_SideRibbonButton("Home");
    protected JButton settingsButton = new APP_SideRibbonButton("Settings");
    protected JButton inventoryButton = new APP_SideRibbonButton("Inventory");

    public WP_SideRibbon() {
        super();
        compile();
    }
    
    public void prepareComponents() {}

    public void prepare() {
        // setBackground(ColorConfig.ACCENT_2);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        add(menuButton, gbc);
        
        gbc.gridy = 1;
        add(homeButton, gbc);

        gbc.gridy = 2;
        add(settingsButton, gbc);

        gbc.gridy = 3;
        add(inventoryButton, gbc);
    }

    public void finalizePrepare() {}
}
