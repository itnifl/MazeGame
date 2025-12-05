# main.game.maze.mazeworld

This module contains the game world for MazeGame.
It is the place where the grid, walls, spawn points, navigation graph, board size and difficulty are combined into a playable level that the rest of the game can use.

The module is designed as a thin layer between the domain models
such as walls, difficulties and opponents
and the runtime logic such as rendering, behaviour and input.

---

## One World Object For The Whole Level

MazeWorld is responsible for holding the complete state of a single maze level.

Typical responsibilities

* Define board size and scaling constants for the level
* Hold the grid and cell information
* Build wall geometry and collision information
* Provide a navigation graph for pathfinding
* Offer a central place where other systems can ask where things are in the world

Typical concepts in this module include

* A world object for the maze
  This combines cells, walls, start positions, goals, portals and similar features into one structure.

* A navigation graph
  This gives a graph representation of the grid for pathfinding
  and is used by Dijkstra like algorithms or other route planners.

* Position types
  These capture logical grid positions and possibly world or pixel positions
  and help keep pathfinding and rendering consistent.

---

## Board Size And Stage Constants

Board size and related values live in a constants area inside this module, usually in a type called StageConstants or similar.

You normally find values such as

* Default board width and height for the easiest difficulty
* Medium board width and height for a normal difficulty
* Large board width and height for a hard difficulty

The application uses small helper functions in the front end layer to select the correct values based on the last chosen difficulty.
When you change board size, you usually update the constants in this module.
All callers that go through these helpers will then pick up the new size automatically.

---

## Grid, Cells And Walls

MazeWorld represents the labyrinth as a grid of cells.
On top of this grid it builds walls, openings and collision data.

Typical structure

* A two dimensional grid. 
  
  For example a matrix of cells where each cell knows which neighbours it has and on which edges there are walls.

* Wall geometry
  
  MazeWorld generates line segments or rectangles that represent walls in world space.
  These are used both for drawing and for collision detection.

* Connection to the wall module. 
  
  The properties of walls such as material, health and whether they are breakable come from the main dot game dot maze dot walls module.
  MazeWorld only decides where each wall goes and how it is represented in the level.

With this division of responsibility

* The wall module defines what a wall is
* MazeWorld defines where each wall is and how it shapes the level

---

## Navigation Graph And Pathfinding

Navigation is handled by a graph structure inside this module, often called MazeNavigationGraph or something similar.

Its main tasks:

* Create nodes for all reachable cells or positions in the grid
* Connect nodes with edges wherever movement is allowed
* Provide a clear and simple interface that pathfinding algorithms can use

Other modules for behaviour can attach path calculators to this graph, for example implementations of Dijkstra or A star like algorithms for patrol, chase and flee behaviours.

The typical flow is:

* MazeWorld builds the grid and walls
* The navigation graph scans this grid and builds a graph
* Behaviour code asks for a path from one location to another
* Pathfinding returns a sequence of positions that characters can follow

---

## Integration With Other Modules

MazeWorld stands in the middle between several parts of the system.

* Wall definitions
  
  It uses the definitions from the walls module to know which wall type to place where, which image each wall should use and which properties it has.

* Difficulty definitions
  
  It reads difficulty settings to decide board size, enemy density, wall density and other parameters that shape each level.

* Opponents and behaviour
  
  Enemy and behaviour modules ask MazeWorld about valid positions, pathfinding, line of sight and collision information.

* User interface and rendering
  
  The rendering layer asks MazeWorld which cells and walls to draw, where the player is located and where the camera or viewport should be.

This makes MazeWorld the logical centre of the game world while still keeping it independent from any concrete user interface technology.

---

## Typical Life Cycle Of A Maze Level

A typical sequence when a new level is created looks like this

* The player chooses a difficulty
* The system selects board size and other constants based on that difficulty
* MazeWorld generates the grid and places walls using a maze generator
* The navigation graph is built on top of this grid
* Enemies and the player are placed on their starting positions
* During the game, behaviour and rendering continuously ask MazeWorld for the current state

Because of this structure it is easy to swap maze generators, adjust difficulty rules or experiment with new ways of building levels without changing the rest of the game.

---

## Design Guidelines For Extending MazeWorld

When you extend or refactor this module, keeping a few principles in mind helps maintain clarity and flexibility.

* Keep MazeWorld free from user interface specific details
  The module should describe the logical game world, not how it is drawn.

* Clearly separate grid coordinates from world or pixel coordinates
  This makes both pathfinding and collision logic easier to reason about.

* Let other modules be clients of MazeWorld
  MazeWorld should provide services and data that others use, not pull details directly from controllers, input managers or external systems.

* Centralise constants in one place
  Changes in board size, scaling or margins should be made in the constants in this module rather than scattered throughout the code base.

Following these ideas helps main dot game dot maze dot mazeworld remain a stable and understandable foundation for the logical game world of MazeGame.
