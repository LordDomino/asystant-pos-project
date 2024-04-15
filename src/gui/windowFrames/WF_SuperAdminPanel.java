package gui.windowFrames;

import components.APP_Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WF_SuperAdminPanel extends APP_Frame {
    
    public JPanel buttonsPanel = new JPanel();
    public JButton userManagementButton = new JButton("User Management");
    public JButton dashboardButton = new JButton("Dashboard");

    public WF_SuperAdminPanel() {
        super("Super Administrator Panel");
        compile();
    }

    @Override
    public void prepareComponents() {
        buttonsPanel.setLayout(new GridLayout(1, 2)); // Layout for two buttons vertically

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
    
    
    public void prepare() {
        // No additional preparation needed in this method
    }
    
    public void addComponents() {
        // Add buttons to the frame
        buttonsPanel.add(userManagementButton);
        buttonsPanel.add(dashboardButton);
        add(buttonsPanel, BorderLayout.CENTER); // Add buttons to the center of the frame
    }

    public void finalizePrepare() {
        // No finalization needed in this method
    }

        // Method to open the user management frame
    private void openUserManagementFrame() {
        // Code to open the user management frame 
    }

    // Method to open the dashboard frame
    private void openDashboardFrame() {
        // Code to open the dashboard frame 
    }
}