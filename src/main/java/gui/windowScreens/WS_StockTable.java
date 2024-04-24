package main.java.gui.windowScreens;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.components.APP_AccentButton;
import main.java.components.APP_Frame;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.sql.DBReferences;
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
    DefaultTableModel inventoryModel = new DefaultTableModel(inventoryFields, 0);
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
    JButton addButton = new APP_AccentButton("Add");
    JButton editButton = new APP_AccentButton("Edit") {
        public void fireValueChanged() {
            int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();

            if (selectedIndices.length ==1) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }
    };
    JButton deleteButton = new APP_AccentButton("Delete"){
        public void fireValueChanged() {
            int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();

            if (selectedIndices.length > 0) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }
    };

    public WS_StockTable () {
        super();
        compile();
    }

    public void prepareComponents() {

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
    
    public void finalizePrepare() {

    }

    protected void updateTable() {
        loadFromDatabase();
    }

    protected void loadFromDatabase() {
        try {
            // Clear the rows
            inventoryModel.setRowCount(0);
    
            SQLConnector.establishSQLConnection();
    
            // modify query
            String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY;
            Statement statement = SQLConnector.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = statement.executeQuery(query);
    
            // Go to last row to get the total number of rows
            result.last();
            int n = result.getRow();
    
            // Then go back to the starting point before looping
            result.beforeFirst();
            int i = 0;
            while (i < n) {
                result.next();
                
                String productCode = result.getString("product_code");
                String name = result.getString("name");
                String category = result.getString("category");
                String unitCost = result.getString("unit_cost");
                String stockQUantity = result.getString("stock_quantity");
    
                i++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        // Clear the rows
        inventoryModel.setRowCount(0);

        SQLConnector.establishSQLConnection();

        // modify query
        String query = "SELECT product_code, name, category, unit_cost, stock_quantity FROM " + DBReferences.TBL_STOCKS_INVENTORY;
        Statement statement = SQLConnector.connection.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        ResultSet result = statement.executeQuery(query);

        // Go to last row to get the total number of rows
        result.last();
        int n = result.getRow();

        // Then go back to the starting point before looping
        result.beforeFirst();
        int i = 0;
        while (i < n) {
            result.next();
        
            String productCode = result.getString();
            String name = result.getString();

            if (condition) {
                
            }

            i++;
        }
    }
}

class AddPopupWindow extends APP_Frame {

    public WS_StockTable parentFrame;
    
    final JLabel header = new JLabel("Insert a new Product");
    final JPanel form = new JPanel(new GridBagLayout());

    JLabel productCodeLabel = new JLabel("Product Code");
    JLabel orderDateLabel = new JLabel("Order Date");
    JLabel nameLabel = new JLabel("Name");
    JLabel categoryLabel = new JLabel("Category");
    JLabel unitCostLabel = new JLabel("Unit Cost");
    JLabel stockQuantityLabel = new JLabel("Stock Quantity");
    JLabel totalCostLabel = new JLabel("Total Cost");
    JLabel stockLeftLabel = new JLabel("Stock Left");

    JTextField productCodeField = new JTextField();
    JTextField orderDateField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField categoryField = new JTextField();
    JTextField unitCostField = new JTextField();
    JTextField stockQuantityField = new JTextField();
    JTextField stockLeftField = new JTextField();

    JButton submitButton = new JButton("Submit");

    final JTextField[] fields = {productCodeField, orderDateField};

    public AddPopupWindow() {
        super();
        compile();
    }

    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(submitButton, null);
    }

    public void prepare() {

    }

    public void addComponents() {

    }

    public void finalizePrepare() {

    }

}