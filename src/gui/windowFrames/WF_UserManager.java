package gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import components.APP_AccentButton;
import components.APP_Frame;
import configs.APPResourceLoader;
import configs.ColorConfig;
import configs.InsetsConfig;
import configs.StylesConfig;

public class WF_UserManager extends APP_Frame {

    public JPanel buttonsPanel = new JPanel(new GridBagLayout());

    String[] dataFields = {"Username", "Password", "Access Level", "Activation Status"};

    Object[][] sampleData = {{"lancer826", "woahz", "User", true}};
    
    public JTable userTable = new JTable(sampleData, dataFields);

    public JScrollPane scrollPane = new JScrollPane(userTable);

    public JButton addButton = new APP_AccentButton("Add");

    public JButton editButton = new APP_AccentButton("Edit");

    public JButton deleteButton = new APP_AccentButton("Delete");


    public WF_UserManager () {
        super("User Management Window");
        compile();
    }
    
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        buttonsPanel.setOpaque(false);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        add(buttonsPanel, gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonsPanel.add(addButton, gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttonsPanel.add(editButton, gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttonsPanel.add(deleteButton, gbc);
        
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);
    }

    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
    }
}

class AddPopupWindow extends APP_Frame {

    String[] accessLevelTypes = {"Admin", "User"};

    // Components
    JLabel header = new JLabel("Add a new user account");
    JPanel form = new JPanel(new GridBagLayout());

    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel accessLevelLabel = new JLabel("Account access level");
    JLabel activatedLabel = new JLabel("Activated");

    JTextField usernameField = new JTextField(10);
    JTextField passwordField = new JTextField(10);
    JComboBox<String> accessLevelComboBox = new JComboBox<String>(accessLevelTypes);
    JCheckBox activatedCheckBox = new JCheckBox("", false);

    JButton submitButton = new APP_AccentButton("Submit");

    public AddPopupWindow() {
        super("Add New Account");
        compile();
    }


    public void prepareComponents() {
        header.setFont(StylesConfig.HEADING3);
        form.setOpaque(false);

        accessLevelComboBox.setBackground(ColorConfig.BG);
        activatedCheckBox.setOpaque(false);
    }
    
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(header, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(form, gbc);

        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            form.add(usernameLabel, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.XS, 0, 0, 0);
            form.add(passwordLabel, gbc);

            gbc.gridy = 2;
            form.add(accessLevelLabel, gbc);

            gbc.gridy = 3;
            form.add(activatedLabel, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XS, InsetsConfig.L, 0, 0);
            form.add(usernameField, gbc);

            gbc.gridy = 1;
            form.add(passwordField, gbc);

            gbc.gridy = 2;
            form.add(accessLevelComboBox, gbc);

            gbc.gridy = 3;
            form.add(activatedCheckBox, gbc);
        }
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(submitButton, gbc);
    }

    public void finalizePrepare() {

    }

    public static void main(String[] args) {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts
        
        JFrame testFrame = new AddPopupWindow();
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
        testFrame.setResizable(false);
    }

}

class EditPopupWindow extends APP_Frame {

    EditPopupWindow(){
        super("Edit account properties");
        compile();
    }

    public void prepareComponents() {
        
    }

    public void addComponents() {

    }

    public void prepare() {

    }

    public void finalizePrepare() {

        
    }
}