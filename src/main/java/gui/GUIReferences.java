package main.java.gui;

import java.util.LinkedHashMap;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.userAccountSystem.LoginManager;

public final class GUIReferences {

    /**
     * The hashmap of the dashboard's different view panels which is
     * used to procedurally generate their respective buttons in the
     * side ribbon.
     * <p>
     * Use this as a reference.
     */
    public static final LinkedHashMap<String, APP_Panel> VIEW_PANELS = new LinkedHashMap<>();

    /**
     * The view panel that is initially displayed when the dashboard is
     * first opened.
     */
    public static JPanel DEFAULT_VIEW;

    public static final void addViewPanels() {
        Main.app.defineViewPanels();
        Main.app.defineDefaultViewPanel();
        
        if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_SUPERADMIN) {
            VIEW_PANELS.put("Purchase", Main.app.PURCHASE_VIEW);
            VIEW_PANELS.put("Inventory", Main.app.INVENTORY_VIEW);
            VIEW_PANELS.put("Customers", Main.app.CUSTOMERS_VIEW);
        } else if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_ADMIN) {
            VIEW_PANELS.put("Purchase", Main.app.PURCHASE_VIEW);
            VIEW_PANELS.put("Inventory", Main.app.INVENTORY_VIEW);
            VIEW_PANELS.put("Customers", Main.app.CUSTOMERS_VIEW);
        } else if (LoginManager.getCurrentAccessLevelMode() == LoginManager.ACCESS_LEVEL_USER) {
            VIEW_PANELS.put("Purchase", Main.app.PURCHASE_VIEW);
            VIEW_PANELS.put("Inventory", Main.app.INVENTORY_VIEW);
            VIEW_PANELS.put("Customers", Main.app.CUSTOMERS_VIEW);
        }
    }
}
