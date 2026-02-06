# Model-Driven Code Generation Plan

This document describes the plan for implementing true model-driven code generation in the MazeGame project.

---

## The Problem: Hand-Written Boilerplate

Currently, the MazeGame has a **disconnect** between the EMF models and the runtime code:

```
┌─────────────────┐         ┌─────────────────┐
│  Ecore Models   │         │  Runtime Code   │
│  (source of     │   ???   │  (hand-written  │
│   truth)        │ ──────► │   switch/if)    │
└─────────────────┘         └─────────────────┘
```

**Example**: When you add a new enemy type to `opponents.ecore`:

1. Add `Skeleton` class extending `CharacterType` ✅ (model)
2. Regenerate EMF code ✅ (automatic)
3. **Manually** update `OpponentRuntimeFactory.java` ❌
4. **Manually** update difficulty multiplier logic ❌
5. **Manually** update graphics factory ❌
6. **Manually** update behavior dispatcher ❌

This is error-prone and violates the DRY principle.

---

## The Goal: Model-Driven Generation

We want the **model to drive the code**, not the other way around:

```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│  Ecore Models   │         │  Acceleo        │         │  Generated Java │
│  (.ecore)       │ ──────► │  Templates      │ ──────► │  (src-gen/)     │
│                 │         │  (.mtl)         │         │                 │
└─────────────────┘         └─────────────────┘         └─────────────────┘
```

**After implementation**:

1. Add `Skeleton` class extending `CharacterType` ✅ (model)
2. Run `mvn clean verify` ✅ (build)
3. **Done** — all switch statements, factories, dispatchers are generated automatically

---

## Models Available for Generation

| Model | Location | Key Classes | Current Usage |
|-------|----------|-------------|---------------|
| **walls** | `walls.ecore` | `WallMaterial`, `WallModel`, `WallMaterialBaseType` | Manual wall rendering |
| **difficulties** | `difficulty-module.ecore` | `Difficulty`, `EasyDifficulty`, `NormalDifficulty`, `HardDifficulty`, `EnemyMaxCount` | `DifficultyService.java` interprets XMI |
| **behaviour** | `movements.ecore` | `MovementBehavior`, `PatrolBehavior`, `ChaseBehavior`, `RandomBehavior`, `PathCalculator` variants | `PatrolHelper.java` interprets |
| **opponents** | `opponents.ecore` | `CharacterType`, `Zombie`, `Ghost`, `PumpkinBomber` | `OpponentRuntimeFactory.java` |

---

## What Gets Generated (Per Domain)

| Domain | Model Change | Generated Code |
|--------|--------------|----------------|
| **Walls** | Add `LAVA` material type | Renderer switch case, collision handler |
| **Difficulties** | Add `NightmareDifficulty` | Configuration class, multiplier logic |
| **Behaviour** | Add `FleeBehavior` | Dispatcher case, state machine transitions |
| **Opponents** | Add `Skeleton` enemy | Registration, attribute setter, graphics factory |

---

## Generation Opportunities by Domain

### 1. Walls Domain

| Generated Artifact | Source | Purpose |
|--------------------|--------|---------|
| `WallMaterialRenderer.java` | `WallMaterialBaseType` enum | Switch on material → render style |
| `WallCollisionHandler.java` | `WallModel` | Generate collision box calculations |
| `WallPropertyAccessor.java` | `WallMaterial` attributes | Type-safe getters per material |

**Value**: Adding a new `WallMaterialBaseType` (e.g., `LAVA`) auto-generates rendering logic.

### 2. Difficulties Domain

| Generated Artifact | Source | Purpose |
|--------------------|--------|---------|
| `DifficultyConfigurator.java` | `Difficulty` subclasses | Switch on type → apply multipliers |
| `EnemySpawnLimits.java` | `EnemyMaxCount` | Generate caps per enemy type per difficulty |
| `DifficultyConstants.java` | All `*Difficulty` classes | Static final values for each setting |

**Value**: Adding `NightmareDifficulty` to model auto-generates configuration code.

### 3. Behaviour Domain

| Generated Artifact | Source | Purpose |
|--------------------|--------|---------|
| `BehaviorDispatcher.java` | `MovementBehavior` subtypes | Dispatch to correct executor |
| `PathCalculatorFactory.java` | `PathCalculator` variants | Factory for A*, Dijkstra, Local |
| `PatrolStateMachine.java` | `PatrolBehavior`, `PatrolPoint`, `PatrolZone` | State transitions for patrol |

**Value**: Adding `FleeBehavior` to model auto-generates dispatch and state machine.

### 4. Opponents Domain

| Generated Artifact | Source | Purpose |
|--------------------|--------|---------|
| `CharacterRegistrar.java` | `CharacterType` subtypes | Registration switch |
| `CharacterAttributeSetter.java` | `CharacterType` + `Difficulty` | Multiplier application |
| `CharacterGraphicsFactory.java` | `CharacterType` graphics attrs | Graphics creation (sprite, animation, scale) |

