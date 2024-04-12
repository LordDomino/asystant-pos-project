package configs;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class StylesConfig {

    public static final FontUIResource defaultNormal = new FontUIResource(new Font("Inter", Font.PLAIN, 14));
    public static final FontUIResource defaultButton = new FontUIResource(new Font("Inter", Font.BOLD, 14));
    public static final FontUIResource itemButton = new FontUIResource(new Font("Inter", Font.BOLD, 16));

    public static void setupUI() {
        setUIFont(StylesConfig.defaultNormal);
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
