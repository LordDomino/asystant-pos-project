package main.java.gui.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Frame;
import main.java.components.APP_SideRibbonButton;
import main.java.configs.ColorConfig;
import main.java.gui.GUIReferences;
import main.java.gui.panels.WP_SideRibbonViews;

public class WF_Dashboard extends APP_Frame {

    protected JPanel leftPanel = new JPanel(new GridBagLayout());
    public JPanel viewingPanel;
    public WP_SideRibbonViews sideRibbon;
    public APP_SideRibbonButton logoutButton = new APP_SideRibbonButton("Logout");

    public WF_Dashboard() {
        super("Dashboard");
        compile();
    }

    @Override
    public void prepare() {
        getContentPane().setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void prepareComponents() {
        leftPanel.setBackground(ColorConfig.ACCENT_1);
        logoutButton.setBackground(ColorConfig.CONTRAST_BUTTON_BG);
        logoutButton.setForeground(ColorConfig.CONTRAST_BUTTON_FG);
        logoutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(ColorConfig.ACCENT_2);
                logoutButton.setForeground(ColorConfig.CONTRAST);
            }
            
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(ColorConfig.CONTRAST_BUTTON_BG);
                logoutButton.setForeground(ColorConfig.CONTRAST_BUTTON_FG);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.app.DASHBOARD_FRAME.dispose();
                Main.app.logout();
            }
        });
    }

    @Override
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(leftPanel, gbc);

        pack();
    }

    @Override
    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**Switches to the default view panel defined in
     * {@code GUIReferences.DEFAULT_VIEW}.
    */
    public void setToDefaultView() {
        setView(GUIReferences.DEFAULT_VIEW);
    }

    /**Switches the current view panel of the dashboard to the target
     * JPanel {@code view}.
     * @param view the target JPanel to show
     */
    public void setView(JPanel view) {
        if (viewingPanel == null) {
            viewingPanel = view;

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 1;
            gbc.weightx = 1;
            add(viewingPanel, gbc);

        } else {
            remove(viewingPanel);

            refreshUpdate();

            viewingPanel = view;
            viewingPanel.revalidate();
            viewingPanel.repaint();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 1;
            gbc.weightx = 1;
            add(viewingPanel, gbc);

        }
        refreshUpdate();
        setMinimumSize(getPreferredSize());
    }

    /**Constructs and adds the side ribbon to the dashboard. */
    public void initializeSideRibbon() {
        sideRibbon = new WP_SideRibbonViews();
        
        GridBagConstraints gbc = new GridBagConstraints();
        // LEFT PANEL
        // Side ribbon
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 1;
        leftPanel.add(sideRibbon, gbc);
        
        // Add logout here
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        leftPanel.add(logoutButton, gbc);
    }

    /**Convenience method for calling {@code revalidate()} and
     * {@code repaint()} for this component.
     */
    public void refreshUpdate() {
        revalidate();
        repaint();
        Main.app.INVENTORY_VIEW.refreshUpdate();
        Main.app.CUSTOMERS_VIEW.refreshUpdate();
    }
}