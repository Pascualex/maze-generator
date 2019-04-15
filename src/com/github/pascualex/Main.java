package com.github.pascualex;

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
        MazeAppView mazeAppView = new MazeAppView();        
        MazeAppController mazeAppController = new MazeAppController(mazeAppView);
    }
}