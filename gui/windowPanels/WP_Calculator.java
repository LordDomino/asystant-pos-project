package gui.windowPanels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.APP_DefaultButton;
import components.APP_Panel;

public class WP_Calculator extends APP_Panel {
    
    protected JPanel outputPanel = new JPanel(new GridBagLayout());
    protected JPanel buttonsPanel = new JPanel(new GridBagLayout());

    protected JLabel outputText = new JLabel("Output");

    protected JButton btn_1 = new APP_DefaultButton("1", false);
    protected JButton btn_2 = new APP_DefaultButton("2", false);
    protected JButton btn_3 = new APP_DefaultButton("3");
    protected JButton btn_4 = new APP_DefaultButton("4");
    protected JButton btn_5 = new APP_DefaultButton("5");
    protected JButton btn_6 = new APP_DefaultButton("6");
    protected JButton btn_7 = new APP_DefaultButton("7");
    protected JButton btn_8 = new APP_DefaultButton("8");
    protected JButton btn_9 = new APP_DefaultButton("9");
    protected JButton btn_0 = new APP_DefaultButton("0");
    protected JButton btn_00 = new APP_DefaultButton("00");
    protected JButton btn_DOT = new APP_DefaultButton(".");

    public WP_Calculator() {
        super();
        compile();
    }

    public void prepareComponents() {
        outputPanel.setBackground(Color.BLUE);
        buttonsPanel.setBackground(Color.YELLOW);
        outputText.setOpaque(true);
        outputText.setBackground(Color.MAGENTA);
    }

    public void prepare() {}

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(outputPanel, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(buttonsPanel, gbc);
        
        // OUTPUT PANEL
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        outputPanel.add(outputText, gbc);
        
        // BUTTONS PANEL
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.weightx = 1;
        gbc.weighty = 1;
        buttonsPanel.add(btn_7, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(btn_8, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonsPanel.add(btn_9, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(btn_4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(btn_5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonsPanel.add(btn_6, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonsPanel.add(btn_1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonsPanel.add(btn_2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        buttonsPanel.add(btn_3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonsPanel.add(btn_0, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        buttonsPanel.add(btn_00, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        buttonsPanel.add(btn_DOT, gbc);

    }

    public void finalizePrepare() {}
}
