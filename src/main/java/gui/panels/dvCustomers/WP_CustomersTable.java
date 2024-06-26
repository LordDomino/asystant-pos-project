package main.java.gui.panels.dvCustomers;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
import java.util.LinkedHashMap;
import java.util.Vector;

import main.java.Main;
import main.java.components.APP_AccentButton;
import main.java.components.APP_ContrastButton;
import main.java.components.APP_LabeledTextField;
import main.java.components.APP_Panel;
import main.java.components.APP_PopUpFrame;
import main.java.components.APP_TextField;
import main.java.components.RFIDPopUp;
import main.java.components.RfidReceivable;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.gui.frames.WF_Dashboard;
import main.java.gui.panels.WP_DetailsPanel;
import main.java.sql.DBReferences;
import main.java.sql.Queries;
import main.java.sql.SQLConnector;
import main.java.userAccountSystem.LoginManager;
import main.java.utils.GUIHelpers;
import main.java.utils.SQLUtils;

public class WP_CustomersTable extends APP_Panel {

    public static final LinkedHashMap<String, String> fieldMappings = new LinkedHashMap<>();

    static {
        fieldMappings.put("Customer ID", "id");
        fieldMappings.put("RFID No.", "rfid_no");
        fieldMappings.put("Student No.", "student_no");
        fieldMappings.put("Customer Name", "customer_name");
        fieldMappings.put("Amount Deposited", "amount_deposited");
        fieldMappings.put("Activated", "activated");
    }

