package components;
import java.awt.Color;

import javax.swing.JFrame;

import configs.ColorConfig;

public abstract class APPFrame extends JFrame {

    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.DEFAULT_BG;

    public APPFrame() {
        super();
    }

    public APPFrame(String title) {
        super(title);
    }

    protected abstract void prepare();

    protected abstract void prepareComponents();

    protected abstract void addComponents();

    protected abstract void finalizePrepare();

    protected void compile() {
        this.prepare();
        this.prepareComponents();
        this.addComponents();
        this.finalizePrepare();
    }
}