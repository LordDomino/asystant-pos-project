package main.java.gui.panels.dvPurchaseView;

import java.util.ArrayList;

public class CheckoutRowData<T> {

    private ArrayList<T> data = new ArrayList<>();

    public CheckoutRowData() {}

    public CheckoutRowData(ArrayList<T> data) {
        this.data = data;
    }

    public void addValue(T value) {
        this.data.add(value);
    }

    public ArrayList<T> getData() {
        return this.data;
    }

    public T getValueAt(int index) {
        return this.data.get(index);
    }
}
