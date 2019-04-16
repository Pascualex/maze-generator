package com.github.pascualex;

import com.github.pascualex.maze.Maze;
import com.github.pascualex.maze.MazeController;

import javax.swing.JFrame;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * This class defines the controller of the maze app.
 * @author Alejandro Pascual
 */
public class MazeAppController {
    private Point click;
    
    /**
     * Instantiates a maze app controller, given its maze app view.
     * @param maze The maze of the maze app.
     * @param mazeAppView The maze app view of the maze app controller.
     */
    public MazeAppController(Maze maze, MazeAppView mazeAppView) {
        // Creates the maze controller and the maze thread
        MazeController mazeController = new MazeController(maze, mazeAppView.mazeView);
        Thread mazeThread = new Thread(mazeController);
        mazeThread.start();

        // Sets up the draggable bar of the maze app
        click = new Point();
        mazeAppView.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!e.isMetaDown()) {
                    click.x = e.getX();
                    click.y = e.getY();
                }
            }
        });
        mazeAppView.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!e.isMetaDown()) {
                    // Checks if the cursor is inside the draggable bar
                    if (click.y < 50) {
                        Point p = mazeAppView.mazeView.getLocation();
                        int x = p.x+e.getX()-click.x;
                        int y = p.y+e.getY()-click.y;
                        mazeAppView.mazeView.setLocation(x, y);
                    }
                }
            }
        });

        // Sets up the action listener of the minimize button of the maze app
        mazeAppView.minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeAppView.setState(JFrame.ICONIFIED);
            }
        });
        
        // Sets up the action listener of the close button of the maze app
        mazeAppView.closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeAppView.setVisible(false);

                // Indicates that the maze thread must end
                mazeController.finish();
                mazeThread.interrupt();

                mazeAppView.dispose();
            }
        });

        // Sets up the action listener of the generate button of the maze app
        mazeAppView.generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtains the tile side from its input
                String tileSideText = mazeAppView.tileSideInput.getText();
                int tileSide;
                try {
                    tileSide = Integer.parseInt(tileSideText);
                    if (tileSide < MazeAppView.minTileSide || tileSide > MazeAppView.maxTileSide) {
                        tileSide = -1;
                    }
                } catch (NumberFormatException ex) {
                    tileSide = -1;
                }

                // Obtains the step delay from its input
                String stepDelayText = mazeAppView.stepDelayInput.getText();
                int stepDelay;
                try {
                    stepDelay = Integer.parseInt(stepDelayText);
                    if (stepDelay < MazeAppView.minStepDelay 
                        || stepDelay > MazeAppView.maxStepDelay) {
                        stepDelay = -1;
                    }
                } catch (NumberFormatException ex) {
                    stepDelay = -1;                        
                }
                
                // If the tile side input is incorrect, it's notified visually
                if (tileSide == -1) {
                    mazeAppView.tileSideInput.setForeground(MazeAppView.errorColor);
                } else {
                    mazeAppView.tileSideInput.setForeground(MazeAppView.tertiaryColor);
                }

                // If the step delay input is incorrect, it's notified visually
                if (stepDelay == -1) {
                    mazeAppView.stepDelayInput.setForeground(MazeAppView.errorColor);
                } else {
                    mazeAppView.stepDelayInput.setForeground(MazeAppView.tertiaryColor);
                }
                
                // Checks if some input is incorrect
                if (tileSide != -1 && stepDelay != -1) {
                    // Restarts the maze with the new parameters
                    mazeController.setTileSide(tileSide);
                    mazeController.setStepDelay(stepDelay);
                    mazeController.restart();
                    mazeThread.interrupt();
                } else {
                    // Clears the maze
                    if (mazeController.clear()) {
                        mazeThread.interrupt();
                    }
                }  
            }
        });

        // Sets up the action listener of the solve button of the maze app
        mazeAppView.solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   
                // Checks if the maze controller accepts the solve request             
                if (mazeController.solve()) {
                    // Notifies the maze thread
                    mazeThread.interrupt();
                }
            }
        });

        // Sets up the action listener of the cancel button of the maze app
        mazeAppView.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checks if the maze controller accepts the clear request 
                if (mazeController.clear()) {
                    // Interrupts the maze thread
                    mazeThread.interrupt();
                }
            }
        });
    }
}