    public static ArrayList<ArrayList<String>> pendingDeletedRows = new ArrayList<>();
    public final DefaultTableModel model = new DefaultTableModel(new Vector<String>(fieldMappings.keySet()), 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public final JTable table = new JTable(model);

    // Layout components
    public final JPanel headerPanel         = new JPanel(new GridBagLayout());
    public final JPanel buttonsPanel        = new JPanel(new GridBagLayout());
    public final JPanel tablePanel          = new JPanel(new GridBagLayout());
    public final JPanel footerPanel         = new JPanel(new GridBagLayout());
    public final JPanel footerButtonsPanel  = new JPanel(new GridBagLayout());

    public final JScrollPane scrollPane         = new JScrollPane(table);
    public final WP_DetailsPanel detailsPanel   = new WP_DetailsPanel();
    
    public static final ArrayList<ArrayList<String>> pendingDeletedProducts = new ArrayList<>();

    // Components
    public final APP_AccentButton addButton = new APP_AccentButton("Add");
    public final APP_AccentButton editButton = new APP_AccentButton("Edit") {

        @Override
        public void fireValueChanged() {
            final int[] selectedIndices = table.getSelectionModel().getSelectedIndices();
            if (selectedIndices.length == 1) {
                setEnabled(true);    
            } else {
                setEnabled(false);
            }
        }

    };
    public final APP_AccentButton deleteButton = new APP_AccentButton("Delete") {

        @Override
        public void fireValueChanged() {
            int[] selectedIndices = table.getSelectionModel().getSelectedIndices();

            if (selectedIndices.length > 0) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }

    };

    public final APP_AccentButton refreshButton = new APP_AccentButton("Refresh");

    public final APP_ContrastButton submitChangesButton = new APP_ContrastButton("Submit changes");

    public WP_CustomersTable () {
        super();
        compile();
    }

    public void prepare() {
        refreshUpdate();
        setBackground(ColorConfig.BG);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        refreshUpdate();

        headerPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);
        footerPanel.setOpaque(false);
        footerButtonsPanel.setOpaque(false);

        tablePanel.setBackground(ColorConfig.BG);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBackground(ColorConfig.BG);
        scrollPane.getViewport().setBackground(ColorConfig.BG);
        scrollPane.setBackground(ColorConfig.BG);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        detailsPanel.setBackground(ColorConfig.ACCENT_1);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    editButton.fireValueChanged();
                    deleteButton.fireValueChanged();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers_AddPopupWindow popUp = new Customers_AddPopupWindow();
                popUp.setLocationRelativeTo(null);
                popUp.setVisible(true);
            }
        });

        editButton.setEnabled(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers_EditPopupWindow popUp = new Customers_EditPopupWindow();

                final int selectedRowIndex            = table.getSelectedRow();
                final String selectedCustomerNo       = table.getValueAt(selectedRowIndex, 0).toString();
                final String selectedRfidNo           = table.getValueAt(selectedRowIndex, 1).toString();
                final String selectedStudentNo        = table.getValueAt(selectedRowIndex, 2).toString();
                final String selectedCustomerName     = table.getValueAt(selectedRowIndex, 3).toString();
                final String selectedAmountDeposited  = table.getValueAt(selectedRowIndex, 4).toString();
                final String selectedActivated          = table.getValueAt(selectedRowIndex, 5).toString();

                final String inferredRfidNo;
                final boolean inferredActivated;

                if (selectedRfidNo instanceof String) {
                    inferredRfidNo = (String) selectedRfidNo;
                } else {
                    inferredRfidNo = "";
                }

                if (selectedActivated.equals("Yes")) {
                    inferredActivated = true;
                } else {
                    inferredActivated = false;
                }

                popUp.customerIDField.setText(selectedCustomerNo);
                popUp.rfidNoField.setText(inferredRfidNo);
                popUp.studentNoField.setText(selectedStudentNo);
                popUp.customerNameField.setText(selectedCustomerName);
                popUp.amountDepositedField.setText(selectedAmountDeposited);
                popUp.activatedCheckBox.setSelected(inferredActivated);

                popUp.setLocationRelativeTo(null);
                popUp.setVisible(true);
            }
        });

        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();

                ArrayList<String> tableColumns = new ArrayList<String>(fieldMappings.keySet());

                for (int rowID : selectedRows) {
                    ArrayList<String> pendingRowValues = new ArrayList<>();

                    for (int i = 0; i < tableColumns.size(); i++) {
                        if (table.getValueAt(rowID, i) == null) {
                            pendingRowValues.add("");
                        } else {
                            pendingRowValues.add(table.getValueAt(rowID, i).toString());
                        }
                    }

                    pendingDeletedRows.add(pendingRowValues);
                }
                
                if (selectedRows.length > 0) {
                    for (int i = selectedRows.length - 1; i >= 0; i--) {
                        model.removeRow(selectedRows[i]);
                    }

                    submitChangesButton.setEnabled(true);
                }

            }
        });

        submitChangesButton.setEnabled(false);
        submitChangesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (pendingDeletedRows.size() >= 1) {
                    // Open the warning pop up window if there are pending deletions
                    Customers_DeletePopUpWindow popUp = new Customers_DeletePopUpWindow();
                    Main.app.DASHBOARD_FRAME.setEnabled(false);
                    popUp.setVisible(true);
                }
            }
            
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                refreshUpdate();
            }
        });
    } 

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, 0, 0, 0);
        add(headerPanel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(InsetsConfig.XL, 0, 0, 0);
        add(buttonsPanel, gbc);

        if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_SUPERADMIN) {
            buttonsPanel.setVisible(true);
        } else if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_ADMIN) {
            buttonsPanel.setVisible(true);
        } else {
            buttonsPanel.setVisible(false);
        }

        {
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.BOTH;

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

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 3;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            buttonsPanel.add(refreshButton, gbc);
        }

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(tablePanel, gbc);

        {
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.BOTH;

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            tablePanel.add(scrollPane, gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.S, 0, 0);
            tablePanel.add(detailsPanel, gbc);
        }

        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.M, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(footerPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
            footerPanel.add(footerButtonsPanel, gbc);

            if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_SUPERADMIN) {
                footerButtonsPanel.setVisible(true);
            } else if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_ADMIN) {
                footerButtonsPanel.setVisible(true);
            } else {
                footerButtonsPanel.setVisible(false);
            }

            {
                gbc.anchor = GridBagConstraints.EAST;
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
                footerButtonsPanel.add(submitChangesButton, gbc);
            }
        }
    }

    public void finalizePrepare() {}

    @SuppressWarnings("rawtypes")
    public void refreshUpdate() {
        Main.app.DASHBOARD_FRAME.setEnabled(true);
        loadFromDatabase();

        // Loop through the pendingDeletedUsernames and remove the
        // rows which are in the purgatory of deleted accounts
        for (ArrayList<String> row : pendingDeletedRows) {
            Vector<Vector> tableData = model.getDataVector();

            for (int i = 0; i < tableData.size(); i++) {
                Vector tableRow = tableData.get(i);
                if (tableRow.get(0).equals(row.get(0))) {
                    model.removeRow(i);
                }
            }
        }
    }

    protected void loadFromDatabase() {
        try {
            // Always clear the rows when beginning to "reload" the table
            model.setRowCount(0);

            // Establish the SQL connection first
            SQLConnector.establishConnection();
    
            // Query setup
            String query = "SELECT * FROM " + DBReferences.TBL_CUSTOMERS;
            Statement statement = SQLConnector.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            ResultSet result = statement.executeQuery(query);
    
            // Get the total number of rows
            final int size = SQLUtils.getResultSetRowCount(result);
    
            if (size != 0) {
                // Then go back to the starting point before looping
                result.beforeFirst();

                // Then begin loop
                int i = 0;
                while (i < size) {
                    result.next();

                    final int retrievedCustomerNo           = result.getInt("id");
                    final int retrievedRfidNo               = result.getInt("rfid_no");
                    final int retrievedStudentNo            = result.getInt("student_no");
                    final String retrievedCustomerName      = result.getString("customer_name");
                    final float retrievedAmountDeposited    = Float.parseFloat(result.getString("amount_deposited"));
                    final int retrievedActivated            = result.getInt("activated");

                    final String customerNo       = String.format("%06d", retrievedCustomerNo);
                    final String rfidNo;
                    final String studentNo        = String.valueOf(retrievedStudentNo);
                    final String customerName     = retrievedCustomerName;
                    final String amountDeposited  = String.valueOf(retrievedAmountDeposited);
                    final String activated;

                    if (retrievedActivated == 1) {
                        activated = "Yes";
                    } else {
                        activated = "No";
                    }

                    if (retrievedRfidNo == 0) {
                        rfidNo = "";
                    } else {
                        rfidNo = String.valueOf(retrievedRfidNo);
                    }

                    final String[] rowData = {customerNo, rfidNo, studentNo, customerName, amountDeposited, activated};

                    model.addRow(rowData);
                    i++;
                }    
            }

            SQLConnector.connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void purgatoryPardon() {
        pendingDeletedRows.clear();
        refreshUpdate();
    }

    protected void purgatoryPurge() {
        // Performing the delete (SQL-Java connection)
        final int pendingSize = pendingDeletedRows.size();

        try {
            String query;
            SQLConnector.establishConnection();

            query = "DELETE FROM " + DBReferences.TBL_CUSTOMERS;

            if (pendingSize == 1) {
                // If only 1 row is selected, use the equal operator for SQL query
                query = query + " WHERE id = \"" + pendingDeletedRows.get(0).get(0) + "\";";
            } else {
                // If more than one row is selected, use the IN keyword for SQL query
                query = query + " WHERE id IN ( ";

                // Get all usernameIDs
                for (ArrayList<String> id : pendingDeletedRows) {
                    query = query + "\"" + id.get(0) + "\", ";
                }

                query = query.substring(0, query.length()-2) + " );";
            }

            Statement statement = SQLConnector.connection.createStatement();
            statement.executeUpdate(query);

            // SQLConnector.connection.close();

            refreshUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(deleteButton),
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        pendingDeletedRows.clear();
    }
}

final class Customers_AddPopupWindow extends APP_PopUpFrame<WF_Dashboard> implements RfidReceivable {

    public final JLabel header = new JLabel("Register customer");
    public final JPanel form = new JPanel(new GridBagLayout());

    public final JLabel rfidNoLabel        = new JLabel("RFID No:");
    public final JLabel studentNoLabel     = new JLabel("Student No:");
    public final JLabel customerNameLabel  = new JLabel("<html><b>*</b>Customer name:</html>");
    public final JLabel amountDepositLabel = new JLabel("<html><b>*</b>Initial amount deposit:</html>");
    public final JLabel activatedLabel      = new JLabel("Activated");

    public final APP_TextField rfidNoField                  = new APP_TextField(10);
    public final APP_TextField studentNoField               = new APP_TextField(10);
    public final APP_TextField customerNameField            = new APP_TextField(10);
    public final APP_LabeledTextField amountDepositedField    = new APP_LabeledTextField("Php", 10);
    public final JCheckBox activatedCheckBox                = new JCheckBox("", false);

    public final JLabel activationHeader = new JLabel("Activation");
    public final JTextArea infoArea1 = new JTextArea(
        "Please register the customer's RFID number by scanning the"
        + " RFID card. A customer account can still be registered but it will"
        + " be inactive and unusable without a registered RFID number."
    );
    public final APP_AccentButton registerRfidButton = new APP_AccentButton("Register RFID");
    public final JLabel infoLabel1 = new JLabel("<html><i>*Required fields</i></html>");
    public final JButton submitButton = new APP_ContrastButton("Submit");

    public final JTextField[] requiredFields = {
        customerNameField,
        amountDepositedField.getTextField()
    };

    public final DocumentListener rfidFieldListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changed();
        }

        private void changed() {
            if (rfidNoField.getText().equals("")) {
                activatedCheckBox.setEnabled(false);
                activatedCheckBox.setSelected(false);
            } else {
                activatedCheckBox.setEnabled(true);
            }
        }
        
    };

    public Customers_AddPopupWindow() {
        super(Main.app.DASHBOARD_FRAME, "Register Customer");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(submitButton, requiredFields);

        if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_SUPERADMIN) {
            rfidNoField.setEnabled(true);
        } else {
            rfidNoField.setEnabled(false);
        }

        header.setFont(StylesConfig.HEADING3);
        form.setOpaque(false);
        rfidNoField.getDocument().addDocumentListener(rfidFieldListener);
        amountDepositedField.setText("50");
        activatedCheckBox.setEnabled(false);
        activatedCheckBox.setOpaque(false);
        activationHeader.setFont(StylesConfig.LEAD);
        infoArea1.setEditable(false);
        infoArea1.setFocusable(false);
        infoArea1.setFont(StylesConfig.DETAIL);
        infoArea1.setHighlighter(null);
        infoArea1.setLineWrap(true);
        infoArea1.setOpaque(false);
        infoArea1.setWrapStyleWord(true);
        infoLabel1.setFont(StylesConfig.DETAIL);
        registerRfidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers_AddPopupWindow parentFrame = (Customers_AddPopupWindow) SwingUtilities.getWindowAncestor(registerRfidButton);
                RFIDPopUp<Customers_AddPopupWindow> popUp = new RFIDPopUp<Customers_AddPopupWindow>(parentFrame);
                popUp.setVisible(true);
            }
        });
        submitButton.setEnabled(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve inputs
                final String retrievedRFIDNo        = rfidNoField.getText();
                final String retrievedStudentNo     = studentNoField.getText();
                final String retrievedCustomerName  = customerNameField.getText();
                final float retrievedAmountDeposit  = Float.parseFloat(amountDepositedField.getText());
                final int retrievedActivated;

                final String inferredRFIDNo;
                final String inferredStudentNo;

                if (retrievedRFIDNo == null || retrievedRFIDNo.isBlank() || retrievedRFIDNo.isEmpty()) {
                    inferredRFIDNo = null;
                } else {
                    inferredRFIDNo = retrievedRFIDNo;
                }

                if (retrievedStudentNo == null || retrievedStudentNo.isBlank() || retrievedStudentNo.isEmpty()) {
                    inferredStudentNo = "0";
                } else {
                    inferredStudentNo = retrievedStudentNo;
                }

                if (activatedCheckBox.isSelected()) {
                    retrievedActivated = 1;
                } else {
                    retrievedActivated = 0;
                }

                // Variables reserved for query
                String queryRfidNo         = inferredRFIDNo;
                String queryStudentNo      = inferredStudentNo;
                String queryCustomerName    = retrievedCustomerName;
                String queryAmountDeposit  = String.valueOf(retrievedAmountDeposit);
                String queryActivated       = String.valueOf(retrievedActivated);

                try {
                    SQLConnector.establishConnection();

                    /**
                     * The inferred number of existing accounts
                     */
                    final int existingAccounts;

                    if (queryRfidNo != "0") {
                        ResultSet result = Queries.getExistingCustomersByID(queryRfidNo);
                        existingAccounts = result.getFetchSize();
                    } else {
                        existingAccounts = 0;
                    }

                    if (existingAccounts == 0) {
                        Queries.registerCustomer(
                            queryRfidNo,
                            queryStudentNo,
                            queryCustomerName,
                            queryAmountDeposit,
                            queryActivated
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            SwingUtilities.getWindowAncestor(submitButton),
                            "An existing customer is already registered",
                            "Error",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    SQLConnector.connection.close();

                    Window popUp = SwingUtilities.getWindowAncestor(submitButton);
                    popUp.dispose();

                    Main.app.CUSTOMERS_VIEW.TABLE_PANEL.refreshUpdate();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
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
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            form.add(rfidNoLabel, gbc);
            
            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(studentNoLabel, gbc);
            
            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(customerNameLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(amountDepositLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(activatedLabel, gbc);
            
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.L, 0, 0);
            form.add(rfidNoField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(studentNoField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(customerNameField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(amountDepositedField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(activatedCheckBox, gbc);
        }

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(infoLabel1, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = GridBagConstraints.RELATIVE;
        add(activationHeader, gbc);
        
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(infoArea1, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        add(registerRfidButton, gbc);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(submitButton, gbc);
    }

    public void finalizePrepare() {
        prepackTextAreas();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void prepackTextAreas() {
        pack();
        infoArea1.setSize(infoArea1.getSize());
    }

    @Override
    public void setRfidNo(long rfidNo) {
        rfidNoField.setText(String.valueOf(rfidNo));
        activatedCheckBox.setSelected(true);
    }
}

class Customers_EditPopupWindow extends APP_PopUpFrame<WF_Dashboard> implements RfidReceivable {

    public final JLabel header = new JLabel("Edit customer details");
    public final JPanel form = new JPanel(new GridBagLayout());

    public final JLabel customerIDLabel         = new JLabel("Customer ID");
    public final JLabel rfidNoLabel             = new JLabel("RFID No.");
    public final JLabel studentNoLabel          = new JLabel("Student No.");
    public final JLabel customerNameLabel       = new JLabel("Customer Name");
    public final JLabel amountDepositedLabel    = new JLabel("Remaining deposit");
    public final JLabel activatedLabel          = new JLabel("Activated");
    
    public final APP_TextField customerIDField              = new APP_TextField(10);
    public final APP_TextField rfidNoField                  = new APP_TextField(10);
    public final APP_TextField studentNoField               = new APP_TextField(10);
    public final APP_TextField customerNameField            = new APP_TextField(10);
    public final APP_LabeledTextField amountDepositedField  = new APP_LabeledTextField("Php", 10);
    public final JCheckBox activatedCheckBox                = new JCheckBox();

    public final JLabel activationHeader = new JLabel("Activation");
    public final JTextArea infoArea1 = new JTextArea(
        "Please register the customer's RFID number by scanning the"
        + " RFID card. While this customer account is registered, it is"
        + " currently inactive and unusable without a registered RFID number."
    );
    public final APP_AccentButton registerRfidButton = new APP_AccentButton("Register RFID");
    public final JLabel infoLabel1 = new JLabel("<html><i>*Required fields</i></html>");
    public final JButton updateButton = new APP_ContrastButton("Submit");

    public final JTextField[] requiredFields = {
        customerNameField,
        amountDepositedField.getTextField()
    };

    public final DocumentListener rfidFieldListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            changed();
        }

        private void changed() {
            if (rfidNoField.getText().equals("")) {
                activatedCheckBox.setEnabled(false);
                activatedCheckBox.setSelected(false);
            } else {
                activatedCheckBox.setEnabled(true);
            }
        }
        
    };


    public Customers_EditPopupWindow() {
        super(Main.app.DASHBOARD_FRAME, "Edit Customer details");
        compile();
    }

    @Override
    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    @Override
    public void prepareComponents() {
        GUIHelpers.setButtonTriggerOnAllFields(updateButton, requiredFields);
        
        if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_SUPERADMIN) {
            rfidNoField.setEnabled(true);
        } else {
            rfidNoField.setEnabled(false);
        }

        header.setFont(StylesConfig.HEADING3);
        form.setOpaque(false);
        customerIDField.setEnabled(false);
        rfidNoField.getDocument().addDocumentListener(rfidFieldListener);
        amountDepositedField.setText("100");
        activatedCheckBox.setEnabled(false);
        activatedCheckBox.setOpaque(false);
        activationHeader.setFont(StylesConfig.LEAD);
        infoArea1.setEditable(false);
        infoArea1.setFocusable(false);
        infoArea1.setFont(StylesConfig.DETAIL);
        infoArea1.setHighlighter(null);
        infoArea1.setLineWrap(true);
        infoArea1.setOpaque(false);
        infoArea1.setWrapStyleWord(true);
        infoLabel1.setFont(StylesConfig.DETAIL);
        registerRfidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers_EditPopupWindow parentFrame = (Customers_EditPopupWindow) SwingUtilities.getWindowAncestor(registerRfidButton);
                RFIDPopUp<Customers_EditPopupWindow> popUp = new RFIDPopUp<Customers_EditPopupWindow>(parentFrame);
                popUp.setVisible(true);
            }
        });
        updateButton.setEnabled(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve inputs
                final String retrievedCustomerID    = customerIDField.getText();
                final String retrievedRFIDNo        = rfidNoField.getText();
                final String retrievedStudentNo     = studentNoField.getText();
                final String retrievedCustomerName  = customerNameField.getText();
                final float retrievedAmountDeposit  = Float.parseFloat(amountDepositedField.getText());
                final int retrievedActivated;

                final String inferredRFIDNo;
                final String inferredStudentNo;

                if (retrievedRFIDNo == null || retrievedRFIDNo.isBlank() || retrievedRFIDNo.isEmpty()) {
                    inferredRFIDNo = null;
                } else {
                    inferredRFIDNo = retrievedRFIDNo;
                }

                if (retrievedStudentNo == null || retrievedStudentNo.isBlank() || retrievedStudentNo.isEmpty()) {
                    inferredStudentNo = "0";
                } else {
                    inferredStudentNo = retrievedStudentNo;
                }

                if (activatedCheckBox.isSelected()) {
                    retrievedActivated = 1;
                } else {
                    retrievedActivated = 0;
                }

                // Variables reserved for query
                String queryCustomerID      = retrievedCustomerID;
                String queryRfidNo         = inferredRFIDNo;
                String queryStudentNo      = inferredStudentNo;
                String queryCustomerName    = retrievedCustomerName;
                String queryAmountDeposited  = String.valueOf(retrievedAmountDeposit);
                String queryActivated       = String.valueOf(retrievedActivated);

                try {
                    SQLConnector.establishConnection();
                    ResultSet result = Queries.getExistingCustomersByID(queryCustomerID);

                    if (result.getFetchSize() == 0) {
                        result.next();
                        
                        int id = result.getInt("id");

                        Queries.updateCustomer(
                            id,
                            queryRfidNo,
                            queryStudentNo,
                            queryCustomerName,
                            queryAmountDeposited,
                            queryActivated
                        );
                    }

                    SQLConnector.connection.close();

                    Window popUp = SwingUtilities.getWindowAncestor(updateButton);
                    popUp.dispose();

                    Main.app.CUSTOMERS_VIEW.TABLE_PANEL.refreshUpdate();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @Override
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
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
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, 0, 0, 0);
            form.add(customerIDLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(rfidNoLabel, gbc);
            
            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(studentNoLabel, gbc);
            
            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(customerNameLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(amountDepositedLabel, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(activatedLabel, gbc);
            
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.L, 0, 0);
            form.add(customerIDField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(rfidNoField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(studentNoField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(customerNameField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(amountDepositedField, gbc);

            gbc.gridy = GridBagConstraints.RELATIVE;
            form.add(activatedCheckBox, gbc);
        }

        String rfidNo = Main.app.CUSTOMERS_VIEW.TABLE_PANEL.table.getValueAt(Main.app.CUSTOMERS_VIEW.TABLE_PANEL.table.getSelectedRow(), 1).toString();

        if (rfidNo == null || rfidNo.isEmpty() || rfidNo.isBlank()) {
            gbc.anchor = GridBagConstraints.EAST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(InsetsConfig.S, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            gbc.weightx = 1;
            gbc.weighty = 1;
            add(infoLabel1, gbc);
            
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.insets = new Insets(InsetsConfig.XL, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = GridBagConstraints.RELATIVE;
            add(activationHeader, gbc);
            
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            add(infoArea1, gbc);
            
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.XXL, 0, InsetsConfig.XXL);
            add(registerRfidButton, gbc);
        }
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(updateButton, gbc);
    }

    @Override
    public void finalizePrepare() {
        prepackTextAreas();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    
    private void prepackTextAreas() {
        pack();
        infoArea1.setSize(infoArea1.getSize());
    }

    @Override
    public void setRfidNo(long rfidNo) {
        rfidNoField.setText(String.valueOf(rfidNo));
        activatedCheckBox.setSelected(true);
    }
}

class Customers_DeletePopUpWindow extends APP_PopUpFrame<WF_Dashboard> {

    public static final String[] columnNames = {"rfid_no", "student_no", "customer_name", "amount_deposited"};
    
    public boolean isExiting = false;

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

    public Customers_DeletePopUpWindow() {
        super(Main.app.DASHBOARD_FRAME, "Pending Deletion Warning");
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

        for (ArrayList<String> row : WP_CustomersTable.pendingDeletedRows) {
            tableModel.addRow(new Vector<>(row));
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Main.app.CUSTOMERS_VIEW.TABLE_PANEL.purgatoryPardon();
                Main.app.CUSTOMERS_VIEW.TABLE_PANEL.submitChangesButton.setEnabled(false);
                
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(continueButton);
                source.dispose();
            }
        });
        
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Main.app.CUSTOMERS_VIEW.TABLE_PANEL.purgatoryPurge();
                Main.app.CUSTOMERS_VIEW.TABLE_PANEL.submitChangesButton.setEnabled(false);
                
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(continueButton);
                source.dispose();
            }
        });
    }

    public void addComponents() {
        final GridBagConstraints gbc = new GridBagConstraints();

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