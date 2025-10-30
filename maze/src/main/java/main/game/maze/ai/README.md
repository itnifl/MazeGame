## PatrolFollower

**File:** `PatrolFollower.java`  
**Location:** `src/main/java/main/game/maze/ai/`

The `PatrolFollower` class controls how an enemy progresses through patrol waypoints.  
It determines **which waypoint to target next**, handles **looping**, **reach detection** (with a distance tolerance), and **per-waypoint hold timers**.  
Physical movement (velocity/steering) is handled separately by the Patrol Movement Controller.

### Main Responsibilities
- Follow waypoints in order and **wrap** from the last back to the first.
- Detect waypoint reach using a configurable tolerance (ε).
- On reach: **snap** to the waypoint and **zero** velocity (applied by the controller).
- Start and track a **hold** using `PatrolPoint.time` (ms), then advance.
- Handle **single-waypoint** paths (idle there, honoring the hold each cycle).
- Provide **deterministic** behavior given the same path/tolerance/holds.

### ⚙Key Methods
- `getCurrentTarget()` → returns the current target `Position`.
- `update(x, y, currentTimeMs)` → updates hold/advance state; call each frame/tick.
- `reachedWaypoint(x, y)` → true if within ε of the target.
- `advanceWaypoint()` → increments index (mod N) for looping.
- `isHolding()` → indicates if the follower is currently in a hold.

### Typical Usage
Call `update(...)` every tick, then read the target for the movement controller:

    follower.update(enemy.getX(), enemy.getY(), System.currentTimeMillis());
    Position target = follower.getCurrentTarget();
    // Movement controller steers enemy toward 'target'

### Integration
- Consumes validated data from `PatrolHelper.PatrolDefinition` (path + optional zone).
- Feeds targets to the **Patrol Movement Controller**, which applies patrol speed,
  acceleration caps, and turn-rate limits.
