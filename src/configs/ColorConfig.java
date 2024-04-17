package configs;
import java.awt.Color;

public final class ColorConfig {

    /**The default background color. */
    public static final Color BG = new Color(255, 255, 255);
    
    /**The accent 1 background color. */
    public static final Color ACCENT_1 = new Color(205, 239, 239);
    
    /**The accent 2 background color. */
    public static final Color ACCENT_2 = new Color(0, 176, 240);
    
    /**The accent 3 background color. */
    public static final Color ACCENT_3 = new Color(0, 32, 96);

    /**The contrasting background color. */
    public static final Color CONTRAST = new Color(13, 6, 40);

    /**The default text color. */
    public static final Color FG = new Color(0, 0, 0);

    /**The background color of default buttons. */
    public static final Color DEFAULT_BUTTON_BG = ACCENT_1;

    /**The background color of accented buttons. */
    public static final Color ACCENT_BUTTON_BG = ACCENT_2;

    /**The background color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_BG = CONTRAST;
    
    /**The text color of default buttons. */
    public static final Color DEFAULT_BUTTON_FG = ACCENT_3;

    /**The text color of accented buttons. */
    public static final Color ACCENT_BUTTON_FG = ACCENT_3;

    /**The text color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_FG = ACCENT_1;

    /**The outline color of default buttons. */
    public static final Color DEFAULT_BUTTON_OUTLINE = ACCENT_3;

    /**The outline color of accented buttons. */
    public static final Color ACCENT_BUTTON_OUTLINE = ACCENT_3;

    /**The outline color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_OUTLINE = ACCENT_1;
}
