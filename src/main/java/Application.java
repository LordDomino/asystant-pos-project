package main.java;

import javax.swing.JFrame;

import main.java.configs.APPResourceLoader;
import main.java.configs.StylesConfig;
import main.java.gui.GUIReferences;
import main.java.gui.dashboardViews.DV_Customers;
import main.java.gui.dashboardViews.DV_Inventory;
import main.java.gui.dashboardViews.DV_PurchaseView;
import main.java.gui.frames.WF_Dashboard;
import main.java.gui.frames.WF_LoginWindow;
import main.java.gui.frames.WF_SuperAdminScreen;
import main.java.gui.frames.WF_UserManager;

public class Application {

    /**
     * Singleton reference for this application's login screen JFrame
     * via the respective {@code WF_LoginWindow} instance.
     */
    public WF_LoginWindow LOGIN_WINDOW;

    /**
     * Singleton reference for this application's dashboard JFrame via
     * the respective {@code WF_Dashboard} instance.
     */
    public WF_Dashboard DASHBOARD_FRAME;

    /**
     * Singleton reference for this application's super administrator
     * screen JFrame via the respective {@code WF_SuperAdminScreen}
     * instance.
     */
    public WF_SuperAdminScreen SUPERADMIN_SCREEN;

    /**T
     * Singleton reference for this application's user manager screen
     * JFrame via the respective {@code WF_UserManager} instance.
     */
    public WF_UserManager USER_MANAGER_SCREEN;

    /**
     * Singleton reference for this application's purchase view, which
     * is contained within {@code DASHBOARD_FRAME}, accessed via the
     * respective {@code DV_PurchaseView} instance.
     */
    public DV_PurchaseView PURCHASE_VIEW;

    /**
     * Singleton reference for this application's inventory view, which
     * is contained within {@code DASHBOARD_FRAME}, accessed via the
     * respective {@code DV_Inventory} instance.
     */
    public DV_Inventory INVENTORY_VIEW;

    /**
     * Singleton reference for this application's customers view, which
     * is contained within {@code DASHBOARD_FRAME}, accessed via the
     * respective {@code DV_Customers} instance.
     */
    public DV_Customers CUSTOMERS_VIEW;

    /**
     * Initializes and runs the application.
     */
    public void run() {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts
        openLoginScreen();
    }
    
    public void initializeDashboardFrame() {
        DASHBOARD_FRAME = new WF_Dashboard();
        DASHBOARD_FRAME.initializeSideRibbon();
        DASHBOARD_FRAME.setToDefaultView();
    }

    public void openLoginScreen() {
        LOGIN_WINDOW = new WF_LoginWindow();
        
        LOGIN_WINDOW.setVisible(true);
        LOGIN_WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void logout() {
        Main.app = new Application();
        Main.app.run();
    }

    public void defineViewPanels() {
        Main.app.PURCHASE_VIEW  = new DV_PurchaseView();
        Main.app.INVENTORY_VIEW = new DV_Inventory();
        Main.app.CUSTOMERS_VIEW = new DV_Customers();
    }

    public void defineDefaultViewPanel() {
        GUIReferences.DEFAULT_VIEW = Main.app.PURCHASE_VIEW;
    }
}
