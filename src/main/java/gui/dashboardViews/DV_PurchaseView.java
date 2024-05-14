package main.java.gui.dashboardViews;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.gui.panels.WP_Calculator;
import main.java.gui.panels.dvPurchaseView.WP_CheckoutPanel;
import main.java.gui.panels.dvPurchaseView.WP_ItemMenu;

public class DV_PurchaseView extends APP_Panel {

    protected final JPanel centerPanel = new JPanel(new GridBagLayout());
    protected final JPanel rightPanel = new JPanel(new GridBagLayout());
    
    public final WP_ItemMenu ITEM_MENU = new WP_ItemMenu();
    protected final JScrollPane scrollArea = new JScrollPane(ITEM_MENU, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    public final WP_CheckoutPanel CHECKOUT = new WP_CheckoutPanel();
    protected final JPanel CALCULATOR = new WP_Calculator();

    public DV_PurchaseView() {
        super();
        compile();
    }

    public void prepare() {
        setBackground(ColorConfig.BG);
    }

    public void prepareComponents() {
        CALCULATOR.setOpaque(false);
        centerPanel.setBackground(ColorConfig.BG);
        rightPanel.setBackground(ColorConfig.ACCENT_1);

        rightPanel.setBorder(new MatteBorder(0, 1, 0, 0, ColorConfig.ACCENT_BUTTON_OUTLINE));
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(centerPanel, gbc);

        {
            // Item Menu
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
            gbc.weightx = 1;
            gbc.weighty = 1;
            centerPanel.add(scrollArea, gbc);
        }
        
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(rightPanel, gbc);

        {
            // Checkout Panel
            gbc.anchor = GridBagConstraints.NORTHEAST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.L, 0, InsetsConfig.L);
            gbc.weightx = 1;
            gbc.weighty = 1;
            rightPanel.add(CHECKOUT, gbc);

            rightPanel.setMinimumSize(getSize());
            
            // Calculator
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, InsetsConfig.L, InsetsConfig.XL, InsetsConfig.L);
            gbc.weightx = 0;
            gbc.weighty = 1;
            // rightPanel.add(CALCULATOR, gbc);
        }

    }

    public void finalizePrepare() {}

    /**Re-renders the items shown in the item menu. */
    public void refreshItemMenu() {
        ITEM_MENU.removeAll();
        ITEM_MENU.generateCategoryPanels();
    }
}