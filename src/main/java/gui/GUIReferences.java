package main.java.gui;

import java.util.LinkedHashMap;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.gui.dashboardViews.DV_Customers;
import main.java.gui.dashboardViews.DV_Inventory;

public final class GUIReferences {

    public static LinkedHashMap<String, APP_Panel> PANELS = new LinkedHashMap<>();
    
    static {
        Main.app.INVENTORY = new DV_Inventory();
        Main.app.CUSTOMERS = new DV_Customers();

        PANELS.put("Inventory", Main.app.INVENTORY);
        PANELS.put("Customers", Main.app.CUSTOMERS);
    }
}
