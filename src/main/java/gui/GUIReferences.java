package main.java.gui;

import java.util.LinkedHashMap;
import javax.swing.JPanel;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.gui.dashboardViews.DV_Inventory;
import main.java.gui.dashboardViews.DV_PurchaseView;

public final class GUIReferences {

    public static final LinkedHashMap<String, APP_Panel> PANELS = new LinkedHashMap<>();

    static {
        Main.app.PURCHASE_VIEW  = new DV_PurchaseView();
        Main.app.INVENTORY      = new DV_Inventory();
        // Main.app.CUSTOMERS      = new DV_Customers();
        
        PANELS.put("Purchase", Main.app.PURCHASE_VIEW);
        PANELS.put("Inventory", Main.app.INVENTORY);
        // PANELS.put("Customers", Main.app.CUSTOMERS);
    }

    public static final JPanel DEFAULT_VIEW = Main.app.PURCHASE_VIEW;
}
