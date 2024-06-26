package main.java.gui.dashboardViews;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.gui.panels.dvCustomers.WP_CustomersTable;

import java.awt.GridBagLayout;
import java.awt.Insets;


import main.java.components.APP_Panel;

public class DV_Customers extends APP_Panel {

    public final JLabel header = new JLabel("Customers");

    public final WP_CustomersTable TABLE_PANEL = new WP_CustomersTable();

    public DV_Customers() {
        super();
        compile();
    }

    public void prepare() {
        setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        // set font of header here
        header.setFont(StylesConfig.HEADING1);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL); // xxl margins for top and left
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(header, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL); // xxl margins for left, right, and bottom
        add(TABLE_PANEL, gbc);
    }


    public void finalizePrepare() {
        // this will be inputted when the stock and profit are working
    
    }

    public void refreshUpdate() {
        TABLE_PANEL.refreshUpdate();
    }
}

    

