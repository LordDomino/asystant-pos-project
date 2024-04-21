package main.java.gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.components.APP_AccentButton;
import main.java.components.APP_ContrastButton;
import main.java.components.APP_Frame;
import main.java.components.APP_TextField;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;
import main.java.utils.GUIHelpers;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
import java.util.List;
import java.util.Vector;

public class WF_UserManager extends APP_Frame {

    public static final String[] columnNames = {"Username", "Password", "Access Level", "Activation Status"};
    public static ArrayList<ArrayList<String>> pendingDeletedUsernames = new ArrayList<>();

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
    public final JButton editButton = new APP_AccentButton("Edit selected user account") {

        @SuppressWarnings("unused")
        public void fireValueChanged() {  // Anonymous class hack :D
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();

            if (selectedIndices.length == 1) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    };
    public final JButton deleteButton = new APP_AccentButton("Delete user accounts") {

        @SuppressWarnings("unused")
        public void fireValueChanged() {  // Anonymous class hack :D
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();

            if (selectedIndices.length > 0) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    };

    public final JTable table = new JTable(tableModel);
    public final JScrollPane scrollPane = new JScrollPane(table);

    public final JLabel statusLabel = new JLabel("Status:");
    public final JTextField statusBar = new APP_TextField(30);
    public final JButton submitChangesButton = new APP_ContrastButton("Submit changes");

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
        statusBar.setFont(StylesConfig.NORMAL);

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (pendingDeletedUsernames.size() >= 1) {
                    DeletePopUpWindow popUp = new DeletePopUpWindow();
                    popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);
                    popUp.info.setText(
                        "<html>Adding new user accounts is not allowed while "
                        + "some rows are pending to be deleted. "
                        + "Confirm changes to pending deletions first."
                    );
                    popUp.setVisible(true);
                } else {
                    AddPopupWindow popUp = new AddPopupWindow();
                    popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);
                    popUp.setVisible(true);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditPopupWindow popUp = new EditPopupWindow();
                popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);

