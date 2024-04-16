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

import components.APP_Frame;

public class WF_UserManager extends APP_Frame {

    public JPanel buttonsPanel = new JPanel();

    String[] dataFields = {"username", "password", "access level", "activation"};

    Object[][] sampleData = {{"lancer826", "woahz", "User", true}};
    
    public JTable userTable = new JTable(sampleData, dataFields);

    public JScrollPane scrollPane = new JScrollPane(userTable);

    public JButton addButton = new JButton("Add");


    public WF_UserManager () {
        super();
        compile();
    }

    public void prepareComponents() {

     



    }

    public void addComponents() {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buttonsPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scrollPane, gbc);
    }

    public void prepare() {

    }

    public void finalizePrepare() {

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