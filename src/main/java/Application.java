package main.java;

import javax.swing.JFrame;

import main.java.configs.APPResourceLoader;
import main.java.configs.StylesConfig;
import main.java.gui.windowFrames.WF_Dashboard;
import main.java.gui.windowFrames.WF_LoginWindow;
import main.java.gui.windowFrames.WF_SuperAdminScreen;
import main.java.gui.windowFrames.WF_UserManager;

public class Application {

    /**The login screen which the application opens first during run. */
    public WF_LoginWindow loginFrame;

    /**The dashboard instance of the app. */
    public WF_Dashboard dashboard;

    /**The super admin screen. */
    public WF_SuperAdminScreen superAdminScreen;

    /**The user manager window. */
    public WF_UserManager userManager;

    public void run() {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts

        loginFrame = new WF_LoginWindow();
        dashboard = new WF_Dashboard();

        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