                if (table.getSelectionModel().getSelectedIndices().length != 1) {

                } else {
                    // Get the index of the selected row
                    int rowIndex = table.getSelectedRow();

                    // Get values from the selected row in JTable
                    final String retrievedUsername = table.getValueAt(rowIndex, 0).toString();
                    final String retrievedPassword = table.getValueAt(rowIndex, 1).toString();
                    final String retrievedAccessLevel = table.getValueAt(rowIndex, 2).toString();
                    final String retrievedActivationStatus = table.getValueAt(rowIndex, 3).toString();

                    // Preview the values in the fields
                    popUp.usernameEditField.setText(retrievedUsername);
                    popUp.passwordEditField.setText(retrievedPassword);

                    // Access level
                    if (retrievedAccessLevel == "Admin") {
                        popUp.accessLevelComboBoxEdit.setSelectedItem("Admin");
                    } else if (retrievedAccessLevel == "User") {
                        popUp.accessLevelComboBoxEdit.setSelectedItem("User");
                    } else {}

                    // Activation status
                    if (retrievedActivationStatus == "Activated") {
                        popUp.activatedCheckBoxEdit.setSelected(true);
                    } else if (retrievedActivationStatus == "Inactivated") {
                        popUp.activatedCheckBoxEdit.setSelected(false);
                    } else if (retrievedActivationStatus == "Deactivated") {
                        popUp.activatedCheckBoxEdit.setSelected(false);
                    }

                    popUp.usernameEditField.setEditable(false);

                    popUp.setVisible(true);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Get indices of selected rows from the JTable
                int[] selRows = table.getSelectionModel().getSelectedIndices();

                // Loop through the indices per row and append the usernames
                // to the pendingDeletedUsernames variable.
                // This allows that user accounts are not brutely deleted in
                // the database and are temporarily stored to a 'purgatory'
                for (int rowID : selRows) {
                    ArrayList<String> pendingRowValues = new ArrayList<>();
                    pendingRowValues.add(table.getValueAt(rowID, 0).toString());
                    pendingRowValues.add(table.getValueAt(rowID, 1).toString());
                    pendingRowValues.add(table.getValueAt(rowID, 2).toString());
                    pendingRowValues.add(table.getValueAt(rowID, 3).toString());
                    pendingDeletedUsernames.add(pendingRowValues);
                }

                // Remove the preview rows in JTable
                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        tableModel.removeRow(selectedRows[i]);
                    }
                }

                // Update the status status bar
                statusBar.setText(pendingDeletedUsernames.size() + " accounts pending to be deleted.");
            }
        });

        submitChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (pendingDeletedUsernames.size() >= 1) {
                    // Open the warning pop up window if there are pending deletions
                    DeletePopUpWindow popUp = new DeletePopUpWindow();
                    popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(submitChangesButton);
                    popUp.setVisible(true);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    editButton.getClass().getMethod("fireValueChanged").invoke(editButton);
                    deleteButton.getClass().getMethod("fireValueChanged").invoke(deleteButton);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    protected void fireValueChanged() {
        int[] selectedIndices = table.getSelectionModel().getSelectedIndices();

        if (selectedIndices.length == 1) {
            editButton.setEnabled(true);
        } else {
            editButton.setEnabled(false);
        }
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
            footerPanel.add(submitChangesButton, gbc);
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

            String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username NOT LIKE \"%SUPERADMIN%\"";
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

    protected void purgatoryPardon() {
        pendingDeletedUsernames.clear();
        statusBar.setText("");
        try {
            updateTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void purgatoryPurge() {
        // Performing the delete (SQL-Java connection)
        final int pendingSize = pendingDeletedUsernames.size();

        try {
            String query;
            SQLConnector.establishSQLConnection();

            if (pendingSize == 1) {
                // If only 1 row is selected, use the equal operator for SQL query
                query = "DELETE FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + pendingDeletedUsernames.get(0).get(0) + "\";";
            } else {
                // If more than one row is selected, use the IN keyword for SQL query
                query = "DELETE FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username IN ( ";

                // Get all usernameIDs
                for (ArrayList<String> username : pendingDeletedUsernames) {
                    query = query + "\"" + username.get(0) + "\", ";
                }

                query = query.substring(0, query.length()-2) + " );";
            }

            System.out.println(query);

            Statement statement = SQLConnector.connection.createStatement();
            statement.executeUpdate(query);

            SQLConnector.connection.close();

            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(deleteButton),
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        pendingDeletedUsernames.clear();
        statusBar.setText(pendingSize + " account(s) deleted successfully.");
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

                    String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + retrievedUsername + "\";";

                    Statement statement = SQLConnector.connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    if (result.getFetchSize() == 0) {
                        // No existing username has been found
                        query = "INSERT INTO " + DBReferences.TBL_USER_ACCOUNTS + " (";

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

                // Update status bar
                parentFrame.statusBar.setText("Created new user \"" + retrievedUsername + "\".");
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

    public JTextField usernameEditField = new JTextField(10);
    public JTextField passwordEditField = new JTextField(10);
    public String[] accessEditField = {"Admin", "User"};
    public JComboBox<String> accessLevelComboBoxEdit = new JComboBox<String>(accessEditField);
    JCheckBox activatedCheckBoxEdit = new JCheckBox("", false);

    JButton updateButton = new JButton("Update");

    JTextField[] dataField = {usernameEditField, passwordEditField};


    EditPopupWindow() {
        super("Edit Existing User Account");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        setLayout(new GridBagLayout());
        GUIHelpers.setButtonTriggerOnAllFields(updateButton, dataField);

        editForm.setOpaque(false);

        header.setFont(StylesConfig.HEADING3);
        accessLevelComboBoxEdit.setBackground(ColorConfig.BG);
        activatedCheckBoxEdit.setOpaque(false);
        updateButton.setEnabled(false);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Retrieve inputs
                final String retrievedUsername = usernameEditField.getText();
                final String retrievedPassword = passwordEditField.getText();
                final int retrievedAccessLevel;
                final int retrievedActivationStatus;

                if (accessLevelComboBoxEdit.getSelectedItem().toString().equals("Admin")) {
                    retrievedAccessLevel = 2;
                } else if (accessLevelComboBoxEdit.getSelectedItem().toString().equals("User")) {
                    retrievedAccessLevel = 3;
                } else {
                    retrievedAccessLevel = -1;
                }

                if (activatedCheckBoxEdit.isSelected()) {
                    retrievedActivationStatus = 1;
                } else {
                    retrievedActivationStatus = 0;
                }

                final Object[] retrieved = {retrievedUsername, retrievedPassword, retrievedAccessLevel, retrievedActivationStatus};

                try {
                    // Open database to get all registered user accounts
                    SQLConnector.establishSQLConnection();

                    String query = "SELECT * FROM " + DBReferences.TBL_USER_ACCOUNTS + " WHERE username = \"" + retrievedUsername + "\";";

                    Statement statement = SQLConnector.connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    if (result.getFetchSize() == 0) {
                        // No existing username has been found
                        query = "UPDATE " + DBReferences.TBL_USER_ACCOUNTS + " SET ";

                        List<String> columnNames = Arrays.asList(SQLConnector.FIELDS_user_accounts);
                        List<Object> values = Arrays.asList(retrieved);

                        for (int i = 0; i < columnNames.size(); i++) {
                            String columnName = columnNames.get(i);
                            Object value = values.get(i);

                            query = query + " " + columnName + " = \"" + value + "\",";
                        }

                        query = query.substring(0, query.length()-1);

                        query = query + " WHERE username = \"" + retrievedUsername + "\";";

                        // Preview and execute query, if there are no errors
                        System.out.println(query);
                        Statement statement2 = SQLConnector.connection.createStatement();
                        statement2.executeUpdate(query);

                        // Close the connection
                        SQLConnector.connection.close();

                        // Close the current pop up window
                        Window popUp = SwingUtilities.getWindowAncestor(updateButton);
                        popUp.dispose();

                        // Update table
                        parentFrame.loadFromDatabase();
                    } else {
                        // No existing username has been found
                    }

                } catch (SQLException exception) {
                    // To do when exception caught
                    exception.printStackTrace();
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

    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

class DeletePopUpWindow extends APP_Frame {

    /**Reference field so this instance can have access to the
     * WF_UserManager JFrame instance.
     */
    public WF_UserManager parentFrame;

    public static final String[] columnNames = {"Username", "Password", "Access Level", "Activation Status"};

    public final JPanel headerPanel = new JPanel(new GridBagLayout());
    public final JPanel buttonsPanel = new JPanel(new GridBagLayout());

    public final JLabel header = new JLabel("Deletion Warning");
    public final JLabel info = new JLabel("<html>Accounts are pending to be deleted. Proceed?");

    protected final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public final JTable table = new JTable(tableModel);
    public final JScrollPane scrollPane = new JScrollPane(table);

    public final JButton cancelButton = new APP_ContrastButton("Discard pending deletions");
    public final JButton continueButton = new APP_ContrastButton("Continue");

    public DeletePopUpWindow() {
        super("Pending Deletion Warning");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        headerPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);

        header.setFont(StylesConfig.HEADING3);
        info.setFont(StylesConfig.NORMAL);

        for (ArrayList<String> row : WF_UserManager.pendingDeletedUsernames) {
            tableModel.addRow(new Vector<>(row));
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parentFrame.purgatoryPardon();
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(continueButton);
                source.dispose();
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parentFrame.purgatoryPurge();
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(continueButton);
                source.dispose();
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XL, 0, InsetsConfig.XL);
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(headerPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            headerPanel.add(header, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            headerPanel.add(info, gbc);
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XL, 0, InsetsConfig.XL);
        gbc.weighty = 1;
        add(scrollPane, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XL, InsetsConfig.XL, InsetsConfig.XL);
        gbc.weighty = 0;
        add(buttonsPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            buttonsPanel.add(cancelButton, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, InsetsConfig.S, 0, 0);
            buttonsPanel.add(continueButton, gbc);
        }
    }

    public void finalizePrepare() {
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(getSize());
    }
}