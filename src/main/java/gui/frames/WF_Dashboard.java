package main.java.gui.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.components.APP_Frame;
import main.java.configs.ColorConfig;
import main.java.gui.GUIReferences;
import main.java.gui.panels.WP_SideRibbon;

public class WF_Dashboard extends APP_Frame {

    protected JPanel leftPanel = new JPanel(new GridBagLayout());
    public JPanel viewingPanel;
    protected JPanel sideRibbon;

    public WF_Dashboard() {
        super("Dashboard");
        compile();
    }

    @Override
    public void prepare() {
        getContentPane().setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }

    @Override
    public void prepareComponents() {
        leftPanel.setBackground(ColorConfig.ACCENT_1);
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

            updateComponent();
        } else {
            remove(viewingPanel);

            updateComponent();

            viewingPanel = view;

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 1;
            gbc.weightx = 1;
            add(viewingPanel, gbc);

            updateComponent();
        }
    }

    /**Constructs and adds the side ribbon to the dashboard. */
    public void initializeSideRibbon() {
        sideRibbon = new WP_SideRibbon();
        
        GridBagConstraints gbc = new GridBagConstraints();
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
    }

    /**Convenience method for calling {@code revalidate()} and
     * {@code repaint()} for this component.
     */
    public void updateComponent() {
        revalidate();
        repaint();
    }
}