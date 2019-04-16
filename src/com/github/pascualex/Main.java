package com.github.pascualex;

import com.github.pascualex.maze.Maze;

/**
 * This class is the one that contains the main method and creates the maze app.
 * @author Alejandro Pascual
 */
public class Main {
    
    /**
     * The main method creates the view and the controller of the app.
     * @param args The arguments of the program. They will be ignored.
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Maze maze = new Maze();
        MazeAppView mazeAppView = new MazeAppView(maze);        
        MazeAppController mazeAppController = new MazeAppController(maze, mazeAppView);
    }
}