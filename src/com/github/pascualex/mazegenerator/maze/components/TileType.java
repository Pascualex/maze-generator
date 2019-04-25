package com.github.pascualex.mazegenerator.maze.components;

/**
 * This enumeration defines the different types of tiles.
 * @author Alejandro Pascual
 */
public enum TileType {

    /**
     * Part of the path built in the maze.
     */
    FLOOR,

    /**
     * Part of the walls of the maze.
     */
    WALL,

    /**
     * Part of the path built in the maze. With the algorithm currently focused on it.
     */
    FOCUS,

    /**
     * Part of the path built in the maze. With the algorithm currently focused on the path that 
     * conects this tile with its parent. It will become the focus on the next step.
     */
    FOCUS_ENTERING,

    /**
     * Part of the path built in the maze. With the algorithm currently focused on the path that
     * conects this tile with its parent. Its parent will become the focus on the next step.
     */
    FOCUS_LEAVING,

    /**
     * Part of the solution built in the maze. The path between this tile and its parent is also 
     * part of the solution.
     */
    SOLUTION,
    
    /**
     * Part of the path built in the maze. The path between this tile and its parent is part of the
     * solution. It will become part of the solution on the next step.
     */
    SOLUTION_ENTERING
}