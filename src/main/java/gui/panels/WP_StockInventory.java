package main.java.gui.panels;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.java.Main;
import main.java.components.APP_AccentButton;
import main.java.components.APP_LabeledTextField;
import main.java.components.APP_Panel;
import main.java.components.APP_PopUpFrame;
import main.java.components.APP_TextField;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.gui.frames.WF_Dashboard;
import main.java.sql.DBReferences;
import main.java.sql.Queries;
import main.java.sql.SQLConnector;
import main.java.utils.GUIHelpers;

public class WP_StockInventory extends APP_Panel {

    public static final HashMap<String, String> fieldMappings = new HashMap<>();

    static {
        fieldMappings.put("Product Code", "product_code");
        fieldMappings.put("Name", "name");
        fieldMappings.put("Category", "category");
        fieldMappings.put("Unit Cost", "unit_cost");
        fieldMappings.put("Stock Quantity", "stock_quantity");
        fieldMappings.put("Markup Price", "markup_price");
        fieldMappings.put("Unit Price", "unit_price");
    }
    
    public static final String[] inventoryFields = {
        "Product Code",
        "Name",
        "Category",
        "Unit Cost",
        "Stock Quantity",
        "Markup Price",
        "Unit Price"
    };

    public static ArrayList<ArrayList<String>> pendingDeletedRows = new ArrayList<>();
    public final DefaultTableModel inventoryModel = new DefaultTableModel(inventoryFields, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public final JTable inventoryTable = new JTable(inventoryModel);

    // Layout components
    public final JPanel headerPanel     = new JPanel(new GridBagLayout());
    public final JPanel buttonsPanel    = new JPanel(new GridBagLayout());
    public final JPanel tablePanel      = new JPanel(new GridBagLayout());
    public final JPanel footerPanel     = new JPanel(new GridBagLayout());

    public final JScrollPane scrollPane         = new JScrollPane(inventoryTable);
    public final WP_DetailsPanel detailsPanel   = new WP_DetailsPanel();
    
    public static final ArrayList<ArrayList<String>> pendingDeletedProducts = new ArrayList<>();

    // Components
    public final APP_AccentButton addButton = new APP_AccentButton("Add");
    public final APP_AccentButton editButton = new APP_AccentButton("Edit") {

        @Override
        public void fireValueChanged() {
            final int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length == 1) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }

    };
    public final APP_AccentButton deleteButton = new APP_AccentButton("Delete") {

        @Override
        public void fireValueChanged() {
            final int[] selRows = inventoryTable.getSelectionModel().getSelectedIndices();
            if (selRows.length > 0) {
                setEnabled(true);    

                for (int rowID : selRows) {
                    ArrayList<String> pendingRowValues = new ArrayList<>();
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 0).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 1).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 2).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 3).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 4).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 5).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 6).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 7).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 8).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 9).toString());
                    pendingRowValues.add(inventoryTable.getValueAt(rowID, 10).toString());
                    pendingDeletedRows.add(pendingRowValues);
                }
                
                int[] selectedRows = inventoryTable.getSelectedRows();
                if (selectedRows.length > 0) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        inventoryModel.removeRow(selectedRows[i]);
                    }
                }

                // pakitanggal toh nasa baba kapag ok na yung delete @Potatopowers
                setEnabled(false);
            } else {
                setEnabled(false);
            }
        }

    };

    public final APP_AccentButton refreshButton = new APP_AccentButton("Refresh");

    public WP_StockInventory () {
        super();
        compile();
    }

    public void prepare() {
        updateTable();
        setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        headerPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);

        tablePanel.setBackground(ColorConfig.BG);
        inventoryTable.setBackground(ColorConfig.BG);
        scrollPane.getViewport().setBackground(ColorConfig.BG);
        scrollPane.setBackground(ColorConfig.BG);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        detailsPanel.setBackground(ColorConfig.ACCENT_1);

        inventoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    editButton.fireValueChanged();
                    deleteButton.fireValueChanged();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPopupWindow popUp = new AddPopupWindow();
                popUp.setLocationRelativeTo(null);
                popUp.setVisible(true);
            }
        });

        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPopupWindow popUp = new EditPopupWindow();

                int selectedRowIndex = inventoryTable.getSelectedRow();
                String selectedProductCode      = inventoryTable.getValueAt(selectedRowIndex, 0).toString();
                String selectedName             = inventoryTable.getValueAt(selectedRowIndex, 1).toString();
                String selectedCategory         = inventoryTable.getValueAt(selectedRowIndex, 2).toString();
                String selectedUnitCost         = inventoryTable.getValueAt(selectedRowIndex, 3).toString();
                String selectedStockQuantity    = inventoryTable.getValueAt(selectedRowIndex, 4).toString();
                String selectedMarkupPrice      = inventoryTable.getValueAt(selectedRowIndex, 5).toString();
                String selectedUnitPrice        = inventoryTable.getValueAt(selectedRowIndex, 6).toString();

                popUp.productCodeField.setText(selectedProductCode);
                popUp.nameField.setText(selectedName);
                popUp.categoryField.setSelectedItem(selectedCategory);
                popUp.unitCostField.setText(selectedUnitCost);
                popUp.stockQuantityField.setText(selectedStockQuantity);
                popUp.markupPriceField.setText(selectedMarkupPrice);
                popUp.unitPriceField.setText(selectedUnitPrice);

                popUp.setLocationRelativeTo(null);
                popUp.setVisible(true);
            }
        });

        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = inventoryTable.getSelectedRows();
                if (selectedRows.length > 0) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        inventoryModel.removeRow(selectedRows[i]);
                    }
                }
                // TODO Auto-generated method stub
                // @Potatopowers
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                updateTable();
            }
        });
    } 

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(headerPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(buttonsPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.BOTH;

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            buttonsPanel.add(addButton, gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            buttonsPanel.add(editButton, gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            buttonsPanel.add(deleteButton, gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            buttonsPanel.add(refreshButton, gbc);
        }

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(tablePanel, gbc);

        {
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.BOTH;

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            tablePanel.add(scrollPane, gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            tablePanel.add(detailsPanel, gbc);
        }

        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(footerPanel, gbc);
    }

    public void finalizePrepare() {}

    protected void updateTable() {
        loadFromDatabase();
    }

    protected void loadFromDatabase() {
        try {
            // Clear the rows
            inventoryModel.setRowCount(0);

            // Establish the SQL connection first
            SQLConnector.establishSQLConnection();
    
            // Query setup
            String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY;
            Statement statement = SQLConnector.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = statement.executeQuery(query);
    
            // Go to last row to get the total number of rows
            result.last();
            final int n = result.getRow();
    
            if (n ==0) {

            } else {
                // Then go back to the starting point before looping
                result.beforeFirst();

                // Then begin loop
                int i = 0;
                while (i < n) {
                    result.next();
                    String productCode = result.getString("product_code");
                    String name = result.getString("name");
                    String category = result.getString("category");
                    String unitCost = result.getString("unit_cost");
                    String stockQuantity = result.getString("stock_quantity");
                    String markupPrice = result.getString("markup_price");
                    String unitPrice = result.getString("unit_price");

                    String[] rowData = {productCode, name, category, unitCost, stockQuantity, markupPrice, unitPrice};

                    inventoryModel.addRow(rowData);
                    i++;
                }    
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
// Delete SQL not working..
/*protected void purgatoryPurge() {
    // Performing the delete (SQL-Java connection)
    final int pendingSize = pendingDeletedRows.size();

    try {
        String query;
        SQLConnector.establishSQLConnection();

        if (pendingSize == 1) {
            // If only 1 row is selected, use the equal operator for SQL query
            query = "DELETE FROM " + DBReferences.TBL_STOCKS_INVENTORY + " WHERE product_code = \"" + pendingDeletedRows.get(0).get(0) + "\";";
        } else {
            // If more than one row is selected, use the IN keyword for SQL query
            query = "DELETE FROM " + DBReferences.TBL_STOCKS_INVENTORY + " WHERE product_code IN ( ";

            // Get all usernameIDs
            for (ArrayList<String> product_code : pendingDeletedRows) {
                query = query + "\"" + product_code.get(0) + "\", ";
            }

            query = query.substring(0, query.length()-2) + " );";
        }

        System.out.println(query);

        Statement statement = SQLConnector.connection.createStatement();
        statement.executeUpdate(query);

        SQLConnector.connection.close();

        updateFrame();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            SwingUtilities.getWindowAncestor(deleteButton),
            e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    pendingDeletedRows.clear();
}*/



class AddPopupWindow extends APP_PopUpFrame<WF_Dashboard> {

    public final JLabel header = new JLabel("Insert a new Product");
    public final JPanel form = new JPanel(new GridBagLayout());

    public final JLabel productCodeLabel        = new JLabel("Product Code");
    public final JLabel nameLabel               = new JLabel("Name");
    public final JLabel descriptionLabel        = new JLabel("Product description");
    public final JLabel categoryLabel           = new JLabel("Category");
    public final JLabel unitCostLabel           = new JLabel("Unit Cost");
    public final JLabel stockQuantityLabel      = new JLabel("Stock Quantity");
    public final JLabel markupPriceLabel        = new JLabel("Markup Price");
    public final JLabel unitPriceLabel          = new JLabel("Unit Price");

    public final JTextField productCodeField            = new APP_TextField(10);
    public final JTextField nameField                   = new APP_TextField(10);
    public final APP_TextField descriptionField         = new APP_TextField(10);
    public final JComboBox<String> categoryField        = new JComboBox<String>();
    public final APP_LabeledTextField unitCostField     = new APP_LabeledTextField("Php", 10);
    public final APP_TextField stockQuantityField       = new APP_TextField(10);
    public final APP_LabeledTextField markupPriceField  = new APP_LabeledTextField("Php", 10);
    public final APP_LabeledTextField unitPriceField    = new APP_LabeledTextField("Php", 10);

    final DocumentListener unitCostListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update either markup or unit price
            if (!unitCostField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());

                if (!markupPriceField.getText().equals("")) {
                    float markupPrice = Float.parseFloat(markupPriceField.getText());
                    unitPriceField.setText(String.valueOf(unitCost + markupPrice));
                } else if (!unitPriceField.getText().equals("")) {
                    float unitPrice = Float.parseFloat(unitPriceField.getText());
                    markupPriceField.setText(String.valueOf(unitPrice - unitCost));
                }
            }
        }
    };

    final DocumentListener markupPriceListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update unit price based on typed markup price
            if (!unitCostField.getText().equals("") && !markupPriceField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());
                float markupPrice = Float.parseFloat(markupPriceField.getText());

                unitPriceField.getTextField().getDocument().removeDocumentListener(unitPriceListener);
                unitPriceField.setText(String.valueOf(unitCost + markupPrice));
                unitPriceField.getTextField().getDocument().addDocumentListener(unitPriceListener);
            }
        }
    };

    final DocumentListener unitPriceListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update unit price based on typed markup price
            if (!unitCostField.getText().equals("") && !unitPriceField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());
                float unitPrice = Float.parseFloat(unitPriceField.getText());

                markupPriceField.getTextField().getDocument().removeDocumentListener(markupPriceListener);
                markupPriceField.setText(String.valueOf(unitPrice - unitCost));
                markupPriceField.getTextField().getDocument().addDocumentListener(markupPriceListener);
            }
        }
    };

    public final JButton submitButton = new APP_AccentButton("Submit");

    public final JTextField[] fields = {
        productCodeField,
        nameField,
        unitCostField.getTextField(),
        stockQuantityField,
        markupPriceField.getTextField(),
        unitPriceField.getTextField()
    };



    
    public AddPopupWindow() {
        super(Main.app.DASHBOARD, "Add new product");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(submitButton, fields);
        submitButton.setEnabled(false);

        header.setFont(StylesConfig.HEADING3);
        form.setOpaque(false);
        categoryField.setBackground(ColorConfig.BG);
        categoryField.setEditable(true);

        // Document listeners to enable functionality of auto-updating
        // the markupPriceField and unitPriceField based on the data
        // typed in each other and with the unitCostField
        unitCostField.getTextField().getDocument().addDocumentListener(unitCostListener);
        markupPriceField.getTextField().getDocument().addDocumentListener(markupPriceListener);
        unitPriceField.getTextField().getDocument().addDocumentListener(unitPriceListener);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve inputs
                final String retrievedProductCode   = productCodeField.getText();
                final String retrievedName          = nameField.getText();
                final String retrievedDescription   = descriptionField.getText();
                final Object retrievedCategory      = categoryField.getSelectedItem();
                final float retrievedUnitCost       = Float.parseFloat(unitCostField.getText());
                final int retrievedStockQuantity    = Integer.parseInt(stockQuantityField.getText());
                final float retrievedMarkupPrice    = Float.parseFloat(markupPriceField.getText());
                final float retrievedUnitPrice      = Float.parseFloat(unitPriceField.getText());

                // Variables reserved for query
                String queryProductCode             = retrievedProductCode;
                String queryName                    = retrievedName;
                String queryDescription             = retrievedDescription;
                String queryCategory; // Do not initialize yet because category maybe empty
                String queryUnitCost                = String.valueOf(retrievedUnitCost);
                String queryStockQuantity           = String.valueOf(retrievedStockQuantity);
                String queryMarkupPrice             = String.valueOf(retrievedMarkupPrice);
                String queryUnitPrice               = String.valueOf(retrievedUnitPrice);

                // Check if category is empty. If so, set category to "Uncategorized"
                if (retrievedCategory == null) {
                    queryCategory = "Uncategorized";
                } else {
                    queryCategory = retrievedCategory.toString();
                }

                try {
                    SQLConnector.establishSQLConnection();
                    ResultSet result = Queries.getExistingProductsOfProductCode(queryProductCode);

                    if (result.getFetchSize() == 0) {
                        // No existing product exists
                        
                        Queries.insertNewProduct(
                            queryProductCode,
                            queryName,
                            queryDescription,
                            queryCategory,
                            queryUnitCost,
                            queryStockQuantity,
                            queryMarkupPrice,
                            queryUnitPrice
                        );
                    }

                    SQLConnector.connection.close();

                    Window popUp = SwingUtilities.getWindowAncestor(submitButton);
                    popUp.dispose();

                    Main.app.INVENTORY.STOCK_TABLE.updateTable();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(header, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(form, gbc);
        
        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            form.add(productCodeLabel, gbc);
            
            gbc.gridy = 1;
            form.add(nameLabel, gbc);
            
            gbc.gridy = 2;
            form.add(descriptionLabel, gbc);

            gbc.gridy = 3;
            form.add(categoryLabel, gbc);
            
            gbc.gridy = 4;
            form.add(unitCostLabel, gbc);
            
            gbc.gridy = 5;
            form.add(stockQuantityLabel, gbc);

            gbc.gridy = 6;
            form.add(markupPriceLabel, gbc);
            
            gbc.gridy = 7;
            form.add(unitPriceLabel, gbc);
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.L, 0, 0);
            form.add(productCodeField, gbc);

            gbc.gridy = 1;
            form.add(nameField, gbc);

            gbc.gridy = 2;
            form.add(descriptionField, gbc);

            gbc.gridy = 3;
            form.add(categoryField, gbc);

            gbc.gridy = 4;
            form.add(unitCostField, gbc);

            gbc.gridy = 5;
            form.add(stockQuantityField, gbc);

            gbc.gridy = 6;
            form.add(markupPriceField, gbc);

            gbc.gridy = 7;
            form.add(unitPriceField, gbc);
        }
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(submitButton, gbc);
    }

    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

