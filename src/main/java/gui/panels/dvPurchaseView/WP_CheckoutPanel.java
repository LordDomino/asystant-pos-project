package main.java.gui.panels.dvPurchaseView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

    // Layout components
    public final JPanel headerButtonsPanel = new JPanel(new GridBagLayout());
    public final JPanel footerButtonsPanel = new JPanel(new GridBagLayout());
    public final JPanel footerPanel = new JPanel(new GridBagLayout());

    // Components
    public final JLabel header = new JLabel("Checkout");
    public final APP_AccentButton removeProductButton = new APP_AccentButton("Subtract 1") {
        
        @Override
        public void fireValueChanged() {
            final int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length == 1) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }
        
    };
    public final APP_AccentButton clearCheckoutButton = new APP_AccentButton("Clear Checkout") {

        @Override
        public void fireValueChanged() {
            final int selectedIndices = table.getModel().getRowCount();
            if (selectedIndices > 0) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        };

    };
    public final JLabel totalAmountLabel = new JLabel("Total:");
    public final JLabel totalAmount = new JLabel("Php0.0");
    
    public final APP_AccentButton proceedToPaymentButton = new APP_AccentButton("Proceed to Payment") {
        @Override
        public void fireValueChanged() {
            if (!isCheckoutClear()) {
                proceedToPaymentButton.setEnabled(true);
            } else {
                proceedToPaymentButton.setEnabled(false);
            }
        };
    };

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
        
        headerButtonsPanel.setOpaque(false);
        footerPanel.setOpaque(false);

        header.setFont(StylesConfig.HEADING2);
        scrollPane.setPreferredSize(new Dimension(getPreferredSize().width, 300));
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        totalAmount.setFont(StylesConfig.HEADING3);
        totalAmountLabel.setFont(StylesConfig.LEAD);

        // proceedToPaymentButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         // TO DO -- RFID tapping process
        //     }
        // });

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                clearCheckoutButton.fireValueChanged();
                proceedToPaymentButton.fireValueChanged();
                totalAmount.setText("Php" + recomputeTotalPrice());

            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    removeProductButton.fireValueChanged();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        removeProductButton.setEnabled(false);
        clearCheckoutButton.setEnabled(false);
        proceedToPaymentButton.setEnabled(false);

        removeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int originalRowCount = tableModel.getRowCount();
                int selectedRow = table.getSelectedRow();
                APP_ItemButton item = new ArrayList<APP_ItemButton>(checkoutItems.keySet()).get(selectedRow);
                
                removeItemFromCheckout(item);
                rerenderCurrentItems();
                reselectItems(selectedRow, originalRowCount);
            }
            
            public void reselectItems(int selectedRow, int originalRowCount) {
                int newRowCount = tableModel.getRowCount();

                if (originalRowCount == newRowCount) {
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                } else {
                    table.clearSelection();
                }
            }
        });

        clearCheckoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutItems.clear();
                tableModel.setRowCount(0);

                // Allow switching view once checkout is clear
                Main.app.DASHBOARD_FRAME.sideRibbon.allowViewSwitching();
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(header, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.L, 0, 0, 0);
        gbc.weightx = 1;
        add(headerButtonsPanel, gbc);
        
        {
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = 0;
            gbc.weightx = 0;
            
            gbc.gridx = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(0, 0, 0, 0);
            headerButtonsPanel.add(removeProductButton, gbc);

            gbc.gridx = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(0, InsetsConfig.S, 0, 0);
            headerButtonsPanel.add(clearCheckoutButton, gbc);
        }

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
        add(scrollPane, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(InsetsConfig.L, 0, 0, 0);
        gbc.weightx = 1;

        gbc.gridy = GridBagConstraints.RELATIVE;
        add(footerPanel, gbc);

        {
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.gridy = 0;
            gbc.weightx = 1;
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            footerPanel.add(totalAmountLabel, gbc);
            
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = GridBagConstraints.RELATIVE;
            footerPanel.add(totalAmount, gbc);
        }
        
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
        add(footerButtonsPanel, gbc);
        
        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            footerButtonsPanel.add(proceedToPaymentButton, gbc);
        }
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

    public void removeItemFromCheckout(APP_ItemButton item) {
        // Check if this item already exists in the currentItems
        if (checkoutItems.containsKey(item)) {
            // Item exists in currentItems                                
            // Get quantity from existing product and add 1
            int quantity = checkoutItems.get(item) - 1;
            checkoutItems.put(item, quantity);
        } else {
            // Item needs to be registered in currentItems
            // Initial quantity should always be 1
            checkoutItems.put(item, checkoutItems.get(item));
        }

        if (checkoutItems.get(item).equals(0)) {
            checkoutItems.remove(item);
        }
    }

    public void rerenderCurrentItems() {
        tableModel.setRowCount(0);
        for (APP_ItemButton itemButton : new ArrayList<>(checkoutItems.keySet())) {
            // For fields {"Product ID", "Product", "Quantity", "Total Price"} of checkout table
            Object[] itemInfo = {
                itemButton.getProductCode(), 
                itemButton.getItemName(),
                String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.checkoutItems.get(itemButton)),
                "Php" + String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.checkoutItems.get(itemButton) * itemButton.getPrice())
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
            float itemPrice = itemButton.getPrice();
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
