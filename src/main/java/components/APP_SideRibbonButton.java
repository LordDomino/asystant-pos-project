package main.java.components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import main.java.configs.ColorConfig;

import javax.swing.border.EmptyBorder;

public class APP_SideRibbonButton extends APP_DefaultButton {

    public APP_SideRibbonButton(String text) {
        super(text);
        setBackground(ColorConfig.ACCENT_1);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setBorderPainted(false);
        setHorizontalAlignment(JButton.LEFT);
        
        setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BUTTON_OUTLINE, 1, false),
            new EmptyBorder(10, 16, 10, 32)
        ));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_1.brighter());
            }

            public void mouseExited(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_1);
            }
        });
    }
}
