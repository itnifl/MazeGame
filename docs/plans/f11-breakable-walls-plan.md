# Implementation Plan — F11. Breakable Walls and Hit Points

**Status:** PLANNED
**ID:** `F11`
**Source:** `walls.ecore` — `WallMaterial.breakable`, `WallMaterial.hitPoints`
**Backend:** both
**Target:** Maze generation & Game Loop
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

Plan for **F11. Breakable walls**. Walls currently are static and indestructible. The model allows materials (like WOOD or GLASS) to be marked as `breakable` with a finite `hitPoints` value.

### Goals
- Enable walls to be damaged and removed from the maze grid during gameplay.

### Detailed Approach
1. **Model Propagation**: Update `WallSegment` / `MazeArena` to include `breakable` boolean and `hitPoints` integer, derived from the selected `WallMaterial`.
2. **Combat Expansion**: 
   - When a projectile (from a Bomber or a player weapon) intersects a wall, check if the wall is `breakable`.
   - If breakable, subtract damage from `hitPoints`.
   - If `hitPoints <= 0`, remove the `WallSegment` from `MazeArena.walls()`.
3. **Pathfinding Update**: When a wall breaks, trigger a recalculation of the `MazeNavigationGraph` so enemies can now path through the broken segment.
4. **Testing**: Write tests asserting that firing a mock projectile at an unbreakable wall does nothing, but firing at a breakable wall with sufficient damage removes the wall and updates the nav graph.