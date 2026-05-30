# Maze Generation Rules (MGR)

This document lists the requirements that govern how the maze world is
generated and exposed to the JavaFX and libGDX frontends. Every requirement
is numbered MGR-N. Implementation must be identical between frontends; a
backend that diverges from any MGR is a defect.

## Identity and ownership

- **MGR-1**: The maze world is owned by the shared module
  `main.game.maze.mazeworld`. Both frontends (`maze`, `maze-libgdx`)
  consume the same generator types, the same wall vector representation,
  and the same navigation graph service.
- **MGR-2**: The canonical generator entry point is
  `GameMazeWorld.RegenerateWorld(int boardMaxX, int boardMaxY)`. Both
  frontends call this exact factory for a fresh maze. Frontends MUST NOT
  hand-build wall vectors at runtime; the only producer of `Vector2D` walls
  for the play board is the shared generator.
- **MGR-3**: The default generator implementation is
  `DfsMazeGenerator(MazeGeneratorConfig)`. The default
  `MazeGeneratorConfig` is produced by `GameMazeWorld.getMazeConfig(w, h)`
  with `StageConstants.WallSegmentLengthPx` and the default cell tuning
  constants. Any per-difficulty tuning MUST flow into this config; backends
  MUST NOT bypass it.

## Determinism and sizing

- **MGR-4**: For a given `(boardMaxX, boardMaxY)` and generator seed, both
  frontends MUST produce the same set of `Vector2D` walls. Seed control is
  the responsibility of the generator, not the frontend.
- **MGR-5**: Board pixel size per difficulty is taken from
  `StageConstants` (`BoardMaxX`, `BoardMaxY`, `BoardMaxXMedium`,
  `BoardMaxYMedium`, `BoardMaxXLarge`, `BoardMaxYLarge`). Frontends MUST
  use those exact constants when mapping a `Difficulty` to a board size.
- **MGR-6**: The runtime window size MUST equal the maze pixel size. HUD
  bars overlay the maze; they do not push the maze inward. This is the
  JavaFX behavior and libGDX MUST match it (`window = (boardW, boardH)`).
- **MGR-7**: The gameplay viewport in libGDX MUST cover the full window
  (`x=0, y=0, w=window.w, h=window.h`). Reserved-strip viewports are
  forbidden because they create visible gaps between the HUD bars and the
  outer walls.

## Coordinate model

- **MGR-8**: Internal wall vectors use the shared `Vector2D` / `Point2D`
  coordinates produced by the generator. The Y axis of the generator is
  top-down (origin at the top-left).
- **MGR-9**: Frontends that render bottom-up (libGDX) MUST flip the Y axis
  through the shared adapter `RealMaze.fresh(w, h)` and use the flipped
  `WallSegment` list it returns. They MUST NOT reimplement the flip.
- **MGR-10**: The shared `MazeArena` interface is the only contract the
  frontends are allowed to consume for maze geometry. Frontends MUST NOT
  read `GameMazeWorld.getMazeVectors()` directly when rendering; they go
  through `MazeArena.walls()`.

## Navigation graph

- **MGR-11**: The navigation graph is built once per generated maze by
  `MazeNavigationGraphService.buildFrom(vectors, StageConstants.NaviGraphStepSize)`
  inside `GameMazeWorld`. Frontends MUST reuse this graph; they MUST NOT
  rebuild it from raw walls.
- **MGR-12**: Spanning-tree rebuilds for the visualizer use
  `MazeNavigationGraphService.rebuildSpanningTreeFrom(graph, playerPos)`.
  This is the only allowed entry point for the path overlay logic.

## Start, goal, and spawn safety

- **MGR-13**: Start and goal positions MUST be derived from the shared
  navigation graph (nearest open node to the conventional start/goal
  targets). `RealMaze.computeStartAndGoal` is the canonical implementation
  for libGDX. JavaFX MUST use the equivalent logic over the shared graph.
- **MGR-14**: The player's spawn position MUST be filtered through
  `PlayerState.spawnAwayFromWalls(x, y, size, arena)`. Both frontends use
  this exact helper to guarantee a wall-free spawn.

## Failure modes

- **MGR-15**: A frontend that cannot reach the shared mazeworld module
  MUST fail fast at startup. It MUST NOT fall back to a hand-rolled maze.
- **MGR-16**: If the generator returns zero walls, the runtime MUST log a
  warning and terminate the level setup; rendering an empty world is a
  defect.

## Locked-in tests (regression contracts)

- **MGR-17**: A parity test MUST verify that, for the same difficulty,
  both frontends consume a maze of the same pixel size.
- **MGR-18**: A parity test MUST verify that the libGDX viewport strip
  equals the full window (MGR-7).
- **MGR-19**: A parity test MUST verify that `PlayerState.spawnAwayFromWalls`
  produces a non-colliding position for the canonical start of a generated
  maze (MGR-14).
