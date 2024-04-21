package main.java.gui.windowFrames;

import javax.swing.*;

import main.java.components.APP_AccentButton;
import main.java.components.APP_Frame;
import main.java.configs.ColorConfig;
import main.java.configs.InsetsConfig;
import main.java.configs.StylesConfig;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WF_SuperAdminScreen extends APP_Frame {

    // Layout components
    public final JPanel headerPanel = new JPanel(new GridBagLayout());

    // Components
    public final JLabel header = new JLabel("Welcome");
    public final JLabel userGreeting = new JLabel("<html>You are logged in as <b>super administrator</b>.");

    public final JPanel buttonsPanel = new JPanel();
    public final JButton userManagementButton = new APP_AccentButton("User Management");
    public final JButton dashboardButton = new APP_AccentButton("Dashboard");

    public WF_SuperAdminScreen() {
        super("Super Administrator Panel");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(ColorConfig.ACCENT_1);
        setLayout(new GridBagLayout());
    }

    public void prepareComponents() {

        headerPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setOpaque(false);

        header.setFont(StylesConfig.HEADING1);
        userGreeting.setFont(StylesConfig.DETAIL);


        // Set accent button background color
        userManagementButton.setBackground(ColorConfig.ACCENT_BUTTON_BG);
        dashboardButton.setBackground(ColorConfig.ACCENT_BUTTON_BG);

        // Set accent button foreground (text) color
        userManagementButton.setForeground(ColorConfig.ACCENT_BUTTON_FG); // Adjust this to the appropriate foreground color
        dashboardButton.setForeground(ColorConfig.ACCENT_BUTTON_FG); // Adjust this to the appropriate foreground color

        // Add action listeners to the buttons
        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame target = new WF_UserManager();
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(userManagementButton);
                target.setVisible(true);
                source.dispose();
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame target = new WF_Dashboard();
                JFrame source = (JFrame) SwingUtilities.getWindowAncestor(userManagementButton);
                target.setVisible(true);
                source.dispose();
            }
        });
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(InsetsConfig.XXL, InsetsConfig.XXL,  InsetsConfig.XXL, InsetsConfig.XXL);
        add(headerPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            headerPanel.add(header, gbc);

            gbc.gridy = 1;
            gbc.insets = new Insets(InsetsConfig.XS, 0, 0, 0);
            headerPanel.add(userGreeting, gbc);
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, InsetsConfig.XXL, InsetsConfig.XXL, InsetsConfig.XXL);
        add(buttonsPanel, gbc);

        {
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            buttonsPanel.add(dashboardButton, gbc);

            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 1;
            gbc.insets = new Insets(0, InsetsConfig.M, 0, 0);
            buttonsPanel.add(userManagementButton, gbc);
        }
    }

    public void finalizePrepare() {
        pack();
        setSize(new Dimension(getSize().width, (int) Math.round(getSize().height * 1.25)));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}