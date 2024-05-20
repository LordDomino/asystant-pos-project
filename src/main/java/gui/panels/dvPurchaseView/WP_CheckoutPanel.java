package main.java.gui.panels.dvPurchaseView;
 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import main.java.components.APP_Frame;
import main.java.components.APP_ItemButton;
import main.java.components.APP_Panel;
import main.java.components.RFIDPopUp;
import main.java.components.RfidReceivable;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.sql.Queries;
import main.java.sql.SQLConnector;
import main.java.utils.exceptions.ExceedingCreditsException;
import main.java.utils.exceptions.NonExistentCustomer;

import javax.swing.JScrollPane;

public class WP_CheckoutPanel extends APP_Panel implements RfidReceivable {

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
    public long currentRFIDNumber;
    public final APP_AccentButton subtract1Button = new APP_AccentButton("Subtract 1") {
        
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

         proceedToPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RFIDPopUp<WP_CheckoutPanel> popUp = new RFIDPopUp<WP_CheckoutPanel>(Main.app.PURCHASE_VIEW.CHECKOUT);
                popUp.setVisible(true);
            }
         });

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
                    subtract1Button.fireValueChanged();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        subtract1Button.setEnabled(false);
        clearCheckoutButton.setEnabled(false);
        proceedToPaymentButton.setEnabled(false);

        subtract1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int originalRowCount = tableModel.getRowCount();
                int selectedRow = table.getSelectedRow();
                APP_ItemButton item = new ArrayList<APP_ItemButton>(checkoutItems.keySet()).get(selectedRow);
                
                item.addToStock(1);
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
                clearCheckoutTable();
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
            headerButtonsPanel.add(subtract1Button, gbc);

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

    public void clearCheckoutTable() {
        for (APP_ItemButton item : new ArrayList<APP_ItemButton>(checkoutItems.keySet())) {
            item.addToStock(checkoutItems.get(item));
        }
        checkoutItems.clear();
        tableModel.setRowCount(0);
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

    @Override
    public void setRfidNo(long rfidNo) throws NonExistentCustomer {
        try {
            SQLConnector.establishConnection();
            ResultSet customer = Queries.getCustomerBasedOnRFID(rfidNo);
            customer.next();

            if (customer.getInt("activated") == 0) {
                JOptionPane.showMessageDialog(
                    Main.app.DASHBOARD_FRAME,
                    "Customer account " + customer.getString("customer_name") + " is inactive.",
                    "Customer account inactive",
                    JOptionPane.ERROR_MESSAGE
                );
            } else {
                currentRFIDNumber = rfidNo;
                Queries.createOrder(rfidNo, getOrdersFromCheckOut(), recomputeTotalPrice());
                Queries.processOrder();
                
    
                new ReceiptScreen(
                    customer.getString("customer_name"),
                    customer.getInt("id"),
                    customer.getInt("student_no"),
                    getOrdersFromCheckOut(),
                    recomputeTotalPrice()
                );
    
                clearCheckoutTable();
    
                Main.app.DASHBOARD_FRAME.refreshUpdate();
                Main.app.PURCHASE_VIEW.ITEM_MENU.regenerateCategoryPanels();
            }

        } catch (SQLException | ExceedingCreditsException exception) {
            exception.printStackTrace();
            if (exception instanceof ExceedingCreditsException) {
                JOptionPane.showMessageDialog(
                    Main.app.DASHBOARD_FRAME,
                    exception.getMessage(),
                    "Account credit limit warning",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private CheckoutTableData getOrdersFromCheckOut() {
        CheckoutTableData tableData = new CheckoutTableData();
        for (int r = 0; r < table.getRowCount(); r++) {
            CheckoutRowData<String> row = new CheckoutRowData<>();
            for (int c = 0; c < table.getColumnCount(); c++) {     
                row.addValue(table.getValueAt(r, c).toString());       
            }
            tableData.addRow(row);
        }
        return tableData;
    }
}

class ReceiptScreen extends APP_Frame {

    private String customerName;
    private int customerId;
    private int studentId;
    private CheckoutTableData orderData;
    private double totalPrice;

    private final JPanel contentPanel = new JPanel(new GridBagLayout());
    private final JPanel headerPanel = new JPanel(new GridBagLayout());
    private final JLabel header = new JLabel("Digital Receipt");
    private JLabel customerNameLabel;
    private JLabel customerIdLabel;
    private JLabel studentIdLabel;
    private JLabel totalPriceLabel;
    
    private final JPanel footerPanel = new JPanel(new GridBagLayout());

    private JLabel imageIcon;
    private final JPanel signaturePanel = new JPanel(new GridBagLayout());
    private final JLabel signatureLabel1 = new JLabel("Provided to you by");
    private final JLabel signatureLabel2 = new JLabel("The Asystant POS");
    private final JLabel signatureLabel3 = new JLabel("Developed by L. Naquita, L. Resurreccion, R. Pangilinan, Z. Cruz");

    public ReceiptScreen(String cname, int cid, int studid, CheckoutTableData orderData, double totalPrice) {
        super("Receipt");
        this.customerName = cname;
        this.customerId = cid;
        this.studentId = studid;
        this.orderData = orderData;
        this.totalPrice = totalPrice;
        compile();
    }

    @Override
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    @Override
    public void prepareComponents() {
        initializeImageIcon();
        initializeHeaderLabels();

        contentPanel.setBackground(ColorConfig.ACCENT_1);
        headerPanel.setOpaque(false);
        header.setFont(StylesConfig.HEADING2);
        totalPriceLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalPriceLabel.setFont(StylesConfig.HEADING3);

        footerPanel.setBackground(ColorConfig.CONTRAST);
        signaturePanel.setOpaque(false);
        signatureLabel1.setForeground(ColorConfig.CONTRAST_BUTTON_FG);
        signatureLabel1.setFont(new Font("Inter", Font.PLAIN, 12));
        signatureLabel2.setForeground(ColorConfig.CONTRAST_BUTTON_FG);
        signatureLabel2.setFont(new Font("Inter", Font.BOLD, 14));
        signatureLabel3.setForeground(ColorConfig.CONTRAST_BUTTON_FG);
        signatureLabel3.setFont(new Font("Inter", Font.PLAIN, 10));


        imageIcon.setSize(100, 100);
    }

    @Override
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(contentPanel, gbc);
        
        {
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            contentPanel.add(headerPanel, gbc);
            
            {
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                headerPanel.add(header, gbc);
                
                gbc.gridy = GridBagConstraints.RELATIVE;
                gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
                headerPanel.add(customerNameLabel, gbc);
                
                gbc.gridy = GridBagConstraints.RELATIVE;
                gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
                headerPanel.add(customerIdLabel, gbc);
                
                gbc.gridy = GridBagConstraints.RELATIVE;
                gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
                headerPanel.add(studentIdLabel, gbc);
            }

            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            JScrollPane sp = generateReceiptInfo();
            contentPanel.add(sp, gbc);
            pack();
            if (sp.getPreferredSize().height > 300) {
                sp.setPreferredSize(new Dimension(300, 300));
            }

            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XL, InsetsConfig.XXL);
            contentPanel.add(totalPriceLabel, gbc);
        }
        
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(footerPanel, gbc);
        
        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XL, InsetsConfig.L, 0);
            footerPanel.add(imageIcon, gbc);
            
            gbc.gridx = 1;
            gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.M, InsetsConfig.L, InsetsConfig.XL);
            footerPanel.add(signaturePanel, gbc);
            
            {
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                signaturePanel.add(signatureLabel1, gbc);
                
                gbc.gridy = GridBagConstraints.RELATIVE;
                signaturePanel.add(signatureLabel2, gbc);
                
                gbc.gridy = GridBagConstraints.RELATIVE;
                signaturePanel.add(signatureLabel3, gbc);
            }
        }
    }

    @Override
    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initializeImageIcon() {
        try {
            BufferedImage myPicture = ImageIO.read(new File("src\\main\\resources\\pos_logo.png"));
            imageIcon = new JLabel(new ImageIcon(myPicture.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeHeaderLabels() {
        customerNameLabel = new JLabel("Customer: " + customerName);
        customerIdLabel = new JLabel("Customer ID: " + customerId);
        studentIdLabel = new JLabel("Student ID: " + studentId);
        totalPriceLabel = new JLabel("Total: Php" + (float) totalPrice);
    }

    private JScrollPane generateReceiptInfo() {
        JPanel superPanel = new JPanel(new GridBagLayout());
        superPanel.setOpaque(false);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weightx = 1;
        superPanel.add(contentPanel, gbc);

        JScrollPane sp = new JScrollPane(superPanel);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setBackground(ColorConfig.ACCENT_1);

        gbc = new GridBagConstraints();

        for (int i = 0; i < orderData.getRows().size(); i++) {
            JPanel itemPanel = new JPanel(new GridBagLayout());
            itemPanel.setOpaque(false);

            {
                final JLabel prodQtyName = new JLabel(
                    orderData.getValueAt(i, 2).toString()
                    + " " + orderData.getValueAt(i, 1).toString()
                );
                final JLabel prodCost = new JLabel(orderData.getValueAt(i, 3).toString());
                final JLabel prodId = new JLabel(orderData.getValueAt(i, 0).toString());

                prodQtyName.setFont(new Font("Inter", Font.PLAIN, 16));
                prodCost.setFont(new Font("Inter", Font.BOLD, 16));
                prodCost.setHorizontalAlignment(JLabel.RIGHT);
                prodId.setFont(new Font("Inter", Font.PLAIN, 14));

                GridBagConstraints gbc2 = new GridBagConstraints();

                gbc2.fill = GridBagConstraints.HORIZONTAL;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.insets = new Insets(5, 5, 0, 5);
                gbc2.weightx = 1;
                itemPanel.add(prodQtyName, gbc2);
                
                gbc2.anchor = GridBagConstraints.EAST;
                gbc2.gridx = 1;
                itemPanel.add(prodCost, gbc2);
                
                gbc2.anchor = GridBagConstraints.NORTHWEST;
                gbc2.gridwidth = 2;
                gbc2.gridx = 0;
                gbc2.gridy = 1;
                gbc2.insets = new Insets(0, 5, 0, 5);
                itemPanel.add(prodId, gbc2);
            }
            
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = i;
            gbc.insets = new Insets(0, 0, InsetsConfig.L, 0);
            gbc.weightx = 1;
            contentPanel.add(itemPanel, gbc);
        } 

        // sp.setPreferredSize(new Dimension(300, 450));
        return sp;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getStudentId() {
        return studentId;
    }

    public CheckoutTableData getOrderData() {
        return orderData;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}