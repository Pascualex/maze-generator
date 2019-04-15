# Maze generator

A personal project made to practice with Swing and multi-thread applications (and because seeing the building and solving proccess of a maze is very relaxing).

## Characteristics of the application

The most relevant characteristics of the application are:
* The interface is made with Swing.
* It's based on the MVC architecture.
* The logic of the maze runs in its own thread.

## The algorithms used for building and solving the maze

The algorithm used for building the maze is based on the depth-first search algorithm but, instead of using a stack, the tiles store all the necessary information.

The "solving" process isn't made by an algorithm, instead the solution is obtained from the information stored in the tiles.