# main.game.maze.behaviour

This module contains the movement and decision logic for opponents and other actors in MazeGame.  
Where other modules describe *what* exists in the maze (walls, cells, opponents, difficulty), this module describes *how* those actors move and react over time.

It is intentionally independent of user interface details and focuses on pure game logic so that it can be tested and evolved without touching rendering code.

---

## Responsibilities

The behaviour module is responsible for

- Defining basic concepts such as positions and paths in the game world  
- Providing navigation and path calculation services based on the maze navigation graph  
- Implementing reusable behaviour patterns such as patrol, chase and idle  
- Offering factories and configuration points so that game code can easily plug in the right behaviour per opponent type  
- Keeping behaviour deterministic and testable

Typical concepts found here include

- Positions and path nodes that refer to locations in the maze  
- Path calculators that compute routes between positions  
- Behaviour classes that decide where an actor should move next  
- Helper services that connect to the navigation graph from `main.game.maze.mazeworld`

---

## Core Concepts

### Positions

The module provides lightweight types that represent positions in the maze.  
They usually wrap grid coordinates or world coordinates in a type that is convenient for algorithms and tests.

A position type serves to

- make pathfinding and behaviour code easier to read  
- avoid mixing up different coordinate systems  
- provide small utility methods for comparing and transforming locations

### Paths

Paths are sequences of positions that an actor can follow over time.  
They are produced by path calculators and consumed by behaviour classes.

Typical properties of a path

- a list of positions or waypoints  
- a current index indicating where the actor is along the path  
- support for queries such as “is this the last node” or “what is the next step”

---

## Navigation And Pathfinding

The behaviour module relies on the navigation graph defined in `main.game.maze.mazeworld` to find valid routes through the maze.

A dedicated path calculator interface is used to request routes between positions.  
Concrete implementations can use different algorithms such as Dijkstra like shortest path or more specialised strategies.

Key ideas

- The behaviour module does not build the navigation graph itself  
  It only consumes the `MazeNavigationGraph` service from the maze world.

- Different calculators can be swapped in  
  For example a generic shortest path calculator, a patrol path calculator or a cost aware variant.

- Path calculation is a pure operation  
  Given the same graph and the same start and end position, the result is stable and easy to test.

---

## Behaviour Classes

Behaviour classes encapsulate decision rules for how an actor moves or reacts.  
Each behaviour is responsible for updating the “next position” or “next action” for the actor that uses it.

Common patterns include

- **Patrol behaviour**  
  An actor walks along a predefined sequence of patrol points, usually looping forever.  
  The behaviour keeps track of which patrol point is active and moves towards it using a path calculator.  
  When the current patrol point is reached, it advances to the next one.

- **Chase or follow behaviour**  
  An actor attempts to move towards a moving target, such as the player.  
  The behaviour frequently recomputes paths as the target moves.

- **Idle or guard behaviour**  
  An actor stays still or performs small movements within a local area until a trigger occurs.

- **Random walk or wandering behaviour**  
  An actor chooses random valid neighbours or random reachable nodes to move to, within certain constraints.

Each behaviour is designed to work with the rest of the game through clear interfaces so that different opponent types can reuse the same building blocks.

---

## Behaviour Factory And Configuration

To make it simple to attach behaviours to different opponents, this module exposes factory or builder utilities.

The factory is responsible for

- creating behaviour instances appropriate for a given character type  
- wiring behaviours with path calculators and navigation services  
- reading any configuration or parameters needed from difficulty or opponent definitions

### Future: Generated Support Code

> **Note**: The `BehaviorDispatcher` class is **planned but not yet implemented**.  
> When created, it will route behaviour requests by character type based on EMF model definitions.  
> See [readme-mddcodegeneration.md](../readme-mddcodegeneration.md) for the generation roadmap.

This keeps the rest of the game code small and declarative.  
For a given opponent type, the game only needs to ask the factory for "its" behaviour instead of manually creating and wiring objects.

---

## Interaction With Other Modules

The behaviour module sits between the maze world and the opponent runtime.

It depends on

- `main.game.maze.mazeworld`  
  For the navigation graph, board size and coordinate conventions.

- `main.game.maze.opponents`  
  For opponent types and possibly configuration flags such as speed, detection range or aggression.

It is used by

- The game loop and opponent controllers  
  These call into behaviours each tick to decide how actors move.

- Test suites  
  Behaviour and path calculation are intended to be easy to test without any graphics or input code.

This design allows behaviours to change and grow without affecting the data models or the rendering layer.

---

## Testing And Maintenance

Because this module focuses on pure logic, it is well suited for unit tests.

Recommended practices

- Test behaviours with small, hand crafted graphs or maze fragments  
  so that corner cases such as dead ends, cycles and blocked paths are covered.

- Keep behaviour classes focused  
  A single class should implement one coherent decision rule.  
  More complex behaviour can be composed from smaller pieces.

- Avoid hiding side effects  
  Behaviour methods should clearly state what they read and what they modify, which makes reasoning about them easier.

By keeping these goals in mind, `main.game.maze.behaviour` remains a clear and reliable foundation for all movement and decision making in MazeGame.

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](../docs/technology-laymans-guide.md) | Simple explanation of metamodels in everyday terms |
| [Metamodel Architecture](../docs/metamodel-architecture.md) | Technical details about the Ecore metamodels |
| [Model-Driven Code Generation Plan](../readme-mddcodegeneration.md) | Architecture for generating code from models |
| [Maze World Module](../main.game.maze.mazeworld/readme.md) | Navigation graph and maze structure |
| [Opponents Module](../main.game.maze.opponents/readme.md) | Opponent definitions and runtime factory |
