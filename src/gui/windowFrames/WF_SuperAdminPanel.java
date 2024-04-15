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

        // Set accent button background color
        userManagementButton.setBackground(new Color(205, 239, 239));
        dashboardButton.setBackground(new Color(205, 239, 239));

        // Set accent button foreground (text) color
        userManagementButton.setForeground(Color.BLACK); // Adjust this to the appropriate foreground color
        dashboardButton.setForeground(Color.BLACK); // Adjust this to the appropriate foreground color

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
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 0, 50);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(contentAreaPanel, gbc);

        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 30, 0, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        contentAreaPanel.add(titleCardPanel, gbc);
        
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 0, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        contentAreaPanel.add(fieldsPanel, gbc);

        l
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 30, 20, 30);
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        contentAreaPanel.add(buttonsPanel, gbc);
    }

    public void finalizePrepare() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add buttons to the frame
        buttonsPanel.add(userManagementButton);
        buttonsPanel.add(dashboardButton);
        add(buttonsPanel, BorderLayout.CENTER); // Add buttons to the center of the frame
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