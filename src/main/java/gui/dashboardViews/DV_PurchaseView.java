package main.java.gui.dashboardViews;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.gui.panels.WP_Calculator;
import main.java.gui.panels.WP_CheckoutPanel;
import main.java.gui.panels.WP_ItemMenu;

public class DV_PurchaseView extends APP_Panel {

    protected final JPanel centerPanel = new JPanel(new GridBagLayout());
    protected final JPanel rightPanel = new JPanel(new GridBagLayout());

    protected final WP_ItemMenu itemMenu = new WP_ItemMenu();
    protected final JPanel checkoutPanel = new WP_CheckoutPanel();
    protected final JPanel calculator = new WP_Calculator();

    public DV_PurchaseView() {
        super();
        compile();
    }

    public void prepare() {
        setBackground(ColorConfig.BG);
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
            gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
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

    /**Re-renders the items shown in the item menu. */
    public void refreshItemMenu() {
        itemMenu.removeAll();
        itemMenu.generateCategoryPanels();
    }
}