package gui;

import java.util.HashMap;

import components.APP_Panel;
import gui.windowScreens.WS_Inventory;

public final class GUIReferences {
    public static HashMap<String, APP_Panel> PANELS;

    static {
        PANELS.put("Inventory", new WS_Inventory());
    }
}
