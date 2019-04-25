package com.github.pascualex.mazegenerator.swing;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class defines a custom version of the JButton which can set a different background color
 * when the button is hovered.
 * @author Alejandro Pascual
 */
public class JCustomButton extends JButton {
    private static final long serialVersionUID = 0;

    private Color hoveredColor;

    /**
     * Instantiates a custom button given it's text.
     * @param text The text of the custom button
     */
    public JCustomButton(String text) {
        super(text);

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    /**
     * Sets the hovered color of the custom button.
     * @param hoveredColor The new hovered color of the custom button
     */
    public void setHoveredColor(Color hoveredColor) {
        this.hoveredColor = hoveredColor;
    }

    /**
     * Paints the appropriate background color and then prints the text of the custom button.
     * @param g The graphics in which the maze will be painted.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (getModel().isRollover() && hoveredColor != null) {
            g2d.setColor(hoveredColor);
        } else {
            g2d.setColor(getBackground());
        }

        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g);
    }
}