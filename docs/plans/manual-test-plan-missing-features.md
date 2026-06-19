# Manual Test Plan — Missing Features Implementation

**Status:** PLANNED
**Target Backends:** JavaFX (`maze-javafx-backend`) & libGDX (`maze-libgdx`)
**Purpose:** Ensure CRR-5 (Feature Parity) and adherence to acceptance criteria for all missing features defined in `docs\requirements-features\missing-feature.md`.

This document outlines step-by-step manual test cases to be performed after the implementation of the missing features. Every test case must be executed on **both** the JavaFX and libGDX runners.

---

## 1. F25: Ghost Visibility Level
**Objective:** Verify that the `visibilityLevel` attribute scales the baseline opacity of solid ghosts.

*   **Setup:** Edit `opponents.xmi` (or the equivalent DSL file). Set the Ghost `visibilityLevel` to `30`.
*   **Execution (JavaFX):** Run `pwsh ./make-javafx.ps1 -Target prepare-run`.
*   **Execution (libGDX):** Run `mvn -pl maze-libgdx compile exec:java`.
*   **Steps:**
    1. Locate a Ghost in the maze.
    2. Observe its opacity while it is floating in open corridors (tangible state).
    3. Observe its opacity as it phases through a wall (intangible state).
*   **Expected Result:**
    *   In the open corridor, the ghost is heavily transparent (approx 30% opacity), not fully solid.
    *   As it phases into a wall, its opacity dynamically fades further (down to the 10% minimum threshold), blending smoothly with its base 30% visibility limit.
    *   *Both backends must look visually identical.*

---

## 2. F2 & F21: Loot Drops & DSL Configuration
**Objective:** Verify enemies drop items upon death, the player can pick them up, and effects are applied.

*   **Setup:** Create a `.mazedsl` file containing a `loot_table` with a `FOOD` item (value: 20, weight: 1, probability: 100%). Assign this `loot` reference to the Zombie definition.
*   **Execution:** Run the game on both backends using the `.mazedsl` configuration flag.
*   **Steps:**
    1. Allow an enemy to hit the player to reduce HP below 100.
    2. Kill the configured Zombie.
    3. Verify that a Food sprite appears on the floor where the Zombie died.
    4. Walk the player character over the Food sprite.
*   **Expected Result:**
    *   The Food sprite renders correctly on the floor.
    *   Upon intersection, an audio cue plays (pickup sound).
    *   The Food sprite disappears from the board.
    *   The player's HP increases by 20 points (visible on the HUD/Health bar).

---

## 3. F1: Animated Character Sprites
**Objective:** Verify characters render multiple frames and scale accurately.

*   **Setup:** Edit `opponents.xmi` for the Zombie type. Set `animationFrameCount` to `4` and `spriteScale` to `2.0`. Provide a sprite sheet image in `imageBase`.
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Locate a Zombie on the map.
    2. Observe its size relative to the grid.
    3. Observe its movement animation.
*   **Expected Result:**
    *   The Zombie renders twice as large as the standard grid cell (`spriteScale = 2.0`).
    *   The texture cycles seamlessly through 4 distinct frames as the game clock ticks, instead of sliding a static image across the screen.

---

## 4. F24: Zombie Resurrection Time
**Objective:** Verify that zombies respawn after a defined delay post-death.

*   **Setup:** Edit `opponents.xmi` for the Zombie type. Set `resurrectionTime` to `5000` (5 seconds).
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Kill a Zombie and record its death location.
    2. Wait exactly 5 seconds.
*   **Expected Result:**
    *   The zombie is removed from the board immediately upon death.
    *   After 5 seconds, the zombie respawns at (or near) its original death coordinates.
    *   The respawned zombie resumes standard AI behavior and can deal damage.

---

## 5. F26: Projectile Speed
**Objective:** Verify that `projectileSpeed` dictates the flight velocity of ranged attacks.

*   **Setup:** Configure two different PumpkinBomber types in `opponents.xmi`. Set Bomber A's `projectileSpeed` to `2.0` (slow) and Bomber B's `projectileSpeed` to `15.0` (fast).
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Trigger an attack from Bomber A. Observe the flight time.
    2. Trigger an attack from Bomber B. Observe the flight time.
