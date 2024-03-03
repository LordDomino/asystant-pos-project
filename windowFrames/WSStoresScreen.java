package windowFrames;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import components.APPButton;
import components.APPScreen;
import configs.ColorConfig;

public class WSStoresScreen extends APPScreen {

    // Components
    public JLabel header = new JLabel("Select a store to operate");
    public JPanel contr_main = new JPanel(new GridBagLayout());
    public JPanel contr_stores = new JPanel(new GridBagLayout());
    public JButton STORE_canteen = new APPButton("Canteen", new WFLoginWindow(), true);
    public JButton addNewStore = new APPButton("New store");

    public WSStoresScreen() {
        super();
        compile();
    }

    public void prepare() {
        this.bg = ColorConfig.DEFAULT_ACCENT_1;

        setBackground(this.bg);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        contr_main.setBackground(this.bg);
        contr_stores.setBackground(this.bg);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contr_main.add(header, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contr_stores.add(STORE_canteen, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contr_stores.add(addNewStore, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contr_main.add(contr_stores, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.ipady = 50;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(contr_main, gbc);
    }

    public void finalizePrepare() {
        setMinimumSize(getPreferredSize());
    }
}
