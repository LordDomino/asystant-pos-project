package gui.windowFrames;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import components.APP_DefaultButton;
import components.APP_Frame;
import components.APPTextField;
import components.APP_AccentButton;
import configs.ColorConfig;

public final class WF_LoginWindow extends APP_Frame {

    public Color bg = ColorConfig.ACCENT_1;
    public int textBoxWidth = 10;
    
    // Components
    public JLabel titleText = new JLabel("Asystant - POS System");
    public JLabel loginUserLabel = new JLabel("User");
    public JLabel passwordLabel = new JLabel("Password");
    public JTextField loginUserField = new APPTextField(textBoxWidth);
    public JPasswordField passwordField = new JPasswordField(textBoxWidth);
    public JButton submitButton = new APP_AccentButton("Log In"); 
    public JButton backButton = new APP_AccentButton("Back");

    // Layout components
    private JPanel contentAreaPanel = new JPanel(new GridBagLayout());
    private JPanel titleCardPanel = new JPanel(new GridBagLayout());
    private JPanel fieldsPanel = new JPanel(new GridBagLayout());
    private JPanel buttonsPanel = new JPanel(new GridBagLayout());
    
    public WF_LoginWindow() {
        super("Login");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.CONTRAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        contentAreaPanel.setBackground(this.bg);
        titleCardPanel.setBackground(this.bg);
        fieldsPanel.setBackground(this.bg);
        buttonsPanel.setBackground(this.bg);

        passwordField.setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.CONTRAST),
            new LineBorder(ColorConfig.BG, 2)));
    
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame source = (JFrame) SwingUtilities.getRoot(submitButton);
                APP_Frame target = new WF_Dashboard();
                target.setVisible(true);
                source.dispose();
            }	
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame source = (JFrame) SwingUtilities.getRoot(backButton);
                source.dispose();
            }
        });
    }
    
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        // All styling JFrames will go here

        // Content area panel
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 0, 50);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(contentAreaPanel, gbc);

        // Title card panel
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 30, 0, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        contentAreaPanel.add(titleCardPanel, gbc);
        
        // Fields panel
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 0, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contentAreaPanel.add(fieldsPanel, gbc);

        // Buttons panel
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        contentAreaPanel.add(buttonsPanel, gbc);
        
        // Title text
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        titleCardPanel.add(titleText, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 10);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        fieldsPanel.add(loginUserLabel, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0,0, 10);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        fieldsPanel.add(passwordLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        fieldsPanel.add(loginUserField, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        fieldsPanel.add(passwordField, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        buttonsPanel.add(submitButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        buttonsPanel.add(backButton, gbc);
    }
    
    public void finalizePrepare() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}