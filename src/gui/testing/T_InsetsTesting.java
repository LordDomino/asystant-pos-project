package gui.testing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;

public class T_InsetsTesting {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Insets testing");
        frame.setLayout(new GridBagLayout());

        JButton lead = new JButton("Lead");
        JButton xs = new JButton("X-Small");
        JButton s = new JButton("Small");
        JButton m = new JButton("Medium");
        JButton l = new JButton("Large");
        JButton xl = new JButton("X-Large");
        JButton xxl = new JButton("XX-Large");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        frame.add(lead, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(InsetSizes.XS, 0, 0, 0);
        frame.add(xs, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(InsetSizes.S, 0, 0, 0);
        frame.add(s, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(InsetSizes.M, 0, 0, 0);
        frame.add(m, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(InsetSizes.L, 0, 0, 0);
        frame.add(l, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(InsetSizes.XL, 0, 0, 0);
        frame.add(xl, gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(InsetSizes.XXL, 0, 0, 0);
        frame.add(xxl, gbc);

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
