package components;
import java.awt.Color;

import javax.swing.JFrame;

import configs.ColorConfig;

public abstract class APP_Frame extends JFrame {

    /**The background color used by this JFrame. */
    public Color bg = ColorConfig.BG;

    public APP_Frame() {
        super();
    }

    public APP_Frame(String title) {
        super(title);
    }

    public abstract void prepare();

    public abstract void prepareComponents();

    public abstract void addComponents();

    public abstract void finalizePrepare();

    public void compile() {
        prepare();
        prepareComponents();
        addComponents();
        finalizePrepare();
    }
}