package components;
import java.awt.Color;

import javax.swing.JFrame;

import configs.ColorConfig;

public abstract class APPFrame extends JFrame implements Customizable {

    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.DEFAULT_BG;

    public APPFrame() {
        super();
    }

    public APPFrame(String title) {
        super(title);
    }

    public abstract void prepare();

    public abstract void prepareComponents();

    public abstract void addComponents();

    public abstract void finalizePrepare();

    protected void compile() {
        this.prepare();
        this.prepareComponents();
        this.addComponents();
        this.finalizePrepare();
    }
}