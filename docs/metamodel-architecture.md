# Metamodel Architecture and Xtext Integration

This document explains how Xtext and Ecore metamodels work together in the MazeGame project, covering the architecture, relationships, and build flow.

## Table of Contents

- [Understanding Meta-Levels](#understanding-meta-levels)
- [How Xtext Fits In](#how-xtext-fits-in)
- [Domain Metamodels](#domain-metamodels)
- [Metamodel Relationships](#metamodel-relationships)
- [Build and Generation Flow](#build-and-generation-flow)
- [File Locations](#file-locations)

## Understanding Meta-Levels

Model-Driven Engineering (MDE) uses a layered approach to define domain concepts. This project follows the standard four-level architecture:

```mermaid
graph TB
    subgraph M3["M3 - Meta-Metamodel"]
        ecore["Ecore<br>(Eclipse Modeling Framework)"]
    end
    
    subgraph M2["M2 - Metamodels"]
        opponents["opponents.ecore"]
        difficulty["difficulty-module.ecore"]
        movements["movements.ecore"]
        walls["walls.ecore"]
        mazeDsl["mazeDsl.ecore<br>(Xtext-generated)"]
    end
    
    subgraph M1["M1 - Models (Instances)"]
        oppXmi["OpponentModel.xmi"]
        diffXmi["difficulties.xmi"]
        wallsXmi["walls.xmi"]
        mazegame[".mazegame files"]
    end
    
    subgraph M0["M0 - Runtime Objects"]
        java["Java Objects<br>(Zombie, Ghost, etc.)"]
    end
    
    ecore --> opponents
    ecore --> difficulty
    ecore --> movements
    ecore --> walls
    ecore --> mazeDsl
    
    opponents --> oppXmi
    difficulty --> diffXmi
    walls --> wallsXmi
    mazeDsl --> mazegame
    
    oppXmi --> java
    diffXmi --> java
    wallsXmi --> java
    mazegame --> java
```

| Level | Name | Role | Examples in This Project |
|-------|------|------|--------------------------|
| **M3** | Meta-Metamodel | Defines how metamodels are structured | Ecore (from Eclipse EMF) |
| **M2** | Metamodel | Defines structure of domain models | `opponents.ecore`, `difficulty-module.ecore` |
| **M1** | Model | Instances conforming to a metamodel | `OpponentModel.xmi`, `.mazegame` files |
| **M0** | Runtime | Live objects in the running application | Java instances of `Zombie`, `Ghost` |

## How Xtext Fits In

Xtext provides a **textual concrete syntax** for defining game configurations. Rather than editing XML/XMI files directly, developers write human-readable `.mazegame` files.

### The Dual Metamodel Architecture

This project has two categories of metamodels working together:

```mermaid
flowchart LR
    subgraph TextualSyntax["Textual Syntax Layer"]
        grammar["MazeDsl.xtext<br>(Grammar Rules)"]
        generated["mazeDsl.ecore<br>(Auto-Generated)"]
        mazegame[".mazegame Files"]
    end
    
    subgraph DomainLayer["Domain Metamodel Layer"]
        opp["opponents.ecore"]
        diff["difficulty-module.ecore"]
        mov["movements.ecore"]
        walls["walls.ecore"]
    end
    
    subgraph OutputLayer["Generated Outputs"]
        factory["Factory Classes<br>(Java)"]
        xmi["XMI Instances"]
    end
    
    grammar -->|"Xtext generates"| generated
    mazegame -->|"parsed via"| generated
    mazegame -->|"MazeDslGenerator<br>transforms to"| factory
    mazegame -->|"MazeDslGenerator<br>transforms to"| xmi
    xmi -->|"conforms to"| opp
    xmi -->|"conforms to"| diff
```

**Category 1: Xtext-Generated Metamodel**

The `MazeDsl.xtext` grammar file defines the textual syntax. When Xtext processes it, it automatically generates a `mazeDsl.ecore` metamodel that represents the grammar structure.

**Category 2: Domain Metamodels**

The handcrafted `.ecore` files define the actual game domain concepts (opponents, difficulties, behaviors, walls). These are the "target" metamodels that the DSL ultimately populates.

### Transformation Flow

```mermaid
sequenceDiagram
    participant Dev as Developer
    participant DSL as .mazegame File
    participant Parser as Xtext Parser
    participant AST as mazeDsl AST
    participant Gen as MazeDslGenerator
    participant XMI as XMI Files
    participant Java as Factory Classes
    
    Dev->>DSL: Writes game configuration
    DSL->>Parser: Parse textual syntax
    Parser->>AST: Build AST from grammar
    AST->>Gen: Invoke generator
    Gen->>XMI: Generate XMI instances<br>(opponents, difficulty)
    Gen->>Java: Generate factory classes
```

### Example: From DSL to Domain Model

**Input: tutorial.mazegame**
```
game TutorialLevel {
    difficulty {
        level easy
        maxThreat 30
        speedMultiplier 0.8
    }
    
    opponent TutorialZombie {
        type zombie
        displayName "Slow Zombie"
        health 50
        speed 0.5
        threatLevel 10
        behavior patrol
        
        zombie-stats {
            attackDamage 5
            infectionLevel 1
        }
    }
}
```

**Output: Generated XMI conforming to opponents.ecore**
```xml
<opp:OpponentModel name="TutorialLevel">
    <characterTypes xsi:type="opp:Zombie"
        id="TutorialZombie"
        displayName="Slow Zombie"
        health="50"
        speed="0.5"
        threatLevel="10"
        behavior="PATROL"
        attackDamage="5"
        infectionLevel="1"/>
</opp:OpponentModel>
```

## Domain Metamodels

### Overview

The project defines four domain metamodels, each in its own OSGi bundle:

```mermaid
classDiagram
    direction TB
    
    class opponents_ecore {
        <<metamodel>>
        nsURI: http://main.game.maze/opponents
    }
    
    class difficulty_ecore {
        <<metamodel>>
        nsURI: http://main.game.maze/difficulties
    }
    
    class movements_ecore {
        <<metamodel>>
        nsURI: http://main.game.maze/behaviour
    }
    
    class walls_ecore {
        <<metamodel>>
        nsURI: http://main.game.maze/walls
    }
    
    opponents_ecore --> difficulty_ecore : references selectedDifficulty
    movements_ecore --> opponents_ecore : references charactertype
```

### opponents.ecore

Defines enemy character types and their properties.

```mermaid
classDiagram
    class OpponentModel {
        +String name
        +int maxThreat [derived]
        +double gameSetCurrentThreatLevel [derived]
    }
    
    class CharacterType {
        <<abstract>>
        +String id
        +String displayName
        +boolean enabled
        +int health
        +double speed
        +double threatLevel
        +BehaviorType behavior
    }
    
    class Zombie {
        +int attackDamage
        +int infectionLevel
        +int resurrectionTime
    }
    
    class Ghost {
        +int attackDamage
        +int visibilityLevel
        +double nonTangibilityEnergy
    }
    
    class RangedEnemy {
        <<abstract>>
        +double attackRange
        +int attackCooldownMs
        +int attackDamage
        +double projectileSpeed
        +ProjectileType projectileType
    }
    
    class PumpkinBomber
    
    class LootTable {
        +int weightCapacity
    }
    
    class LootItem {
        +String name
        +LootItemType type
        +int value
        +int weight
    }
    
    OpponentModel "1" *-- "*" CharacterType : characterTypes
    CharacterType <|-- Zombie
    CharacterType <|-- Ghost
    CharacterType <|-- RangedEnemy
    RangedEnemy <|-- PumpkinBomber
    Zombie --> LootTable : zombieLootTable
    LootTable "1" *-- "*" LootItem : items
```

**OCL Constraints:**
- `validateMaxThreat`: Sum of character threat levels must not exceed `maxThreat`

### difficulty-module.ecore

Defines difficulty levels and their modifiers.

```mermaid
classDiagram
    class DifficultyGameData {
        +Difficulty currentDifficulty
    }
    
    class Difficulty {
        <<abstract>>
        +boolean instantDeath
        +double monstersMovementSpeedMultiplier
        +double monstersDamageMultiplier
        +int maxThreat
    }
    
    class EasyDifficulty
    class NormalDifficulty
    class HardDifficulty
    
    class EnemyMaxCount {
        +EnemyTypes type
        +int maxCount
    }
    
    DifficultyGameData "1" *-- "*" Difficulty : difficulties
    Difficulty <|-- EasyDifficulty
    Difficulty <|-- NormalDifficulty
    Difficulty <|-- HardDifficulty
    Difficulty "1" *-- "*" EnemyMaxCount : enemyMaxCount
```

### movements.ecore

Defines movement behaviors and patrol patterns.

```mermaid
classDiagram
    class MovementBehavior {
        <<abstract>>
        +boolean ignoreWalls [derived]
        +boolean instantKillOnCollision [derived]
        +double visionRange [derived]
        +move()
        +update(HealthEvent)
        +update(SpeedEvent)
    }
    
    class RandomBehavior
    class PatrolBehavior
    
    class Position {
        +double posX
        +double posY
    }
    
    class Direction {
        +Position startPosition
        +Position endPosition
    }
    
    class PatrolPoint {
        +int waitTimeMs
    }
    
    class PatrolZone {
        +double width
        +double height
    }
    
    MovementBehavior <|-- RandomBehavior
    MovementBehavior <|-- PatrolBehavior
    MovementBehavior --> CharacterType : charactertype
    PatrolBehavior *-- PatrolPoint : waypoints
    PatrolBehavior --> PatrolZone : zone
    PatrolPoint --> Position : position
    PatrolZone --> Position : topLeft
```

**OCL Constraints:**
- `ValidVisionRange`: For ranged enemies, attack range must not exceed vision range
- `PositivePositions`: Position coordinates must be non-negative

### walls.ecore

Defines wall materials and properties.

```mermaid
classDiagram
    class WallModel {
        +WallMaterial[] materials
    }
    
    class WallMaterial {
        +WallMaterialBaseType wallBaseType
        +String displayName
        +boolean breakable
        +int hitPoints
        +String baseImage
    }
    
    class WallMaterialBaseType {
        <<enumeration>>
        GLASS
        DIRT
        WOOD
        STONE
        STEEL
    }
    
    WallModel "1" *-- "*" WallMaterial : materials
    WallMaterial --> WallMaterialBaseType
```

**OCL Constraints:**
- `ValidHitPoints`: Breakable walls must have hitPoints > 0; unbreakable walls must have hitPoints = 0

## Metamodel Relationships

The metamodels form a dependency graph through cross-references:

```mermaid
flowchart TB
    subgraph core["Core Domain"]
        diff["difficulty-module.ecore<br>Difficulty Settings"]
        walls["walls.ecore<br>Wall Materials"]
    end
    
    subgraph characters["Character Domain"]
        opp["opponents.ecore<br>Enemy Types"]
    end
    
    subgraph behavior["Behavior Domain"]
        mov["movements.ecore<br>Movement Patterns"]
    end
    
    subgraph syntax["Textual Syntax"]
        dsl["MazeDsl.xtext<br>Grammar"]
        mazeDsl["mazeDsl.ecore<br>(Generated)"]
    end
    
    opp -->|"selectedDifficulty"| diff
    mov -->|"charactertype"| opp
    dsl -->|"generates"| mazeDsl
    dsl -.->|"maps to concepts in"| opp
    dsl -.->|"maps to concepts in"| diff
    dsl -.->|"maps to concepts in"| mov
```

### Import Declarations in Ecore

**opponents.ecore imports:**
```xml
<eAnnotations source="http://www.eclipse.org/OCL/Import">
    <details key="diff" value="platform:/resource/main.game.maze.difficulties/..."/>
    <details key="ecore" value="http://www.eclipse.org/emf/2002/Ecore"/>
</eAnnotations>
```

**movements.ecore imports:**
```xml
<eAnnotations source="http://www.eclipse.org/OCL/Import">
    <details key="opp" value="...opponents.ecore#/"/>
    <details key="ecore" value="http://www.eclipse.org/emf/2002/Ecore"/>
</eAnnotations>
```

## Build and Generation Flow

### Complete Build Pipeline

```mermaid
flowchart TB
    subgraph phase1["Phase 1: Xtext Generation"]
        grammar["MazeDsl.xtext"]
        mwe2["GenerateMazeDsl.mwe2"]
        xtext_gen["Xtext Artifacts<br>(parser, validator, etc.)"]
        grammar --> mwe2 --> xtext_gen
    end
    
    subgraph phase2["Phase 2: Compile"]
        compile["Compile Java Sources"]
        xtext_gen --> compile
    end
    
    subgraph phase3["Phase 3: DSL Processing"]
        mazegame[".mazegame Files"]
        dsl_gen["MazeDslGenerator"]
        xmi["XMI Instances"]
        factory["Factory Classes"]
        mazegame --> dsl_gen
        dsl_gen --> xmi
        dsl_gen --> factory
    end
    
    subgraph phase4["Phase 4: FreeMarker"]
        ecore_files["Ecore Metamodels"]
        xmi_files["XMI Model Files"]
        freemarker["FreeMarker Templates"]
        src_gen["src-gen/*.java<br>(Registry, Factories)"]
        ecore_files --> freemarker
        xmi_files --> freemarker
        freemarker --> src_gen
    end
    
    subgraph phase5["Phase 5: Package"]
        osgi["OSGi Bundles<br>(Tycho)"]
        p2["P2 Repository"]
        osgi --> p2
    end
    
    compile --> phase3
    phase3 --> phase4
    phase4 --> phase5
```

### Maven Build Commands

```bash
# Step 1: Generate Xtext artifacts (requires Java 21)
mvn -f main.game.maze.dsl/pom.xml generate-sources -DskipTests

# Step 2: Full build with all modules
mvn -U clean verify
```

### MWE2 Workflow Configuration

The `GenerateMazeDsl.mwe2` file configures Xtext generation:

```
module main.game.maze.dsl.GenerateMazeDsl

Workflow {
    component = XtextGenerator {
        configuration = {
            project = StandardProjectConfig {
                baseName = "main.game.maze.dsl"
                runtimeTest = { enabled = true }
                eclipsePlugin = { enabled = true }
                genericIde = { enabled = true }
            }
        }
        language = StandardLanguage {
            name = "main.game.maze.dsl.MazeDsl"
            fileExtensions = "mazegame"
            validator = {
                composedCheck = "org.eclipse.xtext.validation.NamesAreUniqueValidator"
            }
        }
    }
}
```

## File Locations

### Metamodels (M2)

| Metamodel | Location |
|-----------|----------|
| opponents.ecore | `main.game.maze.opponents/src/main/resources/opponents.ecore` |
| difficulty-module.ecore | `main.game.maze.difficulties/src/main/resources/difficulty-module.ecore` |
| movements.ecore | `main.game.maze.behaviour/src/main/resources/movements/movements.ecore` |
| walls.ecore | `main.game.maze.walls/model/walls.ecore` |

### DSL Components

| Component | Location |
|-----------|----------|
| Grammar | `main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext` |
| MWE2 Workflow | `main.game.maze.dsl/src/main/java/main/game/maze/dsl/GenerateMazeDsl.mwe2` |
| Generator | `main.game.maze.dsl/src/main/java/main/game/maze/dsl/generator/MazeDslGenerator.java` |
| Validator | `main.game.maze.dsl/src/main/java/main/game/maze/dsl/validation/MazeDslValidator.java` |

### Model Instances (M1)

| Model | Location |
|-------|----------|
| OpponentModel.xmi | `main.game.maze.opponents/src/main/resources/OpponentModel.xmi` |
| difficulties.xmi | `maze/src/main/resources/xmi/difficulties/difficulties.xmi` |
| walls.xmi | `main.game.maze.walls/xmi/walls.xmi` |
| DSL levels | `maze/src/main/resources/levels/*.mazegame` |

### Code Generation Templates

| Template Category | Location |
|-------------------|----------|
| Opponent templates | `maze-generator.acceleo/src/main/resources/templates/opponents/` |
| Wall templates | `maze-generator.acceleo/src/main/resources/templates/walls/` |

## Summary

This architecture provides:

1. **Separation of Concerns**: Domain metamodels are independent of the textual syntax
2. **Multiple Concrete Syntaxes**: XMI for tools, `.mazegame` for developers
3. **Validation at Multiple Levels**: OCL constraints in Ecore, custom validators in Xtext
4. **Extensibility**: New enemy types, behaviors, or difficulty levels can be added by extending the metamodels
5. **Code Generation**: Automated generation of boilerplate code from model definitions

The Xtext DSL serves as a developer-friendly entry point that ultimately populates the same domain models as direct XMI editing, but with syntax checking, content assist, and validation built into the editing experience.
