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

    public void prepare() {

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

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(descriptionPanel, gbc);

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


    }
    
    public void finalizePrepare() {

    }
}
