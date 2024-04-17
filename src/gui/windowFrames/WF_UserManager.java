package gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import components.APP_AccentButton;
import components.APP_ContrastButton;
import components.APP_Frame;
import components.APP_TextField;
import configs.ColorConfig;
import configs.InsetsConfig;
import configs.StylesConfig;
import sql.SQLConnector;
import utils.GUIHelpers;

public class WF_UserManager extends APP_Frame {

    public static final String[] columnNames = {"Username", "Password", "Access Level", "Activation Status"};
    public static ArrayList<String> pendingDeletedUsernames = new ArrayList<>();
    
    protected final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Layout components
    public final JPanel headerPanel = new JPanel(new GridBagLayout());
    public final JPanel buttonsPanel = new JPanel(new GridBagLayout());
    public final JPanel footerPanel = new JPanel(new GridBagLayout());

    // Components
    public final JLabel header = new JLabel("Manage user accounts");
    public final JLabel guide1 = new JLabel(
        "<html>Information here..."
    );

    public final JLabel buttonsPanelLead = new JLabel("User account actions");
    public final JButton addButton = new APP_AccentButton("Add new user account");
    public final JButton editButton = new APP_AccentButton("Edit selected user account");
    public final JButton deleteButton = new APP_AccentButton("Delete user accounts");
    
    public final JTable table = new JTable(tableModel);
    public final JScrollPane scrollPane = new JScrollPane(table);

    public final JLabel statusLabel = new JLabel("Status:");
    public final JTextField statusBar = new APP_TextField(30);
    public final JButton saveAndCloseButton = new APP_ContrastButton("Submit and Close");

    public WF_UserManager() {
        super("User Management Control Panel");
        compile();
    }
    
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        try {
            updateTable();
        } catch (SQLException exception) {
            // To do if table cannot be updated from initialization
        }

        headerPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);
        footerPanel.setOpaque(false);

        header.setFont(StylesConfig.HEADING1);
        buttonsPanelLead.setFont(StylesConfig.LEAD);
        statusBar.setEditable(false);
        statusBar.setFont(StylesConfig.DETAIL);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                AddPopupWindow popUp = new AddPopupWindow();
                popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);
                popUp.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditPopupWindow popUp = new EditPopupWindow();
                popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);
                
                // access parentFrame's JTable
                // IF conditional
                // only 1 selected is allowed for edit
                
                // get index of the selected row
                table.getSelectedRow();
                
                // get values from selected row in jtable
                final String retrievedUsername = table.getValueAt(index, 0).toString(); // index 0 = username
                final String retrievedPassword = table.getValueAt(index, 1).toString();  // index 1 = password
                final int retrievedAccessLevel = table.getValueAt(index, 2);  // java convert object to int
                final int retrievedActivationStatus = table.getValueAt(index, 3); // convert
                
                // set text in textFields
                popUp.usernameEditField.setText(retrievedUsername);
                // password
                // access level
                // activation status
                popUp.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Convert columnNames into array list and get the index of the
                // unique identifier column
                ArrayList<String> headers = new ArrayList<>(Arrays.asList(columnNames));
                int uniqueColumnID = headers.indexOf("username");

                // Get indices of selected rows from the JTable
                int[] selRows = table.getSelectionModel().getSelectedIndices();

                // Loop through the indices per row and append the usernames
                // to the pendingDeletedUsernames variable.
                // This allows that user accounts are not brutely deleted in
                // the database and are temporarily stored to a 'purgatory'
                for (int rowID : selRows) {
                    String username = table.getValueAt(rowID, uniqueColumnID).toString();
                    pendingDeletedUsernames.add(username);
                }

                // Performing the delete (SQL-Java connection)
                try {
                    String query;
                    SQLConnector.establishSQLConnection();

                    if (pendingDeletedUsernames.size() == 1) {
                        // If only 1 row is selected, use the equal operator for SQL query
                        query = "DELETE FROM " + SQLConnector.sqlTbl + " WHERE username = \"" + pendingDeletedUsernames.get(0) + "\"";
                    } else {
                        // If more than one row is selected, use the IN keyword for SQL query
                        query = "DELETE FROM " + SQLConnector.sqlTbl + " WHERE username IN ( ";

                        // Get all uniqueIDEmails
                        for (String username : pendingDeletedUsernames) {
                            query = query + "\"" + username + "\", ";
                        }

                        query = query.substring(0, query.length()-2) + " );";
                    }
                    System.out.println(query);
                    Statement statement = SQLConnector.connection.createStatement();
                    statement.executeUpdate(query);
                    
                    SQLConnector.connection.close();
                    loadFromDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                        SwingUtilities.getWindowAncestor(deleteButton),
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(headerPanel, gbc);

        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            headerPanel.add(header, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            headerPanel.add(guide1, gbc);
        }

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(buttonsPanel, gbc);
        
        {
            gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 0, 0);
            buttonsPanel.add(buttonsPanelLead, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
        buttonsPanel.add(addButton, gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
        buttonsPanel.add(editButton, gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
        buttonsPanel.add(deleteButton, gbc);
        
        }
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);
        
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(footerPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.weightx = 0;
            gbc.weighty = 0;
            footerPanel.add(statusLabel, gbc);
            
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.insets = new Insets(0, InsetsConfig.S, 0, 0);
            footerPanel.add(statusBar, gbc);
            
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
            footerPanel.add(saveAndCloseButton, gbc);
        }
    }

    public void finalizePrepare() {
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    protected void updateTable() throws SQLException {
        loadFromDatabase();
    }

    protected void loadFromDatabase() throws SQLException {
        // Clear the rows
        tableModel.setRowCount(0);

        try {
            SQLConnector.establishSQLConnection();

            String query = "SELECT * FROM " + SQLConnector.sqlTbl + " WHERE username NOT LIKE \"%SUPERADMIN%\"";
            Statement statement = SQLConnector.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = statement.executeQuery(query);

            // Go to last row to get the total number of rows
            result.last();
            int n = result.getRow();

            // Then go back to the starting point before looping
            result.beforeFirst();

            int i = 0;
            while (i < n) {
                result.next();
                
                // Skip adding the super admin credentials to the table (for security reasons).
                // Fail safe conditional if super admin info has been passed through the filter query
                if (result.getString("username").equals("%SUPERADMIN%")) {
                    System.out.println("Warning: Super admin account information bypassed SQL filter query");
                    i++;
                    continue;
                }

                ArrayList<Object> dataFromSQL = new ArrayList<>();

                for (String field : SQLConnector.FIELDS_user_accounts) {
                    Object data = result.getObject(field);

                    // Modify presentation of values based on data
                    if (field == "activated") {
                        if (data.equals(1) || data.equals(true)) {
                            dataFromSQL.add("Activated");
                        } else if (data.equals(0) || data.equals(false)) {
                            dataFromSQL.add("Inactivated");
                        } else if (data.equals(-1)) {
                            dataFromSQL.add("Deactivated");
                        }
                    } else if (field == "access_level") {
                        if (data.equals(2)) {
                            dataFromSQL.add("Admin");
                        } else if (data.equals(3)) {
                            dataFromSQL.add("User");
                        } else {
                            dataFromSQL.add("Unrecognized access level: " + data);
                        }
                    } else {
                        dataFromSQL.add(result.getObject(field));
                    }
                }

                tableModel.addRow(dataFromSQL.toArray());
                i++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

class AddPopupWindow extends APP_Frame {

    /**Reference field so this instance can have access to the
     * WF_UserManager JFrame instance.
     */
    public WF_UserManager parentFrame;

    String[] accessLevelTypes = {"Admin", "User"};

    // Components
    final JLabel header = new JLabel("Add a new user account");
    final JPanel form = new JPanel(new GridBagLayout());

    JLabel usernameLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JLabel accessLevelLabel = new JLabel("Account access level");
    JLabel activatedLabel = new JLabel("Activated");

    JTextField usernameField = new JTextField(10);
    JTextField passwordField = new JTextField(10);
    JComboBox<String> accessLevelComboBox = new JComboBox<String>(accessLevelTypes);
    JCheckBox activatedCheckBox = new JCheckBox("", false);

    JButton submitButton = new APP_AccentButton("Submit");

    final JTextField[] fields = {usernameField, passwordField};

    public AddPopupWindow() {
        super("Add New User Account");
        compile();
    }
    
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(submitButton, fields);  // ensures that all fields are non-empty for button to enable

        form.setOpaque(false);
        
        header.setFont(StylesConfig.HEADING3);
        accessLevelComboBox.setBackground(ColorConfig.BG);
        activatedCheckBox.setOpaque(false);
        submitButton.setEnabled(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Retrieve inputs
                final String retrievedUsername = usernameField.getText();
                final String retrievedPassword = passwordField.getText();
                final int retrievedAccessLevel;
                final int retrievedActivationStatus;
                
                if (accessLevelComboBox.getSelectedItem().toString().equals("Admin")) {
                    retrievedAccessLevel = 2;
                } else if (accessLevelComboBox.getSelectedItem().toString().equals("User")) {
                    retrievedAccessLevel = 3;
                } else {
                    retrievedAccessLevel = -1;
                }
                
                if (activatedCheckBox.isSelected()) {
                    retrievedActivationStatus = 1;
                } else {
                    retrievedActivationStatus = 0;
                }

                final Object[] retrieved = {retrievedUsername, retrievedPassword, retrievedAccessLevel, retrievedActivationStatus};

                try {
                    // Open database to get all registered user accounts
                    SQLConnector.establishSQLConnection();

                    String query = "SELECT * FROM " + SQLConnector.sqlTbl + " WHERE username = \"" + retrievedUsername + "\";";

                    Statement statement = SQLConnector.connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    if (result.getFetchSize() == 0) {
                        // No existing username has been found
                        query = "INSERT INTO " + SQLConnector.sqlTbl + " (";

                        for (String field : SQLConnector.FIELDS_user_accounts) {
                            query = query + " `" + field + "`,";
                        }

                        query = query.substring(0, query.length()-1) + " ) VALUES (";

                        for (Object val : retrieved) {
                            if (val instanceof String) {
                                query = query + " \"" + val + "\",";
                            } else if (val instanceof Integer) {
                                query = query + " " + val + ",";
                            }
                        }

                        query = query.substring(0, query.length()-1) + " );";

                        // Preview and execute query, if there are no errors
                        System.out.println(query);
                        Statement statement2 = SQLConnector.connection.createStatement();
                        statement2.executeUpdate(query);

                        // Close the connection
                        SQLConnector.connection.close();

                        // Close the current pop up window
                        Window popUp = SwingUtilities.getWindowAncestor(submitButton);
                        popUp.dispose();

                        // Update table
                        parentFrame.loadFromDatabase();
                    } else {
                        // An existing username has been found
                    }

                } catch (SQLException exception) {
                    // To do when exception caught
                }
            }
        });
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
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Resets all fields to their default states.
     */
    protected void clearTextFields() {
        for (JTextField field : fields) {
            field.setText("");
        }
        accessLevelComboBox.setSelectedIndex(0);
        activatedCheckBox.setSelected(false);
    }
}

class EditPopupWindow extends APP_Frame {

    public WF_UserManager parentFrame;

    String[] accessLevelTypes = {"Admin", "User"};

    final JLabel header = new JLabel("Edit an Existing Account");
    final JPanel editForm = new JPanel(new GridBagLayout());

    public JLabel usernameEditLabel = new JLabel("Username");
    public JLabel passwordEditLabel = new JLabel("Password");
    public JLabel accessEditLabel = new JLabel("Account access level");
    public JLabel activatedEditLabel = new JLabel("Activated");

    public JTextField usernameEditField = new JTextField();
    public JTextField passwordEditField = new JTextField();
    public String[] accessEditField = {"Admin", "User"};
    public JComboBox<String> accessLevelComboBoxEdit = new JComboBox<String>(accessEditField);
    JCheckBox activatedCheckBoxEdit = new JCheckBox("", false);

    JButton updateButton = new JButton("Update");

    JTextField[] dataField = {usernameEditField, passwordEditField};


    EditPopupWindow(){
    super("Edit Existing User Account");
    compile();
    }

    public void prepareComponents() {
        setLayout(new GridBagLayout());
        GUIHelpers.setButtonTriggerOnAllFields(updateButton, dataField);

        accessLevelComboBoxEdit.setBackground(ColorConfig.BG);
        activatedCheckBoxEdit.setOpaque(false);
        updateButton.setEnabled(false);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Retrieve inputs
                final String retrievedUsername = usernameField.getText();
                final String retrievedPassword = passwordField.getText();
                final int retrievedAccessLevel;
                final int retrievedActivationStatus;

                if (accessLevelComboBox.getSelectedItem().toString().equals("Admin")) {
                    retrievedAccessLevel = 2;
                } else if (accessLevelComboBox.getSelectedItem().toString().equals("User")) {
                    retrievedAccessLevel = 3;
                } else {
                    retrievedAccessLevel = -1;
                }

                if (activatedCheckBox.isSelected()) {
                    retrievedActivationStatus = 1;
                } else {
                    retrievedActivationStatus = 0;
                }

                final Object[] retrieved = {retrievedUsername, retrievedPassword, retrievedAccessLevel, retrievedActivationStatus};

                try {
                    // Open database to get all registered user accounts
                    SQLConnector.establishSQLConnection();
                
                    String query = "SELECT * FROM " + SQLConnector.sqlTbl + " WHERE username = \"" + retrievedUsername + "\";";
                
                    Statement statement = SQLConnector.connection.createStatement();
                    ResultSet result = statement.executeQuery(query);
                                
                    if (result.getFetchSize() == 0) {
                        // No existing username has been found
                        query = "INSERT INTO " + SQLConnector.sqlTbl + " (";
                    
                        for (String field : SQLConnector.FIELDS_user_accounts) {
                            query = query + " `" + field + "`,";
                        }
                    
                        query = query.substring(0, query.length()-1) + " ) VALUES (";
                    
                        for (Object val : retrieved) {
                            if (val instanceof String) {
                                query = query + " \"" + val + "\",";
                            } else if (val instanceof Integer) {
                                query = query + " " + val + ",";
                                            }
                                        }
                    
                        query = query.substring(0, query.length()-1) + " );";
                    
                        // Preview and execute query, if there are no errors
                        System.out.println(query);
                        Statement statement2 = SQLConnector.connection.createStatement();
                        statement2.executeUpdate(query);
                    
                        // Close the connection
                        SQLConnector.connection.close();
                    
                        // Close the current pop up window
                        Window popUp = SwingUtilities.getWindowAncestor(submitButton);
                        popUp.dispose();
                    
                        // Update table
                        parentFrame.loadFromDatabase();
                    } else {
                        // An existing username has been found
                    }
                
                } catch (SQLException exception) {
                    // To do when exception caught
                }
            }

        });


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
        add(editForm, gbc);

        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            editForm.add(usernameEditLabel, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.XS, 0, 0, 0);
            editForm.add(passwordEditLabel, gbc);

            gbc.gridy = 2;
            editForm.add(accessEditLabel, gbc);

            gbc.gridy = 3;
            editForm.add(activatedEditLabel, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.XS, InsetsConfig.L, 0, 0);
            editForm.add(usernameEditField, gbc);

            gbc.gridy = 1;
            editForm.add(passwordEditField, gbc);

            gbc.gridy = 2;
            editForm.add(accessLevelComboBoxEdit, gbc);

            gbc.gridy = 3;
            editForm.add(activatedCheckBoxEdit, gbc);
        }
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(updateButton, gbc);

    }

    public void prepare() {

    }

    public void finalizePrepare() {

        
    }
}