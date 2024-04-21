package main.java.gui.windowScreens;

import javax.swing.JLabel;

import main.java.components.APP_Panel;

public class WS_Inventory extends APP_Panel {

    public JLabel test = new JLabel("Test");

    public WS_Inventory() {
        super();
        compile();

        add(test);
    }

    public void prepare() {}

    public void prepareComponents() {}

    public void addComponents() {}

    public void finalizePrepare() {}
}
