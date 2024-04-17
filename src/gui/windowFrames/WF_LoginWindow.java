package gui.windowFrames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import components.APP_Frame;
import components.APP_PasswordField;
import components.APP_TextField;
import components.APP_AccentButton;
import components.APP_ContrastButton;
import components.APP_DefaultButton;
import configs.ColorConfig;
import configs.InsetsConfig;
import configs.StylesConfig;
import sql.SQLConnector;
import userAccountSystem.LoginManager;
import utils.GUIHelpers;

public final class WF_LoginWindow extends APP_Frame {

    public int textBoxWidth = 10;

    // Layout components
    private JPanel contentAreaPanel = new JPanel(new GridBagLayout());
    private JPanel titleCardPanel = new JPanel(new GridBagLayout());
    private JPanel fieldsPanel = new JPanel(new GridBagLayout());
    private JPanel buttonsPanel = new JPanel(new GridBagLayout());

    // Components
    public JLabel titleText = new JLabel("Asystant - POS System");
    public JLabel subtitleText = new JLabel("Developed by Grade 12 ICT (S.Y. 2023-24)");
    public JLabel versionInfo = new JLabel("Version x.x.x.1");

    public JLabel usernameLabel = new JLabel("Username");
    public JLabel passwordLabel = new JLabel("Password");

    public JTextField usernameField = new APP_TextField(textBoxWidth);
    public JPasswordField passwordField = new APP_PasswordField(textBoxWidth);
    public JTextField[] fields = {usernameField, passwordField};

    public JButton loginButton = new APP_AccentButton("Log In");
    public JButton quitButton = new APP_ContrastButton("Quit");

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
        
        // General preparations
        GUIHelpers.setButtonTriggerOnAllFields(loginButton, fields);



        // Preparations per component
        contentAreaPanel.setBackground(ColorConfig.ACCENT_1);
        titleCardPanel.setOpaque(false);
        fieldsPanel.setOpaque(false);
        fieldsPanel.setBorder(new CompoundBorder(
            new TitledBorder(
                new LineBorder(ColorConfig.ACCENT_2),
                "Login",
                TitledBorder.CENTER,
                TitledBorder.TOP, 
                StylesConfig.HEADING3
            ),
            new EmptyBorder(new Insets(10, 10, 10, 10))
        ));
        buttonsPanel.setOpaque(false);

        titleText.setFont(StylesConfig.HEADING1);
        subtitleText.setFont(StylesConfig.DETAIL);
        versionInfo.setFont(StylesConfig.DETAIL);

        passwordField.setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.CONTRAST),
            new LineBorder(ColorConfig.BG, 2)
        ));

        loginButton.setEnabled(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean permitLogin = false;  // The boolean flag for allowing SUPER ADMIN access

                // Retrieve the credentials
                final String username = usernameField.getText();
                final String password = new String(passwordField.getPassword());

                // To retrieve the user accounts credentials from the database,
                // establish a SQL connection first
                try {
                    SQLConnector.establishSQLConnection();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                    Component parent = SwingUtilities.getWindowAncestor(loginButton);
                    JOptionPane.showMessageDialog(
                        parent,
                        exception.toString(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }

                try {
                    if (LoginManager.validateSuperAdminCredentials(username, password)) {
                        // Attempt to login super admin
                        permitLogin = true;
                    } else if (LoginManager.validateCredentials(username, password)) {
                        // Super admin is not accessed and LoginManager
                        // defaults to attempting login either admin or user
                        permitLogin = true;
                    } else {
                        // The login cannot take place
                        System.out.println("No account credentials found");
                    }

                    // Counter-check for illegal usernames.
                    // This ensures that illegal usernames that have
                    // bypassed the SQL database cannot be logged on.
                    if (!LoginManager.isUsernameLegal(username)) {
                        // Error message goes here
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                if (permitLogin) {
                    authenticateLogin();
                } else {
                    // Execute when all user account type login attempts fail
                    // Perform counter operation
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame source = (JFrame) SwingUtilities.getRoot(quitButton);
                source.dispose();
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        // All styling JFrames will go here

        // Content area panel
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 0, 50);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(contentAreaPanel, gbc);

        {
            // Title card panel
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XXL, 30, InsetsConfig.XXL, 30);
            gbc.weightx = 1;
            gbc.weighty = 0;
            contentAreaPanel.add(titleCardPanel, gbc);

            {
                // Title text
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                gbc.weightx = 0;
                gbc.weighty = 0;
                titleCardPanel.add(titleText, gbc);

                gbc.gridy = 1;
                gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
                titleCardPanel.add(subtitleText, gbc);

                gbc.gridy = 2;
                gbc.insets = new Insets(InsetsConfig.XS, 0, 0, 0);
                titleCardPanel.add(versionInfo, gbc);
            }

            // Fields panel
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            gbc.weightx = 0;
            gbc.weighty = 0;
            contentAreaPanel.add(fieldsPanel, gbc);

            {
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.NONE;
                gbc.ipadx = 0;
                gbc.ipady = 0;
                gbc.weightx = 1;
                gbc.weighty = 1;

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                fieldsPanel.add(usernameLabel, gbc);
                
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(InsetsConfig.S, 0,0, 0);
                fieldsPanel.add(passwordLabel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, InsetsConfig.L, 0, 0);
                fieldsPanel.add(usernameField, gbc);
                
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.L, 0, 0);
                fieldsPanel.add(passwordField, gbc);
            }

            // Buttons panel
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.insets = new Insets(InsetsConfig.XL, 0, InsetsConfig.XXL, 0);
            gbc.weightx = 1;
            gbc.weighty = 0;
            contentAreaPanel.add(buttonsPanel, gbc);

            {
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 0);
                gbc.weightx = 1;
                gbc.weighty = 1;
                buttonsPanel.add(loginButton, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
                buttonsPanel.add(quitButton, gbc);
            }
        }
    }

    public void finalizePrepare() {
        pack();
        setSize(new Dimension(getSize().width, (int) Math.round(getSize().height * 1.5)));
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void authenticateLogin() {
        JFrame target = LoginManager.getCurrentAccessLevelTargetJFrame();
        target.setVisible(true);
        dispose();
    }
}