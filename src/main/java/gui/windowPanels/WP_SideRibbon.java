package main.java.gui.windowPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.components.APP_SideRibbonButton;
import main.java.gui.GUIReferences;

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
    
    public void prepare() {
        // setBackground(ColorConfig.ACCENT_2);
    }
    
    public void prepareComponents() {
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.app.DASHBOARD.setView(GUIReferences.PANELS.get("Inventory"));
                System.out.println("Passed");
            }
        });
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