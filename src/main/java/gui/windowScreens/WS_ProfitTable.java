package main.java.gui.windowScreens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;

public class WS_ProfitTable extends APP_Panel {

    public static final String[] columnNames = {"Username", "Password", "Access Level", "Activation Status"};

    protected final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Table
    public final JTable table = new JTable(tableModel);

    // Layout components
    public final JPanel headerPanel = new JPanel(new GridBagLayout());
    public final JScrollPane tableScrollPane = new JScrollPane(table);
    public final JPanel footerPanel = new JPanel(new GridBagLayout());

    public final JPanel buttonsPanel = new JPanel(new GridBagLayout());

    // Components
    public final JLabel header = new JLabel("Manage user accounts");
    public final JLabel guide1 = new JLabel(
        "<html>Information here..."
    );
    public final JLabel buttonsPanelLead = new JLabel("Operations");

    // Buttons
    public final JButton editButton = new JButton("Edit");
    public final JButton deleteButton = new JButton("Delete");
    
    public WS_ProfitTable() {
        super();
        compile();
    }

    public void prepare() {
        setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {
        // Update the table
        updateTable();

        headerPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);
        footerPanel.setOpaque(false);

        header.setFont(StylesConfig.HEADING1);
        buttonsPanelLead.setFont(StylesConfig.LEAD);

        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
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

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.XXL, 0, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(tableScrollPane, gbc);

        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(InsetsConfig.M, InsetsConfig.M, InsetsConfig.XXL, InsetsConfig.XXL);
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(footerPanel, gbc);
    }

    public void finalizePrepare() {}

    public void updateTable() {
        loadFromDatabase();
    }

    public void loadFromDatabase() {
        // Clear the rows
        tableModel.setRowCount(0);

        try {
            SQLConnector.establishSQLConnection();

            String query = "SELECT * FROM " + DBReferences.TBL_PROFIT_TABLE + " WHERE username NOT LIKE \"%SUPERADMIN%\"";
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
