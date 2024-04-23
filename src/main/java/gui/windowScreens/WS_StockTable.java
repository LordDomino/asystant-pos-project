package main.java.gui.windowScreens;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import main.java.components.APP_AccentButton;
import main.java.components.APP_Panel;

public class WS_StockTable extends APP_Panel {

    JPanel header = new JPanel();
    JPanel inventoryPanel = new JPanel();
    JPanel descriptionPanel = new JPanel();

    public static final String[] inventoryFields = {"Product Code", "Order Date", "Name", "Category", "Unit Cost", 
    "Stock Quantity", "Total Cost", "Stock Left"};
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
/* To be fixed.. 
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            if (pendingDeletedProduct.size() >= 1) {
                DeletePopUpWindow popUp = new DeletePopUpWindow();
                popUp.info.setText(
                    "<html>Adding new Products is not allowed while "
                    + "some rows are pending to be deleted. "
                    + "Confirm changes to pending deletions first."
                ); 
                popUp.setVisible(true);
            } else {
                AddPopupWindow popUp = new AddPopupWindow();
                popUp.parentFrame = (WS_StockTable) SwingUtilities.getWindowAncestor(addButton);
                popUp.setVisible(true);
            }
            }
        });

        editButton.addActionListener(new ActionListener()) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditPopupWindow popUp = new EditpopUpWindow();
                popUp.parentFrame = (WS_StockTable) SwingUtilities.getWindowAncestor(addButton);

                if (table.getSelectionModel().getSelectedIndices().length != 1) {

                } else {

                    int rowIndex = inventoryTable.getSelectedRow();

                    final String retrievedProductCode = inventoryTable.getValueAt(rowIndex, 0).toString();
                    final String retrievedOrderDate = inventoryTable.getValueAt(rowIndex, 1).toString();
                    final String retrievedName = inventoryTable.getValueAt(rowIndex, 2).toString();
                    final String retrievedCategory = inventoryTable.getValueAt(rowIndex, 3).toString();
                    final String retrievedUnitCost = inventoryTable.getValueAt(rowIndex, 4).toString();
                    final String retrievedStockQuantity = inventoryTable.getValueAt(rowIndex, 5).toString();
                    final String retrievedTotalCost = inventoryTable.getValueAt(rowIndex, 6).toString();
                    final String retrievedStockLeft = inventoryTable.getValueAt(rowIndex, 7).toString();

                    popUp.usernameEditField.setText(retrievedProductCode);
                    popUp.user
                }
            }
        }
 */

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

class AddPopupWindow extends APP_