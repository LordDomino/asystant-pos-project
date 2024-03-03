package windowFrames;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import components.APPButton;
import components.APPFrame;
import components.APPTextField;
import configs.ColorConfig;

public class WFLoginWindow extends APPFrame {

    public JPanel loginPanel = new JPanel(new GridBagLayout());
    
    private GridBagConstraints gbc = new GridBagConstraints();

    public Color bg = ColorConfig.DEFAULT_ACCENT_1;
    public int textBoxWidth = 10;
    
    // Components
    public JLabel loginLabel = new JLabel("Asystant - POS System");
    public JLabel loginUserLabel = new JLabel("User");
    public JLabel passwordLabel = new JLabel("Password");
    public JTextField loginUserField = new APPTextField(textBoxWidth);
    public JPasswordField passwordField = new JPasswordField(textBoxWidth);
    public JButton submitButton = new APPButton("Log In"); 
    public JPanel paddingPanelA = new JPanel();
    public JPanel paddingPanelB = new JPanel();
    public JPanel loginTitlePanel = new JPanel();
    
    public WFLoginWindow() {
        super("Login");
        compile();
    }

    public void prepare() {
        getContentPane().setBackground(this.bg);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
    }
    
    public void prepareComponents() {
        loginPanel.setBackground(this.bg);
        loginTitlePanel.setBackground(ColorConfig.DEFAULT_BG);
        paddingPanelA.setBackground(ColorConfig.DEFAULT_BG_CONTRAST);
        paddingPanelB.setBackground(ColorConfig.DEFAULT_BG_CONTRAST);

        passwordField.setBorder(new CompoundBorder(
            new LineBorder(ColorConfig.DEFAULT_BG_CONTRAST),
            new LineBorder(ColorConfig.DEFAULT_BG, 2)));
    
        // submitButton.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         JFrame frame = (JFrame) SwingUtilities.getRoot(submitButton);
        //         MainWindow.getInstance().setVisible(true);
        //         frame.dispose();
        //     }	
        // });
    }
    
    public void addComponents() {
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        this.add(loginPanel, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(paddingPanelA, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(paddingPanelB, gbc);

        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        loginTitlePanel.add(loginLabel, gbc);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginPanel.add(loginTitlePanel, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 30, 0, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        loginPanel.add(loginUserLabel, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 30,0, 0);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        loginPanel.add(passwordLabel, gbc);	  

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 30);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        loginPanel.add(loginUserField, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 30);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        loginPanel.add(passwordField, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0,0, 0);
        gbc.ipadx = 10;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        loginPanel.add(submitButton, gbc);
    }
    
    public void finalizePrepare() {
        pack();
        setSize(360, 240);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}