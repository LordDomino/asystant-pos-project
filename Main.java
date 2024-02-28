import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import components.APPButton;
import components.APPButtonContrast;
import components.APPFrame;
import configs.APPResourceLoader;
import configs.StylesConfig;

public class Main {
    public static void main(String[] args) {
        APPResourceLoader.loadFontResources();
        UIDefaults defaultUI = UIManager.getDefaults();
        defaultUI.put("Button.font", StylesConfig.defaultNormal);
        APPFrame mainAppFrame = new APPFrame("Test frame");
        mainAppFrame.setLayout(null);
        
        JButton buttonMid = new APPButton("Button mid");
        buttonMid.setBorderPainted(false);
        buttonMid.setBounds(10, 10, 130, 30);
        
        JButton buttonDark = new APPButtonContrast("Button dark");
        buttonDark.setBounds(150, 10, 130, 30);

        mainAppFrame.add(buttonMid);
        mainAppFrame.add(buttonDark);

        mainAppFrame.setSize(500, 500);
        mainAppFrame.setVisible(true);
        mainAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}