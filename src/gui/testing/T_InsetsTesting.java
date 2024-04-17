package gui.testing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.APP_DefaultButton;
import configs.APPResourceLoader;
import configs.StylesConfig;

public class T_InsetsTesting {
    public static void main(String[] args) {
        APPResourceLoader.loadFonts(); // load custom fonts
        StylesConfig.setupUI();        // then register them as the default fonts

        JFrame frame = new JFrame("Insets testing");
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JPanel labelsPanel = new JPanel(new GridBagLayout());
        frame.setLayout(new GridBagLayout());

        JButton lead = new APP_DefaultButton("Lead");
        JButton xs = new APP_DefaultButton("X-Small");
        JButton s = new APP_DefaultButton("Small");
        JButton m = new APP_DefaultButton("Medium");
        JButton l = new APP_DefaultButton("Large");
        JButton xl = new APP_DefaultButton("X-Large");
        JButton xxl = new APP_DefaultButton("XX-Large");

        JLabel heading1 = new JLabel("Heading 1");
        JLabel heading2 = new JLabel("Heading 2");
        JLabel heading3 = new JLabel("Heading 3");
        JLabel leadText = new JLabel("Lead - The lead text is used to denote partitions in UI.");
        JLabel normal = new JLabel("Normal - This is a normal text.");
        JLabel detail = new JLabel("Detail - This is a detail text used to indicate smaller UI details.");

        heading1.setFont(FontStyles.HEADING1);
        heading2.setFont(FontStyles.HEADING2);
        heading3.setFont(FontStyles.HEADING3);
        leadText.setFont(FontStyles.LEAD);
        normal.setFont(FontStyles.NORMAL);
        detail.setFont(FontStyles.DETAIL);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        frame.add(buttonsPanel, gbc);
        
        gbc.gridx = 1;
        frame.add(labelsPanel, gbc);
        
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0;
        gbc.weighty = 0;
        buttonsPanel.add(lead, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(InsetSizes.XS, 0, 0, 0);
        buttonsPanel.add(xs, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(InsetSizes.S, 0, 0, 0);
        buttonsPanel.add(s, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(InsetSizes.M, 0, 0, 0);
        buttonsPanel.add(m, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(InsetSizes.L, 0, 0, 0);
        buttonsPanel.add(l, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(InsetSizes.XL, 0, 0, 0);
        buttonsPanel.add(xl, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(InsetSizes.XXL, 0, 0, 0);
        buttonsPanel.add(xxl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, InsetSizes.XXL, 0, 0);
        labelsPanel.add(heading1, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(InsetSizes.M, InsetSizes.XXL, 0, 0);
        labelsPanel.add(heading2, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(InsetSizes.M, InsetSizes.XXL, 0, 0);
        labelsPanel.add(heading3, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(InsetSizes.M, InsetSizes.XXL, 0, 0);
        labelsPanel.add(leadText, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(InsetSizes.M, InsetSizes.XXL, 0, 0);
        labelsPanel.add(normal, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(InsetSizes.M, InsetSizes.XXL, 0, 0);
        labelsPanel.add(detail, gbc);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class InsetSizes {
    public static final int XS  = 2;
    public static final int S   = 4;
    public static final int M   = 6;
    public static final int L   = 10;
    public static final int XL  = 16;
    public static final int XXL = 24;
}

class FontStyles {
    public static final Font NORMAL = new Font("Inter", Font.PLAIN, 14);
    public static final Font HEADING1 = new Font("Inter", Font.BOLD, 26);
    public static final Font HEADING2 = new Font("Inter", Font.BOLD, 22);
    public static final Font HEADING3 = new Font("Inter", Font.BOLD, 18);
    public static final Font LEAD = new Font("Inter", Font.PLAIN, 16);
    public static final Font DETAIL = new Font("Inter", Font.PLAIN, 11);
}