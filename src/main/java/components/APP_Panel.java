package main.java.components;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public abstract class APP_Panel extends JPanel {

    public APP_Panel() {
        super(new GridBagLayout());
    }

    public APP_Panel(LayoutManager l) {
        super(l);
    }
    
    public abstract void prepareComponents();

    public abstract void prepare();

    public abstract void addComponents();

    public abstract void finalizePrepare();

    public void compile() {
        prepareComponents();
        prepare();
        addComponents();
        finalizePrepare();
    }
}
