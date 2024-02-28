package components;
import java.awt.Color;

import javax.swing.JFrame;

import configs.ColorConfig;

public class APPFrame extends JFrame {

    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.DEFAULT_BG;

    public APPFrame() {
        super();
        initialize();
    }

    public APPFrame(String title) {
        super(title);
        initialize();
    }

    private void initialize() {
        getContentPane().setBackground(this.bg);
    }
}