*   **Expected Result:**
    *   Bomber A's projectile crawls across the screen slowly.
    *   Bomber B's projectile zips across the screen rapidly.

---

## 6. F11: Breakable Walls and Hit Points
**Objective:** Verify that specific walls can be destroyed.

*   **Setup:** Edit `walls.ecore` / `walls.xmi` to configure the `WOOD` material with `breakable = true` and `hitPoints = 2`. Ensure the loaded maze contains WOOD walls.
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Locate a WOOD wall.
    2. Attack the wall 2 times (using a player weapon or luring an enemy projectile into it).
    3. Attempt to walk through the space where the wall was.
    4. Press `P` (Show pathfinding tree) to check if the AI navigation graph updated.
*   **Expected Result:**
    *   After the second hit, the wall sprite is removed from the screen.
    *   The player and enemies can now walk through the newly opened tile.
    *   The AI navigation graph (spanning tree overlay) shows paths routing through the broken wall tile.

---

## 7. F10 & F20: Projectile Variants
**Objective:** Verify LOB, BEAM, and STRAIGHT attack behaviors.

*   **Setup:** Create three ranged enemy profiles with `STRAIGHT`, `LOB`, and `BEAM` respectively. Set `splashRadius` to `1.5` tiles for the LOB profile.
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Hide behind a wall and lure a `STRAIGHT` projectile.
    2. Hide behind a wall and lure a `LOB` projectile. Ensure you are standing within 1 tile of the impact zone.
    3. Step in front of a `BEAM` enemy.
*   **Expected Result:**
    *   `STRAIGHT`: Hits the wall and stops. No damage taken.
    *   `LOB`: The projectile renders flying in a visual arc *over* the wall. It lands at the target coordinate, explodes, and deals splash damage to the player despite the wall.
    *   `BEAM`: An instant laser line draws from the enemy to the player, dealing immediate damage without travel time.

### 7.1 Rapid smoke pass, one minute

Use this before deeper manual sessions.

1. Start JavaFX with one PumpkinBomber per projectile type.
2. Validate `STRAIGHT` blocked by wall and zero damage to player.
3. Validate `LOB` arc visual and splash damage around impact.
4. Validate `BEAM` flash line and instant damage.
5. Repeat steps 2 to 4 in libGDX.
6. Mark parity pass only if behavior and readability match across both frontends.

---

## 8. F3-F6, F22-F23: Patrol and Pathfinding AI Overhaul
**Objective:** Verify waypoints, dwell times, path algorithms, and zones.

*   **Setup:** 
    *   Configure Enemy 1 with `PatrolBehavior`: 3 waypoints, `path.time` = `2000` (2 seconds), Strategy = `LOOP`, `PatrolZone` bounded to a 5x5 grid.
    *   Configure Enemy 2 with `ChaseBehavior` using `AstarPathCalculator (heuristicMethod=EUCLIDEAN)`.
*   **Execution:** Run the game on both backends.
*   **Steps:**
    1. Observe Enemy 1 without engaging it.
    2. Engage Enemy 2 (step into its line of sight).
*   **Expected Result:**
    *   **Enemy 1 (Patrol):** Walks to Waypoint 1. Stops moving entirely for 2 seconds. Walks to Waypoint 2. Stops for 2 seconds. Walks to Waypoint 3. Stops for 2 seconds. Loops back to Waypoint 1. It never leaves the 5x5 defined PatrolZone.
    *   **Enemy 2 (Chase):** Actively chases the player using the Euclidean A* heuristic, pathing efficiently around corners to reach the player.

---

### Verification Matrix Sign-off Template

| Feature | JavaFX Run | libGDX Run | Parity Confirmed? | Notes |
|---------|------------|------------|-------------------|-------|
| F25: Ghost Visibility | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F2/F21: Loot Drops | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F1: Animated Sprites | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F24: Zombie Resurrect | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F26: Projectile Speed | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F11: Breakable Walls | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F10/20: Projectile Vars | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |
| F3-F6: Patrol & AI | [ ] Pass / [ ] Fail | [ ] Pass / [ ] Fail | [ ] Yes | |