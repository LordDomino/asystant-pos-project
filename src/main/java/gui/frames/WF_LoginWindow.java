package main.java.gui.frames;

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

import main.java.Main;
import main.java.components.APP_AccentButton;
import main.java.components.APP_ContrastButton;
import main.java.components.APP_Frame;
import main.java.components.APP_PasswordField;
import main.java.components.APP_TextField;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.userAccountSystem.LoginManager;
import main.java.utils.GUIHelpers;

public final class WF_LoginWindow extends APP_Frame {

    public int textBoxWidth = 10;

    // Layout components
    private final JPanel contentAreaPanel = new JPanel(new GridBagLayout());
    private final JPanel titleCardPanel = new JPanel(new GridBagLayout());
    private final JPanel fieldsPanel = new JPanel(new GridBagLayout());
    private final JPanel buttonsPanel = new JPanel(new GridBagLayout());

    // Components
    private final JLabel titleText = new JLabel("Asystant - POS System");
    private final JLabel subtitleText = new JLabel("Developed by Grade 12 ICT (S.Y. 2023-24)");
    private final JLabel versionInfo = new JLabel("Version 0.5.2");

    private final JLabel usernameLabel = new JLabel("Username");
    private final JLabel passwordLabel = new JLabel("Password");

    protected final JTextField usernameField = new APP_TextField(textBoxWidth);
    protected final JPasswordField passwordField = new APP_PasswordField(textBoxWidth);
    protected final JTextField[] fields = {usernameField, passwordField};

    protected final JButton loginButton = new APP_AccentButton("Log In");
    protected final JButton quitButton = new APP_ContrastButton("Quit");

    public WF_LoginWindow() {
        super("Login - Asystant POS");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.CONTRAST);
        getRootPane().setDefaultButton(loginButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {

        // General preparations
        GUIHelpers.setButtonTriggerOnAllFields(loginButton, fields);

        // Preparations per layout component
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

        // Preparations per component
        subtitleText.setFont(StylesConfig.DETAIL);
        titleText.setFont(StylesConfig.HEADING1);
        versionInfo.setFont(StylesConfig.DETAIL);

        passwordField.setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.CONTRAST),
            new LineBorder(ColorConfig.BG, 2)
        ));

        loginButton.setEnabled(false);
        loginButton.addActionListener(new LoginActionListener());

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame source = (JFrame) SwingUtilities.getRoot(quitButton);
                source.dispose();
                System.exit(0);
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

    protected void authenticateLogin() {
        APP_Frame target = (APP_Frame) LoginManager.getCurrentAccessLevelTargetJFrame();
        target.finalizePrepare();
        target.setVisible(true);
        Main.app.LOGIN_WINDOW.dispose();
    }
}

class LoginActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean permitLogin = false;  // The boolean flag for allowing access

        // Retrieve the inputs
        final String username = getInputUsername();
        final String password = getInputPassword();

        try {
            /**
             * The login process follows a sequence of login attempts
             * with the help of the LoginManager class.
             * 
             * The super administrator account is first attempted to be
             * logged in regardless of illegal characters in the user's
             * inputs. We (developers) may opt to use special characters
             * that should only be allowed when logging in as the
             * superadmin.
             */
            if (LoginManager.validateSuperAdminUsername(username)) {
                permitLogin = true;
            }

            /**
             * If the string inputs do not match the super administrator
             * credentials, then the code infers that the user tries to
             * access a standard administrator or user account. Thus,
             * fetch all existing accounts in the database. The code
             * only needs to know if an existing account of the given
             * inputs is available.
             * 
             *  --> If there is: Assume that the user will eventually be
             *      authenticated to log in, so set permitLogin to true.
             */
            else if (LoginManager.validateUsername(username)) {
                permitLogin = true;
            }

            /**
             *  --> If no existing account reflects from the database:
             *      Forbid the user from authenticating to log in, so
             *      set permitLogin to false. Also notify via an error
             *      pop up window.
             */
            else {
                permitLogin = false;
                Component parent = SwingUtilities.getWindowAncestor(Main.app.LOGIN_WINDOW.loginButton);
                JOptionPane.showMessageDialog(
                    parent,
                    "Account of username \"" + username + "\" does not exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

            /**
             * Restrictions for illegal usernames (usernames containing
             * illegal characters) should already be enforced during
             * account creation. However, here the code counterchecks
             * for any potential bypasses that might allow illegal
             * usernames to be recorded in the database.
             * 
             * If an illegal username is found, forbid authentication
             * and display an error pop up window.
             */
            if (!LoginManager.isUsernameLegal(username) && !permitLogin) {
                permitLogin = false;
                Component parent = SwingUtilities.getWindowAncestor(Main.app.LOGIN_WINDOW.loginButton);
                JOptionPane.showMessageDialog(
                    parent,
                    "Account of username \"" + username + "\" is illegal.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

            /**
             * If the input strings and the account credentials pass the
             * previous validations, we then check if the user's given
             * inputs (i.e., the username and password) are correct.
             * Regardless of whether or not the account is active, input
             * checking should always go first. 
             * 
             *  --> If the input credentials are incorrect: Forbid user
             *      from authenticating via permitLogin = false.
             * 
             *      The number of incorrect attempts should also be
             *      incremented via the helper method
             *      LoginManager.incrementIncorrectAttempts(). If the
             *      number of incorrect attempts hits 3 times, the
             *      requested account is locked and a notification will
             *      display, otherwise, the user is warned via a pop up.
             */
            if (!LoginManager.isAccountPasswordCorrect(username, password)) {
                permitLogin = false;  // An account of the correct username was found
                                      // but an incorrect password was given
                LoginManager.incrementIncorrectAttempts(username);
                    
                String errorMessage;
                if (!LoginManager.isAccountActivated(username)) {
                    errorMessage = "Account \"" + username + "\" has been locked after three login attempts failed."
                                 + " Contact administrator for reactivation.";
                } else {
                    int remaining = LoginManager.getRemainingAttempts(username);
                    
                    errorMessage = "Incorrect password for \"" + username + "\".";

                    if (remaining == 1) {
                        errorMessage = errorMessage + " Only " + LoginManager.getRemainingAttempts(username)
                                     + " login attempt is remaining.";
                    } else {
                        errorMessage = errorMessage + " There are " + LoginManager.getRemainingAttempts(username)
                                     + " login attempts remaining.";
                    }

                    errorMessage = errorMessage + " If all login attempt fails, the account \""
                                 + username + "\" will be locked.";
                }

                Component parent = SwingUtilities.getWindowAncestor(Main.app.LOGIN_WINDOW.loginButton);
                JOptionPane.showMessageDialog(
                    parent,
                    errorMessage,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
            /**
             * The user's input credentials may be correct, but the
             * requested account is currently inactive or deactivated.
             * 
             *  --> If the account is inactive: Show an error message
             *      via an error pop up.
             */
            else if (!LoginManager.isAccountActivated(username)) {            
                permitLogin = false;  // Account has valid credentials but is not activated
               
                Component parent = SwingUtilities.getWindowAncestor(Main.app.LOGIN_WINDOW.loginButton);
                JOptionPane.showMessageDialog(
                    parent,
                    "Account \"" + username + "\" is not activated or is locked. Contact administrator for activation.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } 

            /**
             * Account login authentication always bases on the
             * permitLogin boolean flag to determine whether or not to
             * allow the user to login.
             * 
             * If a user successfully passes the validation processes,
             * reset its login attempts back to zero and then
             * authenticate it via LoginManager.resetLoginAttempts().
             */
            if (permitLogin) {
                LoginManager.resetLoginAttempts(username);
                Main.app.LOGIN_WINDOW.authenticateLogin();
            } else {
                // Execute when all user account type login attempts fail
                // Perform counter operation; maybe try alternative methods for future features
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            Component parent = SwingUtilities.getWindowAncestor(Main.app.LOGIN_WINDOW.loginButton);
            JOptionPane.showMessageDialog(
                parent,
                exception.toString(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Returns the input string provided in the username field of the 
     * login form.
     * @return the username input string
     */
    private String getInputUsername() {
        return Main.app.LOGIN_WINDOW.usernameField.getText();
    }

    /**
     * Returns the input string provided in the password field of the
     * login form.
     * @return the password input string
     */
    private String getInputPassword() {
        return new String(Main.app.LOGIN_WINDOW.passwordField.getPassword());
    }
}