package main.java.gui.panels.dvPurchaseView;

import java.util.ArrayList;

public class CheckoutTableData {

    private ArrayList<CheckoutRowData<?>> rows;

    public CheckoutTableData() {}

    public CheckoutTableData(ArrayList<CheckoutRowData<?>> rows) {
        this.rows = rows;
    }

    public void addRow(CheckoutRowData<?> row) {
        this.rows.add(row);
    }

    public CheckoutRowData<?> getRowAt(int index) {
        return rows.get(index);
    }

    public Object getValueAt(int row, int col) {
        return rows.get(row).getValueAt(col);
    }

    public int size() {
        return rows.size();
    }
}
