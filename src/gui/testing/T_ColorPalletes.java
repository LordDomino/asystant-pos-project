package gui.testing;

import javax.swing.*;

import java.awt.*;
import java.awt.Color;

public class T_ColorPalletes {
    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("Color Palettes Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        
        // Create JLabels
        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        JLabel label3 = new JLabel("Label 3");
        JLabel label4 = new JLabel("Label 4");

        // Set background colors for JLabels
        label1.setBackground(Pallete.BG);
        label2.setBackground(Pallete.ACCENT_1);
        label3.setBackground(Pallete.ACCENT_2);
        label4.setBackground(Pallete.ACCENT_3);

        // Set foreground colors for JLabels
        label1.setForeground(Pallete.FG);
        label2.setForeground(Pallete.FG);
        label3.setForeground(Pallete.FG);
        label4.setForeground(Pallete.FG);

        // Set opaque to true for JLabels to make background colors visible
        label1.setOpaque(true);
        label2.setOpaque(true);
        label3.setOpaque(true);
        label4.setOpaque(true);

        // Create JButtons
        JButton defaultButton = new JButton("Default");
        JButton accentButton = new JButton("Accent");
        JButton contrastButton = new JButton("Contrast");

        // Set background colors for JButtons
        defaultButton.setBackground(Pallete.DEFAULT_BUTTON_BG);
        accentButton.setBackground(Pallete.ACCENT_BUTTON_BG);
        contrastButton.setBackground(Pallete.CONTRAST_BUTTON_BG);

        // Set foreground colors for JButtons
        defaultButton.setForeground(Pallete.DEFAULT_BUTTON_FG);
        accentButton.setForeground(Pallete.ACCENT_BUTTON_FG);
        contrastButton.setForeground(Pallete.CONTRAST_BUTTON_FG);

        // Add JLabels and JButtons to the frame
        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);
        frame.add(defaultButton);
        frame.add(accentButton);
        frame.add(contrastButton);

        // Set frame properties
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
}
class Pallete {
    /** The default background color. */
    public static final Color BG = new Color(255, 255, 255);
    
    /** The accent 1 background color. */
    public static final Color ACCENT_1 = new Color(205, 239, 239);
    
    /** The accent 2 background color. */
    public static final Color ACCENT_2 = new Color(0, 176, 240);
    
    /** The accent 3 background color. */
    public static final Color ACCENT_3 = new Color(0, 32, 96);

    /** The contrasting background color. */
    public static final Color CONTRAST = new Color(13, 6, 40);

    /** The default text color. */
    public static final Color FG = new Color(0, 0, 0);

    /** The background color of default buttons. */
    public static final Color DEFAULT_BUTTON_BG = ACCENT_1;

    /** The background color of accented buttons. */
    public static final Color ACCENT_BUTTON_BG = ACCENT_2;

    /** The background color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_BG = CONTRAST;
    
    /** The text color of default buttons. */
    public static final Color DEFAULT_BUTTON_FG = ACCENT_3;

    /** The text color of accented buttons. */
    public static final Color ACCENT_BUTTON_FG = ACCENT_3;

    /** The text color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_FG = ACCENT_1;

    /** The outline color of default buttons. */
    public static final Color DEFAULT_BUTTON_OUTLINE = ACCENT_3;

    /** The outline color of accented buttons. */
    public static final Color ACCENT_BUTTON_OUTLINE = ACCENT_3;

    /** The outline color of contrasting buttons. */
    public static final Color CONTRAST_BUTTON_OUTLINE = ACCENT_1;
}