class EditPopupWindow extends APP_PopUpFrame<WF_Dashboard> {

    public final JLabel header = new JLabel("Edit product details");
    public final JPanel form = new JPanel(new GridBagLayout());

    public final JLabel productCodeLabel        = new JLabel("Product Code");
    public final JLabel nameLabel               = new JLabel("Name");
    public final JLabel descriptionLabel        = new JLabel("Product description");
    public final JLabel categoryLabel           = new JLabel("Category");
    public final JLabel unitCostLabel           = new JLabel("Unit Cost");
    public final JLabel stockQuantityLabel      = new JLabel("Stock Quantity");
    public final JLabel markupPriceLabel        = new JLabel("Markup Price");
    public final JLabel unitPriceLabel          = new JLabel("Unit Price");

    public final JTextField productCodeField            = new APP_TextField(10);
    public final JTextField nameField                   = new APP_TextField(10);
    public final APP_TextField descriptionField         = new APP_TextField(10);
    public final JComboBox<String> categoryField        = new JComboBox<String>();
    public final APP_LabeledTextField unitCostField     = new APP_LabeledTextField("Php", 10);
    public final APP_TextField stockQuantityField       = new APP_TextField(10);
    public final APP_LabeledTextField markupPriceField  = new APP_LabeledTextField("Php", 10);
    public final APP_LabeledTextField unitPriceField    = new APP_LabeledTextField("Php", 10);

