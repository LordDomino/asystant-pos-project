package gui.windowFrames;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import components.APP_Frame;
import configs.ColorConfig;
import gui.windowPanels.WP_Calculator;
import gui.windowPanels.WP_CheckoutPanel;
import gui.windowPanels.WP_ItemMenu;

public class WF_Dashboard extends APP_Frame {

    protected JPanel ribbonPanel;
    protected JPanel centerPanel = new JPanel(new GridBagLayout());
    protected JPanel rightPanel = new JPanel(new GridBagLayout());
    protected JPanel itemMenu = new WP_ItemMenu();
    protected JPanel checkoutPanel = new WP_CheckoutPanel();
    protected JPanel calculator = new WP_Calculator();

    public WF_Dashboard() {
        super("Dashboard");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.DEFAULT_ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        centerPanel.setBackground(Color.ORANGE);
        rightPanel.setBackground(Color.RED);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(centerPanel, gbc);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 1;
        add(rightPanel, gbc);


        // CENTER PANEL
        // Item Menu
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        centerPanel.add(itemMenu, gbc);


        // RIGHT PANEL
        // Checkout Panel
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        rightPanel.add(checkoutPanel, gbc);
        
        // Calculator
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        rightPanel.add(calculator, gbc);
    }

    public void finalizePrepare() {
        pack();
        // setSize(500, 300);
        setLocationRelativeTo(null);
    }
}
