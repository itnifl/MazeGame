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
│  Ecore Models   │         │  FreeMarker     │         │  Generated Java │
│  (.ecore)       │ ──────► │  Templates      │ ──────► │  (src-gen/)     │
│                 │         │  (.ftl)         │         │                 │
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

## Template Structure (FreeMarker)

All FreeMarker templates are in `maze-generator.freemarker/src/main/resources/templates/`:

```
maze-generator.freemarker/src/main/resources/templates/
├── opponents/
│   ├── OpponentRegistry.ftl          # Enemy type listing
│   ├── CharacterRegistrar.ftl        # Type dispatch with handlers
│   ├── CharacterAttributeSetter.ftl  # Difficulty multipliers
│   └── CharacterGraphicsFactory.ftl  # Sprite/graphics factory
└── walls/
    ├── WallRegistry.ftl              # Wall material registry
    ├── WallMaterialRenderer.ftl      # Color, sound, transparency
    └── WallCollisionHandler.ftl      # Damage, resistance
```

### Generated Output Structure

Currently generated files (`maze-module-generator/src-gen/main/game/maze/generated/`):

```
src-gen/main/game/maze/generated/
├── OpponentRegistry.java          ✅ Generated
├── CharacterRegistrar.java        ✅ Generated
├── CharacterAttributeSetter.java  ✅ Generated
├── CharacterGraphicsFactory.java  ✅ Generated
├── WallRegistry.java              ✅ Generated
├── WallMaterialRenderer.java      ✅ Generated
└── WallCollisionHandler.java      ✅ Generated
```

**Planned** (templates and generators not yet created):

```
├── DifficultyConfigurator.java    ❌ Not yet generated
├── EnemySpawnLimits.java          ❌ Not yet generated
├── DifficultyRegistry.java        ❌ Not yet generated
├── BehaviorDispatcher.java        ❌ Not yet generated
├── PathCalculatorFactory.java     ❌ Not yet generated
└── BehaviorRegistry.java          ❌ Not yet generated
```

---

## Why This Matters (Addressing Feedback)

The original project feedback stated:

> *"FreeMarker is employed for generating application logic code that complements EMF's built-in code generation"*

**Purpose of FreeMarker generation**:
- FreeMarker generates **application logic** (switches, factories, dispatchers)
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
| Template engine | FreeMarker (true M2T) | FreeMarker |
| Build process | Model → FreeMarker → compile | Model → generate → compile |
| Risk of bugs | Low (templates define structure) | Low (single source of truth) |

This is **true model-driven development**: templates define the transformation from model to code.

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

### FreeMarker Template Engine

The project uses **FreeMarker** for true template-driven code generation:
- Industry-standard template engine (Apache-licensed)
- Available from Maven Central (no Eclipse runtime dependencies)
- Clean separation of templates (`.ftl`) and Java logic
- Model-to-text transformation via data models

**Template Location**: `maze-generator.freemarker/src/main/resources/templates/`

### Templates Created

| Domain | Template | Generator | Generated Artifacts |
|--------|----------|-----------|-------------------|
| **Opponents** | `opponents/*.ftl` | ✅ `RunAcceleo.java` (FreeMarker) | `OpponentRegistry.java`, `CharacterRegistrar.java`, `CharacterAttributeSetter.java`, `CharacterGraphicsFactory.java` |
| **Walls** | `walls/*.ftl` | ✅ `RunWallsAcceleo.java` (FreeMarker) | `WallRegistry.java`, `WallMaterialRenderer.java`, `WallCollisionHandler.java` |
| **Difficulties** | ❌ Not yet | ❌ Not implemented | *(planned)* |
| **Behaviour** | ❌ Not yet | ❌ Not implemented | *(planned)* |

### Generated Code Statistics

Currently generated files:

| Domain | Files | Switch Cases | Factory Methods | Notes |
|--------|-------|--------------|-----------------|-------|
| Opponents | 4 | 3 (Zombie, Ghost, PumpkinBomber) | 3 | Type dispatch, attribute setters, graphics |
| Walls | 3 | 5 (Glass, Dirt, Wood, Stone, Steel) | N/A | Registry, renderer, collision |
| Difficulties | 0 | — | — | *Not yet implemented* |
| Behaviour | 0 | — | — | *Not yet implemented* |

### Generated Code Output

Generated files in `maze-module-generator/src-gen/main/game/maze/generated/`:

