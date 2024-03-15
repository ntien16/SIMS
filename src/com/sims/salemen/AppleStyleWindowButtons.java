package com.sims.salemen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppleStyleWindowButtons extends JFrame {
    public AppleStyleWindowButtons() {
        setTitle("Apple Style Window Buttons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Nút Minimize
        JButton minimizeButton = createButton("\u2013"); // Sử dụng ký tự Unicode để tạo biểu tượng "-"
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });

        // Nút Maximize
        JButton maximizeButton = createButton("\u25A1"); // Sử dụng ký tự Unicode để tạo biểu tượng hình vuông
        maximizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int state = getExtendedState();
                if ((state & JFrame.MAXIMIZED_BOTH) == 0) {
                    setExtendedState(state | JFrame.MAXIMIZED_BOTH);
                } else {
                    setExtendedState(state & ~JFrame.MAXIMIZED_BOTH);
                }
            }
        });

        // Nút Close
        JButton closeButton = createButton("x");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(minimizeButton);
        buttonPanel.add(maximizeButton);
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 30));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppleStyleWindowButtons example = new AppleStyleWindowButtons();
                example.setVisible(true);
            }
        });
    }
}
