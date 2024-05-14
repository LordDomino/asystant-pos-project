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

    /**Convenience method to modify this component and its properties. */
    public abstract void prepare();

    /**Convenience method to modify this component's children components
     * and their properties. */
    public abstract void prepareComponents();

    /**Convenience method to add this component's children components to
     * this one.
     */
    public abstract void addComponents();

    /**Convenience method to apply other modifications to this component
     * whenever some methods or modifications require the children
     * components to be added first (e.g. when packing the
     * component using {@code pack()}).
     */
    public abstract void finalizePrepare();

    public void compile() {
        prepare();
        addComponents();
        prepareComponents();
        finalizePrepare();
    }
}
