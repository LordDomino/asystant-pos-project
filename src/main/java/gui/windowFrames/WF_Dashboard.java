package main.java.gui.windowFrames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.java.components.APP_Frame;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.gui.windowPanels.WP_Calculator;
import main.java.gui.windowPanels.WP_CheckoutPanel;
import main.java.gui.windowPanels.WP_ItemMenu;
import main.java.gui.windowPanels.WP_SideRibbon;

public class WF_Dashboard extends APP_Frame {

    protected JPanel leftPanel = new JPanel(new GridBagLayout());
    protected JPanel viewingPanel = new ViewingPanel();
    protected JPanel sideRibbon = new WP_SideRibbon();


    public WF_Dashboard() {
        super("Dashboard");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        leftPanel.setBackground(ColorConfig.ACCENT_1);
        viewingPanel.setBackground(ColorConfig.BG);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(leftPanel, gbc);

        {
            // LEFT PANEL
            // Side ribbon
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.weighty = 1;
            leftPanel.add(sideRibbon, gbc);
        }

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.weightx = 1;
        add(viewingPanel, gbc);
    }

    public void finalizePrepare() {
        pack();
        // setSize(500, 300);
        setLocationRelativeTo(null);
    }

    public void setView(JPanel view) {
        viewingPanel.setVisible(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.weightx = 1;
        // add(viewingPanel, gbc);
    }
}

class ViewingPanel extends APP_Panel {

    protected JPanel centerPanel = new JPanel(new GridBagLayout());
    protected JPanel rightPanel = new JPanel(new GridBagLayout());

    protected JPanel itemMenu = new WP_ItemMenu();
    protected JPanel checkoutPanel = new WP_CheckoutPanel();
    protected JPanel calculator = new WP_Calculator();

    public ViewingPanel() {
        super();
        compile();
    }

    public void prepare() {
    }

    public void prepareComponents() {
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
        add(centerPanel, gbc);

        {
            // Item Menu
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.weightx = 0;
            gbc.weighty = 0;
            centerPanel.add(itemMenu, gbc);
        }

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(rightPanel, gbc);

        {
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
    }

    public void finalizePrepare() {}
}