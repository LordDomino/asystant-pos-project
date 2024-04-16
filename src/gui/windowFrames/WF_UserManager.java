package gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.GridBagLayout;

import components.APP_AccentButton;
import components.APP_Frame;
import configs.ColorConfig;

public class WF_UserManager extends APP_Frame {

    public JPanel buttonsPanel = new JPanel();

    String[] dataFields = {"username", "password", "access level", "activation"};

    Object[][] sampleData = {{"lancer826", "woahz", "User", true}};
    
    public JTable userTable = new JTable(sampleData, dataFields);

    public JScrollPane scrollPane = new JScrollPane(userTable);

    public JButton addButton = new APP_AccentButton("Add");

    public JButton editButton = new APP_AccentButton("Edit");

    public JButton deleteButton = new APP_AccentButton("Delete");


    public WF_UserManager () {
        super();
        compile();
    }

    public void prepareComponents() {
        getContentPane().setBackground(ColorConfig.ACCENT_3);

     



    }

    public void addComponents() {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buttonsPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(addButton, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(editButton, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonsPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(scrollPane, gbc);
    }

    public void prepare() {
        setLayout(new GridBagLayout());
    }

    public void finalizePrepare() {
        this.pack();
    }
}

class AddPopupWindow extends APP_Frame {

    JLabel header = new JLabel("Add a new user account");

    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel accessLevelLabel = new JLabel("Account access level");
    JLabel activatedLabel = new JLabel("Activated");

    JTextField usernameField = new JTextField();
    JTextField passwordField = new JTextField();

    String[] accessLevelTypes = {"Admin", "User"};
    JComboBox<String> accessLevelComboBox = new JComboBox<String>(accessLevelTypes);

    public AddPopupWindow() {
        super();
        compile();
    }


    public void prepareComponents() {
        
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);
        add(header, gbc);
    }

    public void prepare() {

    }

    public void finalizePrepare() {

    }

    public static void main(String[] args) {
        JFrame testFrame = new AddPopupWindow();
        testFrame.setVisible(true);
    }

}

class EditPopupWindow extends APP_Frame {

    EditPopupWindow(){
    super();
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