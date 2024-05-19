package main.java.components;

import java.awt.Dimension;
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
    
    private String productCode;
    private String itemName;
    private float priceTag;

    public APP_ItemButton(String productCode, String itemName, float priceTag) {
        super();
        this.productCode = productCode;
        this.itemName = itemName;
        this.priceTag = priceTag;
        initialize();
    }

    public void initialize() {
        
        // Colors
        setBackground(ColorConfig.ACCENT_BUTTON_BG);
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(16, 16, 16, 16)
        ));
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFocusPainted(false);
        setBorderPainted(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        setRolloverEnabled(false);

        // We create two JLabels for the item button so we also need to change
        // the layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel itemLabel = new JLabel("<html>" + itemName + "</html>");
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

        itemLabel.setPreferredSize(new Dimension(150, getHeightFromFixedWidth(150, itemLabel.getPreferredSize().width, itemLabel.getPreferredSize().height)));
        itemLabel.setSize(getPreferredSize());
    }

    public void setLabels(String itemName, float priceTag) {
        this.itemName = itemName;
        this.priceTag = priceTag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPrice() {
        return priceTag;
    }

    public void setPriceTag(float priceTag) {
        this.priceTag = priceTag;
    }

    private int getHeightFromFixedWidth(int fixedWidth, int preferredWidth, int preferredHeight) {
        final int minimumArea = fixedWidth * preferredHeight;
        final int area = preferredWidth * preferredHeight;
        if (area <= minimumArea) {
            return preferredHeight;
        } else {
            return 23*2;
        }
    }
}