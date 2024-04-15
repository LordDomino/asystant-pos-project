package components;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import configs.ColorConfig;

public class APP_SideRibbonButton extends APP_DefaultButton {
    public APP_SideRibbonButton(String text) {
        super(text);
        setBorderPainted(false);
        setBackground(ColorConfig.ACCENT_BUTTON_BG);
        setForeground(ColorConfig.ACCENT_BUTTON_FG);
        setFont(new Font("Inter", Font.PLAIN, 14));
        setHorizontalAlignment(JButton.LEFT);
        setMargin(new Insets(3, 5, 3, 30));
        
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_1);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(ColorConfig.ACCENT_BUTTON_BG);
            }
        });
    }
}
