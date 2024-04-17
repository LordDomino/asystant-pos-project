package configs;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public final class StylesConfig {

    public static final FontUIResource defaultNormal = new FontUIResource(new Font("Inter", Font.PLAIN, 14));
    public static final FontUIResource defaultButton = new FontUIResource(new Font("Inter", Font.BOLD, 14));
    public static final FontUIResource itemButton = new FontUIResource(new Font("Inter", Font.BOLD, 16));

    public static final Font NORMAL = new Font("Inter", Font.PLAIN, 14);
    public static final Font HEADING1 = new Font("Inter", Font.BOLD, 26);
    public static final Font HEADING2 = new Font("Inter", Font.BOLD, 22);
    public static final Font HEADING3 = new Font("Inter", Font.BOLD, 18);
    public static final Font LEAD = new Font("Inter", Font.PLAIN, 16);
    public static final Font DETAIL = new Font("Inter", Font.PLAIN, 11);

    public static final Font HEADER = new Font("Inter", Font.BOLD, 16);
    public static final Font ITEM_BUTTON_FONT = new Font("Inter", Font.BOLD, 14);
    public static final Font OUTPUT_FONT = new Font("Inter", Font.BOLD, 16);
    public static final Font CALCULATOR_FONT = new Font("Inter", Font.BOLD, 16);

    public static void setupUI() {
        setUIFont(StylesConfig.defaultNormal);
        setUIColors();
    }

    private static void setUIColors() {
        UIManager.put("Label.foreground", ColorConfig.FG);
    }

    public static void setUIFont (FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    } 
}
