package main.java.gui.windowScreens;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import main.java.components.APP_Panel;

public class WS_Inventory extends APP_Panel {

    public final JLabel header = new JLabel("Inventory");
    public final JTabbedPane tablesTabbedPane = new JTabbedPane();

    public JLabel test = new JLabel("Test");

    public WS_Inventory() {
        super();
        compile();

        add(test);
    }

    public void prepare() {
        // background of entire Inventory view
        // setBackground
    }

    public void prepareComponents() {
        // set font of header here
        // tablesTabbedPane.addTab ...
        // when adding tabs, use new JPanel() for now
    }

    public void addComponents() {
        // margins, insets, fill, anchor, etc
        // use InsetsConfig for margins
        GridBagConstraints gbc = new GridBagConstraints();

        // header always has xxl margins for top and left
        // jtabbedpane always has xxl margins for left and bottom,
        // jtabbedpane m margins for top
    }

    public void finalizePrepare() {}
}
