# Implementation Plan — F22 & F3 & F4 & F5 & F6 & F23. Patrol and Pathfinding AI

**Status:** PLANNED
**ID:** `F3, F4, F5, F6, F22, F23`
**Source:** `movements.ecore` / `MazeDsl.xtext`
**Backend:** Shared Backend Movement Services
**Target:** `maze-common-frontend`
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

Plan for the **Patrol and AI Overhaul**. Several interconnected missing features dictate how enemies move:
- **F3/F22**: Patrol behavior with explicit waypoints and `dwell time` (pause).
- **F4**: Triggering events (heal, speedup) upon reaching a patrol point.
- **F23**: Bounding patrols to a specific `PatrolZone`.
- **F6**: A dedicated `ChaseBehavior` using offset targeting.
- **F5**: Choosing the pathfinding algorithm (A*, Dijkstra) based on the model rather than hardcoded.

### Goals
- Fully implement the `movements.ecore` capabilities in the shared movement services.

### Detailed Approach
1. **Model Propagation**: Parse `PatrolBehavior`, `ChaseBehavior`, waypoints, dwell times, and zones from XMI/DSL into `EnemySpawn`.
2. **Movement Services Rewrite**:
   - Update `PatrolMovementService` to maintain a state machine: `MOVING_TO_WAYPOINT`, `DWELLING`. Track `currentWaypointIndex`.
   - Apply `PatrolPathBehavior` (LOOP, BACKWARD, RANDOM) logic to select the next waypoint.
   - Implement event dispatching upon reaching a waypoint.
   - Enforce bounding boxes for coordinates.
3. **Pathfinding Switch**: Abstract the A* and Dijkstra algorithms into a strategy pattern (`IPathCalculator`). Read the config from the enemy model and instantiate the correct strategy.
4. **Testing**: Massive expansion of `PatrolMovementServiceTest`. Verify dwell timers, path loop logic, and zone constraints using headless grids.