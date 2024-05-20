package main.java.utils.exceptions;

public class ExceedingCreditsException extends Exception {
    public ExceedingCreditsException(String customerName, double credits) {
        super("Total order price of customer " + customerName + " exceeds account credits. Current: Php" + credits);
    }
}
