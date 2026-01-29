package com.gym.ui;

import javax.swing.*;
import java.awt.*;

public class Theme {
    // Colors
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    public static final Color TEXT_COLOR = new Color(44, 62, 80);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color ERROR_COLOR = new Color(231, 76, 60);
    public static final Color WARNING_COLOR = new Color(241, 196, 15);

    // Fonts
    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);

    // Dimensions
    public static final Dimension BUTTON_SIZE = new Dimension(150, 40);
    public static final Dimension TEXT_FIELD_SIZE = new Dimension(250, 30);
    public static final int PADDING = 20;

    // Styles
    public static void styleTextField(JTextField field) {
        field.setPreferredSize(TEXT_FIELD_SIZE);
        field.setFont(NORMAL_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setPreferredSize(TEXT_FIELD_SIZE);
        comboBox.setFont(NORMAL_FONT);
        comboBox.setBackground(Color.WHITE);
    }

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(BUTTON_SIZE);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    public static void styleTable(JTable table) {
        table.setFont(NORMAL_FONT);
        table.setRowHeight(25);
        table.getTableHeader().setFont(BUTTON_FONT);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(BACKGROUND_COLOR);
        table.setSelectionBackground(SECONDARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
    }

    public static void stylePanel(JPanel panel) {
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
    }

    public static void styleLabel(JLabel label, boolean isTitle) {
        label.setFont(isTitle ? TITLE_FONT : NORMAL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    public static void styleFrame(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLocationRelativeTo(null);
    }
} 