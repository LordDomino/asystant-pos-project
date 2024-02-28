import java.awt.Color;

import javax.swing.JFrame;

public class APPFrame extends JFrame {

    /**The text on this JFrame's title bar. */
    public String title = "APP window";

    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.defaultBg;

    public APPFrame() {
        initialize();
    }

    public APPFrame(String title) {
        this.title = title;
        initialize();
    }

    private void initialize() {
        setTitle(this.title);
        getContentPane().setBackground(this.bg);
    }
}