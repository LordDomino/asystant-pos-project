package main.java.gui.windowPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.java.components.APP_CalculatorButton;
import main.java.components.APP_Panel;
import main.java.configs.ColorConfig;
import main.java.configs.StylesConfig;

public class WP_Calculator extends APP_Panel {
    
    protected JPanel outputPanel = new JPanel(new GridBagLayout());
    protected JPanel buttonsPanel = new JPanel(new GridBagLayout());

    protected JLabel outputText = new JLabel("Output");

    protected JButton btn_AC         = new APP_CalculatorButton("AC");
    protected JButton btn_plus_minus = new APP_CalculatorButton("+/-");
    protected JButton btn_percent    = new APP_CalculatorButton("%");
    protected JButton btn_divide     = new APP_CalculatorButton("\u00f7");

    protected JButton btn_7          = new APP_CalculatorButton("7");
    protected JButton btn_8          = new APP_CalculatorButton("8");
    protected JButton btn_9          = new APP_CalculatorButton("9");
    protected JButton btn_multiply   = new APP_CalculatorButton("\u00d7");

    protected JButton btn_4          = new APP_CalculatorButton("4");
    protected JButton btn_5          = new APP_CalculatorButton("5");
    protected JButton btn_6          = new APP_CalculatorButton("6");
    protected JButton btn_subtract   = new APP_CalculatorButton("\u2212");
    
    protected JButton btn_1          = new APP_CalculatorButton("1");
    protected JButton btn_2          = new APP_CalculatorButton("2");
    protected JButton btn_3          = new APP_CalculatorButton("3");
    protected JButton btn_add        = new APP_CalculatorButton("\u002b");

    protected JButton btn_0          = new APP_CalculatorButton("0");
    protected JButton btn_00         = new APP_CalculatorButton("00");
    protected JButton btn_DOT        = new APP_CalculatorButton(".");
    protected JButton btn_equals     = new APP_CalculatorButton("=");
 
    protected String memory = "";
    protected boolean isDecimal = false;

    public WP_Calculator() {
        super();
        compile();
    }

    public void prepareComponents() {
        outputPanel.setBackground(ColorConfig.ACCENT_1);
        buttonsPanel.setBackground(ColorConfig.BG);

        outputText.setHorizontalAlignment(JLabel.RIGHT);
        outputText.setFont(StylesConfig.CALCULATOR_OUTPUT);
        outputText.setForeground(ColorConfig.CONTRAST);
        outputText.setBorder(new MatteBorder(0, 0, 1, 0, ColorConfig.ACCENT_3));

        btn_AC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = "";
                isDecimal = false;
                outputText.setText("Output");
            }
        });


        btn_divide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "/";
                isDecimal = false;
                outputText.setText(memory);
            }
        });


         btn_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "7";
                outputText.setText(memory);
            }
        });


        btn_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "8";
                outputText.setText(memory);
            }
        });


        btn_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "9";
                outputText.setText(memory);
            }
        });


        btn_multiply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "*";
                isDecimal = false;
                outputText.setText(memory);
            }
        });


         btn_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "4";
                outputText.setText(memory);
            }
        });


        btn_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "5";
                outputText.setText(memory);
            }
        });


        btn_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "6";
                outputText.setText(memory);
            }
        });


        btn_subtract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "-";
                isDecimal = false;
                outputText.setText(memory);
            }
        });



        btn_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "1";
                outputText.setText(memory);
            }
        });


        btn_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "2";
                outputText.setText(memory);
            }
        });


        btn_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "3";
                outputText.setText(memory);
            }
        });


        btn_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "+";
                isDecimal = false;
                outputText.setText(memory);
            }
        });


        btn_0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "0";
                outputText.setText(memory);
            }
        });


        btn_00.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                memory = memory + "00";
                outputText.setText(memory);
            }
        });


        btn_DOT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isDecimal){
                    memory = memory + ".";
                    outputText.setText(memory);

                }
               
            }
        });
    }

    public void prepare() {
        setBackground(ColorConfig.BG);
    }

    public void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(outputPanel, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 2, 2, 2);
        add(buttonsPanel, gbc);
        
        // OUTPUT PANEL
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 0;
        outputPanel.add(outputText, gbc);
        
        // BUTTONS PANEL
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btn_AC, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonsPanel.add(btn_plus_minus, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonsPanel.add(btn_percent, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonsPanel.add(btn_divide, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(btn_7, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonsPanel.add(btn_8, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonsPanel.add(btn_9, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        buttonsPanel.add(btn_multiply, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonsPanel.add(btn_4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonsPanel.add(btn_5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        buttonsPanel.add(btn_6, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        buttonsPanel.add(btn_subtract, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonsPanel.add(btn_1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        buttonsPanel.add(btn_2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        buttonsPanel.add(btn_3, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        buttonsPanel.add(btn_add, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonsPanel.add(btn_0, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        buttonsPanel.add(btn_00, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        buttonsPanel.add(btn_DOT, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        buttonsPanel.add(btn_equals, gbc);    
       
    }

    public void finalizePrepare() {}
}
