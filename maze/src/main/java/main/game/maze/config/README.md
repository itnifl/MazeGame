## PatrolHelper Usage

`PatrolHelper` is a utility class used to **load and validate patrol configurations** defined in the EMF model (`PatrolBehavior`).  
It converts model data into a clean runtime object (`PatrolDefinition`) that the game’s AI can use.

### How to Use

```java
import main.game.maze.config.PatrolHelper;
import main.game.maze.config.PatrolHelper.PatrolDefinition;
import mazeidea.movements.PatrolBehavior;

double mapWidth = 20.0;
double mapHeight = 20.0;

// patrolBehavior is an EMF instance loaded from the model or .xmi file
PatrolDefinition patrol = PatrolHelper.fromModel(patrolBehavior, mapWidth, mapHeight);
```

### What It Does

- Ensures at least one of `patrolPath` or `patrolZone` is defined.  
- Checks all waypoints and zone coordinates are inside map bounds.  
- Collapses duplicate consecutive waypoints.  
- Clamps negative `holdMsPerWaypoint` values to 0.  
- Logs the effective patrol definition (path size, zone size, and corrections).

### Output

`PatrolHelper.fromModel()` returns a `PatrolDefinition` containing:

- `getPath()` → list of validated `PatrolPoint`s (the patrol route).  
- `getZone()` → optional `PatrolZone` (rectangular patrol area).  

This object is later used by the patrol movement and AI systems at runtime.