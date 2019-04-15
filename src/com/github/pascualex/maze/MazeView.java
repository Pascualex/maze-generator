package com.github.pascualex.maze;

import com.github.pascualex.maze.components.Tile;
import com.github.pascualex.maze.components.TileType;
import com.github.pascualex.maze.components.Direction;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class defines the graphic representation of the maze.
 * @author Alejandro Pascual
 */
public class MazeView extends JPanel {
    public static final long serialVersionUID = 0;

    private Maze maze;

    private Color floorColor;
    private Color wallColor;
    private Color focusColor;

    private int tileSide;

    private int offsetX;
    private int offsetY;

    private boolean paintMaze;

    /**
     * Instantiates a maze view given its maze and colors.
     * @param maze The maze of the maze view.
     * @param floorColor The color of the floor tiles of the maze.
     * @param wallColor The color of the wall tiles of the maze.
     * @param focusColor The color of the tiles focused by the maze.
     */
    public MazeView(Maze maze, Color floorColor, Color wallColor, Color focusColor) {
        // Sets the maze and colors
        this.maze = maze;
        this.floorColor = floorColor;
        this.wallColor = wallColor;
        this.focusColor = focusColor;

        // Sets paint maze flag to false
        paintMaze = false;
    }

    /**
     * Sets the tile side of the maze.
     * @param tileSide The new tile side of the maze.
     */
    public void setTileSide(int tileSide) {
        this.tileSide = tileSide;
    }

    /**
     * Sets the x offset of the maze view.
     * @param offsetX The new x offset of the maze view.
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Sets the y offset of the maze view.
     * @param offsetY The new y offset of the maze view.
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Returns the paint maze flag.
     * @return The paint maze flag.
     */
    public boolean getPaintMaze() {
        return paintMaze;
    }

    /**
     * Set the paint maze flag.
     * @param paintMaze The new paint maze flag.
     */
    public void setPaintMaze(boolean paintMaze) {
        this.paintMaze = paintMaze;
    }

    /**
     * Paints the current state of the maze.
     * @param g The graphics in which the maze will be painted.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Prints the background
        super.paintComponent(g);

        // If the paint maze flag is false, returns
        if (!paintMaze) return;

        // If the maze has no tiles, returns
        Tile[][] tiles = maze.getTiles();
        if (tiles == null) return;

        // Casts the graphics into 2d graphics
        Graphics2D g2d = (Graphics2D) g;
        
        // Prints the wall background
        g2d.setColor(wallColor);
        int width = (tiles[0].length*2+1)*tileSide;
        int height = (tiles.length*2+1)*tileSide;
        g2d.fillRect(offsetX, offsetY, width, height);

        // Checks all the tiles and prints them if they aren't walls
        g2d.setColor(floorColor);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // If a tile is null, aborts the process
                if (tiles[i][j] == null) return;

                // Checks if the tile is a wall
                TileType type = tiles[i][j].getType();
                if (type != TileType.WALL) {
                    // Selects the colors based on the type of the tile
                    Color parentColor;
                    if (type == TileType.FLOOR) {
                        g2d.setColor(floorColor);
                        parentColor = floorColor;
                    } else if (type == TileType.FOCUS) {
                        g2d.setColor(focusColor);
                        parentColor = floorColor;
                    } else if (type == TileType.SOLUTION) {
                        g2d.setColor(focusColor);
                        parentColor = focusColor;
                    } else if (type == TileType.FOCUS_ENTERING) {
                        g2d.setColor(wallColor);
                        parentColor = focusColor;
                    } else {
                        g2d.setColor(floorColor);
                        parentColor = focusColor;
                    }

                    // Prints the tile on its position
                    int x = offsetX+tileSide+j*tileSide*2;
                    int y = offsetY+tileSide+i*tileSide*2;
                    g2d.fillRect(x, y, tileSide, tileSide);

                    // Prints the path to the parent of the tile on its position
                    g2d.setColor(parentColor);
                    Direction direction = tiles[i][j].getParentDirection();
                    x = offsetX+(1+direction.getX())*tileSide+j*tileSide*2;
                    y = offsetY+(1+direction.getY())*tileSide+i*tileSide*2;           
                    g2d.fillRect(x, y, tileSide, tileSide);
                }
            }
        }

        // Checks if the exit of the maze is built
        if (maze.getExitBuilt()) {
            // Selects the color based on the state of the exit
            if (maze.getBuildingExit()) g2d.setColor(focusColor);
            else if (maze.getSolutionExitBuilt()) g2d.setColor(focusColor);
            else g2d.setColor(floorColor);

            // Prints the exit on its position
            int x = offsetX+tileSide+(tiles[0].length-1)*tileSide*2;
            int y = offsetY+(tiles.length)*tileSide*2;  
            g2d.fillRect(x, y, tileSide, tileSide);
        }
    }
}