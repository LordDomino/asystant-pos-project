package main.java.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.components.APP_SideRibbonButton;
import main.java.gui.GUIReferences;

public class WP_SideRibbon extends APP_Panel {
    
    protected JPanel headerPanel = new JPanel(new GridBagLayout());

    public WP_SideRibbon() {
        super();
        compile();
    }
    
    public void prepare() {
        // setBackground(ColorConfig.ACCENT_2);
    }
    
    public void prepareComponents() {}

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;

        for (int i = 0; i < GUIReferences.PANELS.size(); i++) {
            // Get all the keys of GUIReferences.PANELS
            Set<String> keys = GUIReferences.PANELS.keySet();

            // Convert key set to list of view names
            List<String> viewNames = new ArrayList<>(keys);

            // Get the current view name in the loop
            String currentViewName = viewNames.get(i);

            // Create a new button based on the view name
            APP_SideRibbonButton viewButton = new APP_SideRibbonButton(currentViewName);

            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.app.DASHBOARD.setView(GUIReferences.PANELS.get(currentViewName));
                    System.out.println("Current view mode: " + currentViewName);
                }
            });

            gbc.gridy = i;
            add(viewButton, gbc);
        }
    }

    public void finalizePrepare() {}
}