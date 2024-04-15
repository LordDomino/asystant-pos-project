package components;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import configs.ColorConfig;
import configs.StylesConfig;

public class APP_ItemButton extends JButton {
    
    protected String itemName;
    protected float priceTag;

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
        setMargin(new Insets(5, 5, 5, 5));
        setRolloverEnabled(false);

        // We create two JLabels for the item button so we also need to change
        // the layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel itemLabel = new JLabel(itemName);
        JLabel priceLabel = new JLabel("Php" + String.valueOf(priceTag));

        itemLabel.setFont(StylesConfig.ITEM_BUTTON_FONT);
        priceLabel.setFont(StylesConfig.ITEM_BUTTON_FONT);  
        itemLabel.setForeground(ColorConfig.ACCENT_BUTTON_FG);
        priceLabel.setForeground(ColorConfig.ACCENT_BUTTON_FG);
        
        add(itemLabel);
        add(priceLabel);

        // Font
        setFont(StylesConfig.defaultButton);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_1);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_BUTTON_BG);
            }
        });
    }
}