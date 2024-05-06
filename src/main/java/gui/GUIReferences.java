package main.java.gui;

import java.util.LinkedHashMap;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.gui.dashboardViews.DV_Customers;
import main.java.gui.dashboardViews.DV_Inventory;
import main.java.gui.dashboardViews.DV_PurchaseView;
import main.java.userAccountSystem.LoginManager;

public final class GUIReferences {

    /**
     * The hashmap of the dashboard's different side panels which will
     * be used to procedurally generate the side ribbon.
     * <p>
     * Use this as a reference.
     */
    public static final LinkedHashMap<String, APP_Panel> VIEW_PANELS = new LinkedHashMap<>();

    static {

    }

    public static JPanel DEFAULT_VIEW = Main.app.PURCHASE_VIEW;

    public static final void addViewPanels() {
        Main.app.PURCHASE_VIEW      = new DV_PurchaseView();
        Main.app.INVENTORY_VIEW     = new DV_Inventory();
        Main.app.CUSTOMERS_VIEW     = new DV_Customers();

        DEFAULT_VIEW = Main.app.PURCHASE_VIEW;
        
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
