package com.github.pascualex.maze;

import com.github.pascualex.maze.Maze;

/**
 * This class defines the logic to enable the main thread to notify requests to the maze and to
 * refresh its view.
 * @author Alejandro Pascual
 */
public class MazeController implements Runnable {
    private Maze maze;
    private MazeView mazeView;

    private int tileSide;
    private int stepDelay;

    private int tileSideTemp;
    private int stepDelayTemp;    

    private boolean pause;
    private boolean restart;
    private boolean solve;
    private boolean finish;

    /**
     * Instantiates a maze controller given a maze and a maze view.
     * @param maze The maze of the maze controller.
     * @param mazeView The maze view of the maze controller.
     */
    public MazeController(Maze maze, MazeView mazeView) {
        // Sets the maze and the maze view
        this.maze = maze;
        this.mazeView = mazeView;

        // Sets the default values of the maze and maze controller parameters
        tileSide = 30;
        stepDelay = 20;

        // Initializes the maze and the flags
        pause = true;
        restart = true;
        solve = true;
        finish = false;
    }

    /**
     * Sets the tile side of the maze.
     * @param tileSide The new tile side of the maze.
     */
    public void setTileSide(int tileSide) {
        tileSideTemp = tileSide;
    }

    /**
     * Sets the step delay of the maze controller.
     * @param stepDelay The new step delay of the maze controller.
     */
    public void setStepDelay(int stepDelay) {
        stepDelayTemp = stepDelay;
    }

    /**
     * Runs a loop that acts based on the flags and the state of the maze.
     */
    @Override
    public void run() {
        // Runs while the finish flag is false
        while (!finish) {
            // Checks if the thread has to pause and if the maze has to restart
            if (pause) pauseThread();
            if (restart) restartMaze();

            // Advances one step in the current phase of the maze
            maze.step();

            // Checks if the maze is building or solving
            if (!maze.getMazeFinished() || 
                solve && !maze.getSolutionFinished()) {
                // If the step delay is higher than 0, repaints and waits
                if (stepDelay > 0) {
                    mazeView.repaint();
                    waitStepDelay();
                }
            } else {
                // Pauses the thread, waiting a restart, clear or solve request                
                pause = true;
            }
        }
    }

    /**
     * Sets the restart flag to true.
     */
    public void restart() {
        restart = true;
    }

    /**
     * Sets the solve flag to true if it isn't already true and only if the maze is finished.
     * @return If the solve flag was set to true or not.
     */
    public boolean solve() {
        if (solve || !maze.getMazeFinished()) return false;

        solve = true;
        return true;
    }

    /**
     * Sets the solve flag and the pause flag to true if the first one isn't already true.
     * @return If the solve flag and the pause flag were set to true or not.
     */
    public boolean clear() {
        if (!mazeView.getPaintMaze()) return false;

        mazeView.setPaintMaze(false);
        pause = true;
        return true;
    }

    /**
     * Sets the finish flag to true.
     */
    public void finish() {
        finish = true;
    }

    /**
     * Restarts the maze, updates the parameters and changes the flags.
     */
    private void restartMaze() {
        // Updates the changes made to the maze parameters
        stepDelay = stepDelayTemp;
        tileSide = tileSideTemp;
        mazeView.setTileSide(tileSide);

        // Restarts the maze
        int columns = mazeView.getWidth()/tileSide-2;
        columns = columns-columns/2;
        mazeView.setOffsetX((mazeView.getWidth()-(columns*2+1)*tileSide)/2);
        int rows = mazeView.getHeight()/tileSide-2;
        rows = rows-rows/2;
        mazeView.setOffsetY((mazeView.getHeight()-(rows*2+1)*tileSide)/2);
        maze.restart(columns, rows);
        
        // Changes the flags to avoid unwanted stacked requests
        restart = false;
        solve = false;
        mazeView.setPaintMaze(true);
    }

    /**
     * Pauses the current thread, waiting for an interruption.
     */
    private void pauseThread() {
        // Repaints before pausing, since run() won't update the changes
        mazeView.repaint();

        // Pauses the current thread until it's interrupted.
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait();
            }
        } catch (InterruptedException e) {
            pause = false;
        }
    }

    /**
     * Waits the step delay, unless it's interrupted.
     */
    private void waitStepDelay() {
        try {
            Thread.sleep(stepDelay);
        } catch (InterruptedException e) {
            // Untreated
        }
    }
}