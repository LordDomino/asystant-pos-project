package main.java.components;

import main.java.utils.exceptions.NonExistentCustomer;

public interface RfidReceivable {
    public abstract void setRfidNo(long rfidNo) throws NonExistentCustomer;
}
