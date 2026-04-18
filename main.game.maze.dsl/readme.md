# MazeGame DSL

A textual Domain-Specific Language (DSL) for configuring MazeGame levels, opponents, difficulties, and patrol behaviors.

See also the dedicated setup and learning guide: [../docs/xtext-readme.md](../docs/xtext-readme.md)

## What is a DSL?

A **Domain-Specific Language** is a small, focused programming language designed for a specific task — in our case, configuring game levels. Unlike general-purpose languages (Java, Python), a DSL:

- Uses **vocabulary from your domain** ("opponent", "patrol", "difficulty") instead of generic programming terms
- **Restricts what you can write** to only valid configurations (you can't accidentally write a web server)
- **Provides better tooling** because the language is constrained and well-understood

**Analogy**: SQL is a DSL for database queries. HTML is a DSL for web page structure. MazeDsl is a DSL for game level configuration.

## Domain

The MazeGame DSL addresses the **game configuration domain** for a JavaFX maze game. It allows defining:

| Concept | Description | Example |
|---------|-------------|---------|
| **Difficulty** | Game difficulty settings including speed multipliers, damage scaling, instant-death mode, and threat budgets | `level hard`, `speedMultiplier 1.5`, `maxThreat 100` |
| **Opponents** | Enemy characters with type, stats, behavior patterns, and loot drops | Zombies, Ghosts, PumpkinBombers with health, speed, threat levels |
| **Patrol Behaviors** | Movement patterns for AI-controlled enemies including waypoints, timing, and vision zones | Guard routes with wait times at checkpoints |
| **Loot Tables** | Item drops from defeated enemies | Health potions, bombs, traps with weights and values |

The DSL integrates with three existing **Ecore metamodels**:
- `difficulty-module.ecore` — Difficulty scaling and enemy limits
- `opponents.ecore` — Enemy types, attributes, and behaviors  
- `movements.ecore` — Patrol paths, zones, and positions

## Value Proposition

### For Game Designers
- **Human-readable syntax** — No XML/XMI knowledge required
- **Instant feedback** — Errors highlighted as you type, not at runtime
- **Autocomplete** — Press Ctrl+Space to see valid options
- **Quick fixes** — One-click solutions for common mistakes

### For Developers
- **Type-safe code generation** — Java factory classes with compile-time checks
- **Single source of truth** — Edit `.mazegame` files, everything else is derived
- **Validation at build time** — Invalid configurations fail the Maven build
- **EMF integration** — Works with existing Ecore models and tooling

### For the Project
- **Complete MDE chain** — Metamodel → Grammar → DSL → Generated Code
- **Addresses course requirement** — Fulfills "concrete syntax and editor using Xtext"
- **Maintainable** — Grammar changes automatically update the editor

## Quick Start

### 1. Create a `.mazegame` file

```
game MyFirstLevel {
    difficulty {
        level normal
        maxThreat 80
    }
    
    opponent Guard {
        type zombie
        health 75
        behavior patrol
    }
}
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Use the generated factory

```java
// In your game code
MyFirstLevelFactory factory = new MyFirstLevelFactory();
List<Opponent> enemies = factory.createAllOpponents();
DifficultyConfig config = factory.createDifficulty();
```

## Project Structure

```
main.game.maze.dsl/          # Core grammar and runtime
main.game.maze.dsl.ide/      # Language server support
main.game.maze.dsl.ui/       # Eclipse editor integration
main.game.maze.dsl.tests/    # Automated tests
```

## DSL Syntax

### Basic Structure

```
game MyLevel {
    difficulty { ... }
    patrol PatrolName { ... }
    opponent EnemyName { ... }
    loot-table LootName { ... }
}
```

### Difficulty Configuration

```
difficulty {
    level easy | normal | hard
    instantDeath true
    speedMultiplier 1.5
    damageMultiplier 2.0
    maxThreat 100
    limit zombie max 5
    limit ghost max 3
}
```

### Opponent Definition

```
opponent ZombieGuard {
    type zombie | ghost | pumpkinbomber
    displayName "Guard Zombie"
    health 100
    speed 1.5
    threatLevel 25
    enabled true | false
    behavior passive | wander | aggressive | patrol
    patrol PatrolReference
    loot LootTableReference
    
    // Type-specific stats
    zombie-stats { attackDamage 15 infectionLevel 2 resurrectionTime 5000 }
    ghost-stats { visibilityLevel 80 nonTangibilityEnergy 200.0 }
    ranged-stats { attackRange 150.0 attackCooldown 3000 projectileType lob }
}
```

### Patrol Configuration

```
patrol GuardPath {
    visionRange 150.0
    
    zone {
        topLeft (0, 0)
        width 500
        height 500
    }
    
    path [
        (100, 100),
        (200, 100) : 2000 ms,   // Wait 2 seconds at this point
        (200, 200),
        (100, 200)
    ]
}
```

### Loot Table

```
loot-table BasicLoot {
    capacity 10
    
    item HealthPotion {
        type food | bomb | trap | weapon
        value 25
        weight 1
    }
}
```

## Generated Output

The DSL generator produces:

1. **Java Factory Class** (`*Factory.java`)
   - Methods to create each opponent
   - Methods to create patrol behaviors
   - Difficulty configuration method
   - `createAllOpponents()` convenience method

2. **XMI Model Instance** (`*-config.xmi`)
   - Opponents model conforming to `opponents.ecore`

3. **Difficulty XMI** (`*-difficulty.xmi`)
   - Difficulty settings conforming to `difficulty-module.ecore`

## Validation Rules

The DSL validates:

- Threat level bounds (0-100)
- Positive health and speed values
- Patrol paths have at least 2 waypoints
- Waypoints are within patrol zones
- No duplicate opponent/patrol names
- Character-specific blocks match character type
- Total threat doesn't exceed max threat

## Editor Features

The Eclipse editor provides:

- Syntax highlighting
- Content assist (autocomplete)
- Validation error markers
- Quick fixes for common errors
- Outline view
- Cross-reference navigation

## Example Files

See example configurations in:
- `maze/src/main/resources/levels/tutorial.mazegame`
- `maze/src/main/resources/levels/challenge.mazegame`
- `maze/src/main/resources/levels/survival.mazegame`

## Setup & Development Workflow

### First-Time Setup (Eclipse)

The DSL requires a one-time code generation step before it can be used:

1. **Import projects into Eclipse**
   - File → Import → Existing Maven Projects
   - Select the `MazeGame` root folder
   - Ensure Xtext SDK is installed (Help → Eclipse Marketplace → search "Xtext")

2. **Run the MWE2 workflow**
   - Navigate to `main.game.maze.dsl/src/main/java/main/game/maze/dsl/GenerateMazeDsl.mwe2`
   - Right-click → Run As → MWE2 Workflow
   - Wait for generation to complete (~30 seconds)

3. **Verify generated content**
   
   After MWE2 runs, `src-gen/` folders appear in each DSL project:
   ```
   main.game.maze.dsl/src-gen/          # Parser, serializer, grammar services
   main.game.maze.dsl.ide/src-gen/      # IDE bindings
   main.game.maze.dsl.ui/src-gen/       # UI components
   ```

4. **Build with Maven**
   ```bash
   mvn clean install
   ```

### When to Regenerate

| Change Type | Action Required |
|-------------|-----------------|
| Edit `MazeDsl.xtext` (grammar) | Run MWE2 workflow, then `mvn clean install` |
| Edit `MazeDslValidator.java` | Just `mvn clean install` |
| Edit `MazeDslGenerator.xtend` | Just `mvn clean install` |
| Edit `MazeDslProposalProvider.java` | Just `mvn clean install` |
| Edit `.mazegame` files | Just `mvn clean install` (or run game directly) |

**Key point**: MWE2 only needs to run when you change the grammar file (`MazeDsl.xtext`). All other changes just need a normal Maven build.

### Building

The DSL modules are built as part of the main Maven build:

```bash
mvn clean install
```

## Integration with Existing Models

The DSL references and extends:

- `difficulty-module.ecore` - Difficulty settings
- `movements.ecore` - Patrol behaviors and positions
- `opponents.ecore` - Enemy types and attributes

---

## Why Xtext Instead of Manual XML Generation?

### The Problem with Manual XMI/XML

You *could* write XMI files by hand or generate them with templates, but this approach has significant drawbacks:

#### 1. XMI is Not Human-Friendly

```xml
<!-- Manual XMI: verbose, error-prone, hard to read -->
<opp:OpponentModel xmi:version="2.0" 
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:opp="http://main.game.maze/opponents">
  <characterTypes xsi:type="opp:Zombie" 
      id="Guard1" 
      displayName="Tutorial Guard"
      health="50"
      speed="0.5"
      threatLevel="10"
      behavior="PATROL"/>
</opp:OpponentModel>
```

- Requires EMF/XML namespace knowledge
- Easy to make syntax errors
- No autocomplete or validation while editing
- Game designers can't use it

#### 2. No Immediate Feedback

With manual XMI:
- Write XML → Build project → Run game → See error at runtime
- Cycle time: **minutes**

With Xtext DSL:
- Type invalid value → See red underline **immediately**
- Cycle time: **milliseconds**

#### 3. Template-Based XML Generation is Fragile

If you generate XMI from another format using string templates:

```java
// Fragile: string concatenation, no type safety
String xmi = "<opp:Zombie id=\"" + name + "\" health=\"" + health + "\"/>";
```

- No compile-time validation
- Easy to generate malformed XML
- Changes to metamodel break templates silently

---

### What Xtext Achieves

#### 1. Domain-Specific Syntax

The DSL speaks the **game designer's language**, not the EMF implementation language:

```
// DSL: reads like game configuration
game TutorialLevel {
    opponent Guard1 {
        type zombie
        displayName "Tutorial Guard"
        health 50
        behavior patrol
    }
}
```

#### 2. Full IDE Support (Free)

Xtext generates a complete editor with:

| Feature | Manual XMI | Xtext DSL |
|---------|-----------|-----------|
| Syntax highlighting | Generic XML | Domain-aware colors |
| Autocomplete | None | `type` → suggests `zombie`, `ghost`, `pumpkinbomber` |
| Validation | Runtime only | Immediate red underlines |
| Quick fixes | None | "Set threat to 100" |
| Outline view | XML tree | Game structure (opponents, patrols) |
| Hover documentation | None | Shows property descriptions |

#### 3. Type-Safe Code Generation

Xtext parses the DSL into a **typed EMF model**, then the generator works with real objects:

```java
// Type-safe: compiler catches errors
for (OpponentConfig opponent : game.getOpponents()) {
    String type = opponent.getType().getLiteral();  // Enum, not string
    int health = opponent.getHealth();              // int, not string parsing
}
```

vs. manual template approach:

```java
// Fragile: runtime errors
String type = xmlElement.getAttribute("type");  // Could be anything
int health = Integer.parseInt(xmlElement.getAttribute("health"));  // Could fail
```

#### 4. Bidirectional Mapping

Xtext provides:
- **Parsing**: `.mazegame` → EMF model (automatic)
- **Serialization**: EMF model → `.mazegame` (automatic)
- **Generation**: EMF model → Java code, XMI files (your templates)

This means the DSL is the **single source of truth**, and everything else is derived.

---

### The MDE Value Chain

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   .mazegame     │────▶│   EMF Model     │────▶│  Generated Code │
│   (DSL file)    │     │   (in memory)   │     │  + XMI files    │
└─────────────────┘     └─────────────────┘     └─────────────────┘
        │                       │                       │
   Human edits             Validation              Runtime uses
   with IDE support        (immediate)            generated output
```

**Without Xtext**: You'd manually maintain XMI files AND keep them synchronized with code. Any change requires updating multiple places.

**With Xtext**: Edit the DSL → Everything else is derived automatically.

---

### Addressing MDE Best Practices

The Xtext DSL establishes a proper MDE workflow:

1. **Provides a true concrete syntax** (not just XMI editing)
2. **Generates meaningful application code** (factories, configurations) from model instances
3. **Establishes a proper metamodel → model → code chain**

The workflow is now:

```
Ecore metamodels (difficulties, opponents, behaviour)
        ↓
   Xtext grammar imports/references these
        ↓
   .mazegame files (model instances in DSL syntax)
        ↓
   Generator produces Java factories + XMI
        ↓
   Game uses generated factories
```

This is the **true MDE approach**: models drive code generation, not the other way around.

---

## Glossary

| Term | Definition |
|------|------------|
| **DSL** | Domain-Specific Language — a small language designed for a specific purpose |
| **Xtext** | Eclipse framework for building DSLs with full IDE support |
| **Ecore** | Eclipse's metamodeling language — defines the structure of your models |
| **EMF** | Eclipse Modeling Framework — runtime for working with Ecore models |
| **Metamodel** | A "model of models" — defines what elements can exist (like a schema) |
| **Model Instance** | A concrete configuration that conforms to a metamodel |
| **XMI** | XML Metadata Interchange — standard XML format for serializing EMF models |
| **MWE2** | Modeling Workflow Engine 2 — runs Xtext's code generation |
| **Grammar** | The `.xtext` file that defines DSL syntax rules |
| **Validator** | Code that checks models for semantic errors beyond syntax |
| **Generator** | Code that transforms DSL models into output (Java, XMI, etc.) |
| **Content Assist** | IDE feature that suggests completions as you type (autocomplete) |
| **Quick Fix** | IDE feature that offers one-click solutions to errors |

---

## Learning Resources

### Xtext
- 📘 [Xtext Documentation](https://eclipse.dev/Xtext/documentation/) — Official guide (start here)
- 📺 [Xtext 15-Minute Tutorial](https://eclipse.dev/Xtext/documentation/102_domainmodelwalkthrough.html) — Build your first DSL
- 📖 [Implementing Domain-Specific Languages with Xtext and Xtend](https://www.packtpub.com/product/implementing-domain-specific-languages-with-xtext-and-xtend-second-edition/9781786464965) — Book by Lorenzo Bettini

### EMF & Ecore
- 📘 [EMF Documentation](https://eclipse.dev/modeling/emf/docs/) — Eclipse Modeling Framework guide
- 📺 [EMF Tutorial](https://eclipsesource.com/blogs/tutorials/emf-tutorial/) — Practical introduction
- 📖 [EMF: Eclipse Modeling Framework](https://www.pearson.com/en-us/subject-catalog/p/emf-eclipse-modeling-framework/P200000009300) — The definitive book by Dave Steinberg et al.

### Model-Driven Engineering
- 📘 [MDE Basics](https://modeling-languages.com/model-driven-engineering-mde-mda-basics/) — Introduction to MDE concepts
- 📺 [What is Model-Driven Development?](https://www.youtube.com/watch?v=5Ks8v7hR_Mw) — Video explanation

### This Project
- 📄 [DSL Reference Guide](../docs/dsl-reference.md) — Complete syntax reference for MazeDsl
- 📄 [DSL Tutorial](../docs/dsl-tutorial.md) — Step-by-step guide to creating game levels
- 📄 [Project Demo](../demo.md) — Demonstration script showing the DSL in action
- 📄 [Technology Layman's Guide](../docs/technology-laymans-guide.md) — Simple explanation of Xtext, metamodels, and FreeMarker

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](../docs/technology-laymans-guide.md) | Simple explanation of Xtext, metamodels, and FreeMarker in everyday terms |
| [DSL Reference Guide](../docs/dsl-reference.md) | Complete syntax reference for MazeDsl |
| [DSL Tutorial](../docs/dsl-tutorial.md) | Step-by-step guide to creating game levels |
| [Xtext Setup Guide](../docs/xtext-readme.md) | Build and development setup for the DSL |
| [Metamodel Architecture](../docs/metamodel-architecture.md) | Technical details about metamodels and Xtext integration |
| [MDD Code Generation](../docs/mdd-code-generation.md) | Code generation plan with FreeMarker |

### DSL Module Documentation

| Module | Description |
|--------|-------------|
| [DSL IDE Module](../main.game.maze.dsl.ide/readme.md) | Language server support for LSP-compatible editors |
| [DSL UI Module](../main.game.maze.dsl.ui/readme.md) | Eclipse-specific editor integration |
| [DSL Tests Module](../main.game.maze.dsl.tests/readme.md) | Automated test suite |
