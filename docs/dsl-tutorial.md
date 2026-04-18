# MazeGame DSL Tutorial

Learn how to create game levels using the MazeGame Domain-Specific Language step by step.

## Prerequisites

- Eclipse IDE with Xtext support installed
- MazeGame project imported
- DSL plugins built and installed

---

## Part 1: Your First Level

### Step 1: Create a New File

1. Navigate to `maze/src/main/resources/levels/`
2. Create a new file named `myfirst.mazegame`

### Step 2: Add the Game Declaration

Every level starts with a `game` declaration:

```
game MyFirstLevel {
}
```

Save the file. You should see no errors.

### Step 3: Add Difficulty Settings

Inside the game block, add difficulty configuration:

```
game MyFirstLevel {
    difficulty {
        level easy
        maxThreat 30
    }
}
```

**Try this:** Change `easy` to `normal` or `hard` and observe content assist (Ctrl+Space) suggestions.

### Step 4: Add Your First Enemy

Add a simple zombie opponent:

```
game MyFirstLevel {
    difficulty {
        level easy
        maxThreat 30
    }
    
    opponent Zombie1 {
        type zombie
        displayName "Slow Zombie"
        health 50
        speed 0.5
        threatLevel 10
    }
}
```

**Validation:** Notice that `threatLevel 10` is within the `maxThreat 30` limit.

### Step 5: Test Validation

Try setting an invalid threat level:

```
opponent Zombie1 {
    type zombie
    threatLevel 150    // Error: exceeds 100
}
```

You should see a red error marker. Use the quick fix (Ctrl+1) to correct it.

---

## Part 2: Patrol Behaviors

### Step 1: Create a Basic Patrol

Add a patrol path for your zombie:

```
game MyFirstLevel {
    difficulty {
        level easy
        maxThreat 30
    }
    
    patrol SquareRoute {
        path [
            (50, 50),
            (200, 50),
            (200, 200),
            (50, 200)
        ]
    }
    
    opponent Zombie1 {
        type zombie
        threatLevel 10
        behavior patrol
        patrol SquareRoute
    }
}
```

**Key points:**
- Define patrols before referencing them
- Use `behavior patrol` to enable patrol movement
- Reference the patrol by name

### Step 2: Add Wait Times

Make the zombie pause at certain points:

```
patrol SquareRoute {
    visionRange 100.0
    path [
        (50, 50) : 2000 ms,     // Wait 2 seconds
        (200, 50),
        (200, 200) : 3000 ms,   // Wait 3 seconds
        (50, 200)
    ]
}
```

### Step 3: Add a Patrol Zone

Constrain the patrol to a specific area:

```
patrol SquareRoute {
    visionRange 100.0
    zone {
        topLeft (0, 0)
        width 250
        height 250
    }
    path [
        (50, 50),
        (200, 50),
        (200, 200),
        (50, 200)
    ]
}
```

**Validation:** If you add a waypoint outside the zone, you'll see a warning.

---

## Part 3: Multiple Enemy Types

### Step 1: Add a Ghost

```
opponent Spooky {
    type ghost
    displayName "Haunting Spirit"
    health 40
    speed 1.5
    threatLevel 15
    behavior wander
    
    ghost-stats {
        visibilityLevel 60
        nonTangibilityEnergy 200.0
    }
}
```

**Note:** Use `ghost-stats` for ghost-specific properties.

### Step 2: Add a Ranged Enemy

```
opponent Bomber {
    type pumpkinbomber
    displayName "Pumpkin Thrower"
    health 75
    speed 0.7
    threatLevel 20
    behavior aggressive
    
    ranged-stats {
        attackRange 150.0
        attackCooldown 4000
        attackDamage 25
        projectileType lob
        splashRadius 40.0
    }
}
```

### Step 3: Check Total Threat

Your level now has:
- Zombie1: 10 threat
- Spooky: 15 threat  
- Bomber: 20 threat
- **Total: 45 threat**

Since `maxThreat` is 30, you'll see a warning. Increase it:

```
difficulty {
    level easy
    maxThreat 50    // Increased to accommodate all enemies
}
```

---

## Part 4: Loot Tables

### Step 1: Create a Loot Table

