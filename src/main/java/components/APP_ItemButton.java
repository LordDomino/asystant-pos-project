package main.java.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.configs.ColorConfig;
import main.java.configs.StylesConfig;

public class APP_ItemButton extends JButton {
    
    public String itemName;
    public float priceTag;

    public APP_ItemButton(String itemName, float priceTag) {
        super();
        this.itemName = itemName;
        this.priceTag = priceTag;
        initialize();
    }

    public void initialize() {
        
        // Colors
        setBackground(ColorConfig.ACCENT_BUTTON_BG);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        setRolloverEnabled(false);

        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(16, 16, 16, 16)
        ));

        // We create two JLabels for the item button so we also need to change
        // the layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel itemLabel = new JLabel(itemName);
        JLabel priceLabel = new JLabel("Php" + String.valueOf(priceTag));

        itemLabel.setFont(StylesConfig.ITEM_LABEL);
        priceLabel.setFont(StylesConfig.ITEM_LABEL);  
        itemLabel.setForeground(ColorConfig.ACCENT_BUTTON_FG);
        priceLabel.setForeground(ColorConfig.ACCENT_BUTTON_FG);
        
        add(itemLabel);
        add(priceLabel);

        // Font
        itemLabel.setFont(StylesConfig.ITEM_LABEL);
        priceLabel.setFont(StylesConfig.ITEM_PRICE);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_BUTTON_BG.brighter());
            }

            public void mouseExited(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_BUTTON_BG);
            }
        });
    }

    public void setLabels(String itemName, float priceTag) {
        this.itemName = itemName;
        this.priceTag = priceTag;
    }
}