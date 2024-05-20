package main.java.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.Main;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.utils.exceptions.NonExistentCustomer;

public class RFIDPopUp<F extends RfidReceivable> extends APP_Frame {

    public F parentFrame;

    private JLabel header = new JLabel("Confirm with RFID");
    private JLabel info1 = new JLabel("Please tap your RFID to the scanner to confirm registration");
    private APP_PasswordField passwordField = new APP_PasswordField();

    public RFIDPopUp(F frameToReceiveRFID) {
        super("RFID Registration Confirmation");
        this.parentFrame = frameToReceiveRFID;
        compile();
    }

    @Override
    public void prepare() {
        setLayout(new GridBagLayout());
        getContentPane().setBackground(ColorConfig.ACCENT_1);
    }

    @Override
    public void prepareComponents() {
        header.setFont(StylesConfig.LEAD);
        passwordField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            private void changed() {
                if (passwordField.getPassword().length == 10) {
                    try {
                        long rfidNo = Long.valueOf(String.valueOf(passwordField.getPassword()));
                        parentFrame.setRfidNo(rfidNo);
                        dispose();
                    } catch (NonExistentCustomer | NumberFormatException e) {
                        if (e instanceof NonExistentCustomer) {
                            JOptionPane.showMessageDialog(
                                Main.app.DASHBOARD_FRAME,
                                e.getMessage(),
                                "Please try again",
                                JOptionPane.ERROR_MESSAGE
                            );
                            dispose();
                        } else if (e instanceof NumberFormatException) {
                            JOptionPane.showMessageDialog(
                                Main.app.DASHBOARD_FRAME,
                                "Invalid RFID number for given input",
                                "Please try again",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                        e.printStackTrace();
                    } finally {
                        dispose();
                    }
                }
            }

        });
    }

    @Override
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XL, 0, InsetsConfig.XL);
        add(header, gbc);

        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XL, 0, InsetsConfig.XL);
        gbc.gridy = GridBagConstraints.RELATIVE;
        add(info1, gbc);

        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XL, InsetsConfig.XL, InsetsConfig.XL);
        gbc.gridy = GridBagConstraints.RELATIVE;
        add(passwordField, gbc);
    }

    @Override
    public void finalizePrepare() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
