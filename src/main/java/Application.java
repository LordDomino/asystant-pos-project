package main.java;

import javax.swing.JFrame;

import main.java.configs.APPResourceLoader;
import main.java.configs.StylesConfig;
import main.java.gui.dashboardViews.DV_Customers;
import main.java.gui.dashboardViews.DV_Inventory;
import main.java.gui.frames.WF_Dashboard;
import main.java.gui.frames.WF_LoginWindow;
import main.java.gui.frames.WF_SuperAdminScreen;
import main.java.gui.frames.WF_UserManager;

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
    public DV_Inventory INVENTORY;

    public DV_Customers CUSTOMERS;

    public void run() {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts

        LOGIN_WINDOW = new WF_LoginWindow();
        DASHBOARD = new WF_Dashboard();
        DASHBOARD.initializeSideRibbon();

        LOGIN_WINDOW.setVisible(true);
        LOGIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