**Value**: Adding `Skeleton` to model auto-generates all factory/registration code.

---

## Template Structure (Acceleo 4)

All Acceleo 4 templates are consolidated in `maze-generator-java`:

```
maze-generator-java/src/main/java/main/game/maze/codegen/
├── AcceleoGeneratorMain.java    # Java launcher for standalone generation
└── acceleo4/
    ├── Generate.mtl             # Opponents domain (CharacterRegistrar, etc.)
    ├── GenerateWalls.mtl        # Walls domain (WallRegistry, Renderer, Collision)
    ├── GenerateDifficulties.mtl # Difficulties domain (Configurator, SpawnLimits)
    └── GenerateBehaviour.mtl    # Behaviour domain (Dispatcher, PathFactory)
```

### Generated Output Structure

```
src-gen/main/game/maze/generated/
├── OpponentRegistry.java
├── CharacterRegistrar.java
├── CharacterAttributeSetter.java
├── CharacterGraphicsFactory.java
├── WallRegistry.java
├── WallMaterialRenderer.java
├── WallCollisionHandler.java
├── DifficultyConfigurator.java
├── EnemySpawnLimits.java
├── DifficultyRegistry.java
├── BehaviorDispatcher.java
├── PathCalculatorFactory.java
└── BehaviorRegistry.java
```

---

## Why This Matters (Addressing Feedback)

The original project feedback stated:

> *"Acceleo is employed, but only to generate additional registry-like code that largely duplicates utilities already provided by EMF"*

**Before** (registry duplication):
- Acceleo generates package registries → EMF already does this
- No real value added

**After** (model-driven generation):
- Acceleo generates **application logic** (switches, factories, dispatchers)
- Real code reduction
- Model changes propagate automatically
- True MDE value chain

---

## The Validation Test

**Success criteria**: After implementation, this workflow must work:

1. Add `Skeleton` to `opponents.ecore`
2. Run `mvn clean verify`
3. Game compiles **without any manual code changes**
4. Game runs with new enemy type functional

If any manual edits are required after step 2, the generation is incomplete.

---

## Summary

| Aspect | Current State | Target State |
|--------|---------------|--------------|
| Model changes | Require manual code updates | Auto-generate code |
| Acceleo usage | Registry boilerplate | Application logic |
| Build process | Model + manual sync | Model → generate → compile |
| Risk of bugs | High (forgotten updates) | Low (single source of truth) |

This transforms the project from "uses EMF" to "true model-driven development."

---

## Implementation Priority

| Priority | Domain | Reason |
|----------|--------|--------|
| **1** | Difficulties | Simplest model, clear switch generation, immediate value |
| **2** | Behaviour | Most MDE-interesting (state machines), addresses Requirement 5 |
| **3** | Opponents | Largest code reduction, already partially analyzed |
| **4** | Walls | Smallest model, lower impact but demonstrates completeness |

---

## Implementation Status

✅ = Implemented | 🔄 = In Progress | ❌ = Not Started

### Acceleo 4 Migration

The project has been migrated from Acceleo 3 to **Acceleo 4**, which provides:
- Better AQL (Acceleo Query Language) support
- Improved Java API for standalone generation
- Modern EMF integration
- Simplified template syntax

**Acceleo 4 Module Location**: `maze-generator-java/src/main/java/main/game/maze/codegen/acceleo4/`

### Templates Created

| Domain | Template | Status | Generated Artifacts |
|--------|----------|--------|-------------------|
| **Opponents** | `Generate.mtl` | ✅ | `OpponentRegistry.java`, `CharacterRegistrar.java`, `CharacterAttributeSetter.java`, `CharacterGraphicsFactory.java` |
| **Walls** | `GenerateWalls.mtl` | ✅ | `WallRegistry.java`, `WallMaterialRenderer.java`, `WallCollisionHandler.java` |
| **Difficulties** | `GenerateDifficulties.mtl` | ✅ | `DifficultyConfigurator.java`, `EnemySpawnLimits.java`, `DifficultyRegistry.java` |
| **Behaviour** | `GenerateBehaviour.mtl` | ✅ | `BehaviorDispatcher.java`, `PathCalculatorFactory.java`, `BehaviorRegistry.java` |

### Generated Code Statistics

Generated files provide comprehensive domain coverage:

| Domain | Files | Switch Cases | Factory Methods | Notes |
|--------|-------|--------------|-----------------|-------|
| Opponents | 4 | 3 (Zombie, Ghost, PumpkinBomber) | 3 | Type dispatch, attribute setters, graphics |
| Walls | 3 | 5 (Glass, Dirt, Wood, Stone, Steel) | N/A | Registry, renderer, collision |
| Difficulties | 3 | 3 (Easy, Normal, Hard) | 3 | Configuration, spawn limits, factory |
| Behaviour | 3 | 3 behaviors + 3 path calculators | 8 | Dispatcher, path factory, event factory |

