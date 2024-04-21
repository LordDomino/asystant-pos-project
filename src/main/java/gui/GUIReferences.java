package main.java.gui;

import java.util.HashMap;

import main.java.components.APP_Panel;
import main.java.gui.windowScreens.WS_Inventory;

public final class GUIReferences {
    public static HashMap<String, APP_Panel> PANELS = new HashMap<>();
    
    static {
        PANELS.put("Inventory", new WS_Inventory());
    }
}
