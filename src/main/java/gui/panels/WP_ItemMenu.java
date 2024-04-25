package main.java.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import main.java.components.APP_ItemButton;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;

public class WP_ItemMenu extends APP_Panel {

    public WP_ItemMenu() {
        super(new GridLayout(2, 2, InsetsConfig.L, InsetsConfig.L));
        compile();
    }
    
    public void prepare() {
        setBackground(ColorConfig.BG);
        generateItemButtons();
    }

    public void prepareComponents() {}

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.L, InsetsConfig.L, InsetsConfig.L);
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(menuItem1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(menuItem2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(menuItem3, gbc);
    }

    public void finalizePrepare() {}

    private void generateItemButtons() {
        // Get data from SQL db first
        try {
            SQLConnector.establishSQLConnection();
            
            Statement statement = SQLConnector.connection.createStatement();
            String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY + ";";
            
            // Start reading results
            ResultSet result = statement.executeQuery(query);
            
            /**The unique category names identified from the data. */
            ArrayList<String> categoryNames = new ArrayList<>();

            LinkedHashMap<String, JPanel> categoryPanels = new LinkedHashMap<>();
            LinkedHashMap<String, Integer> categoryPanelsGridx = new LinkedHashMap<>();
            
            for (int i = 0; i < result.getFetchSize(); i++) {
                result.next();
                final String currentCategory = result.getString("category");

                if (!categoryNames.contains(currentCategory)) {
                    // Add the read category name if it's not in categoryNames yet
                    categoryNames.add(currentCategory);

                    // Also create a specific JPanel for this category panel
                    // This is where we add the items later
                    JPanel categoryPanel = new JPanel(new GridBagLayout());

                    // Generate the initial design for the category JPanel
                    JLabel categoryLabel = new JLabel(currentCategory);
                    categoryLabel.setFont(StylesConfig.HEADING2);

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.weightx = 1;

                    categoryPanel.add(categoryLabel, gbc);
                    
                    // Add the decorated JPanel to the map of category panels
                    // and the hashmap of the gridx formattings
                    categoryPanels.put(currentCategory, categoryPanel);
                    categoryPanelsGridx.put(currentCategory, 0);
                }



            }
        } catch (SQLException e) {
            // TODO: handle exception
        }
    }
}