### Generated Code Output

Generated files are in `maze-module-generator/src-gen/main/game/maze/generated/`:

| File | Uses EMF Methods | Purpose |
|------|------------------|----------|
| `CharacterRegistrar.java` | `eClass().getName()` | Type-safe switch dispatch |
| `CharacterAttributeSetter.java` | `getHealth()`, `setHealth()`, `getThreatLevel()`, `setThreatLevel()`, `getSpeed()`, `setSpeed()` | Difficulty multipliers |
| `CharacterGraphicsFactory.java` | `getImageBase()`, `getAnimationFrameCount()`, `getSpriteScale()` | Sprite factory |
| `OpponentRegistry.java` | Model iteration | Enemy type listing |
| `WallRegistry.java` | Model iteration | Wall material listing |
| `WallMaterialRenderer.java` | `WallMaterialBaseType` switch | Color, sound, transparency |
| `WallCollisionHandler.java` | `WallMaterial` attributes | Damage, resistance, effects |
| `DifficultyConfigurator.java` | `Difficulty` attributes | Settings extraction |
| `EnemySpawnLimits.java` | `EnemyMaxCount` iteration | Spawn cap management |
| `DifficultyRegistry.java` | `DifficultiesFactory` | Difficulty creation |
| `BehaviorDispatcher.java` | `MovementBehavior` switch | Behavior routing |
| `PathCalculatorFactory.java` | `BehaviourFactory` | Algorithm creation |
| `BehaviorRegistry.java` | `BehaviourFactory` | Behavior/event creation |

### Unit Tests

| Test Class | Status | Coverage |
|------------|--------|----------|
| `CharacterRegistrarTest.java` | ✅ | `isKnownType()`, `getKnownTypes()` |
| `CharacterAttributeSetterTest.java` | ✅ | `getBaseHealth()`, `getBaseThreatLevel()`, `applyDamageMultiplier()` |
| `CharacterGraphicsFactoryTest.java` | ✅ | `getAnimationFrameCount()`, `getSpriteScale()` |
| `WallRegistryTest.java` | ✅ | `get()`, `all()`, wall definitions, model validation |
| `OpponentRegistryTest.java` | ✅ | `GAME_NAME`, `listEnemies()`, model validation |

### Infrastructure Updates

| Component | Status | Description |
|-----------|--------|-------------|
| `AcceleoGeneratorMain.java` | ✅ | Acceleo 4 launcher supporting all 4 domains |
| `MANIFEST.MF` (maze-generator-java) | ✅ | All model plugin dependencies including behaviour |
| All `.mtl` templates | ✅ | Acceleo 4 syntax with `[module]/` declaration |

### Remaining Work

| Task | Status | Notes |
|------|--------|-------|
| Acceleo 4 migration | ✅ | All templates migrated to Acceleo 4 in `maze-generator-java` |
| Run generation in Maven build | 🔄 | Use `AcceleoGeneratorMain` with model paths |
| Refactor `OpponentRuntimeFactory.java` | ✅ | Delegates to generated `CharacterRegistrar` and `CharacterAttributeSetter` |
| Refactor `PatrolHelper.java` | ❌ | Use generated `BehaviorDispatcher` (lower priority) |
| Add `src-gen` to `maze/pom.xml` | ✅ | Already configured as dependency on `maze-module-generator` |
| End-to-end test | ❌ | Add new model element, verify no manual code needed |

---

## Running the Generation

### Build Integration

The build currently uses **standalone Java generators** (`RunAcceleo.java`, `RunWallsAcceleo.java`) that write files directly using `PrintWriter`. These are invoked by `maze-generator.acceleo-runner` during the Tycho build.

```bash
# Full build with generation
mvn clean verify
```

### Acceleo 4 Alternative

Acceleo 4 templates exist in `maze-generator-java` for more sophisticated template-based generation:

```bash
# Via AcceleoGeneratorMain (if configured)
java -cp maze-generator-java.jar main.game.maze.codegen.AcceleoGeneratorMain \
    opponents.xmi walls.xmi difficulties.xmi movements.ecore output-dir/
```

**Note**: The Acceleo 4 templates and standalone generators produce equivalent output. The build uses standalone generators for reliability (no Eclipse workspace dependencies).

### Model Validation

The standalone generators validate models before generation and provide null-safe defaults:

**Fail-Fast Validation:**
- Models must have required elements (e.g., at least one `WallMaterial`, non-blank `id` fields)
- `IllegalStateException` with clear message if validation fails

**Null-Safe Defaults:**
| Field | Default Value |
|-------|---------------|
| `wallBaseType` | `STEEL` |
| `baseImage` (walls) | `/images/walls/default_wall.png` |
| `displayName` | `"Unknown Wall"` or `"Unknown Enemy"` |
| `model.name` | `"MazeGame"` |

Warnings are printed for fields using defaults, but generation continues. Special characters in model strings are escaped to prevent invalid Java output.
