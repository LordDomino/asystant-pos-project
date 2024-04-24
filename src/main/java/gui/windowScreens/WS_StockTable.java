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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.components.APP_AccentButton;
import main.java.components.APP_Frame;
import main.java.components.APP_Panel;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;
import main.java.utils.GUIHelpers;

public class WS_StockTable extends APP_Panel {

    JPanel header = new JPanel(new GridBagLayout());
    JPanel inventoryPanel = new JPanel(new GridBagLayout());
    JPanel descriptionPanel = new JPanel(new GridBagLayout());
    JPanel footerPanel = new JPanel(new GridBagLayout());

    public static final String[] inventoryFields = 
    {"Product Code", 
    "Name", 
    "Category", 
    "Unit Cost", 
    "Stock Quantity"};
    public static final ArrayList<ArrayList<String>> pendingDeletedProducts = new ArrayList<>();

    // @Potatopowers kaya mo gawin yung approach ng DefaultTableModel?
    // see WF_UserManager for reference
    DefaultTableModel inventoryModel = new DefaultTableModel(inventoryFields, 0);
    
    JTable inventoryTable = new JTable(inventoryModel);
    JScrollPane scrollPane = new JScrollPane(inventoryTable);

    JButton addButton = new JButton("Add");
    JButton editButton = new JButton("Edit") {

    public void fireValueChanged() {
        int[] selectedIndices = inventoryTable.getSelectionModel().getSelectedIndices();

        if (selectedIndices.length ==1) {
            setEnabled(true);    
        } else {
            setEnabled(false);
        }
    }
};
    JButton deleteButton = new APP_AccentButton("Delete") {

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
    }

    public void addComponents() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // main panels
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(header, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(inventoryPanel, gbc);


        // components
        gbc.gridx = 0;
        gbc.gridy = 0;
        header.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        header.add(editButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        header.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        inventoryPanel.add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inventoryPanel.add(descriptionPanel, gbc);
    }
    
    public void finalizePrepare() {

    }

    protected void updateTable() {
        loadFromDatabase();
    }

    protected void loadFromDatabase() {
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

    final JTextField[] fields = {productCodeField, orderDateField}

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