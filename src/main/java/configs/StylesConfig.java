package main.java.configs;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * Class containing all style-related predefined fields and helper
 * methods used in displaying most components of the application.
 */
public final class StylesConfig {

    public static final int NORMAL_SIZE = 14;

    public static final Font NORMAL             = new Font("Inter", Font.PLAIN, NORMAL_SIZE);
    public static final Font HEADING1           = new Font("Inter", Font.BOLD, 26);
    public static final Font HEADING2           = new Font("Inter", Font.BOLD, 22);
    public static final Font HEADING3           = new Font("Inter", Font.BOLD, 18);
    public static final Font LEAD               = new Font("Inter", Font.PLAIN, 16);
    public static final Font DETAIL             = new Font("Inter", Font.PLAIN, 12);
    public static final Font BUTTON             = new Font("Inter", Font.BOLD, NORMAL_SIZE);
    
    public static final Font ITEM_LABEL         = new Font("Inter", Font.BOLD, 18);
    public static final Font ITEM_PRICE         = new Font("Inter", Font.BOLD, 14);

    public static final Font CALCULATOR_OUTPUT  = new Font("Inter", Font.PLAIN, 16);
    public static final Font CALCULATOR         = new Font("Inter", Font.BOLD, 16);

    public static void setupUI() {
        setUIFont(new FontUIResource(NORMAL));
        setUIColors();
    }

    /**
     * Configures the default UI colors of selected native Swing
     * components.
     * <p>
     * This calls the UIManager to configure the different
     * keys to default as the provided default colors based on the
     * {@code ColorConfig} class.
     */
    private static void setUIColors() {
        UIManager.put("Label.foreground", ColorConfig.FG);
        UIManager.put("Button.background", ColorConfig.ACCENT_1);
    }

    /**
     * Configures the default UI font of all native Swing components.
     * <p>
     * This calls the UIManager to configure the different
     * keys to default as the provided default colors based on the
     * {@code ColorConfig} class.
     */
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
