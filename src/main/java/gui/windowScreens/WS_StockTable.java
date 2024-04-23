package main.java.gui.windowScreens;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import main.java.components.APP_Panel;

public class WS_StockTable extends APP_Panel {

    JPanel header = new JPanel();
    JPanel inventoryPanel = new JPanel();
    JPanel descriptionPanel = new JPanel();

    String[] inventoryFields = {"Product Code", "Order Date", "Name", "Category", "Unit Cost", 
    "Stock Quantity", "Total Cost", "Stock Left"};

    Object[][] placeholderObjects = {{"", "", "", "", "", "", "", ""}};

    // @Potatopowers kaya mo gawin yung approach ng DefaultTableModel?
    // see WF_UserManager for reference
    JTable inventoryTable = new JTable(placeholderObjects, inventoryFields);
    JScrollPane scrollPane = new JScrollPane(inventoryTable);

    JButton addButton = new JButton("Add");
    JButton editButton = new JButton("Edit");
    JButton deleteButton = new JButton("Delete");

    public WS_StockTable () {
        super();
        compile();
    }

    public void prepareComponents() {

    }

    public void addComponents() {

    }

    public void prepare() {

    }
    
    public void finalizePrepare() {

    }
}
