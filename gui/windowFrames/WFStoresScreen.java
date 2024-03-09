package gui.windowFrames;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JFrame;

import components.APPButton;
import components.APPFrame;
import configs.ColorConfig;

public final class WFStoresScreen extends APPFrame {

    public enum StoreMode {
        CANTEEN,
    }

    // Components
    public JLabel header = new JLabel("Select a store to operate");
    public JPanel contr_main = new JPanel(new GridBagLayout());
    public JPanel contr_stores = new JPanel(new GridBagLayout());
    public JButton STORE_canteen = new APPButton("Canteen");
    public JButton addNewStore = new APPButton("New store");

    public WFStoresScreen() {
        super();
        compile();
    }

    /**
     * Prepares this current component before children components are
     * added.
     * 
     * This runs component-related methods such as
     * {@code}setBackground(){@code} and {@code}setLayout(){@code}.
     */
    protected void prepare() {
        this.bg = ColorConfig.DEFAULT_ACCENT_1;

        getContentPane().setBackground(ColorConfig.DEFAULT_BG_CONTRAST);
        setLayout(new GridBagLayout());
    }

    protected void prepareComponents() {
        contr_main.setBackground(this.bg);
        contr_stores.setBackground(this.bg);

        STORE_canteen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(STORE_canteen);
                WFLoginWindow target = new WFLoginWindow();
                target.setExpectedStore(StoreMode.CANTEEN);
                target.setLocationRelativeTo(source);
                target.setVisible(true);
                source.dispose();
            }
        });
    }

    protected void addComponents() {
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
        gbc.insets = new Insets(5, 20, 5, 5);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contr_stores.add(STORE_canteen, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 20);
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

    protected void finalizePrepare() {
        pack();
        setMinimumSize(getPreferredSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
