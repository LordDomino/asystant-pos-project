package test.java;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class T_PanelSwap {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());

        JPanel red = new JPanel();
        JPanel blue = new JPanel();

        red.setBackground(Color.RED);
        blue.setBackground(Color.BLUE);

        red.add(new JLabel("Blag blag"));

        JButton button = new JButton("Swap");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // GridBagConstraints gbc = new GridBagConstraints();
    
                // gbc.fill = GridBagConstraints.BOTH;
                // gbc.gridx = 1;
                // gbc.weightx = 1;
                // gbc.weighty = 1;
                // frame.add(red, gbc);
                frame.remove(red);
                frame.revalidate();
                frame.repaint();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frame.add(button, gbc);

        gbc.gridx = 1;
        frame.add(red, gbc);

        frame.setVisible(true);
    }
}
