package main.java.gui;

import java.util.LinkedHashMap;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.gui.dashboardViews.DV_Customers;
import main.java.gui.dashboardViews.DV_Inventory;
import main.java.gui.dashboardViews.DV_PurchaseView;

public final class GUIReferences {

    /**
     * The hashmap of the dashboard's different side panels which will
     * be used to procedurally generate the side ribbon.
     * <p>
     * Use this as a reference.
     */
    public static final LinkedHashMap<String, APP_Panel> VIEW_PANELS = new LinkedHashMap<>();

    static {
        Main.app.PURCHASE_VIEW      = new DV_PurchaseView();
        Main.app.INVENTORY_VIEW     = new DV_Inventory();
        Main.app.CUSTOMERS_VIEW     = new DV_Customers();
        
        VIEW_PANELS.put("Purchase", Main.app.PURCHASE_VIEW);
        VIEW_PANELS.put("Inventory", Main.app.INVENTORY_VIEW);
        VIEW_PANELS.put("Customers", Main.app.CUSTOMERS_VIEW);
    }

    public static final JPanel DEFAULT_VIEW = Main.app.PURCHASE_VIEW;
}
