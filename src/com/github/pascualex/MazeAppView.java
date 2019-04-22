package com.github.pascualex;

import com.github.pascualex.maze.Maze;
import com.github.pascualex.maze.MazeView;
import com.github.pascualex.swing.JCustomButton;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * This class defines the graphic representation of the application, as well as its controller.
 * @author Alejandro Pascual
 */
public class MazeAppView extends JFrame {
    final static long serialVersionUID = 0;
    
    public static final Color primaryColor = new Color(30, 30, 35);
    public static final Color secondaryColor = new Color(50, 50, 55);
    public static final Color tertiaryColor = new Color(40, 220, 220);    
    public static final Color textColor = new Color(220, 220, 220);
    public static final Color focusColor = new Color(220, 80, 240);
    public static final Color errorColor = new Color(200, 50, 50);

    public static final int minTileSide = 1;
    public static final int maxTileSide = 250;
    public static final int minStepDelay = 0;
    public static final int maxStepDelay = 1000;

    private JPanel mazePanel;
    public MazeView mazeView;

    private JPanel sidePanel;
    private JLabel tileSideLabel;
    public JTextField tileSideInput;
    private JLabel stepDelayLabel;
    public JTextField stepDelayInput;
    public JCustomButton minimizeButton;
    public JCustomButton closeButton;    
    public JCustomButton generateButton;
    public JCustomButton solveButton;
    public JCustomButton cancelButton;

    /**
     * Instantiates a maze app view, given its maze.
     * @param maze The maze of the maze app.
     */
    public MazeAppView(Maze maze) {
        super("Maze generator");
        setVisible(false);
        
        // Configures the frame
        setSize(1200, 850);
        setUndecorated(true);
        setResizable(false);
        setLocationByPlatform(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets up the maze panel
        mazePanel = new JPanel();
        mazePanel.setBounds(0, 0, 850, 850);
        mazePanel.setBackground(primaryColor);
        mazePanel.setLayout(null);
        add(mazePanel);

        // Sets up the maze view
        mazeView = new MazeView(maze, primaryColor, tertiaryColor, focusColor);
        mazeView.setBounds(50, 50, 750, 750);
        mazeView.setBackground(primaryColor);
        mazePanel.add(mazeView);
        
        // Sets up the side panel
        sidePanel = new JPanel();
        sidePanel.setBounds(850, 0, 350, 850);
        sidePanel.setBackground(secondaryColor);
        sidePanel.setLayout(null);
        add(sidePanel);

        // Sets up the minimize button
        minimizeButton = new JCustomButton("\uFF0D");
        minimizeButton.setBounds(1080, 0, 60, 50);
        minimizeButton.setFont(new Font("Helvetica", Font.PLAIN, 24));
        minimizeButton.setMargin(new Insets(0, 0, 0, 0));
        minimizeButton.setForeground(textColor);
        minimizeButton.setBackground(secondaryColor);
        minimizeButton.setHoveredColor(secondaryColor.brighter().brighter().brighter());
        sidePanel.add(minimizeButton);

        // Sets up the close button
        closeButton = new JCustomButton("\u2715");
        closeButton.setBounds(1140, 0, 60, 50);
        closeButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setForeground(textColor);
        closeButton.setBackground(secondaryColor);
        closeButton.setHoveredColor(errorColor);
        sidePanel.add(closeButton);

        // Sets up the border of the inputs
        Border border = BorderFactory.createLineBorder(textColor, 3);
        Border padding = BorderFactory.createEmptyBorder(0, 10, 0, 10);
        border = BorderFactory.createCompoundBorder(border, padding);

        // Sets up the tile side panel
        tileSideLabel = new JLabel("Tile side size (" + minTileSide + "-" + maxTileSide + " px):");
        tileSideLabel.setBounds(870, 100, 310, 30);
        tileSideLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        tileSideLabel.setForeground(textColor);
        sidePanel.add(tileSideLabel);

        // Sets up the tile side input
        tileSideInput = new JTextField("30");
        tileSideInput.setBounds(870, 150, 310, 60);
        tileSideInput.setFont(new Font("Helvetica", Font.BOLD, 25));
        tileSideInput.setForeground(tertiaryColor);
        tileSideInput.setBackground(secondaryColor);
        tileSideInput.setCaretColor(tertiaryColor);
        tileSideInput.setBorder(border);
        sidePanel.add(tileSideInput);

        // Sets up the step delay label
        stepDelayLabel = new JLabel("Step delay ("+ minStepDelay + "-" + maxStepDelay + " ms):");
        stepDelayLabel.setBounds(870, 250, 310, 30);
        stepDelayLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        stepDelayLabel.setForeground(textColor);
        sidePanel.add(stepDelayLabel);
        
        // Sets up the step delay input
        stepDelayInput = new JTextField("20");
        stepDelayInput.setBounds(870, 300, 310, 60);
        stepDelayInput.setFont(new Font("Helvetica", Font.BOLD, 25));
        stepDelayInput.setForeground(tertiaryColor);
        stepDelayInput.setBackground(secondaryColor);
        stepDelayInput.setCaretColor(tertiaryColor);
        stepDelayInput.setBorder(border);
        sidePanel.add(stepDelayInput);

        // Sets up the generate button
        generateButton = new JCustomButton("GENERATE");
        generateButton.setBounds(870, 650, 310, 80);
        generateButton.setFont(new Font("Helvetica", Font.BOLD, 35));
        generateButton.setForeground(secondaryColor);   
        generateButton.setBackground(tertiaryColor);
        generateButton.setHoveredColor(tertiaryColor.brighter().brighter().brighter());
        sidePanel.add(generateButton);

        // Sets up the solve button
        solveButton = new JCustomButton("SOLVE");
        solveButton.setBounds(870, 750, 210, 80);
        solveButton.setFont(new Font("Helvetica", Font.BOLD, 35));
        solveButton.setForeground(secondaryColor);   
        solveButton.setBackground(tertiaryColor);
        solveButton.setHoveredColor(tertiaryColor.brighter().brighter().brighter());
        sidePanel.add(solveButton);

        // Sets up the cancel button
        cancelButton = new JCustomButton("\u2715");
        cancelButton.setBounds(1100, 750, 80, 80);
        cancelButton.setFont(new Font("Helvetica", Font.BOLD, 40));
        cancelButton.setForeground(secondaryColor);   
        cancelButton.setBackground(tertiaryColor);
        cancelButton.setHoveredColor(tertiaryColor.brighter().brighter().brighter());
        sidePanel.add(cancelButton);

        // Makes the frame visible after everything is loaded
        setVisible(true);
    }
}