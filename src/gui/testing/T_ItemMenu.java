package gui.testing;

import javax.swing.JFrame;

import gui.windowPanels.WP_ItemMenu;

public class T_ItemMenu {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        WP_ItemMenu panel = new WP_ItemMenu();

        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
