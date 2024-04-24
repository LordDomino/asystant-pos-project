package main.java;

import javax.swing.JFrame;

import main.java.configs.APPResourceLoader;
import main.java.configs.StylesConfig;
import main.java.gui.windowFrames.WF_Dashboard;
import main.java.gui.windowFrames.WF_LoginWindow;
import main.java.gui.windowFrames.WF_SuperAdminScreen;
import main.java.gui.windowFrames.WF_UserManager;
import main.java.gui.windowScreens.WS_Inventory;

public class Application {

    /**The login screen which the application opens first during run. */
    public WF_LoginWindow LOGIN_WINDOW;

    /**The dashboard instance of the app. */
    public WF_Dashboard DASHBOARD;

    /**The super admin screen. */
    public WF_SuperAdminScreen SUPERADMIN_SCREEN;

    /**The user manager window. */
    public WF_UserManager USER_MANAGER;

    /**The inventory view of the application */
    public WS_Inventory INVENTORY;

    public void run() {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts

        LOGIN_WINDOW = new WF_LoginWindow();
        DASHBOARD = new WF_Dashboard();

        LOGIN_WINDOW.setVisible(true);
        LOGIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
