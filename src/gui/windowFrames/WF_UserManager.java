package gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;


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

    AddPopupWindow() {
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