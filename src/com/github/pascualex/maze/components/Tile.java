package com.github.pascualex.maze.components;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This class defines the tiles that make up the maze.
 * @author Alejandro Pascual
 */
public class Tile {
    private TileType type;
    private Direction parentDirection;
    private LinkedList<Direction> directionsRemaining;

    /**
     * Instantiates a tile as a wall with no parent direction. It also creates a shuffled stack of 
     * directions which represent the directions that haven't been tried yet.
     */
    public Tile() {
        type = TileType.WALL;
        parentDirection = Direction.NONE;

        directionsRemaining = new LinkedList<>();
        directionsRemaining.addFirst(Direction.UP);
        directionsRemaining.addFirst(Direction.RIGHT);
        directionsRemaining.addFirst(Direction.DOWN);
        directionsRemaining.addFirst(Direction.LEFT);

        Collections.shuffle(directionsRemaining);
    }

    /**
     * Returns the type of the tile.
     * @return The type of the tile.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Sets the type of the tile.
     * @param type The new type of the tile.
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Returns the direction of the parent, relative to this tile.
     * @return The direction of the parent.
     */
    public Direction getParentDirection() {
        return parentDirection;
    }

    /**
     * Sets the direction of the parent, relative to this tile.
     * @param parentDirection The new direction of the parent.
     */
    public void setParentDirection(Direction parentDirection) {
        this.parentDirection = parentDirection;
    }

    /**
     * Returns the first direction of the stack of directions that haven't been tried yet or null if
     * the stack is empty.
     * @return A direction that haven't been tried yet.
     */
    public Direction getDirection() {
        if (directionsRemaining.size() == 0) return null;

        return directionsRemaining.pop();
    }

    /**
     * Removes a direction from the stack of directions if it's still present.
     * @param direction The direction that won't be tried.
     */
    public void removeDirection(Direction direction) {
        directionsRemaining.remove(direction);
    }
}