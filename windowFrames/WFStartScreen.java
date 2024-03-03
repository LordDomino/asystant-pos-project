package windowFrames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import components.APPFrame;

public class WFStartScreen extends APPFrame {

    public JPanel SCREEN_stores = new WSStoresScreen();

    public WFStartScreen() {
        super();
        compile();
    }

    public void prepare() {
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {}

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(SCREEN_stores, gbc);
    }

    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(getMinimumSize());
    }
}
