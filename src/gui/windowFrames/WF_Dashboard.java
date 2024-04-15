package gui.windowFrames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import components.APP_Frame;
import configs.ColorConfig;
import gui.windowPanels.WP_Calculator;
import gui.windowPanels.WP_CheckoutPanel;
import gui.windowPanels.WP_ItemMenu;
import gui.windowPanels.WP_SideRibbon;

public class WF_Dashboard extends APP_Frame {

    protected JPanel leftPanel = new JPanel(new GridBagLayout());
    protected JPanel centerPanel = new JPanel(new GridBagLayout());
    protected JPanel rightPanel = new JPanel(new GridBagLayout());
    
    protected JPanel sideRibbonPanel = new WP_SideRibbon();
    protected JPanel itemMenu = new WP_ItemMenu();
    protected JPanel checkoutPanel = new WP_CheckoutPanel();
    protected JPanel calculator = new WP_Calculator();

    public WF_Dashboard() {
        super("Dashboard");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        leftPanel.setBackground(ColorConfig.ACCENT_2);
        centerPanel.setBackground(ColorConfig.BG);
        rightPanel.setBackground(ColorConfig.ACCENT_1);

        rightPanel.setBorder(new MatteBorder(0, 1, 0, 0, ColorConfig.ACCENT_BUTTON_OUTLINE));
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(leftPanel, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(centerPanel, gbc);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(rightPanel, gbc);
        
        
        // LEFT PANEL
        // Side ribbon
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        leftPanel.add(sideRibbonPanel, gbc);


        // CENTER PANEL
        // Item Menu
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 0;
        gbc.weighty = 0;
        centerPanel.add(itemMenu, gbc);
        
        
        // RIGHT PANEL
        // Checkout Panel
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
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
