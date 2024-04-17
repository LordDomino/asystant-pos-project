package gui.windowFrames;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
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
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import components.APP_AccentButton;
import components.APP_Frame;
import configs.ColorConfig;
import configs.InsetsConfig;
import configs.StylesConfig;
import sql.SQLConnector;
import utils.GUIHelpers;

public class WF_UserManager extends APP_Frame {

    
    public static final String[] columnNames = {"Username", "Password", "Access Level", "Activation Status"};
    
    protected DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public final JPanel buttonsPanel = new JPanel(new GridBagLayout());

    public final JButton addButton = new APP_AccentButton("Add");
    public final JButton editButton = new APP_AccentButton("Edit");
    public final JButton deleteButton = new APP_AccentButton("Delete");
    
    public final JTable userTable = new JTable(tableModel);
    public final JScrollPane scrollPane = new JScrollPane(userTable);

    public WF_UserManager () {
        super("User Management Window");
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

        buttonsPanel.setOpaque(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                AddPopupWindow popUp = new AddPopupWindow();
                popUp.parentFrame = (WF_UserManager) SwingUtilities.getWindowAncestor(addButton);
                popUp.setVisible(true);
            }
        });
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
                    dataFromSQL.add(result.getObject(field));
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
        super();
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