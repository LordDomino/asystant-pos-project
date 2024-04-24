package test.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel
;

public class T_JFrameDispose {
    public static void main(String[] args) {
        JFrame main = new JFrame();
        JFrame frame = new JFrame("Test");
        JButton show = new JButton("Show");
        JButton hide = new JButton("Hide");
        JButton add = new JButton("Add");

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
            }
        });
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setLayout(new FlowLayout());
                frame.add(new JLabel("New"));
                frame.revalidate();
                frame.repaint();
            }
        });
        main.setLayout(new FlowLayout());

        main.add(show);
        main.add(hide);
        frame.add(add);

        main.setVisible(true);
    }
}
