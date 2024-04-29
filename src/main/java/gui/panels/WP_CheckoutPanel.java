package main.java.gui.panels;

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
     * The current items added to the checkout table. Use this as a reference.
     */
    public final LinkedHashMap<APP_ItemButton, Integer> currentItems = new LinkedHashMap<>();

    public final JLabel header = new JLabel("Checkout");
    public final JLabel totalLabel = new JLabel("Total:");
    public final JLabel totalAmount = new JLabel();
    public final APP_AccentButton getCustomerButton = new APP_AccentButton("Get Customer...");

    String[] tableFields = {"Product Code", "Product", "Quantity", "Total Price"};

    public final DefaultTableModel tableModel = new DefaultTableModel(tableFields, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JTable table = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(table);
    
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
        header.setHorizontalAlignment(JLabel.RIGHT);

        totalAmount.setFont(StylesConfig.HEADING3);

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
        add(totalAmount, gbc);
    }

    public void finalizePrepare() {}

    public void addAllCurrentItems() {
        for (APP_ItemButton itemButton : new ArrayList<>(currentItems.keySet())) {
            // For fields {"Product ID", "Product", "Quantity", "Total Price"} of checkout table
            String[] itemInfo = {
                itemButton.getProductCode(), 
                itemButton.getItemName(),
                String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.currentItems.get(itemButton)),
                "Php" + String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.currentItems.get(itemButton) * itemButton.getPriceTag()) 
            };

            Main.app.PURCHASE_VIEW.CHECKOUT.tableModel.addRow(itemInfo);
        }
    }

    public float recomputeTotalPrice() {
        float totalPrice = 0;
        for (APP_ItemButton itemButton : new ArrayList<>(currentItems.keySet())) {
            float itemPrice = itemButton.getPriceTag();
            int quantity = currentItems.get(itemButton);
            totalPrice = totalPrice + (itemPrice * quantity);
        }
        return totalPrice;
    }

    public boolean isCheckoutClear() {
        if (currentItems.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
