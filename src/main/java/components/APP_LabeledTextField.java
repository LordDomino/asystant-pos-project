package main.java.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.configs.InsetsConfig;

public class APP_LabeledTextField extends JPanel {
    
    private JLabel label;
    private APP_TextField tf;

    public APP_LabeledTextField(String label) {
        super(new GridBagLayout());
        initialize(label, 1);
    }

    public APP_LabeledTextField(String label, int columns) {
        super(new GridBagLayout());
        initialize(label, columns);
    }

    private void initialize(String l, int columns) {
        this.label = new JLabel(l);
        this.tf = new APP_TextField(columns);

        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        add(label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, InsetsConfig.S, 0, 0);
        add(tf, gbc);
    }

    public JLabel getLabel() {
        return label;
    }

    public String getText() {
        return getTextField().getText();
    }

    public APP_TextField getTextField() {
        return tf;
    }

    public void setLabelText(String l) {
        label.setText(l);
    }

    public void setText(String l) {
        tf.setText(l);
    }

    public void setEnabled(boolean b) {
        tf.setEnabled(b);
    }
}
