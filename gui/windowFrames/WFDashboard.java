package gui.windowFrames;

import javax.swing.JPanel;

import components.APPFrame;
import configs.ColorConfig;

public class WFDashboard extends APPFrame {

    protected JPanel calculatorPanel;

    public WFDashboard() {
        super("Dashboard");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.DEFAULT_ACCENT_1);
    }

    public void prepareComponents() {}

    public void addComponents() {}

    public void finalizePrepare() {
        pack();
        setSize(500, 300);
        setLocationRelativeTo(null);
    }
}
