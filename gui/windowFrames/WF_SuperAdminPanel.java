package gui.windowFrames;

import components.APP_Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WF_SuperAdminPanel extends APP_Frame {

    public WF_SuperAdminPanel() {
        super("Super Administrator Panel");
        compile();
    }

    @Override
    public void prepareComponents() {
        JButton userManagementButton = new JButton("User Management");
        JButton dashboardButton = new JButton("Dashboard");

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

        // Add buttons to the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1)); // Layout for two buttons vertically
        buttonPanel.add(userManagementButton);
        buttonPanel.add(dashboardButton);
        add(buttonPanel, BorderLayout.CENTER); // Add buttons to the center of the frame
    }


    public void prepare() {
        // No additional preparation needed in this method
    }

    public void addComponents() {
        // No additional preparation needed in this method
    }

    public void finalizePrepare() {
        // No finalization needed in this method

        // Method to open the user management frame
    private void openUserManagementFrame() {
        // Code to open the user management frame 
    }

    // Method to open the dashboard frame
    private void openDashboardFrame() {
        // Code to open the dashboard frame 
    }
}
   