```
loot-table BasicDrops {
    capacity 10
    
    item HealthPotion {
        type food
        value 25
        weight 1
    }
    
    item SmokeBomb {
        type bomb
        value 30
        weight 2
    }
}
```

### Step 2: Assign to Enemies

```
opponent Zombie1 {
    type zombie
    threatLevel 10
    loot BasicDrops
}
```

---

## Part 5: Complete Example

Here's the complete level we built:

```
/*
 * My First Level - Tutorial Result
 */
game MyFirstLevel {
    
    difficulty {
        level easy
        maxThreat 50
        speedMultiplier 0.8
        limit zombie max 3
        limit ghost max 2
        limit pumpkinbomber max 1
    }
    
    // Patrol routes
    patrol SquareRoute {
        visionRange 100.0
        zone {
            topLeft (0, 0)
            width 250
            height 250
        }
        path [
            (50, 50) : 2000 ms,
            (200, 50),
            (200, 200) : 3000 ms,
            (50, 200)
        ]
    }
    
    patrol CenterGuard {
        visionRange 150.0
        path [
            (250, 200),
            (250, 300)
        ]
    }
    
    // Loot
    loot-table BasicDrops {
        capacity 10
        
        item HealthPotion {
            type food
            value 25
            weight 1
        }
        
        item SmokeBomb {
            type bomb
            value 30
            weight 2
        }
    }
    
    // Enemies
    opponent Zombie1 {
        type zombie
        displayName "Slow Zombie"
        health 50
        speed 0.5
        threatLevel 10
        behavior patrol
        patrol SquareRoute
        loot BasicDrops
        
        zombie-stats {
            attackDamage 8
            infectionLevel 1
        }
    }
    
    opponent Spooky {
        type ghost
        displayName "Haunting Spirit"
        health 40
        speed 1.5
        threatLevel 15
        behavior wander
        
        ghost-stats {
            visibilityLevel 60
            nonTangibilityEnergy 200.0
        }
    }
    
    opponent Bomber {
        type pumpkinbomber
        displayName "Pumpkin Thrower"
        health 75
        speed 0.7
        threatLevel 20
        behavior patrol
        patrol CenterGuard
        
        ranged-stats {
            attackRange 150.0
            attackCooldown 4000
            attackDamage 25
            projectileType lob
            splashRadius 40.0
        }
    }
}
```

---

## Part 6: Using Generated Code

After saving your `.mazegame` file, check the generated output:

### Generated Factory Class

```java
// In your game code:
import main.game.maze.generated.MyFirstLevelFactory;

// Create all opponents
List<CharacterType> enemies = MyFirstLevelFactory.createAllOpponents();

// Create individual opponent
Zombie zombie = MyFirstLevelFactory.createZombie1();

// Create patrol behavior
PatrolBehavior patrol = MyFirstLevelFactory.createSquareRoutePatrol();

// Get difficulty settings
Difficulty difficulty = MyFirstLevelFactory.createDifficulty();
```

### Generated XMI Files

The generator also creates:
- `myfirstlevel-config.xmi` - Opponents model
- `myfirstlevel-difficulty.xmi` - Difficulty settings

These can be loaded directly by EMF:

```java
ResourceSet resourceSet = new ResourceSetImpl();
Resource resource = resourceSet.getResource(
    URI.createURI("levels/myfirstlevel-config.xmi"), true);
OpponentModel model = (OpponentModel) resource.getContents().get(0);
```

---

## Exercises

### Exercise 1: Create a Boss Fight Level

Create a level with:
- Hard difficulty
- One high-health zombie boss (health 500, threat 40)
- Two ghost minions (threat 15 each)
- Max threat of 80

### Exercise 2: Complex Patrol

Create a patrol that:
- Has 6 waypoints forming a star pattern
- Has 3-second waits at alternating points
- Is constrained to a 400x400 zone

### Exercise 3: Balanced Difficulty

Create three versions of the same level:
- `easy.mazegame` - Low enemy counts, slow speeds
- `normal.mazegame` - Balanced configuration
- `hard.mazegame` - High enemy counts, fast speeds, instant death

---

## Next Steps

- Read the [DSL Reference Guide](dsl-reference.md) for complete syntax documentation
- Study the example files in `maze/src/main/resources/levels/`
- Explore the generated code to understand the mapping
- Modify the generator to add custom output formats
