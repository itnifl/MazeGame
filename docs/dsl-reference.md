# MazeGame DSL Reference Guide

This document provides comprehensive documentation for the MazeGame Domain-Specific Language (DSL), a textual language for configuring game levels, opponents, difficulties, and behaviors.

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [Language Reference](#language-reference)
4. [Validation Rules](#validation-rules)
5. [Code Generation](#code-generation)
6. [Editor Features](#editor-features)
7. [Examples](#examples)
8. [Troubleshooting](#troubleshooting)

---

## Introduction

### What is the MazeGame DSL?

The MazeGame DSL is a custom textual language built with Eclipse Xtext that allows game designers to define game configurations using a human-readable syntax instead of editing raw XMI files.

### Benefits

| Feature | XMI Files | MazeGame DSL |
|---------|-----------|--------------|
| Readability | XML-based, verbose | Clean, intuitive syntax |
| Validation | Runtime only | Immediate editor feedback |
| Autocomplete | None | Full content assist |
| Learning curve | Requires EMF knowledge | Domain-focused keywords |
| Error messages | Technical XML errors | Meaningful game-specific errors |

### File Extension

MazeGame DSL files use the `.mazegame` extension.

---

## Getting Started

### Creating Your First Level

1. Create a new file with `.mazegame` extension
2. Start with the `game` keyword followed by a name
3. Add configuration blocks inside curly braces

```
game MyFirstLevel {
    difficulty {
        level easy
        maxThreat 20
    }
    
    opponent BasicEnemy {
        type zombie
        health 50
        threatLevel 10
    }
}
```

### Project Setup

Place your `.mazegame` files in:
```
main.game.maze.dsl/src/main/resources/levels/
```

---

## Language Reference

### Game Configuration (Root Element)

Every DSL file must have exactly one `game` declaration as the root element.

```
game <name> {
    <imports>*
    <difficulty>?
    <opponents>*
    <patrols>*
    <loot-tables>*
}
```

| Element | Required | Description |
|---------|----------|-------------|
| `name` | Yes | Unique identifier for the level (ID format) |
| `imports` | No | External file references |
| `difficulty` | No | Difficulty settings block |
| `opponents` | No | Enemy definitions |
| `patrols` | No | Patrol path definitions |
| `loot-tables` | No | Loot drop configurations |

---

### Import Statement

Import external XMI model files:

```
import "path/to/model.xmi"
```

---

### Difficulty Configuration

```
difficulty {
    level <easy|normal|hard>
    instantDeath <true>?
    speedMultiplier <DOUBLE>?
    damageMultiplier <DOUBLE>?
    maxThreat <INT>?
    <enemy-limits>*
}
```

#### Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `level` | enum | required | `easy`, `normal`, or `hard` |
| `instantDeath` | boolean | false | Player dies in one hit |
| `speedMultiplier` | double | 1.0 | Monster movement speed modifier |
| `damageMultiplier` | double | 1.0 | Monster damage modifier |
| `maxThreat` | int | varies | Maximum total threat allowed |

#### Enemy Limits

```
limit <zombie|ghost|pumpkinbomber> max <INT>
```

**Example:**
```
difficulty {
    level hard
    instantDeath true
    speedMultiplier 1.5
    damageMultiplier 2.0
    maxThreat 100
    limit zombie max 5
    limit ghost max 3
    limit pumpkinbomber max 2
}
```

---

### Opponent Configuration

```
opponent <name> {
    type <zombie|ghost|pumpkinbomber>
    displayName <STRING>?
    health <INT>?
    speed <DOUBLE>?
    threatLevel <DOUBLE>?
    enabled <true|false>?
    behavior <passive|wander|aggressive|patrol>?
    patrol <PatrolReference>?
    loot <LootTableReference>?
    <character-specifics>?
}
```

#### Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `type` | enum | required | Character type |
| `displayName` | string | null | Display name shown in game |
| `health` | int | 100 | Hit points |
| `speed` | double | 1.0 | Movement speed |
| `threatLevel` | double | 0.0 | Threat contribution (0-100) |
| `enabled` | boolean | true | Whether opponent is active |
| `behavior` | enum | WANDER | AI behavior type |
| `patrol` | reference | null | Reference to patrol path |
| `loot` | reference | null | Reference to loot table |

#### Character-Specific Blocks

**Zombie Stats:**
```
zombie-stats {
    attackDamage <INT>?
    infectionLevel <INT>?
    resurrectionTime <INT>?
}
```

| Property | Default | Description |
|----------|---------|-------------|
| `attackDamage` | 10 | Damage per hit |
| `infectionLevel` | 1 | Infection severity (1-5) |
| `resurrectionTime` | 0 | Time to resurrect (ms), 0 = no resurrection |

**Ghost Stats:**
```
ghost-stats {
    attackDamage <INT>?
    visibilityLevel <INT>?
    nonTangibilityEnergy <DOUBLE>?
}
```

| Property | Default | Description |
|----------|---------|-------------|
| `attackDamage` | 1 | Damage per hit |
| `visibilityLevel` | 100 | Visibility (0=invisible, 100=fully visible) |
| `nonTangibilityEnergy` | 100.0 | Energy for passing through walls |

**Ranged Stats (PumpkinBomber):**
```
ranged-stats {
    attackRange <DOUBLE>?
    attackCooldown <INT>?
    attackDamage <INT>?
    projectileSpeed <DOUBLE>?
    projectileType <straight|lob|beam>?
    splashRadius <DOUBLE>?
}
```

| Property | Default | Description |
|----------|---------|-------------|
| `attackRange` | 50.0 | Attack distance |
| `attackCooldown` | 10000 | Cooldown between attacks (ms) |
| `attackDamage` | 1 | Damage per projectile |
| `projectileSpeed` | 0.0 | Projectile velocity |
| `projectileType` | STRAIGHT | Projectile trajectory |
| `splashRadius` | 0.0 | Area of effect radius |

---

### Patrol Configuration

```
patrol <name> {
    visionRange <DOUBLE>?
    zone <PatrolZone>?
    path [ <waypoints> ]
}
```

#### Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `visionRange` | double | 100.0 | Detection range |
| `zone` | block | null | Optional bounding zone |
| `path` | waypoints | required | List of patrol points |

#### Patrol Zone

```
zone {
    topLeft (<X>, <Y>)
    width <DOUBLE>
    height <DOUBLE>
}
```

#### Waypoints

```
path [
    (<X>, <Y>),
    (<X>, <Y>) : <wait_time> ms,
    ...
]
```

- Coordinates are doubles
- Optional wait time in milliseconds
- Minimum 2 waypoints recommended

**Example:**
```
patrol GuardRoute {
    visionRange 150.0
    zone {
        topLeft (0, 0)
        width 500
        height 500
    }
    path [
        (50, 50),
        (450, 50) : 2000 ms,
        (450, 450),
        (50, 450) : 2000 ms
    ]
}
```

---

### Loot Table Configuration

```
loot-table <name> {
    capacity <INT>?
    <items>+
}
```

#### Loot Item

```
item <name> {
    type <food|bomb|trap|weapon>
    value <INT>
    weight <INT>?
}
```

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `type` | enum | required | Item category |
| `value` | int | required | Item value/effect strength |
| `weight` | int | 1 | Inventory weight |

**Example:**
```
loot-table TreasureChest {
    capacity 15
    
    item HealthPotion {
        type food
        value 50
        weight 1
    }
    
    item Grenade {
        type bomb
        value 75
        weight 2
    }
    
    item BearTrap {
        type trap
        value 30
        weight 3
    }
    
    item Sword {
        type weapon
        value 100
        weight 5
    }
}
```

---

## Validation Rules

The DSL enforces these rules at edit-time:

### Errors (Block Generation)

| Rule | Message |
|------|---------|
| Threat level > 100 | "Threat level cannot exceed 100" |
| Threat level < 0 | "Threat level cannot be negative" |
| Negative max threat | "Max threat cannot be negative" |
| Negative enemy limit | "Max enemy count cannot be negative" |
| Duplicate opponent name | "Duplicate opponent name: X" |
| Duplicate patrol name | "Duplicate patrol name: X" |

### Warnings

| Rule | Message |
|------|---------|
| Single waypoint patrol | "Patrol should have at least 2 waypoints" |
| Waypoint outside zone | "Waypoint (X, Y) is outside patrol zone" |
| Mismatched stats block | "zombie-stats block should only be used with zombie type" |
| Total threat exceeds max | "Total threat level (X) exceeds max threat (Y)" |

---

## Code Generation

### Generated Artifacts

When you save a `.mazegame` file, the generator creates:

#### 1. Java Factory Class

Location: `src-gen/main/game/maze/generated/<GameName>Factory.java`

```java
public class TutorialLevelFactory {
    
    public static Zombie createBasicZombie() {
        Zombie opponent = OpponentsFactory.eINSTANCE.createZombie();
        opponent.setId("BasicZombie");
        opponent.setHealth(50);
        opponent.setThreatLevel(10);
        return opponent;
    }
    
    public static PatrolBehavior createGuardRoutePatrol() {
        // Patrol configuration
    }
    
    public static Difficulty createDifficulty() {
        // Difficulty settings
    }
    
    public static List<CharacterType> createAllOpponents() {
        List<CharacterType> opponents = new ArrayList<>();
        opponents.add(createBasicZombie());
        return opponents;
    }
}
```

#### 2. XMI Model Instance

Location: `src-gen/xmi/<gamename>-config.xmi`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<opp:OpponentModel xmi:version="2.0"
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:opp="http://main.game.maze/opponents"
    name="TutorialLevel">
  <characterTypes xsi:type="opp:Zombie"
      id="BasicZombie"
      health="50"
      threatLevel="10"/>
</opp:OpponentModel>
```

#### 3. Difficulty XMI

Location: `src-gen/xmi/<gamename>-difficulty.xmi`

---

## Editor Features

### Syntax Highlighting

| Element | Color |
|---------|-------|
| Keywords | Bold blue |
| Game names | Bold dark blue |
| Opponent names | Bold red-orange |
| Patrol names | Bold green |
| Numbers | Teal |
| Enum values | Purple |
| Cross-references | Italic cyan |

### Content Assist (Ctrl+Space)

- Keyword completion
- Enum value suggestions
- Cross-reference proposals (patrol names, loot table names)
- Template snippets

### Quick Fixes (Ctrl+1)

| Issue | Available Fixes |
|-------|-----------------|
| Threat level > 100 | Set to 100, Set to 50 |
| Insufficient waypoints | Add sample waypoint |

### Outline View

Hierarchical view showing:
- Game configuration
- Difficulty settings
- All opponents with types
- All patrols with waypoint counts
- All loot tables with item counts

---

## Examples

### Minimal Level

```
game EmptyLevel {
}
```

### Tutorial Level

```
game Tutorial {
    difficulty {
        level easy
        maxThreat 20
        limit zombie max 2
    }
    
    opponent TrainingDummy {
        type zombie
        displayName "Training Dummy"
        health 25
        speed 0.5
        threatLevel 5
        behavior passive
    }
}
```

### Full Configuration

See complete examples in:
- `main.game.maze.dsl/src/main/resources/levels/tutorial.mazegame`
- `main.game.maze.dsl/src/main/resources/levels/challenge.mazegame`
- `main.game.maze.dsl/src/main/resources/levels/survival.mazegame`

---

## Troubleshooting

### Common Issues

**Issue:** Parser errors on valid-looking syntax
**Solution:** Ensure proper spacing around keywords and no trailing commas

**Issue:** Cross-references not resolving
**Solution:** Define patrols/loot-tables before referencing them

**Issue:** Generated code not updating
**Solution:** Clean the project and rebuild: `mvn clean install`

**Issue:** Editor not recognizing .mazegame files
**Solution:** Ensure the DSL plugins are installed in your Eclipse

### Getting Help

1. Check validation errors in the Problems view
2. Use Ctrl+Space for valid completions
3. Hover over elements for documentation
4. Check the [main.game.maze.dsl/readme.md](../main.game.maze.dsl/readme.md)

---

## Appendix: Grammar EBNF Summary

```ebnf
GameConfiguration ::= 'game' ID '{' Import* DifficultyConfig? OpponentConfig* PatrolConfig* LootTableConfig* '}'

Import ::= 'import' STRING

DifficultyConfig ::= 'difficulty' '{' DifficultyLevel ('instantDeath' 'true')? ('speedMultiplier' DOUBLE)? ('damageMultiplier' DOUBLE)? ('maxThreat' INT)? EnemyLimit* '}'

DifficultyLevel ::= 'level' ('easy' | 'normal' | 'hard')

EnemyLimit ::= 'limit' EnemyType 'max' INT

OpponentConfig ::= 'opponent' ID '{' 'type' CharacterType Properties* '}'

PatrolConfig ::= 'patrol' ID '{' ('visionRange' DOUBLE)? PatrolZone? 'path' '[' Waypoint (',' Waypoint)* ']' '}'

Waypoint ::= '(' DOUBLE ',' DOUBLE ')' (':' INT 'ms')?

LootTableConfig ::= 'loot-table' ID '{' ('capacity' INT)? LootItemConfig+ '}'

LootItemConfig ::= 'item' ID '{' 'type' LootItemType 'value' INT ('weight' INT)? '}'
```
