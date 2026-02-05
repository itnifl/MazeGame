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
| `CharacterGraphicsFactory.java` | Sprite paths | Graphics creation |

**Value**: Adding `Skeleton` to model auto-generates all factory/registration code.

---

## Template Structure

```
maze-generator.acceleo/src/main/java/main/game/maze/gen/templates/
├── Generate.mtl                      # Master entry point
│
├── walls/
│   ├── WallMaterialRenderer.mtl
│   ├── WallCollisionHandler.mtl
│   └── WallPropertyAccessor.mtl
│
├── difficulties/
│   ├── DifficultyConfigurator.mtl
│   ├── EnemySpawnLimits.mtl
│   └── DifficultyConstants.mtl
│
├── behaviour/
│   ├── BehaviorDispatcher.mtl
│   ├── PathCalculatorFactory.mtl
│   └── PatrolStateMachine.mtl
│
└── opponents/
    ├── CharacterAttributeSetter.mtl   # Modular (also in Generate.mtl)
    └── CharacterGraphicsFactory.mtl   # Modular (also in Generate.mtl)

> Note: `CharacterRegistrar` is generated directly in `Generate.mtl` to keep all opponent dispatch logic together.
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

### Templates Created

| Domain | Template | Status | Generated Artifact |
|--------|----------|--------|-------------------|
| **Difficulties** | `DifficultyConfigurator.mtl` | ✅ | `DifficultyConfigurator.java` |
| **Difficulties** | `EnemySpawnLimits.mtl` | ✅ | `EnemySpawnLimits.java` |
| **Opponents** | `Generate.mtl` (inline) | ✅ | `CharacterRegistrar.java` |
| **Opponents** | `CharacterAttributeSetter.mtl` | ✅ | `CharacterAttributeSetter.java` |
| **Opponents** | `CharacterGraphicsFactory.mtl` | ✅ | `CharacterGraphicsFactory.java` |
| **Behaviour** | `BehaviorDispatcher.mtl` | ✅ | `BehaviorDispatcher.java` |
| **Behaviour** | `PathCalculatorFactory.mtl` | ✅ | `PathCalculatorFactory.java` |
| **Walls** | `WallMaterialRenderer.mtl` | ✅ | `WallMaterialRenderer.java` |
| **Walls** | `WallCollisionHandler.mtl` | ✅ | `WallCollisionHandler.java` |
| **Walls** | `WallPropertyAccessor.mtl` | ✅ | `WallPropertyAccessor.java` |

### Generated Code Output

Generated files are in `maze-module-generator/src-gen/main/game/maze/generated/`:

| File | Uses EMF Methods | Purpose |
|------|------------------|----------|
| `CharacterRegistrar.java` | `eClass().getName()` | Type-safe switch dispatch |
| `CharacterAttributeSetter.java` | `getThreatLevel()`, `setThreatLevel()` | Difficulty multipliers |
| `CharacterGraphicsFactory.java` | `getImageBase()` | Sprite factory |
| `OpponentRegistry.java` | Model iteration | Enemy type listing |
| `WallRegistry.java` | Model iteration | Wall material listing |

### Unit Tests

| Test Class | Status | Coverage |
|------------|--------|----------|
| `CharacterRegistrarTest.java` | ✅ | `isKnownType()`, `getKnownTypes()` |
| `CharacterAttributeSetterTest.java` | ✅ | `getBaseHealth()`, `getBaseThreatLevel()` |
| `CharacterGraphicsFactoryTest.java` | ✅ | `getAnimationFrameCount()`, `getSpriteScale()` |

### Infrastructure Updates

| Component | Status | Description |
|-----------|--------|-------------|
| `Generate.mtl` | ✅ | Master entry point updated with imports and entry points for all domains |
| `MANIFEST.MF` (acceleo) | ✅ | Added all model plugin dependencies |
| `MANIFEST.MF` (runner) | ✅ | Added all model plugin dependencies |
| `pom.xml` (generator) | ✅ | Added JUnit 5, opponents, difficulties dependencies |

### Remaining Work

| Task | Status | Notes |
|------|--------|-------|
| Run generation in Maven build | 🔄 | Requires Acceleo runner configuration |
| Refactor `OpponentRuntimeFactory.java` | ✅ | Delegates to generated `CharacterRegistrar` and `CharacterAttributeSetter` |
| Refactor `PatrolHelper.java` | ❌ | Use generated `BehaviorDispatcher` (lower priority) |
| Add `src-gen` to `maze/pom.xml` | ✅ | Already configured as dependency on `maze-module-generator` |
| End-to-end test | ❌ | Add new model element, verify no manual code needed |
