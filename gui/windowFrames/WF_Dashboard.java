package gui.windowFrames;

import javax.swing.JPanel;

import components.APP_Frame;
import configs.ColorConfig;

public class WF_Dashboard extends APP_Frame {

    protected JPanel calculatorPanel;

    public WF_Dashboard() {
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
