# Technology Layman's Guide

This guide explains the three core Model-Driven Development (MDD) technologies used in MazeGame in simple, everyday terms with practical scenarios.

## Table of Contents

- [What Problem Are We Solving?](#what-problem-are-we-solving)
- [Metamodels: The Blueprint](#metamodels-the-blueprint)
- [Xtext: The Custom Language](#xtext-the-custom-language)
- [FreeMarker: The Code Factory](#freemarker-the-code-factory)
- [How They Work Together](#how-they-work-together)
- [Real-World Analogy](#real-world-analogy)
- [Related Documentation](#related-documentation)

---

## What Problem Are We Solving?

Imagine you are building a maze game with many enemy types: zombies, ghosts, pumpkin bombers. Every time you add a new enemy, you need to:

1. Define its stats (health, speed, damage)
2. Update the enemy factory code
3. Update the difficulty multiplier logic
4. Update the graphics loader
5. Update the spawn system

That is **five different places** to change for **one new enemy**. Forget one, and the game breaks.

**Model-Driven Development solves this**: Define the enemy once, and let the computer generate all the code automatically.

---

## Metamodels: The Blueprint

### In Simple Terms

A **metamodel** is like a **building blueprint** that defines what rooms can exist in a house, what furniture can go in each room, and what connections are allowed.

In MazeGame, metamodels define:
- What properties an enemy can have (health, speed, type)
- What values are allowed (health must be positive, type must be zombie/ghost/etc.)
- How things connect (a patrol behavior belongs to an enemy)

### Practical Scenario: Adding a New Enemy

Without metamodels:
```java
// You write this by hand everywhere
public class Skeleton {
    private int health = 60;
    private float speed = 1.2f;
    private String type = "skeleton";
    // ... and repeat in 5 other files
}
```

With metamodels:
```
You define in the model:
- Skeleton extends CharacterType
- health: 60
- speed: 1.2

The system knows Skeleton is valid because CharacterType
is defined in the metamodel with those exact fields.
```

### Where Metamodels Live in MazeGame

| Metamodel File | What It Defines |
|----------------|-----------------|
| `opponents.ecore` | Enemy types, stats, threat levels |
| `difficulty-module.ecore` | Difficulty settings, multipliers, enemy caps |
| `movements.ecore` | Patrol paths, behaviors, zones |
| `walls.ecore` | Wall types, materials, properties |

### Think of It Like...

A metamodel is like a **form template**. The template says "Name: ___, Age: ___, Email: ___". You cannot fill in a phone number where email should go, and you cannot skip required fields. The metamodel enforces the rules.

---

## Xtext: The Custom Language

### In Simple Terms

**Xtext** lets you create your own mini programming language. Instead of writing complex XML or Java code, game designers write simple, readable configuration files.

### Practical Scenario: Configuring a Game Level

Without Xtext (raw XML):
```xml
<game>
  <difficulty>
    <level>hard</level>
    <speedMultiplier>1.5</speedMultiplier>
    <maxThreat>100</maxThreat>
  </difficulty>
  <opponent name="Guard">
    <type>zombie</type>
    <health>75</health>
    <behavior>patrol</behavior>
  </opponent>
</game>
```

With Xtext (MazeGame DSL):
```
game DungeonLevel {
    difficulty {
        level hard
        speedMultiplier 1.5
        maxThreat 100
    }
    
    opponent Guard {
        type zombie
        health 75
        behavior patrol
    }
}
```

### What Xtext Gives You

| Feature | Benefit |
|---------|---------|
| **Syntax highlighting** | Keywords like `opponent` and `difficulty` are colored |
| **Error checking** | Red squiggles if you type `healht` instead of `health` |
| **Autocomplete** | Press Ctrl+Space to see valid options |
| **Quick fixes** | One-click solutions for common mistakes |

### Where Xtext Lives in MazeGame

| Module | Purpose |
|--------|---------|
| `main.game.maze.dsl` | The grammar rules that define valid syntax |
| `main.game.maze.dsl.ide` | Language server for editor support |
| `main.game.maze.dsl.ui` | Eclipse plugin for editing `.mazegame` files |
| `main.game.maze.dsl.tests` | Automated tests for the language |

### Think of It Like...

Xtext is like creating a **recipe format** for your family cookbook. Instead of everyone writing recipes differently ("cook until done" vs "bake 20 min at 180°C"), you define a standard format everyone follows. The format catches mistakes ("You forgot to list ingredients!") and suggests completions ("Did you mean 'tablespoon'?").

---

## FreeMarker: The Code Factory

### In Simple Terms

**FreeMarker** is a **template engine** that takes your model data and stamps out Java code automatically. It is like a mail merge for code.

### Practical Scenario: Generating Enemy Registration Code

You have this model data:
```
Enemies: Zombie, Ghost, PumpkinBomber
Each has: health, speed, threatLevel
```

FreeMarker template (`enemy-registry.ftl`):
```
public class EnemyRegistry {
    <#list enemies as enemy>
    public static final ${enemy.name} ${enemy.name?upper_case} = 
        new ${enemy.name}(${enemy.health}, ${enemy.speed}, ${enemy.threatLevel});
    </#list>
}
```

Generated output (`EnemyRegistry.java`):
```java
public class EnemyRegistry {
    public static final Zombie ZOMBIE = new Zombie(100, 1.0, 10);
    public static final Ghost GHOST = new Ghost(50, 2.0, 15);
    public static final PumpkinBomber PUMPKINBOMBER = new PumpkinBomber(75, 1.5, 20);
}
```

### What FreeMarker Gives You

| Feature | Benefit |
|---------|---------|
| **No manual coding** | Add enemy to model, code appears automatically |
| **Consistency** | All generated code follows the same pattern |
| **Single source of truth** | Change the model, regenerate, done |
| **Less bugs** | No copy-paste errors between files |

### Where FreeMarker Lives in MazeGame

| Path | Contains |
|------|----------|
| `maze-generator.freemarker/src/main/resources/templates/` | Template files (`.ftl`) |
| `maze-generator.freemarker/src/main/java/` | Generator Java classes |
| `maze-module-generator/src-gen/` | Generated output |

### Think of It Like...

FreeMarker is like a **personalized letter printer**. You write one letter template with placeholders ("Dear {NAME}, your order of {PRODUCT} ships on {DATE}"), feed it a spreadsheet of customers, and it prints 1000 personalized letters. Same idea, but for code.

---

## How They Work Together

The three technologies form a **generation pipeline**:

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   METAMODEL     │     │     XTEXT       │     │   FREEMARKER    │
│                 │     │                 │     │                 │
│  "What CAN      │────►│  "How to WRITE  │────►│  "Generate      │
│   exist"        │     │   it nicely"    │     │   the CODE"     │
│                 │     │                 │     │                 │
│  opponents.ecore│     │  .mazegame files│     │  Java classes   │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

### Complete Example: Adding a Dragon Enemy

**Step 1: Metamodel already allows it**

The `opponents.ecore` metamodel defines `CharacterType` with health, speed, threatLevel. A Dragon is just another character type.

**Step 2: Write the configuration (Xtext)**

Create or edit a `.mazegame` file:
```
game DragonDungeon {
    opponent Dragon {
        type dragon
        health 500
        speed 0.8
        threatLevel 50
        behavior chase
    }
}
```

The Xtext editor immediately validates:
- Is "dragon" a valid type? (Check against metamodel)
- Is health a positive number? (Validation rule)
- Is "chase" a valid behavior? (Cross-reference check)

**Step 3: Build generates code (FreeMarker)**

Run `mvn clean verify`. FreeMarker reads your `.mazegame` file and generates:
- `DragonDungeonFactory.java` with `createDragon()` method
- Updated `CharacterRegistrar.java` with Dragon entry
- Updated `CharacterGraphicsFactory.java` for Dragon sprites

**Step 4: Use in game**

```java
DragonDungeonFactory factory = new DragonDungeonFactory();
Opponent dragon = factory.createDragon();
game.spawn(dragon);
```

---

## Real-World Analogy

Imagine you run a **pizza restaurant**:

| Technology | Pizza Analogy |
|------------|---------------|
| **Metamodel** | The menu rules: pizzas have crust, sauce, cheese, toppings. Crust can be thin/thick. Sauce can be tomato/white. |
| **Xtext** | The order form customers fill out: "Thin crust, tomato sauce, mozzarella, pepperoni + mushrooms" |
| **FreeMarker** | The kitchen instructions printer: takes the order form and prints "1. Roll thin dough, 2. Spread tomato sauce, 3. Add mozzarella..." for the chef |

The **metamodel** ensures customers cannot order impossible pizzas (like "extra crust" or "no base").  
The **Xtext order form** catches mistakes before the kitchen sees them ("Did you mean pepperoni?").  
The **FreeMarker printer** ensures every order produces consistent kitchen instructions.

---

## Practical Benefits Summary

| Without MDD | With MDD |
|-------------|----------|
| Add enemy = change 5 files | Add enemy = change 1 file, regenerate |
| Typos cause runtime crashes | Typos caught immediately in editor |
| Copy-paste leads to inconsistencies | Templates ensure consistency |
| Documentation gets outdated | Documentation generated from model |
| New team members struggle | DSL syntax is self-documenting |

---

## Related Documentation

### Getting Started
- [DSL Tutorial](dsl-tutorial.md) - Create your first game level
- [Xtext Setup Guide](xtext-readme.md) - Build and development setup

### Reference
- [DSL Reference Guide](dsl-reference.md) - Complete syntax documentation
- [Metamodel Architecture](metamodel-architecture.md) - Technical metamodel details
- [FreeMarker Guide](../freemarker.readme.md) - Template engine details

### Technical
- [Model-Driven Code Generation Plan](../readme-mddcodegeneration.md) - Architecture overview
- [Eclipse Modules](../eclipse.modules.md) - Plugin structure