    final DocumentListener unitCostListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update either markup or unit price
            if (!unitCostField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());

                if (!markupPriceField.getText().equals("")) {
                    float markupPrice = Float.parseFloat(markupPriceField.getText());
                    unitPriceField.setText(String.valueOf(unitCost + markupPrice));
                } else if (!unitPriceField.getText().equals("")) {
                    float unitPrice = Float.parseFloat(unitPriceField.getText());
                    markupPriceField.setText(String.valueOf(unitPrice - unitCost));
                }
            }
        }
    };

    final DocumentListener markupPriceListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update unit price based on typed markup price
            if (!unitCostField.getText().equals("") && !markupPriceField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());
                float markupPrice = Float.parseFloat(markupPriceField.getText());

                unitPriceField.getTextField().getDocument().removeDocumentListener(unitPriceListener);
                unitPriceField.setText(String.valueOf(unitCost + markupPrice));
                unitPriceField.getTextField().getDocument().addDocumentListener(unitPriceListener);
            }
        }
    };

    final DocumentListener unitPriceListener = new DocumentListener() {
        public void changedUpdate(DocumentEvent event) {
            changed();
        }

        public void removeUpdate(DocumentEvent event) {
            changed();
        }

        public void insertUpdate(DocumentEvent event) {
            changed();
        }

        public void changed() {
            // Always update unit price based on typed markup price
            if (!unitCostField.getText().equals("") && !unitPriceField.getText().equals("")) {
                float unitCost = Float.parseFloat(unitCostField.getText());
                float unitPrice = Float.parseFloat(unitPriceField.getText());

                markupPriceField.getTextField().getDocument().removeDocumentListener(markupPriceListener);
                markupPriceField.setText(String.valueOf(unitPrice - unitCost));
                markupPriceField.getTextField().getDocument().addDocumentListener(markupPriceListener);
            }
        }
    };

    public final JButton updateButton = new APP_AccentButton("Submit");

    public final JTextField[] fields = {
        productCodeField,
        nameField,
        unitCostField.getTextField(),
        stockQuantityField,
        markupPriceField.getTextField(),
        unitPriceField.getTextField()
    };

    public EditPopupWindow() {
        super(Main.app.DASHBOARD);
        compile();
    }

    @Override
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    @Override
    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(updateButton, fields);
        updateButton.setEnabled(false);

        // Disable text fields that should not be allowed to update
        productCodeField.setEnabled(false);

        header.setFont(StylesConfig.HEADING3);
        form.setOpaque(false);
        categoryField.setBackground(ColorConfig.BG);
        categoryField.setEditable(true);

        // Document listeners to enable functionality of auto-updating
        // the markupPriceField and unitPriceField based on the data
        // typed in each other and with the unitCostField
        unitCostField.getTextField().getDocument().addDocumentListener(unitCostListener);
        markupPriceField.getTextField().getDocument().addDocumentListener(markupPriceListener);
        unitPriceField.getTextField().getDocument().addDocumentListener(unitPriceListener);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve inputs
                final String retrievedProductCode   = productCodeField.getText();
                final String retrievedName          = nameField.getText();
                final String retrievedDescription   = descriptionField.getText();
                final Object retrievedCategory      = categoryField.getSelectedItem();
                final float retrievedUnitCost       = Float.parseFloat(unitCostField.getText());
                final int retrievedStockQuantity    = Integer.parseInt(stockQuantityField.getText());
                final float retrievedMarkupPrice    = Float.parseFloat(markupPriceField.getText());
                final float retrievedUnitPrice      = Float.parseFloat(unitPriceField.getText());

                // Variables reserved for query
                String queryProductCode             = retrievedProductCode;
                String queryName                    = retrievedName;
                String queryDescription             = retrievedDescription;
                String queryCategory; // Do not initialize yet because category maybe empty
                String queryUnitCost                = String.valueOf(retrievedUnitCost);
                String queryStockQuantity           = String.valueOf(retrievedStockQuantity);
                String queryMarkupPrice             = String.valueOf(retrievedMarkupPrice);
                String queryUnitPrice               = String.valueOf(retrievedUnitPrice);

                // Check if category is empty. If so, set category to "Uncategorized"
                if (retrievedCategory == null) {
                    queryCategory = "Uncategorized";
                } else {
                    queryCategory = retrievedCategory.toString();
                }

                try {
                    SQLConnector.establishSQLConnection();
                    ResultSet result = Queries.getExistingProductsOfProductCode(queryProductCode);

                    if (result.getFetchSize() == 0) {
                        result.next();

                        // Get primary ID key
                        int id = result.getInt("id");
                        
                        Queries.updateProduct(
                            id,
                            queryProductCode,
                            queryName,
                            queryDescription,
                            queryCategory,
                            queryUnitCost,
                            queryStockQuantity,
                            queryMarkupPrice,
                            queryUnitPrice
                        );
                    }

                    SQLConnector.connection.close();

                    Window popUp = SwingUtilities.getWindowAncestor(updateButton);
                    popUp.dispose();

                    Main.app.INVENTORY.STOCK_TABLE.updateTable();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @Override
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(header, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(form, gbc);
        
        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            form.add(productCodeLabel, gbc);
            
            gbc.gridy = 1;
            form.add(nameLabel, gbc);
            
            gbc.gridy = 2;
            form.add(descriptionLabel, gbc);

            gbc.gridy = 3;
            form.add(categoryLabel, gbc);
            
            gbc.gridy = 4;
            form.add(unitCostLabel, gbc);
            
            gbc.gridy = 5;
            form.add(stockQuantityLabel, gbc);

            gbc.gridy = 6;
            form.add(markupPriceLabel, gbc);
            
            gbc.gridy = 7;
            form.add(unitPriceLabel, gbc);
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.L, 0, 0);
            form.add(productCodeField, gbc);

            gbc.gridy = 1;
            form.add(nameField, gbc);

            gbc.gridy = 2;
            form.add(descriptionField, gbc);

            gbc.gridy = 3;
            form.add(categoryField, gbc);

            gbc.gridy = 4;
            form.add(unitCostField, gbc);

            gbc.gridy = 5;
            form.add(stockQuantityField, gbc);

            gbc.gridy = 6;
            form.add(markupPriceField, gbc);

            gbc.gridy = 7;
            form.add(unitPriceField, gbc);
        }
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(updateButton, gbc);
    }

    @Override
    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

