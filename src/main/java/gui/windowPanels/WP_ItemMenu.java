package main.java.gui.windowPanels;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JButton;

import main.java.components.APP_ItemButton;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;

public class WP_ItemMenu extends APP_Panel {
    
    public JButton menuItem1 = new APP_ItemButton("Item 1", 205);
    public JButton menuItem2 = new APP_ItemButton("Item 2", 200);
    public JButton menuItem3 = new APP_ItemButton("Item 3", 200);

    public WP_ItemMenu() {
        super(new GridLayout(2, 2, InsetsConfig.L, InsetsConfig.L));
        compile();
    }

    public void prepareComponents() {
    }

    public void prepare() {
        setBackground(ColorConfig.BG);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.L, InsetsConfig.L, InsetsConfig.L);
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
