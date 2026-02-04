# maze-generator.acceleo

> **Model-Driven Code Generation for MazeGame**

This project uses [Acceleo](https://eclipse.dev/acceleo/) to automatically generate Java code from EMF (Eclipse Modeling Framework) models. Instead of writing repetitive boilerplate code by hand, we define models once and let Acceleo generate the implementation.

---

## ⚡ Quick Start

**Already familiar with the project?** Here's the fast path:

```bash
# 1. Make changes to EMF model (e.g., add new character type to opponents.ecore)

# 2. Regenerate code
mvn -pl maze-module-generator -am clean verify

# 3. Run tests to validate
mvn -pl maze-module-generator test

# 4. Check generated output in maze-module-generator/src-gen/
```

**New to code generation?** Read the sections below to understand why and how we use it.

---

## 🎯 What is This Project?

### The Problem It Solves

In game development, you often have many similar types of objects (enemies, walls, difficulty levels) that need:
- Switch statements to handle each type differently
- Factory methods to create instances
- Registration code to track all types

**Without code generation**, every time you add a new enemy type, you must manually update 5-10 different files. This is error-prone and tedious.

**With code generation**, you add the new enemy to the model file, run the generator, and all the boilerplate code is created automatically.

### The Value of Model-Driven Engineering (MDE)

| Approach | Adding a New Enemy Type |
|----------|------------------------|
| **Manual** | Edit `OpponentRuntimeFactory.java`, `CharacterRegistrar.java`, `GraphicsFactory.java`, etc. (5+ files) |
| **MDE** | Add to `opponents.ecore` model → Run generator → Done! |

This is called **Model-Driven Engineering** - the model is the single source of truth.

### When to Use Code Generation vs. Manual Code

| Scenario | Use Code Generation? | Reason |
|----------|---------------------|--------|
| Adding a new enemy type | ✅ Yes | Automatically updates registrar, factory, dispatcher |
| Changing enemy base stats | ✅ Yes | Model is the source of truth for data |
| Adding complex game logic | ❌ No | Hand-written code is more flexible for unique logic |
| Creating one-off utility class | ❌ No | No repetitive pattern to automate |
| Adding new attribute to all enemies | ✅ Yes | Template updates all generated code at once |
| Debugging runtime behavior | ❌ No | Debug hand-written code that uses generated APIs |

**Rule of thumb**: If you're about to copy-paste similar code for each type, consider generating it instead.

---

## 📁 Project Structure

```
maze-generator.acceleo/
├── src/main/java/main/game/maze/gen/
│   ├── templates/
│   │   ├── Generate.mtl          # Main template for opponents
│   │   ├── GenerateWalls.mtl     # Template for walls
│   │   ├── Generate.emtl         # Compiled template (binary)
│   │   └── GenerateWalls.emtl    # Compiled template (binary)
│   └── RunAcceleo.java           # Standalone runner class
├── META-INF/
│   └── MANIFEST.MF               # Eclipse plugin manifest
├── plugin.xml                    # Plugin configuration
└── pom.xml                       # Maven build file
```

### Key File Types

| Extension | Purpose |
|-----------|---------|
| `.mtl` | **M**odel **T**o **L**anguage - Acceleo template source files |
| `.emtl` | Compiled Acceleo templates (like `.class` for `.java`) |
| `.ecore` | EMF model definitions (the "schema" for your data) |
| `.xmi` | Model instances (actual data conforming to the schema) |

---

## 🔧 What Gets Generated

The templates generate Java classes that would otherwise be hand-written boilerplate.

### Current Generated Files (in `maze-module-generator/src-gen/`)

These files are currently generated and tested:

| Generated File | Purpose | EMF Methods Used |
|---------------|---------|------------------|
| `CharacterRegistrar.java` | Registers and looks up character types | `getName()`, `getIdentifier()` |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers per type | `getThreatLevel()`, `setThreatLevel()` |
| `CharacterGraphicsFactory.java` | Creates sprites for each character type | `getImageBase()` |
| `BehaviorDispatcher.java` | Routes behaviour by character type | `getBehaviourHint()` |
| `package-info.java` | Package documentation | — |

### From Opponents Model (`opponents.ecore`)

| Generated File | Purpose | Status |
|---------------|---------|--------|
| `CharacterRegistrar.java` | Type-safe switch dispatch for character handling | ✅ Implemented |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers per type | ✅ Implemented |
| `CharacterGraphicsFactory.java` | Creates sprites for each character type | ✅ Implemented |
| `BehaviorDispatcher.java` | Routes behaviour requests by type | ✅ Implemented |
| `OpponentRegistry.java` | Lists all enemy types with their stats | 📋 Planned |

### From Walls Model (`walls.ecore`)

| Generated File | Purpose | Status |
|---------------|---------|--------|
| `WallRegistry.java` | Lists all wall material types | 📋 Planned |
| `WallMaterialRenderer.java` | Visual properties per material | 📋 Planned |
| `WallCollisionHandler.java` | Damage/collision behavior per material | 📋 Planned |

### Example: Generated Switch Statement

Instead of writing this by hand:

```java
// ❌ Hand-written - must update for every new enemy
if (character instanceof Zombie) {
    handleZombie((Zombie) character);
} else if (character instanceof Ghost) {
    handleGhost((Ghost) character);
} else if (character instanceof PumpkinBomber) {
    handlePumpkinBomber((PumpkinBomber) character);
}
```

The generator creates:

```java
// ✅ Generated - automatically includes all types from model
switch (character.eClass().getName()) {
    case "Zombie" -> handleZombie((Zombie) character);
    case "Ghost" -> handleGhost((Ghost) character);
    case "PumpkinBomber" -> handlePumpkinBomber((PumpkinBomber) character);
    default -> LOGGER.warning("Unknown type: " + typeName);
}
```

---

## 🚀 How to Run the Generator

### Option 1: In Eclipse (Interactive)

Best for development and experimenting:

1. **Open the template file**
   - Navigate to `src/main/java/main/game/maze/gen/templates/Generate.mtl`

2. **Run as Acceleo Application**
   - Right-click the `.mtl` file
   - Select `Run As` → `Launch Acceleo Application`
   - Choose or create a launch configuration

3. **Configure the launch**
   - **Model**: Path to your `.xmi` file (e.g., `maze/src/main/resources/xmi/opponents/opponentModel.xmi`)
   - **Target Folder**: Where to write output (e.g., `maze-module-generator/src-gen`)

4. **Run and check output**
   - Generated files appear in the target folder
   - Check for any errors in the Console view

### Option 2: Via Maven (Automated)

Best for CI/CD and reproducible builds:

```bash
# From the repository root
mvn clean verify -DskipTests
```

The Maven build:
1. Compiles all EMF models
2. Builds the `maze-generator.acceleo` plugin
3. Runs `maze-generator.acceleo-runner` which invokes the templates
4. Generated sources appear in `maze-module-generator/src-gen/`

---

## 📝 Understanding Acceleo Templates

### Basic Template Anatomy

```mtl
[comment encoding = UTF-8 /]
[module Generate('http://main.game.maze/opponents')]    ← Declares which model to use

[template public generate(root : OpponentModel)]        ← Entry point template
[comment @main /]                                       ← Marks this as the main template

[file ('Output.java', false, 'UTF-8')]                  ← Creates an output file
package generated;

public class Output {
    [for (enemy : CharacterType | root.characterTypes)] ← Loop over model elements
    public static final String [enemy.name/] = "[enemy.displayName/]";
    [/for]
}
[/file]

[/template]
```

### Key Acceleo Syntax

| Syntax | Meaning | Example |
|--------|---------|---------|
| `[for (x : Type | collection)]` | Loop | `[for (e : Enemy | model.enemies)]` |
| `[x.property/]` | Access property | `[enemy.health/]` |
| `[if (condition)]` | Conditional | `[if (enemy.health > 100)]` |
| `[file ('path', ...)]` | Create file | `[file ('Enemy.java', false, 'UTF-8')]` |
| `[comment text /]` | Comment | `[comment This is ignored /]` |

---

## 🎓 Tutorials and Learning Resources

### Official Acceleo Documentation
- [Acceleo User Guide](https://wiki.eclipse.org/Acceleo/User_Guide) - Comprehensive official guide
- [Acceleo Getting Started](https://wiki.eclipse.org/Acceleo/Getting_Started) - Quick start tutorial
- [Acceleo OCL Reference](https://wiki.eclipse.org/Acceleo/OCL_Operations_Reference) - OCL operations in templates

### EMF (Eclipse Modeling Framework)
- [EMF Tutorial](https://eclipsesource.com/blogs/tutorials/emf-tutorial/) - Learn EMF basics
- [EMF Documentation](https://www.eclipse.org/modeling/emf/) - Official EMF site
- [Ecore Metamodel](https://wiki.eclipse.org/Ecore) - Understanding `.ecore` files

### Video Tutorials
- [Acceleo Code Generation (YouTube)](https://www.youtube.com/results?search_query=acceleo+code+generation) - Search for tutorials
- [Model Driven Engineering Basics](https://www.youtube.com/results?search_query=model+driven+engineering+tutorial) - MDE concepts

### Books
- *Model-Driven Software Engineering in Practice* by Brambilla, Cabot, Wimmer
- *Eclipse Modeling Framework* by Steinberg et al.

---

## ✏️ Customizing Templates

### Step 1: Locate the Template

Templates are in `src/main/java/main/game/maze/gen/templates/`:
- `Generate.mtl` - Main opponents/characters template
- `GenerateWalls.mtl` - Walls material template

### Step 2: Make Changes

Edit the `.mtl` file. For example, to add a new method:

```mtl
[comment Add this inside the class generation /]
public static int getTotalEnemyCount() {
    return [root.characterTypes->size()/];
}
```

### Step 3: Recompile the Template

**Important**: After editing `.mtl`, you must recompile it to `.emtl`:
- In Eclipse: Save the file (auto-compiles with Acceleo builder)
- Or: Run a full Maven build

### Step 4: Test

1. Run the generator on a test model
2. Check the generated output for correctness
3. Commit both `.mtl` and `.emtl` files

---

## ➕ Adding a New Model

To generate code from a new EMF model:

### 1. Register the Model URI

In your `.mtl` template header:
```mtl
[module Generate('http://main.game.maze/opponents', 'http://main.game.maze/newmodel')]
```

### 2. Add Dependency in MANIFEST.MF

```
Require-Bundle: ...,
 main.game.maze.newmodel
```

### 3. Create Templates

Write templates that navigate your new model:
```mtl
[template public generateFromNewModel(root : NewModelRoot)]
[file ('NewOutput.java', false, 'UTF-8')]
// Generated from NewModel
[/file]
[/template]
```

### 4. Update the Runner

In `maze-generator.acceleo-runner`, ensure your model is loaded and passed to the generator.

---

## ✅ Development Workflow & Best Practices

This section documents critical steps learned from implementing MDE in this project.

### Step 1: Verify EMF Model Interfaces First

**⚠️ Critical**: Before writing templates that call model methods, verify the actual method names in the EMF-generated interfaces.

```java
// ❌ WRONG - Assumed method names without checking
character.getDamage()    // Does not exist!
character.getImage()     // Does not exist!

// ✅ CORRECT - Actual EMF-generated methods
character.getThreatLevel()   // From opponents.ecore
character.getImageBase()     // From opponents.ecore
```

**How to verify:**
1. Open the generated EMF interface (e.g., `CharacterType.java` in `main.game.maze.opponents`)
2. Check the actual getter/setter method names
3. Use these exact names in your templates

| Model Attribute | EMF Generated Method |
|-----------------|---------------------|
| `threatLevel` | `getThreatLevel()` / `setThreatLevel()` |
| `imageBase` | `getImageBase()` |
| `health` | `getHealth()` / `setHealth()` |

### Step 2: Write Unit Tests for Generated Code

Generated code should be validated with JUnit tests to catch issues early.

**Test location**: `maze-module-generator/src/test/java/`

**Example test structure**:
```java
@Test
void testCharacterAttributeSetter() {
    CharacterAttributeSetter setter = new CharacterAttributeSetter();
    
    // Test that generated code uses correct EMF methods
    double baseThreat = setter.getBaseThreatLevel(CharacterType.ZOMBIE);
    assertThat(baseThreat).isGreaterThan(0);
}
```

**Run tests**:
```bash
mvn -pl maze-module-generator test
```

### Step 3: Build and Verify

Always run a full build after template changes:

```bash
# Full build with tests
mvn clean verify

# Quick rebuild of generator module only
mvn -pl maze-module-generator -am clean verify
```

**Check for**:
- Compilation errors in generated code
- Test failures
- Missing imports or incorrect method calls

### Step 4: Integrate with Existing Runtime

After generating code, integrate it with the existing runtime:

| Generated Class | Integrates With | Purpose |
|-----------------|-----------------|---------|
| `CharacterRegistrar` | `OpponentRuntimeFactory` | Delegate registration logic |
| `CharacterAttributeSetter` | Difficulty system | Apply multipliers |
| `BehaviorDispatcher` | `PatrolHelper` | Route behaviour calls |

**Example integration** (in `OpponentRuntimeFactory.java`):
```java
// Before: Manual registration
registerCharacter("zombie", new ZombieConfig(...));

// After: Delegate to generated code
CharacterRegistrar.registerAll(this);
```

### Common Pitfalls

| Pitfall | Symptom | Solution |
|---------|---------|----------|
| Wrong method names | `method not found` compilation error | Check EMF interface for actual method names |
| Missing `.emtl` | `Cannot find Acceleo module` | Rebuild in Eclipse or run Maven build |
| Stale generated code | Tests pass but runtime fails | Delete `src-gen/` and regenerate |
| Type mismatches | `incompatible types` error | Verify template uses correct EMF types |

---

## 🔍 Troubleshooting

### No Files Generated

**Symptom**: Generator runs but no files appear.

**Solutions**:
- Check that the model file path is correct
- Verify the root element type matches the template parameter
- Look for errors in the Eclipse Console or Maven output
- Ensure the `.emtl` file exists and is up-to-date

### "Cannot find module" Error

**Symptom**: `Cannot find Acceleo module: /path/Generate.emtl`

**Solutions**:
- Rebuild the project in Eclipse to compile `.mtl` → `.emtl`
- Check that `.emtl` is in the classpath
- Verify the module path in `RunAcceleo.java` matches the actual location

### Compilation Errors in Generated Code

**Symptom**: Generated `.java` files have syntax errors.

**Solutions**:
- Review the template for missing imports
- Check that model properties exist and have expected types
- Use `[protected]` blocks for code that shouldn't be overwritten

### Model Loading Errors

**Symptom**: `Resource not found` or `Unknown package URI`

**Solutions**:
- Register the EPackage in `RunAcceleo.java`:
  ```java
  EPackage.Registry.INSTANCE.put(MyPackage.eNS_URI, MyPackage.eINSTANCE);
  ```
- Ensure `.xmi` files reference the correct namespace URI

---

## 📊 Domain Models Overview

| Model | Namespace URI | Contains |
|-------|--------------|----------|
| **Opponents** | `http://main.game.maze/opponents` | CharacterType, Zombie, Ghost, PumpkinBomber |
| **Difficulties** | `http://main.game.maze/difficulties` | Difficulty, EasyDifficulty, NormalDifficulty, HardDifficulty |
| **Walls** | `http://main.game.maze/walls` | WallMaterial, WallMaterialBaseType (GLASS, DIRT, WOOD, STONE, STEEL) |
| **Behaviour** | `http://main.game.maze/behaviour` | MovementBehavior, PatrolBehavior, ChaseBehavior, RandomBehavior |

---

## 🎮 Domain Models In-Depth

This section provides detailed documentation of each EMF domain model, how they relate to game mechanics, and how Acceleo templates generate code from them.

### Opponents Model (`opponents.ecore`)

**Location**: `main.game.maze.opponents/src/main/resources/opponents.ecore`  
**Namespace URI**: `http://main.game.maze/opponents`

The opponents model defines all enemy types in the game. It uses an inheritance hierarchy with a common `CharacterType` abstract base class.

#### Class Hierarchy

```
CharacterType (abstract)
├── Zombie          - Melee attacker with infection mechanics
├── Ghost           - Phasing enemy that can pass through walls
└── RangedEnemy (abstract)
    └── PumpkinBomber - Throws explosive projectiles
```

#### CharacterType (Base Class)

All enemies share these attributes:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `id` | String | — | Unique identifier |
| `displayName` | String | — | Human-readable name |
| `enabled` | boolean | `true` | Whether this enemy spawns |
| `health` | int | `100` | Hit points |
| `speed` | double | `1.0` | Movement speed multiplier |
| `threatLevel` | double | `0.0` | Danger rating (affects difficulty) |
| `ImageBase` | String | `/main/game/maze/zombie.png` | Main sprite |
| `ImageTurnLeft/Right/Up/Down` | String | — | Directional sprites |
| `behavior` | BehaviorType | `WANDER` | AI behavior pattern |

**BehaviorType Enum**: `PASSIVE`, `WANDER`, `AGGRESSIVE`, `PATROL`

#### Zombie

Melee attacker that can infect and resurrect.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackDamage` | int | `10` | Damage per hit |
| `infectionLevel` | int | `1` | Infection strength (1-10) |
| `resurrectionTime` | int | `0` | Seconds until respawn (0 = no respawn) |
| `touchSound` | String | `.../zombieScream.mp3` | Sound on player contact |
| `zombieLootTable` | LootTable | — | Items dropped on death |

#### Ghost

Phasing enemy with variable visibility.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackDamage` | int | `1` | Damage per hit |
| `visibilityLevel` | int | `100` | Opacity percentage (0 = invisible) |
| `nonTangibilityEnergy` | double | `100` | Energy for wall phasing |

#### PumpkinBomber (extends RangedEnemy)

Ranged attacker with explosive projectiles.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `attackRange` | double | `50` | Maximum attack distance |
| `attackCooldownMs` | int | `10000` | Milliseconds between attacks |
| `attackDamage` | int | `1` | Base damage |
| `projectileSpeed` | double | `0` | Projectile velocity |
| `projectileType` | ProjectileType | — | `STRAIGHT`, `LOB`, or `BEAM` |
| `splashRadius` | double | — | Explosion area of effect |
| `arcHeight` | double | — | Arc height for `LOB` projectiles |

#### LootTable System

Enemies can drop items on death:

```
LootTable
├── weightCapacity: int (1-100)
└── items: LootItem[]
    ├── name: String
    ├── type: LootItemType (FOOD, BOMB, TRAP, WEAPON)
    ├── value: int
    └── weight: int (1-10)
```

#### Generated Code from Opponents

The `Generate.mtl` template produces:

| Generated Class | Purpose | Template Section |
|-----------------|---------|------------------|
| `OpponentRegistry` | Lists all enemies, provides counts | Lines 25-60 |
| `CharacterRegistrar` | Type-safe switch dispatch for registration | Lines 65-160 |
| `CharacterAttributeSetter` | Applies difficulty multipliers | Lines 168-250 |
| `CharacterGraphicsFactory` | Creates sprites from `ImageBase` | Lines 255-300 |

**Example: How CharacterRegistrar eliminates instanceof**

```mtl
[comment From Generate.mtl - generates switch cases for each type /]
switch (typeName) {
    [for (enemy : CharacterType | root.characterTypes->filter(Zombie)->asSet())]
    case "Zombie" -> zombieHandler.register((Zombie) character);
    [/for]
    [for (enemy : CharacterType | root.characterTypes->filter(Ghost)->asSet())]
    case "Ghost" -> ghostHandler.register((Ghost) character);
    [/for]
    ...
}
```

---

### Walls Model (`walls.ecore`)

**Location**: `main.game.maze.walls/model/walls.ecore`  
**Namespace URI**: `http://main.game.maze/walls`

The walls model defines maze wall materials with varying durability and visual properties.

#### WallMaterialBaseType Enum

| Value | Ordinal | Characteristics |
|-------|---------|-----------------|
| `GLASS` | 0 | Fragile, transparent |
| `DIRT` | 1 | Very weak, easily broken |
| `WOOD` | 2 | Burnable, moderate strength |
| `STONE` | 3 | Strong, standard wall |
| `STEEL` | 4 | Unbreakable (default) |

#### WallMaterial Class

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `id` | String | (derived) | Unique identifier |
| `displayName` | String | — | Human-readable name |
| `wallBaseType` | WallMaterialBaseType | `STEEL` | Material category |
| `breakable` | boolean | `false` | Can be destroyed |
| `hitPoints` | int | `0` | Damage to break (0 if unbreakable) |
| `baseImage` | String | `/main/game/maze/baseWall.png` | Visual texture |

**OCL Constraint** (enforced at model level):
```ocl
(breakable = false implies hitPoints = 0) and
(breakable = true implies hitPoints > 0)
```

#### Generated Code from Walls

The `GenerateWalls.mtl` template produces:

| Generated Class | Purpose |
|-----------------|---------|
| `WallRegistry` | Static registry of all wall definitions |
| `WallMaterialRenderer` | Visual properties per material (planned) |
| `WallCollisionHandler` | Collision/damage logic per material (planned) |

**Example: WallRegistry generation**

```mtl
[comment From GenerateWalls.mtl - loops over all materials /]
static {
[for (m : WallMaterial | model.materials)]
    register(new WallDefinition(
        "[m.id/]",
        "[m.displayName/]",
        "[m.wallBaseType.name()/]", 
        [m.breakable/],
        [m.hitPoints/],
        "[m.baseImage/]"
    ));
[/for]
}
```

---

### Difficulties Model (`difficulty-module.ecore`)

**Location**: `main.game.maze.difficulties/src/main/resources/difficulty-module.ecore`  
**Namespace URI**: `http://main.game.maze/difficulties`

The difficulties model defines game difficulty levels with multipliers affecting enemy stats.

#### Difficulty Class Hierarchy

```
Difficulty (abstract)
├── EasyDifficulty
├── NormalDifficulty
└── HardDifficulty
```

#### Difficulty (Base Class)

| Attribute | Type | Description |
|-----------|------|-------------|
| `instantDeath` | boolean | One-hit kills player |
| `monstersMovementSpeedMultiplier` | double | Speed modifier (1.0 = normal) |
| `monstersDamageMultiplier` | double | Damage modifier |
| `maxThreat` | int | Max combined enemy threat allowed |
| `enemyMaxCount` | EnemyMaxCount[] | Per-type spawn limits |

#### EnemyMaxCount

| Attribute | Type | Description |
|-----------|------|-------------|
| `type` | EnemyTypes | `ZOMBIE`, `GHOST`, or `PUMPKINBOMBER` |
| `maxCount` | int | Maximum simultaneous spawns |

#### Generated Code from Difficulties

The `DifficultyConfigurator.mtl` template produces:

| Generated Class | Purpose |
|-----------------|---------|
| `DifficultyConfigurator` | Applies multipliers based on difficulty |
| `DifficultySettings` | Immutable record of applied settings |
| `EnemySpawnLimits` | Per-enemy spawn limits by difficulty |

**Example: Difficulty key resolution**

```mtl
[comment From DifficultyConfigurator.mtl /]
public static String getDifficultyKey(Difficulty difficulty) {
    return switch (difficulty.eClass().getName()) {
        [for (diff : Difficulty | gameData.difficulties)]
        case "[diff.eClass().name/]" -> "[diff.eClass().name.toLower()/]";
        [/for]
        default -> "normal";
    };
}
```

---

### Behaviour Model (`movements.ecore`)

**Location**: `main.game.maze.behaviour/src/main/resources/movements/movements.ecore`  
**Namespace URI**: `http://main.game.maze/behaviour`

The behaviour model defines enemy AI movement patterns. It references the opponents model for character binding.

#### MovementBehavior Class Hierarchy

```
MovementBehavior (abstract)
├── RandomBehavior   - Wanders aimlessly with HP regen
├── PatrolBehavior   - Follows predefined patrol points
└── ChaseBehavior    - Pursues the player
```

#### MovementBehavior (Base Class)

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `charactertype` | CharacterType | — | Linked enemy instance |
| `ignoreWalls` | boolean | (derived) | True if Ghost with energy |
| `instantKillOnCollision` | boolean | (derived) | True if threatLevel > 100 |
| `baseVisionRange` | double | `100` | Base sight distance |
| `additionalVisionRange` | double | `100` | Bonus sight distance |
| `visionRangeMultiplier` | double | `1` | Vision scaling factor |
| `visionRange` | double | (derived) | `base * mult + additional` |
| `position` | Position | — | Current location |
| `direction` | Direction | — | Current heading |
| `nextPositions` | Position[] | — | Planned movement path |

**OCL-Derived `ignoreWalls`** (Ghost phasing):
```ocl
if self.charactertype.oclIsKindOf(Ghost) then
    ghostCharacter.nonTangibilityEnergy >= 0
else false endif
```

#### RandomBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `regenPerSecond` | double | HP recovered per second while wandering |

#### PatrolBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `path` | PatrolPoint[] | Ordered patrol waypoints |
| `currentIndex` | int | Current position in path |
| `pathcalculator` | PathCalculator | Navigation algorithm |
| `behavior` | PatrolPathBehavior | Loop/pingpong/once |
| `patrolZone` | PatrolZone | Optional bounding area |

**PatrolPoint** contains:
- `point`: Position (target coordinates)
- `events`: Event[] (things that happen at this waypoint)

**Event Types**: `HealthEvent`, `SpeedEvent`, `TimeEvent`, `AttackEvent`, `VisionEvent`

#### ChaseBehavior

| Attribute | Type | Description |
|-----------|------|-------------|
| `relativePositionTarget` | Position | Offset from player to target |
| `pathcalculator` | PathCalculator | Navigation algorithm |

#### PathCalculator Types

```
PathCalculator (abstract)
├── BFSPathCalculator     - Breadth-first search
├── DijkstraPathCalculator - Weighted shortest path
└── AStarPathCalculator   - Heuristic-based pathfinding
```

#### Generated Code from Behaviour

The `BehaviorDispatcher.mtl` template produces:

| Generated Class | Purpose |
|-----------------|---------|
| `BehaviorDispatcher` | Routes to type-specific movement executors |
| `PathCalculatorFactory` | Creates path calculators by type |

**Example: BehaviorDispatcher**

```mtl
[comment From BehaviorDispatcher.mtl /]
public static void dispatch(
        MovementBehavior behavior,
        double deltaTime,
        BehaviorExecutor randomExecutor,
        BehaviorExecutor patrolExecutor,
        BehaviorExecutor chaseExecutor) {
    
    switch (behavior.eClass().getName()) {
        case "RandomBehavior" -> randomExecutor.execute(behavior, deltaTime);
        case "PatrolBehavior" -> patrolExecutor.execute(behavior, deltaTime);
        case "ChaseBehavior" -> chaseExecutor.execute(behavior, deltaTime);
        default -> LOGGER.warning("Unknown behavior: " + behavior);
    }
}
```

---

## 🔧 MTL Template Architecture

### Template Organization

```
maze-generator.acceleo/src/main/java/main/game/maze/gen/templates/
├── Generate.mtl              # Main opponents orchestrator
├── GenerateWalls.mtl         # Main walls orchestrator
├── opponents/
│   ├── CharacterRegistrar.mtl
│   ├── CharacterAttributeSetter.mtl
│   └── CharacterGraphicsFactory.mtl
├── walls/
│   ├── WallPropertyAccessor.mtl
│   ├── WallMaterialRenderer.mtl
│   └── WallCollisionHandler.mtl
├── difficulties/
│   ├── DifficultyConfigurator.mtl
│   └── EnemySpawnLimits.mtl
└── behaviour/
    ├── BehaviorDispatcher.mtl
    └── PathCalculatorFactory.mtl
```

### Common Template Patterns

#### Pattern 1: Type-Safe Switch Generation

Eliminates `instanceof` chains:

```mtl
switch (typeName) {
    [for (subtype : EClass | pkg.eClassifiers->filter(EClass)->select(c | c.eSuperTypes->includes(baseClass)))]
    case "[subtype.name/]" -> handle[subtype.name/](([subtype.name/]) obj);
    [/for]
    default -> handleUnknown(obj);
}
```

#### Pattern 2: Registry Population

Creates static lookup tables:

```mtl
static {
[for (item : SomeType | model.items)]
    register("[item.id/]", new Definition(
        [item.property1/],
        "[item.property2/]",
        [item.booleanProperty/]
    ));
[/for]
}
```

#### Pattern 3: Constant Generation

Exports model values as Java constants:

```mtl
[for (constant : SomeType | model.constants)]
public static final [constant.type/] [constant.name.toUpperCase()/] = [constant.value/];
[/for]
```

### Cross-Model References

Templates can reference multiple models:

```mtl
[module BehaviorDispatcher('http://main.game.maze/behaviour', 'http://main.game.maze/opponents')]
```

This allows the behaviour model to access opponent types, enabling constraints like:
- Ghost's `ignoreWalls` derived from `nonTangibilityEnergy`
- RangedEnemy's attack range validation against vision range

---

## 🔗 Related Projects

- **[Model-Driven Code Generation Plan](../readme-mddcodegeneration.md)** - Comprehensive plan for MDE code generation across all domains
- [maze-generator.acceleo-runner](../maze-generator.acceleo-runner/readme.md) - Headless runner for Maven builds
- [maze-module-generator](../maze-module-generator/readme.md) - Contains generated sources and **unit tests** for MDE validation
- [main.game.maze.opponents](../main.game.maze.opponents/readme.md) - Opponents EMF model (`CharacterType`, `Zombie`, `Ghost`, `PumpkinBomber`)
- [main.game.maze.walls](../main.game.maze.walls/readme.md) - Walls EMF model (`WallMaterial`, `WallMaterialBaseType`)
- [main.game.maze.difficulties](../main.game.maze.difficulties/readme.md) - Difficulties EMF model (`Difficulty`, `EasyDifficulty`, etc.)
- [main.game.maze.behaviour](../main.game.maze.behaviour/readme.md) - Behaviour EMF model (`MovementBehavior`, `PatrolBehavior`, etc.)

---

## 📚 Glossary

| Term | Definition |
|------|------------|
| **Acceleo** | Eclipse-based code generation framework using templates |
| **EMF** | Eclipse Modeling Framework - foundation for defining models |
| **Ecore** | The metamodel used by EMF (like a schema for models) |
| **XMI** | XML format for storing model instances |
| **MTL** | Model-to-Text Language - Acceleo's template language |
| **EMTL** | Compiled (binary) MTL template |
| **MDE** | Model-Driven Engineering - using models as primary artifacts |
| **Tycho** | Maven plugin for building Eclipse plugins |

---

## 🔄 End-to-End Workflow Summary

Here's the complete workflow for making model-driven changes:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  1. PLAN: Decide what to change                                             │
│     • New enemy type? → Edit opponents.ecore                                │
│     • New wall material? → Edit walls.ecore                                 │
│     • New attribute for all types? → Edit .ecore + update .mtl template     │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  2. VERIFY: Check EMF interface methods                                     │
│     • Open generated interface (e.g., CharacterType.java)                   │
│     • Note exact method names (getThreatLevel, NOT getDamage)               │
│     • Update template to use correct method names                           │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  3. GENERATE: Run the code generator                                        │
│     mvn -pl maze-module-generator -am clean verify                          │
│     • Output appears in maze-module-generator/src-gen/                      │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  4. TEST: Validate generated code                                           │
│     mvn -pl maze-module-generator test                                      │
│     • Fix any compilation errors (wrong method names?)                      │
│     • Fix any test failures                                                 │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  5. INTEGRATE: Wire generated code into runtime                             │
│     • Update OpponentRuntimeFactory to delegate to CharacterRegistrar       │
│     • Update PatrolHelper to use BehaviorDispatcher                         │
│     • Run full build: mvn clean verify                                      │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  6. COMMIT: Save all changes                                                │
│     • Commit .ecore model changes                                           │
│     • Commit .mtl and .emtl template files                                  │
│     • Commit generated src-gen/ files (for builds without Acceleo)          │
│     • Commit updated tests                                                  │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

*By keeping all model-to-code generation logic in `maze-generator.acceleo`, the MazeGame project maintains a clean, reproducible, and model-driven build pipeline.*

