package main.java.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.components.APP_SideRibbonButton;
import main.java.configs.ColorConfig;
import main.java.gui.GUIReferences;
import main.java.gui.dashboardViews.DV_PurchaseView;

public class WP_SideRibbonViews extends APP_Panel {
    
    protected JPanel headerPanel = new JPanel(new GridBagLayout());

    public boolean preventSwitchView = false;
    public JFrame preventionPopUp;
    

    public WP_SideRibbonViews() {
        super();
        compile();
    }
    
    public void prepare() {
        // setBackground(ColorConfig.ACCENT_2);
    }
    
    public void prepareComponents() {

    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;

        GUIReferences.addViewPanels();

        for (int i = 0; i < GUIReferences.VIEW_PANELS.size(); i++) {
            // Get all the keys of GUIReferences.PANELS
            Set<String> keys = GUIReferences.VIEW_PANELS.keySet();

            // Convert key set to list of view names
            List<String> viewNames = new ArrayList<>(keys);

            // Get the current view name in the loop
            String currentViewName = viewNames.get(i);

            // Create a new button based on the view name
            APP_SideRibbonButton viewPanelButton = new APP_SideRibbonButton(currentViewName);

            viewPanelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!Main.app.PURCHASE_VIEW.CHECKOUT.isCheckoutClear()) {
                        preventSwitchingView();
                    }

                    if (preventSwitchView) {
                        if (getPreventionPopUp() != null) {
                            Main.app.DASHBOARD_FRAME.setEnabled(false);
                            preventionPopUp.setVisible(true);
                        }
                    } else {
                        JPanel newView = GUIReferences.VIEW_PANELS.get(currentViewName);
    
                        // Recompile customers view
                        if (newView instanceof DV_PurchaseView) {
                            ((DV_PurchaseView) newView).refreshItemMenu();
                        }
    
                        Main.app.DASHBOARD_FRAME.setView(newView);
                        System.out.println("Current view mode: " + currentViewName);
                    }
                }
            });

            viewPanelButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    viewPanelButton.setBackground(ColorConfig.ACCENT_1.brighter());
                }
    
                public void mouseExited(MouseEvent e) {
                    viewPanelButton.setBackground(ColorConfig.ACCENT_1);
                }
            });

            gbc.gridy = i;
            add(viewPanelButton, gbc);
        }
    }

    @Override
    public void finalizePrepare() {}

    public void setPreventSwitchView(boolean preventSwitchView) {
        this.preventSwitchView = preventSwitchView;
    }
    
    public JFrame getPreventionPopUp() {
        return preventionPopUp;
    }

    public void setPreventionPopUp(JFrame preventionPopUp) {
        this.preventionPopUp = preventionPopUp;
    }
    
    public void preventSwitchingView() {
        setPreventSwitchView(true);
    }

    public void preventSwitchingViewWithPopUp(JFrame popUp) {
        setPreventSwitchView(true);
        setPreventionPopUp(popUp);
    }
}