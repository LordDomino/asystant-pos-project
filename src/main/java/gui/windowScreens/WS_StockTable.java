package main.java.gui.windowScreens;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.Main;
import main.java.components.APP_AccentButton;
import main.java.components.APP_Frame;
import main.java.components.APP_LabeledTextField;
import main.java.components.APP_Panel;
import main.java.components.APP_PopUpFrame;
import main.java.components.APP_TextField;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.gui.windowFrames.WF_Dashboard;
import main.java.sql.DBReferences;
import main.java.sql.Queries;
import main.java.sql.SQLConnector;
import main.java.utils.GUIHelpers;

public class WS_StockTable extends APP_Panel {
    
    public static final String[] inventoryFields = {
        "Product Code",
        "Name",
        "Category",
        "Unit Cost",
        "Stock Quantity"
    };
    DefaultTableModel inventoryModel = new DefaultTableModel(inventoryFields, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable inventoryTable = new JTable(inventoryModel);

    // Layout components
    public final JPanel headerPanel = new JPanel(new GridBagLayout());
    public final JPanel buttonsPanel = new JPanel(new GridBagLayout());
    public final JPanel tablePanel = new JPanel(new GridBagLayout());
    public final JPanel footerPanel = new JPanel(new GridBagLayout());

    public final JScrollPane scrollPane = new JScrollPane(inventoryTable);
    public final JPanel descriptionPanel = new JPanel(new GridBagLayout());
    
    public static final ArrayList<ArrayList<String>> pendingDeletedProducts = new ArrayList<>();

    // Components
    public final APP_AccentButton addButton = new APP_AccentButton("Add");
    public final APP_AccentButton editButton = new APP_AccentButton("Edit") {

        @Override
        public void fireValueChanged() {
            final int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length ==1) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }

    };
    public final APP_AccentButton deleteButton = new APP_AccentButton("Delete") {

        @Override
        public void fireValueChanged() {
            final int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length > 0) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }

    };

    public final APP_AccentButton refreshButton = new APP_AccentButton("Refresh");

    public WS_StockTable () {
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
        scrollPane.setBackground(ColorConfig.BG);
        descriptionPanel.setBackground(ColorConfig.ACCENT_1);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPopupWindow popUp = new AddPopupWindow();
                popUp.setLocationRelativeTo(null);
                popUp.setVisible(true);
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
            tablePanel.add(descriptionPanel, gbc);
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
            String query = "SELECT product_code, name, category, unit_cost, stock_quantity FROM " + DBReferences.TBL_STOCKS_INVENTORY;
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

                    String[] rowData = {productCode, name, category, unitCost, stockQuantity};

                    inventoryModel.addRow(rowData);
                    i++;
                }    
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

class AddPopupWindow extends APP_PopUpFrame<WF_Dashboard> {

    public WS_StockTable parentFrame;
    
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

    public final JButton submitButton = new JButton("Submit");

    public final JTextField[] fields = {
        productCodeField,
        nameField,
        unitCostField.getTextField(),
        stockQuantityField,
        markupPriceField.getTextField(),
        unitPriceField.getTextField()
    };

    public AddPopupWindow() {
        super(Main.app.dashboard, "Add new product");
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
                final String retrievedCategory      = categoryField.getSelectedItem().toString();
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
                if (retrievedCategory.equals("")) {
                    queryCategory = "Uncategorized";
                } else {
                    queryCategory = retrievedCategory;
                }

                final String[] queryData = {
                    queryProductCode,
                    queryName,
                    queryDescription,
                    queryCategory,
                    queryUnitCost,
                    queryStockQuantity,
                    queryMarkupPrice,
                    queryUnitPrice
                };

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