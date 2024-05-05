package main.java.gui.panels.dvPurchaseView;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.Main;
import main.java.components.APP_AccentButton;
import main.java.components.APP_ItemButton;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;

import javax.swing.JScrollPane;

public class WP_CheckoutPanel extends APP_Panel {

    /**
     * The map of current items added to the checkout table in the
     * format {@code B, Q} where {@code B} is the item button that
     * corresponds to the selected product while {@code Q} is the
     * current quantity of orders for that respective item button.
     * <p>
     * Use this as a reference.
     */
    public final LinkedHashMap<APP_ItemButton, Integer> checkoutItems = new LinkedHashMap<>();

    public final JLabel header = new JLabel("Checkout");
    public final JLabel totalLabel = new JLabel("Total:");
    public final JLabel totalAmountJLabel = new JLabel();
    public final APP_AccentButton getCustomerButton = new APP_AccentButton("Get Customer...");

    public final String[] tableFields = {"Product Code", "Product", "Quantity", "Total Price"};

    public final DefaultTableModel tableModel = new DefaultTableModel(tableFields, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public final JTable table = new JTable(tableModel);

    public final JScrollPane scrollPane = new JScrollPane(table);
    
    public WP_CheckoutPanel() {
        super();
        compile();
    }
    
    public void prepare() {
        setBackground(ColorConfig.ACCENT_1);
    }

    public void prepareComponents() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        header.setFont(StylesConfig.HEADING2);

        totalAmountJLabel.setFont(StylesConfig.HEADING3);

        totalLabel.setFont(StylesConfig.LEAD);

        getCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO -- RFID tapping process
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(header, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(InsetsConfig.L, 0, 0, 0);
        add(scrollPane, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(InsetsConfig.L, 0, 0, 0);
        gbc.weightx = 1;
        add(totalLabel, gbc);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(totalAmountJLabel, gbc);
    }

    public void finalizePrepare() {}

    public void addItemToCheckout(APP_ItemButton item) {
        // Check if this item already exists in the currentItems
        if (checkoutItems.containsKey(item)) {
            // Item exists in currentItems                                
            // Get quantity from existing product and add 1
            int quantity = checkoutItems.get(item) + 1;
            checkoutItems.put(item, quantity);
        } else {
            // Item needs to be registered in currentItems
            // Initial quantity should always be 1
            checkoutItems.put(item, 1);
        }
    }

    public void addAllCurrentItems() {
        for (APP_ItemButton itemButton : new ArrayList<>(checkoutItems.keySet())) {
            // For fields {"Product ID", "Product", "Quantity", "Total Price"} of checkout table
            String[] itemInfo = {
                itemButton.getProductCode(), 
                itemButton.getItemName(),
                String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.checkoutItems.get(itemButton)),
                "Php" + String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.checkoutItems.get(itemButton) * itemButton.getPriceTag()) 
            };

            Main.app.PURCHASE_VIEW.CHECKOUT.tableModel.addRow(itemInfo);
        }
    }

    /**
     * Returns the float value of the computed total price of all the
     * current items based on their price tags and ordered quantities.
     * @return the total price of all the items
     */
    public float recomputeTotalPrice() {
        float totalPrice = 0;
        for (APP_ItemButton itemButton : new ArrayList<>(checkoutItems.keySet())) {
            float itemPrice = itemButton.getPriceTag();
            int quantity = checkoutItems.get(itemButton);
            totalPrice = totalPrice + (itemPrice * quantity);
        }
        return totalPrice;
    }

    /**
     * Returns true if the index of current items are empty.
     * @return if the index of current items are empty
     */
    public boolean isCheckoutClear() {
        if (checkoutItems.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
