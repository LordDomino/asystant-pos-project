package main.java.gui.windowScreens;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;


import main.java.components.APP_Panel;

public class WS_Inventory extends APP_Panel {

    public final JLabel header = new JLabel("Inventory");
    public final JTabbedPane tablesTabbedPane = new JTabbedPane();

    public JLabel test = new JLabel("Test");

    public WS_Inventory() {
        super();
        compile();

        add(test);
    }

    public void prepare() {
        // background of entire Inventory view
        // setBackground
        setBackground(Color.WHITE);
    }

    public void prepareComponents() {
        // set font of header here
        header.setFont(StylesConfig.HEADING1);

        // tablesTabbedPane.addTab ...
        // when adding tabs, use new JPanel() for now
        JPanel stockPanel = new JPanel();
        JPanel profitPanel = new JPanel();
        tablesTabbedPane.addTab("Stock", stockPanel);
        tablesTabbedPane.addTab("Profit", profitPanel);
    }

    public void addComponents() {
       
        
       
        // jtabbedpane always has xxl margins for left and bottom,
        // jtabbedpane m margins for top
        // jtabbedpane always has xxl margins for left and bottom,
        // jtabbedpane m margins for top
         
        // Set layout manager
         setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
        
        // margins, insets, fill, anchor, etc
        // use InsetsConfig for margins
        // header always has xxl margins for top and left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL); // xxl margins for top and left
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(header, gbc);
        
        
        // jtabbedpane always has xxl margins for left and bottom,
        // jtabbedpane m margins for top
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL); // xxl margins for left, right, and bottom
        add(tablesTabbedPane, gbc);
    }


    public void finalizePrepare() {
        // implementation will be done when the stock and profit are working
    
  }
}

    

