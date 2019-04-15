package com.github.pascualex.maze.components;

/**
 * This enumeration defines the directions and their coordinates.
 * @author Alejandro Pascual
 */
public enum Direction {

    /**
     * No direction is specified, its coordinates are (0, 0).
     */
    NONE(0, 0),

    /**
     * Up direction, its coordinates are (0, -1).
     */
    UP(0, -1),

    /**
     * Right direction, its coordinates are (1, 0).
     */
    RIGHT(1, 0),

    /**
     * Down direction, its coordinates are (0, 1).
     */
    DOWN(0, 1),

    /**
     * Left direction, its coordinates are (-1, 0).
     */
    LEFT(-1, 0);

    private final int x;
    private final int y;

    /**
     * Instantiates a direction given its x and y coordinates.
     * @param x The x coordinate of the direction.
     * @param y The y coordinate of the direction.
     */
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the direction.
     * @return The x coordiante of the direction.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the direction.
     * @return The y coordinate of the direction.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the opposite direction of the one passed as a parameter.
     * @param direction The direction for which to get the opposite.
     * @return The opposite direction of the one passed as a parameter.
     */
    public static Direction getOpposite(Direction direction) {
        if (direction == UP) return DOWN;
        else if (direction == RIGHT) return LEFT;
        else if (direction == DOWN) return UP;
        else if (direction == LEFT) return RIGHT;
        else return NONE;
    }
}