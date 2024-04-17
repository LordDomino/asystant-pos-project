package gui.windowFrames;

import components.APP_Frame;
import configs.ColorConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WF_SuperAdminScreen extends APP_Frame {
    
    public JPanel buttonsPanel = new JPanel();    
    public JButton userManagementButton = new JButton("User Management");
    public JButton dashboardButton = new JButton("Dashboard");

    public WF_SuperAdminScreen() {
        super("Super Administrator Panel");
        compile();
    }
    
    public void prepare() {
        setLayout(new GridBagLayout());
        setBackground(ColorConfig.ACCENT_1);
    }

    public void prepareComponents() {
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setOpaque(false);

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
                openUserManagementFrame();
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDashboardFrame();
            }
        });
    }
    
    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(buttonsPanel, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        buttonsPanel.add(dashboardButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        buttonsPanel.add(userManagementButton, gbc);
    }

    public void finalizePrepare() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void openUserManagementFrame() {
        // Code to open the user management frame 
    }

    // Method to open the dashboard frame
    private void openDashboardFrame() {
        // Code to open the dashboard frame 
    }
}