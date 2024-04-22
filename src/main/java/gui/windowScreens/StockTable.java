package main.java.gui.windowScreens;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import main.java.components.APP_Frame;

public class StockTable extends APP_Frame {

    String[] inventoryFields = {"Product ID", "Item Name", "Unit Cost", "Quantity"};

    Object[][] placeHolderData = {{"", "", "", ""}};

    JTable stockTable = new JTable(placeHolderData, inventoryFields);

    JScrollPane scrollP = new JScrollPane(stockTable);

    public StockTable() {
        super();
        compile();
    }
    
    public void prepareComponents() {

    }

    public void addComponents() {
        this.add(scrollP);
    }

    public void prepare(){

    }

    public void finalizePrepare() {

    }
}
