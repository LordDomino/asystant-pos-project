package gui.windowPanels;

import java.awt.Color;

import javax.swing.JLabel;

import components.APP_Panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class WP_CheckoutPanel extends APP_Panel {

    protected JLabel header = new JLabel("Checkout");
    protected JLabel totalLabel = new JLabel("Total: ");
    protected JLabel totalAmount = new JLabel();
    
    String[] viewData = {"ID", "QTY", "PRICE"};

    Object[][] sampleData = {{"110", "2", "100.00"}};

    JTable table = new JTable(sampleData, viewData);

    JScrollPane scrollP = new JScrollPane(table);
    
    public WP_CheckoutPanel() {
        super();
        compile();
    }
 
    public void prepareComponents() {}

    public void prepare() {
        setBackground(Color.CYAN);
    }

    public void addComponents() {
       GridBagConstraints gbc = new GridBagConstraints();

       gbc.anchor = GridBagConstraints.CENTER;
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.gridwidth = 2;
       this.add(header, gbc);

       gbc.anchor = GridBagConstraints.CENTER;
       gbc.gridx = 0;
       gbc.gridy = 1;
       gbc.gridwidth = 2;
       this.add(scrollP, gbc);

       gbc.anchor = GridBagConstraints.WEST;
       gbc.gridx = 0;
       gbc.gridy = 2;
       this.add(totalLabel, gbc);

       gbc.anchor = GridBagConstraints.EAST;
       gbc.gridx = 1;
       gbc.gridy = 2;
       this.add(totalAmount, gbc);


       
   
    }

    public void finalizePrepare() {
    }
}
