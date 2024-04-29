package main.java.gui.panels;

// import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import main.java.components.APP_Panel;

public class WP_DetailsPanel extends APP_Panel {

    public final JLabel header = new JLabel("Product details");

    public final JLabel productCodeLabel    = new JLabel("Product Code");
    public final JLabel nameLabel           = new JLabel("Name");
    public final JLabel descriptionLabel    = new JLabel("Product description");
    public final JLabel categoryLabel       = new JLabel("Category");
    public final JLabel unitCostLabel       = new JLabel("Unit Cost");
    public final JLabel stockQuantityLabel  = new JLabel("Stock Quantity");
    public final JLabel markupPriceLabel    = new JLabel("Markup Price");
    public final JLabel unitPriceLabel      = new JLabel("Unit Price");

    public final JLabel productCodeInfo    = new JLabel("Test");
    public final JLabel nameInfo           = new JLabel("Test");
    public final JLabel descriptionInfo    = new JLabel("Test");
    public final JLabel categoryInfo       = new JLabel("Test");
    public final JLabel unitCostInfo       = new JLabel("Test");
    public final JLabel stockQuantityInfo  = new JLabel("Test");
    public final JLabel markupPriceInfo    = new JLabel("Test");
    public final JLabel unitPriceInfo      = new JLabel("Test");

    public WP_DetailsPanel() {
        super();
        compile();
    }

    public void prepare() {}

    public void prepareComponents() {}

    public void addComponents() {
        // GridBagConstraints gbc = new GridBagConstraints();
    }

    public void finalizePrepare() {}
}
