package main.java.gui;

import java.util.HashMap;

import main.java.Main;
import main.java.components.APP_Panel;
import main.java.gui.windowScreens.WS_Inventory;

public final class GUIReferences {

    public static HashMap<String, APP_Panel> PANELS = new HashMap<>();
    
    static {
        Main.app.INVENTORY = new WS_Inventory();

        PANELS.put("Inventory", Main.app.INVENTORY);
    }
}
