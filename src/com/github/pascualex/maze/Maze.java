package com.github.pascualex.maze;

import com.github.pascualex.maze.components.Tile;
import com.github.pascualex.maze.components.TileType;
import com.github.pascualex.maze.components.Direction;

import java.util.Stack;

/**
 * This class defines the state of the maze, as well as the methods to build it and solve it.
 * @author Alejandro Pascual
 */
public class Maze {
    private Tile[][] tiles;

    private int x;
    private int y;

    private boolean firstBuildStep;
    private boolean mazeFinished;
    private boolean firstSolveStep;
    private boolean solutionFinished;
    private boolean exitBuilt;
    private boolean buildingExit;
    private boolean solutionExitBuilt;

    private Stack<Tile> solution;

    private int newX;
    private int newY;

    /**
     * Instantiates a maze, with the maze finished flag and the solution finished flag set to false,
     * since they are the ones that may be checked before initializing the maze.
     */
    public Maze() {        
        mazeFinished = false;
        solutionFinished = false;
    }

    /**
     * Initialises the tiles and all the flags of the maze to their initial state, resetting the
     * state of the maze.
     * @param columns The new number of columns in the maze.
     * @param rows The new number of rows in the maze.
     */
    public void restart(int columns, int rows) {
        // Initialises the tiles
        tiles = new Tile[rows][columns];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile();
            }
        }

        // Sets the current tile to the upper left corner
        x = 0;
        y = 0;

        // Sets the current state of the starting tile
        tiles[y][x].setType(TileType.FOCUS_ENTERING);
        tiles[y][x].setParentDirection(Direction.UP);

        // Initialises all the flags
        firstBuildStep = true;
        mazeFinished = false;
        firstSolveStep = true;
        solutionFinished = false;
        exitBuilt = false;
        buildingExit = false;
        solutionExitBuilt = false;
    }

    /**
     * Based on the current state of the flags of the maze, advances one step in the building or
     * solving process. Won't do anything if the maze and the solution are already finished.
     */
    public void step() {
        if (firstBuildStep) firstBuildStep = false;
        else if (!mazeFinished) buildingStep();
        else if (firstSolveStep) findSolution();
        else if (!solutionFinished) solvingStep();
    }

    /**
     * Based on the state of the current tile and its neighbours, advances one step in the building
     * process, updating the flags when needed.
     */
    private void buildingStep() {
        // Gets the current tile
        Tile tile = tiles[y][x];

        // Checks if the type of the tile is focus entering
        if (tile.getType() == TileType.FOCUS_ENTERING) {
            // Updates the type to focus and returns
            tile.setType(TileType.FOCUS);
            return;
        }

        // Checks if the type of the tile is focus leaving
        if (tile.getType() == TileType.FOCUS_LEAVING) {  
            // Updates the type to floor
            tile.setType(TileType.FLOOR);

            // If the parent is outside the maze, finishes the maze
            if (newY < 0) {
                mazeFinished = true;
                return;
            } 

            // Sets the next tile and updates its type to focus
            x = newX;
            y = newY;
            tiles[y][x].setType(TileType.FOCUS);
            return;
        }

        // Checks if the tile is in the lower right corner
        if (x == tiles[0].length-1 && y == tiles.length-1) {
            // If the exit isn't built, builds the exit
            if (!exitBuilt) {
                tile.setType(TileType.FLOOR);
                exitBuilt = true;
                buildingExit = true;
                return;
            } else if (buildingExit) {                
                tile.setType(TileType.FOCUS);                
                buildingExit = false;
                return;
            }
        }

        // Tries to get a valid new direction
        Direction direction;
        while ((direction = tiles[y][x].getDirection()) != null) {
            // Checks the new tile isn't outside the maze 
            if (x+direction.getX() < 0) continue;
            if (x+direction.getX() >= tiles[0].length) continue;          
            if (y+direction.getY() < 0) continue;
            if (y+direction.getY() >= tiles.length) continue;

            // Checks the new tile isn't already floor
            Tile target = tiles[y+direction.getY()][x+direction.getX()];
            if (target.getType() == TileType.FLOOR) continue;

            // If everything is correct, breaks
            break;
        }
        
        // If there is no valid new direction, returns to the parent of the tile
        if (direction == null) {
            newX = x+tile.getParentDirection().getX();
            newY = y+tile.getParentDirection().getY();            
            tile.setType(TileType.FOCUS_LEAVING);
            return;
        }

        // If there is a valid new direction, advances in that direction
        x = x+direction.getX();
        y = y+direction.getY();

        // Sets the parent direction of the new tile
        Direction opposite = Direction.getOpposite(direction);
        tiles[y][x].setParentDirection(opposite);
        tiles[y][x].removeDirection(opposite);

        // Sets the new states of the current tile and the new tile
        tile.setType(TileType.FLOOR);
        tiles[y][x].setType(TileType.FOCUS_ENTERING);
    }

    /**
     * Based on the parents directions of the tiles, starting with the lower right tile, finds the
     * path that connects the entry and the exit and stores it in a stack.
     */
    private void findSolution() {
        solution = new Stack<>();

        int solX = tiles[0].length-1;
        int solY = tiles.length-1;
        while (solY >= 0) {
            Tile tile = tiles[solY][solX];
            solution.push(tile);
            solX += tile.getParentDirection().getX();
            solY += tile.getParentDirection().getY();            
        }

        firstSolveStep = false;
    }

    /**
     * Based on the state of the solution stack, advances one step in the solving process.
     */
    private void solvingStep() {
        // If the solution is empty, builds the exit and finishes the solution
        if (solution.empty()) {
            if (!solutionExitBuilt) solutionExitBuilt = true;
            else solutionFinished = true;            
            return;
        }

        // Checks the type of the current tile and updates it
        Tile tile = solution.peek();
        if (tile.getType() == TileType.FLOOR) {
            tile.setType(TileType.SOLUTION_ENTERING);
        } else if (tile.getType() == TileType.SOLUTION_ENTERING) {
            tile.setType(TileType.SOLUTION);
            solution.pop();
        }
    }

    /**
     * Returns the tiles of the maze.
     * @return The tiles of the maze.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Returns the maze finished flag.
     * @return The maze finished flag.
     */
    public boolean getMazeFinished() {
        return mazeFinished;
    }

    /** 
     * Returns the solution finished flag.
     * @return The solution finished flag.
     */
    public boolean getSolutionFinished() {
        return solutionFinished;
    }

    /**
     * Returns the exit built flag.
     * @return The exit built flag.
     */
    public boolean getExitBuilt() {
        return exitBuilt;
    }

    /**
     * Returns the building exit flag.
     * @return The building exit flag.
     */
    public boolean getBuildingExit() {
        return buildingExit;
    }

    /**
     * Returns the solution exit built flag.
     * @return The solution exit built flag.
     */
    public boolean getSolutionExitBuilt() {
        return solutionExitBuilt;
    }
}