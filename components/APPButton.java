package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import configs.ColorConfig;
import configs.StylesConfig;

public class APPButton extends JButton {

    public APPButton(String text) {
        super(text);
        initialize();
    }

    public void initialize() {
        // Colors
        setBackground(ColorConfig.DEFAULT_BUTTON_BG);
        setForeground(ColorConfig.DEFAULT_BUTTON_FG_TEXT);
        setFocusPainted(false);
        setBorderPainted(false);

        // Font
        setFont(StylesConfig.defaultButton);
    }

    public APPButton(String text, JFrame targetOnClick, boolean dispose) {
        super(text);
        initialize();
        JButton comp = this;

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                targetOnClick.revalidate();
                targetOnClick.repaint();
                targetOnClick.setLocationRelativeTo(null);
                targetOnClick.setVisible(true);
                if (dispose) {
                    SwingUtilities.getWindowAncestor(comp).dispose();
                }
            }
        });
    }
}
