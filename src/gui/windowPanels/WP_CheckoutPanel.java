package gui.windowPanels;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import components.APP_Panel;
import configs.ColorConfig;
import configs.StylesConfig;

public class WP_CheckoutPanel extends APP_Panel {

    protected JLabel header = new JLabel("Checkout");
    protected JLabel totalLabel = new JLabel("Total:");
    protected JLabel totalAmount = new JLabel("Php200.00");
    
    String[] viewData = {"ID", "QTY", "PRICE"};

    Object[][] sampleData = {
        {"110", "2", "100.00"},
    };

    JTable table = new JTable(sampleData, viewData);

    JScrollPane scrollP = new JScrollPane(table);
    
    public WP_CheckoutPanel() {
        super();
        compile();
    }

    public void prepareComponents() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        header.setHorizontalAlignment(JLabel.RIGHT);
        
        header.setFont(StylesConfig.HEADER);
        totalLabel.setFont(StylesConfig.OUTPUT_FONT);
        totalAmount.setFont(StylesConfig.OUTPUT_FONT);
        
        header.setForeground(ColorConfig.CONTRAST);
        totalLabel.setForeground(ColorConfig.CONTRAST);
        totalAmount.setForeground(ColorConfig.CONTRAST);
    }

    public void prepare() {
        setBackground(ColorConfig.ACCENT_1);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.add(header, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(scrollP, gbc);
        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        this.add(totalLabel, gbc);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        this.add(totalAmount, gbc);
    }

    public void finalizePrepare() {}
}
