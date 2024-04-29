package main.java.gui.panels;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import main.java.Main;
import main.java.components.APP_ItemButton;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;
import main.java.sql.DBReferences;
import main.java.sql.SQLConnector;

public class WP_ItemMenu extends APP_Panel {

    /**
     * Internal reference for accessing the checkout panel class,
     * WP_CheckoutPanel
     */
    private final WP_CheckoutPanel CHECKOUT = Main.app.PURCHASE_VIEW.CHECKOUT;

    public WP_ItemMenu() {
        super(new GridLayout(0, 2, InsetsConfig.XXL, InsetsConfig.XXL));
        compile();
    }
    
    public void prepare() {
        setBackground(ColorConfig.BG);
    }

    public void prepareComponents() {}

    public void addComponents() {}

    public void finalizePrepare() {
        generateCategoryPanels();
    }

    public void generateCategoryPanels() {
        // Get data from SQL db first
        try {
            SQLConnector.establishSQLConnection();
            
            final Statement statement = SQLConnector.connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            String query = "SELECT * FROM " + DBReferences.TBL_STOCKS_INVENTORY + ";";
            
            // Start reading results
            final ResultSet result = statement.executeQuery(query);
            
            // The unique category names identified from the data
            final ArrayList<String> categoryNames = new ArrayList<>();

            // Linked hash map of category panels and their respective
            // gridx for layout
            final LinkedHashMap<String, JPanel> categoryPanels = new LinkedHashMap<>();
            final LinkedHashMap<String, JPanel> contentPanels = new LinkedHashMap<>();
            final LinkedHashMap<String, Integer> categoryPanelsGridx = new LinkedHashMap<>();
            final LinkedHashMap<String, Integer> categoryPanelsGridy = new LinkedHashMap<>();

            // Go to last row to get the total number of rows
            result.last();
            final int resultRowCount = result.getRow();

            result.beforeFirst();

            // Begin reading each product from the result set
            for (int i = 0; i < resultRowCount; i++) {
                result.next();
                final String currentCategory = result.getString("category");

                final JPanel currentCategoryPanel;
                final JPanel contentPanel;
                
                int currentGridx;
                int currentGridy;

                // Test condition to know if currentCategory is first encountered
                if (!categoryNames.contains(currentCategory)) {
                    // Add the detected category name if it's not in categoryNames yet
                    categoryNames.add(currentCategory);

                    // Also create a specific JPanel for this category panel
                    // This is where we add the item buttons later
                    currentCategoryPanel = new JPanel(new GridBagLayout());
                    contentPanel = new JPanel(new GridLayout(0, 3, InsetsConfig.M, InsetsConfig.M));

                    // Generate the initial design for the category JPanel
                    {
                        final JLabel categoryLabel = new JLabel(currentCategory);
                        categoryLabel.setFont(StylesConfig.HEADING2);
                        
                        final GridBagConstraints gbc = new GridBagConstraints();
                        gbc.anchor = GridBagConstraints.WEST;
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.weightx = 1;
                        
                        // The category panel needs a label
                        currentCategoryPanel.add(categoryLabel, gbc);
                        
                        gbc.gridy = 1;
                        gbc.insets = new Insets(InsetsConfig.M, 0, 0, 0);
                        currentCategoryPanel.add(contentPanel, gbc);
                    }
                    
                    currentGridx = 0;
                    currentGridy = 1;
                    
                    // Add the decorated JPanel to the map of category panels
                    // and the initial gridx to the map of gridx formatting
                    categoryPanels.put(currentCategory, currentCategoryPanel);
                    contentPanels.put(currentCategory, contentPanel);
                    categoryPanelsGridx.put(currentCategory, currentGridx);
                    categoryPanelsGridy.put(currentCategory, currentGridy);
                } else {
                    currentCategoryPanel = categoryPanels.get(currentCategory);
                    contentPanel = contentPanels.get(currentCategory);
                    currentGridx = categoryPanelsGridx.get(currentCategory);
                    currentGridy = categoryPanelsGridy.get(currentCategory);
                }

                {
                    // Generate an item button for the current row
                    final APP_ItemButton itemButton = new APP_ItemButton(
                        result.getString("product_code"),
                        result.getString("name"), // "name" instead of "item_name" because we're referencing from stocks inventory
                        result.getFloat("unit_price")
                    );
                    
                    // Action listener
                    itemButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            CHECKOUT.addItemToCheckout(itemButton);
                            CHECKOUT.tableModel.setRowCount(0);
                            CHECKOUT.addAllCurrentItems();

                            // Always update total price label
                            CHECKOUT.totalAmountJLabel.setText(
                                "Php" + String.valueOf(Main.app.PURCHASE_VIEW.CHECKOUT.recomputeTotalPrice())
                            );
                        }
                    });

                    // Layouting
                    final GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.NORTHWEST;
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.gridwidth = 1;
                    gbc.gridx = currentGridx;
                    gbc.gridy = currentGridy;
                    gbc.insets = new Insets(InsetsConfig.L, InsetsConfig.L, InsetsConfig.L, InsetsConfig.L);
                    gbc.weightx = 0;
                    gbc.weighty = 0;

                    contentPanel.add(itemButton);

                    // The gridx and gridy formatting should be incremented after adding the item button
                    // But we need to check if the gridx is 3
                    if (categoryPanelsGridx.get(currentCategory) >= 3) {
                        // If gridx is 3, meaning the position of the item button is fourth in the row,
                        // reset it back to gridx = 0
                        categoryPanelsGridx.put(currentCategory, 0);    
                        categoryPanelsGridy.put(currentCategory, categoryPanelsGridy.get(currentCategory) + 1);
                    } else {
                        categoryPanelsGridx.put(currentCategory, categoryPanelsGridx.get(currentCategory) + 1);
                    }
                }
            }

            for (int i = 0; i < categoryPanels.size(); i++) {
                JPanel categoryPanel = categoryPanels.get(categoryNames.get(i));
                JPanel contentPanel = contentPanels.get(categoryNames.get(i));
                categoryPanel.setOpaque(false);
                contentPanel.setOpaque(false);
                add(categoryPanel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
