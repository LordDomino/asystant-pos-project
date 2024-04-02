import javax.swing.JFrame;

import configs.APPResourceLoader;
import configs.StylesConfig;
import gui.windowFrames.WF_LoginWindow;

public class Application {

    /**The JFrame instance which the application opens first during run. */
    protected JFrame rootFrame;

    public void run() {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts

        rootFrame = new WF_LoginWindow();
        rootFrame.setVisible(true);
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
