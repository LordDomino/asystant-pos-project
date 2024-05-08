package main.java.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.components.APP_SideRibbonButton;
import main.java.configs.ColorConfig;
import main.java.gui.GUIReferences;
import main.java.gui.dashboardViews.DV_PurchaseView;

public class WP_SideRibbonViews extends APP_Panel {

    private boolean isViewSwitchable = true;
    private JFrame preventionPopUp;
    
    public WP_SideRibbonViews() {
        super();
        compile();
    }
    
    @Override
    public void prepare() {
        // setBackground(ColorConfig.ACCENT_2);
    }
    
    @Override
    public void prepareComponents() {

    }

    @Override
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

                    // If checkout is not empty
                    if (!Main.app.PURCHASE_VIEW.CHECKOUT.isCheckoutClear()) {
                         JOptionPane.showMessageDialog(
                            Main.app.DASHBOARD_FRAME,
                            "Please confirm or clear orders in checkout first",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        preventViewSwitching();
                    }

                    // Check if we cannot switch to new view
                    if (!isViewSwitchable()) {
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

        allowViewSwitching();
    }

    @Override
    public void finalizePrepare() {}
  
    /**
     * Returns true if the current side ribbon allows view switching
     * to different view panels, otherwise returns false.
     * @return if the current side ribbon allows view switching
     */
    public boolean isViewSwitchable() {
        return isViewSwitchable;
    }

    /**
     * Returns the pop up JFrame that is reserved to be displayed
     * whenever the program attempts to switch to a different view panel
     * while it is currently prevented.
     * @return the pop up JFrame to be displayed in case of switching
     * attempts
     * @see {@link #preventViewSwitchingWithPopUp(JFrame)}
     * @see {@link #setPreventionPopUp(JFrame)}
     */
    public JFrame getPreventionPopUp() {
        return preventionPopUp;
    }

    /**
     * Sets the boolean flag on whether to allow switching to a
     * different view panel.
     * @param preventSwitchView whether to allow view switching to a
     * different view panel
     * @see {@link #allowViewSwitching()}
     * @see {@link #preventViewSwitching()}
     */
    public void setViewSwitching(boolean preventSwitchView) {
        this.isViewSwitchable = preventSwitchView;
    }
    
    /**
     * Sets the pop up JFrame that is reserved to be displayed whenever
     * the program attempts to switch to a different view panel while it
     * is currently prevented.
     * @param preventionPopUp the pop up JFrame to be displayed in case
     * of switching attempts
     * @see {@link #getPreventionPopUp()}
     */
    public void setPreventionPopUp(JFrame preventionPopUp) {
        this.preventionPopUp = preventionPopUp;
    }

    /**
     * Sets the boolean flag to allow switching to different view
     * panels. This conveniently calls
     * {@code setPreventSwitchView(true)} within this method.
     * @see {@link #setViewSwitching(boolean)}
     */
    public void allowViewSwitching() {
        setViewSwitching(true);
    }
    
    /**
     * Sets the boolean flag to prevent switching to different view
     * panels. This conveniently calls
     * {@code setViewSwitching(false)} within this method.
     * @see {@link #setViewSwitching(boolean)}
     */
    public void preventViewSwitching() {
        setViewSwitching(false);
    }

    /**
     * Sets the boolean flag to prevent switching to different view
     * panels with a reserved pop up JFrame to be displayed on
     * switching attempts.
     * <p>
     * This method conveniently calls both
     * {@code setPreventSwitchView(true)} and
     * {@code setPreventionPopUp()} with the given pop up argument.
     * @param popUp the pop up JFrame to be displayed in case of
     * switching attempts
     * @see {@link #setViewSwitching(boolean)}
     * @see {@link #setPreventionPopUp(JFrame)}
     */
    public void preventViewSwitchingWithPopUp(JFrame popUp) {
        setViewSwitching(false);
        setPreventionPopUp(popUp);
    }
}