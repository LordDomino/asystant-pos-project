package main.java.configs;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public final class StylesConfig {

    public static final int NORMAL_SIZE = 14;

    public static final Font NORMAL             = new Font("Inter", Font.PLAIN, NORMAL_SIZE);
    public static final Font HEADING1           = new Font("Inter", Font.BOLD, 26);
    public static final Font HEADING2           = new Font("Inter", Font.BOLD, 22);
    public static final Font HEADING3           = new Font("Inter", Font.BOLD, 18);
    public static final Font LEAD               = new Font("Inter", Font.PLAIN, 16);
    public static final Font DETAIL             = new Font("Inter", Font.PLAIN, 11);
    public static final Font BUTTON             = new Font("Inter", Font.BOLD, NORMAL_SIZE);
    
    public static final Font ITEM_LABEL         = new Font("Inter", Font.BOLD, 18);
    public static final Font ITEM_PRICE         = new Font("Inter", Font.BOLD, 14);

    public static final Font CALCULATOR_OUTPUT  = new Font("Inter", Font.PLAIN, 16);
    public static final Font CALCULATOR         = new Font("Inter", Font.BOLD, 16);

    public static void setupUI() {
        setUIFont(new FontUIResource(NORMAL));
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
