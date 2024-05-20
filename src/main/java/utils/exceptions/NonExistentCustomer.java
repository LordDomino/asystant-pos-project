package main.java.utils.exceptions;

public class NonExistentCustomer extends Exception {
    public NonExistentCustomer(long neRfid) {
        super("Customer of RFID " + neRfid + " does not exist.");
    }
}