| File | Uses EMF Methods | Purpose | Status |
|------|------------------|----------|--------|
| `CharacterRegistrar.java` | `eClass().getName()` | Type-safe switch dispatch | ✅ Generated |
| `CharacterAttributeSetter.java` | `getHealth()`, `setHealth()`, `getThreatLevel()`, `setThreatLevel()`, `getSpeed()`, `setSpeed()` | Difficulty multipliers | ✅ Generated |
| `CharacterGraphicsFactory.java` | `getImageBase()`, `getAnimationFrameCount()`, `getSpriteScale()` | Sprite factory | ✅ Generated |
| `OpponentRegistry.java` | Model iteration | Enemy type listing | ✅ Generated |
| `WallRegistry.java` | Model iteration | Wall material listing | ✅ Generated |
| `WallMaterialRenderer.java` | `WallMaterialBaseType` switch | Color, sound, transparency | ✅ Generated |
| `WallCollisionHandler.java` | `WallMaterial` attributes | Damage, resistance, effects | ✅ Generated |

**Planned** (templates to be created):

| File | Uses EMF Methods | Purpose | Status |
|------|------------------|----------|--------|
| `DifficultyConfigurator.java` | `Difficulty` attributes | Settings extraction | ❌ Planned |
| `EnemySpawnLimits.java` | `EnemyMaxCount` iteration | Spawn cap management | ❌ Planned |
| `DifficultyRegistry.java` | `DifficultiesFactory` | Difficulty creation | ❌ Planned |
| `BehaviorDispatcher.java` | `MovementBehavior` switch | Behavior routing | ❌ Planned |
| `PathCalculatorFactory.java` | `BehaviourFactory` | Algorithm creation | ❌ Planned |
| `BehaviorRegistry.java` | `BehaviourFactory` | Behavior/event creation | ❌ Planned |

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
| `RunAcceleo.java` | ✅ | FreeMarker-based opponents generator |
| `RunWallsAcceleo.java` | ✅ | FreeMarker-based walls generator |
| `MANIFEST.MF` | ✅ | Embeds FreeMarker JAR, EMF model dependencies |
| FreeMarker templates | ✅ | `.ftl` templates in `src/main/resources/templates/` |

### Remaining Work

| Task | Status | Notes |
|------|--------|-------|
| FreeMarker template engine | ✅ | Embedded in `maze-generator.freemarker` |
| Opponents generation | ✅ | `RunAcceleo.java` (FreeMarker) + 4 `.ftl` templates |
| Walls generation | ✅ | `RunWallsAcceleo.java` (FreeMarker) + 3 `.ftl` templates |
| **Difficulties generation** | ❌ | Create templates and generator |
| **Behaviour generation** | ❌ | Create templates and generator |
| Refactor `OpponentRuntimeFactory.java` | ✅ | Delegates to generated `CharacterRegistrar` and `CharacterAttributeSetter` |
| Refactor `PatrolHelper.java` | ❌ | Use generated `BehaviorDispatcher` (blocked on Behaviour generator) |
| Add `src-gen` to `maze-javafx-backend/pom.xml` | ✅ | Already configured as dependency on `maze-module-generator` |
| End-to-end test | ❌ | Add new model element, verify no manual code needed |

---

## Running the Generation

### Build Integration

The build uses **FreeMarker-based generators** (`RunAcceleo.java`, `RunWallsAcceleo.java`) that:
1. Load EMF models from XMI files
2. Transform EMF objects into template data models
3. Process `.ftl` templates to generate Java source files

These are invoked by `maze-generator.freemarker-runner` during the Tycho build.

```bash
# Full build with generation
mvn clean verify
```

### Template Architecture

```
EMF Model (.xmi)   ────▶    Java Generator   ────▶   FreeMarker   ────▶   Generated Java
(source of truth)        (model → Map<>)           (.ftl templates)        (src-gen/)
```

Key benefits:
- **Templates define structure**: Java code layout is in `.ftl` files, easy to modify
- **Generators define data**: EMF-to-Map transformation in Java, type-safe
- **Clean separation**: Non-Java developers can edit templates

### Model Validation

The FreeMarker generators validate models before generation and provide null-safe defaults:

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

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](docs/technology-laymans-guide.md) | Simple explanation of MDD concepts in everyday terms |
| [FreeMarker Guide](freemarker.readme.md) | Detailed FreeMarker usage in this project |
| [Metamodel Architecture](docs/metamodel-architecture.md) | Technical details about metamodels and Xtext integration |
| [DSL Reference Guide](docs/dsl-reference.md) | MazeGame DSL syntax documentation |
| [DSL Tutorial](docs/dsl-tutorial.md) | Step-by-step guide to creating game levels |
| [Generated Code Module](maze-module-generator/readme.md) | Documentation for the generated code module |
