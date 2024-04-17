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
import javax.swing.JPasswordField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import components.APP_AccentButton;
import components.APP_Frame;
import configs.ColorConfig;

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

    JLabel header = new JLabel("Add a new user account");
    JPanel form = new JPanel(new GridBagLayout());

    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel accessLevelLabel = new JLabel("Account access level");
    JLabel activatedLabel = new JLabel("Activated");

    JTextField usernameField = new JTextField(10);
    JTextField passwordField = new JTextField(10);

    String[] accessLevelTypes = {"Admin", "User"};
    JComboBox<String> accessLevelComboBox = new JComboBox<String>(accessLevelTypes);
    JCheckBox activatedCheckBox = new JCheckBox("", false);

    public AddPopupWindow() {
        super();
        compile();
    }


    public void prepareComponents() {
        
    }
    
    public void prepare() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 0, 20);
        add(header, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 20, 20);
        add(form, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        form.add(usernameLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        form.add(passwordLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 0, 0);
        form.add(accessLevelLabel, gbc);

        gbc.gridy = 3;
        form.add(activatedLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 0);
        form.add(usernameField, gbc);

        gbc.gridy = 1;
        form.add(passwordField, gbc);

        gbc.gridy = 2;
        form.add(accessLevelComboBox, gbc);

        gbc.gridy = 3;
        form.add(activatedCheckBox, gbc);
    }

    public void finalizePrepare() {

    }

    public static void main(String[] args) {
        JFrame testFrame = new AddPopupWindow();
        testFrame.pack();
        testFrame.setVisible(true);
    }

}

class EditPopupWindow extends APP_Frame {

    public JPanel editForm = new JPanel(new GridBagLayout());
    public JLabel usernameEditLabel = new JLabel("Username");
    public JLabel passwordEditLabel = new JLabel("Password");
    public JLabel accessEditLabel = new JLabel("Account access level");
    public JLabel activatedEditLabel = new JLabel("Activated");

    public JTextField usernameEditField = new JTextField();
    public JPasswordField passwordEditField = new JPasswordField();
    public String[] accessEditField = {"Admin", "User"};
    public JComboBox<String> accessLevelComboBoxEdit = new JComboBox<String>(accessEditField);
    JCheckBox activatedCheckBoxEdit = new JCheckBox("", false);

    EditPopupWindow(){
    super();
    compile();
    }

    public void prepareComponents() {
        setLayout(new GridBagLayout());
    }

    public void addComponents() {

    }

    public void prepare() {

    }

    public void finalizePrepare() {
 
        
    }
}