# Test Coverage Improvement Plan

**Date:** 2026-06-16  
**Status:** ACTIVE  
**Author:** Atle Holm

---

## 1. Current State

Coverage is measured as test classes vs production classes per module (class-count proxy, not line coverage).

| Module | Prod classes | Test classes | Proxy % | Assessment |
|--------|-------------|-------------|---------|------------|
| maze-common-backend | 41 | 17 | 41 % | ⚠️ Weak |
| maze-common-frontend | 44 | 18 | 41 % | ⚠️ Weak |
| maze-javafx-backend | 57 | 42 | 74 % | ✅ Good |
| maze-libgdx | 82 | 44 | 54 % | 🟡 Moderate |
| **Total** | **224** | **121** | **54 %** | |

### What is well covered
- Vector / Point2D mathematics
- Scoring engine and path-hint budget
- Movement AI (adaptive-aggressive, patrol, anti-loop wander, ghost phasing)
- Input routing and key-binding registry
- Game session bootstrapping and game-mode routing
- JavaFX game controller lifecycle, scoring parity, path hints
- Parity tests (JavaFX ↔ LibGdx implementations)

### Where coverage is lowest
1. **Game commands** – neither JavaFX nor LibGdx command objects are unit-tested
2. **Common services that drive gameplay** – `DifficultyService`, `ChasePlayerMovementService`, `TerminalCommandParser`, `CompositionResolverImpl`
3. **Character subclasses** – `ZombieCharacter`, `PumpkinBomberCharacter`, `GhostCharacter` damage/sound behaviour
4. **JavaFX action classes** – `GameOverAction`, `WinGameAction`, `StartScreenAction`
5. **JavaFX coordinators** – `FxHudCoordinator`, `FxDifficultyPickerSupport`, `FxPathHintCoordinator`
6. **LibGdx render pipeline** – `GdxGameRenderPipeline`, `GdxGameRenderStateAssembler`, layout/metrics classes
7. **LibGdx support classes** – `GdxGameCombatAndEnemyFlowSupport`, `GdxGameStartFlowApplySupport`, etc.

---

## 2. Principles Driving This Plan

- **Test behaviour, not implementation.** Tests assert observable state changes and side effects, not private fields.
- **CRR-5 compliance.** Every new test for a JavaFX class must have a parity test in maze-libgdx (and vice-versa) where the concept exists in both modules.
- **Headless-first.** JavaFX tests run headless (Xvfb / `Platform.runLater` with `flushFx`). LibGdx tests use the headless backend.
- **No vacuous tests.** Every assertion must be able to fail; tests that only assert non-null on trivially-non-null objects must add a behaviour assertion too.
- **Parity tests.** Where an abstraction is shared (movement, scoring, input), add a parity test that runs the same scenario against both implementations.

---

## 3. Prioritised Work Items

Each item includes: module, new test class name, class(es) under test, and the key scenarios to cover.

---

### Priority 1 — Core logic, no UI dependency (easy wins, high value)

#### P1-1 `ChasePlayerMovementServiceTest` — `maze-common-frontend`
**Class under test:** `ChasePlayerMovementService`  
**Scenarios:**
- Enemy approaches player directly on an open board
- Enemy correctly selects best cardinal direction when no wall blocks
- Enemy falls back to wander when all directions blocked
- Direction preference is stable (no oscillation on symmetric boards)

**Parity:** Add `GdxChaseMovementParityTest` to maze-libgdx verifying the same tick output for the same `WorldView`.

---

#### P1-2 `TerminalCommandParserTest` — `maze-common-frontend`
**Class under test:** `TerminalCommandParser`  
**Scenarios:**
- Parses `help` command to the correct `TerminalCommand` instance
- Parses `show-enemy-paths` with and without arguments
- Returns `TerminalCommand.UNKNOWN` for unrecognised input
- Handles empty string and null input gracefully (no exception)
- Case-insensitive matching

---

#### P1-3 `CompositionResolverImplTest` — `maze-common-backend`
**Class under test:** `CompositionResolverImpl` (implements `CompositionResolver`)  
**Scenarios:**
- Resolves a known opponent model composition to the expected behaviour chain
- Returns empty/default when no composition registered
- Two sequential resolves return the same result (idempotent)

---

#### P1-4 `DifficultyServiceTest` — `maze-common-backend`
**Class under test:** `DifficultyService`  
**Scenarios:**
- `list()` returns the three canonical difficulties in model order
- `getCurrent()` returns the difficulty set via `setCurrent()`
- `setCurrent()` persists across two service instances within the same JVM session
- `getCurrent()` before any `setCurrent()` returns a non-null default or `null` consistently

---

#### P1-5 `PlayerConfigTest` — `maze-common-backend`
**Class under test:** `PlayerConfig` (XMI-loaded player rules)  
**Scenarios:**
- Default values are within expected ranges (speed > 0, hitpoints > 0)
- Values loaded via `XmiRulesLoader` match defaults shipped in resources
- Equality and `toString` do not throw

---

#### P1-6 `GameCommandsTest` (JavaFX) — `maze-javafx-backend`
**Classes under test:** `MovePlayerCommand`, `ApplyPathHintCommand`, `ShowNavigationPathCommand`, `ReturnToMenuCommand`, `OpenHighScoresCommand`, `ToggleSpanningTreeCommand`  
**Scenarios per command:**
- Constructing with a valid sink does not throw
- `execute()` calls exactly the expected sink method (use a capturing test-double for `JavaFxInputCommandContext.ActionSink`)
- Two `execute()` calls produce two sink invocations (not deduplicated)

---

#### P1-7 `GdxGameCommandsTest` — `maze-libgdx`
**Mirror of P1-6 for LibGdx commands.**  
Same scenarios; the sink is `LibgdxInputCommandContext`.  
**Parity:** Verify that the command enum constants and their `execute` targets are 1-to-1 with JavaFX equivalents.

---

#### P1-8 `FxHudCoordinatorTest` — `maze-javafx-backend`
**Class under test:** `FxHudCoordinator`  
**Scenarios:**
- `showMouseCoords(x, y)` updates the supplied `Label` supplier's text to the expected format
- `setHudMessage(text)` updates message label text
- `setHudMessage(text, duration)` clears label text after the duration elapses (use `FakeUiScheduler`)
- Null supplier is handled without NPE

---

#### P1-9 `FxDifficultyPickerSupportTest` — `maze-javafx-backend`
**Class under test:** `FxDifficultyPickerSupport`  
**Scenarios:**
- Cancel path (empty `Optional`) calls `onResume` exactly once and does NOT call `onHardRestart`
- Hard-restart confirmation path calls `onHardRestart` exactly once and does NOT call `onResume`
- Normal path (chosen difficulty, no restart) calls `onDifficultySet` and `onResume` exactly once each
- No path calls `onStop` more than once

Use test doubles (`Runnable` capture via `AtomicInteger` counters) to avoid JavaFX dialog dependency.

---

#### P1-10 `GameOverActionTest` — `maze-javafx-backend`
**Class under test:** `GameOverAction`  
**Scenarios:**
- `execute()` stops the in-game music channel (verify via `AudioEngine` test double)
- `execute()` starts the game-over music loop
- `updateScore()` delegates to the scoring engine and returns a non-negative value
- After `execute()`, calling it again does not trigger duplicate audio calls

---

#### P1-11 `WinGameActionTest` — `maze-javafx-backend`
**Class under test:** `WinGameAction`  
**Scenarios:**
- `execute()` stops in-game music channel
- `execute()` starts win music loop
- `updateScore()` returns a value consistent with `GameScoringConstants.baseScoreFor()` for the current difficulty

---

### Priority 2 — Integration, coordinators, character types

#### P2-1 `ZombieCharacterTest` — `maze-javafx-backend`
**Class under test:** `ZombieCharacter`  
**Scenarios:**
- Constructed with a valid view, does not throw
- `onContact(player)` reduces player HP by the expected damage amount
- Audio `playRateLimited` is called on contact (verify via `AudioEngine.TestDouble`)
- `dispose()` cleans up without exception

---

#### P2-2 `PumpkinBomberCharacterTest` — `maze-javafx-backend`
**Class under test:** `PumpkinBomberCharacter`  
**Scenarios:**
- `detonate()` plays the explosion sound
- `detonate()` applies area damage to characters within radius
- Characters outside radius are not damaged
- Double-detonation guard: second call is a no-op

---

#### P2-3 `FxPathHintCoordinatorTest` — `maze-javafx-backend`
**Class under test:** `FxPathHintCoordinator`  
**Scenarios:**
- `refreshPathCanvas()` calls `clearRect` on the `GraphicsContext` (clear before draw)
- When `isRouteHintVisible()` is false, player path draw is skipped
- Enemy path drawer is always called with alpha reset to 1.0 (regression guard for the alpha-leak fix)
- `showNavigationPath()` sets `routeHintVisible` true in the model

---

#### P2-4 `FxEnemyCoordinatorTest` — `maze-javafx-backend`  
**Class under test:** `FxEnemyCoordinator`  
**Scenarios:**
- `register(enemy, position)` adds the enemy to the active set
- `stepAll()` invokes `step()` on each registered enemy
- `showEnemyDebugLabels(true)` shows debug overlays for all enemies
- `drawEnemyNavigationPaths(gc)` iterates registered enemies without NPE when paths are null

---

#### P2-5 `GdxGameRenderPipelineExtendedTest` — `maze-libgdx`
**Class under test:** `GdxGameRenderPipeline`  
**Scenarios (complementing existing tests):**
- When world is available and not paused, `renderWorld()` is called
- Overlay is rendered on top of world (render order: world → overlay)
- `dispose()` cleans up without exception after render cycle

---

#### P2-6 `GdxGameCombatAndEnemyFlowSupportTest` — `maze-libgdx`
**Class under test:** `GdxGameCombatAndEnemyFlowSupport`  
**Scenarios:**
- Enemy registration wires through to `EnemyRegistrar`
- Combat tick applies damage to player when enemy overlaps
- Player death notification fires when HP reaches zero

---

#### P2-7 `GdxGameStartFlowApplySupportTest` — `maze-libgdx`
**Class under test:** `GdxGameStartFlowApplySupport`  
**Scenarios:**
- Start flow applies difficulty to board config
- Start flow triggers session bootstrapper
- Start flow transitions game mode to PLAYING

---

### Priority 3 — Terminal, layout, asset management

#### P3-1 `GdxTerminalCommandSupportExtendedTest` — `maze-libgdx`
**Classes under test:** `GdxTerminalCommandSupport`, `GdxTerminalController`  
**Scenarios:**
- Sending `show-enemy-paths` command toggles enemy path overlay
- Sending `help` returns a non-empty response string
- Sending unknown command returns an error/unknown message
- Terminal open/close toggles the overlay visibility flag

---

#### P3-2 `GdxGameScreenLayoutTest` (extended) — `maze-libgdx`
**Class under test:** `GdxGameScreenLayout`, `HudLayout`, `MenuLayout`  
**Scenarios:**
- Layout produces non-negative x/y bounds for any positive screen dimension
- HUD occupies the bottom strip (y + height equals screen height)
- Game world area does not overlap HUD area

---

#### P3-3 `GdxAssetServiceExtendedTest` — `maze-libgdx`
**Class under test:** `GdxAssetService`  
**Scenarios:**
- `load(path, type)` registers asset for loading
- `get(path, type)` returns the loaded asset after `finishLoading()`
- `dispose()` releases all assets without exception

---

#### P3-4 `StartScreenActionTest` — `maze-javafx-backend`
**Class under test:** `StartScreenAction`  
**Scenarios:**
- `execute()` stops all game music channels
- `execute()` starts menu music loop
- Calling `execute()` twice does not stack duplicate audio channels

---

## 4. Suggested Testing Infrastructure

### 4.1 Shared test-doubles (add to `src/test/java/.../testutil/`)

| Double | Module | Purpose |
|--------|--------|---------|
| `CapturingAudioEngine` | maze-common-frontend | Records `play`, `playLoop`, `stopChannel` calls for assertion |
| `CapturingUiScheduler` | maze-common-frontend | Records scheduled callbacks; allows manual trigger |
| `FakeWorldView` | maze-common-frontend | Configurable `WorldView` for movement tests |
| `SpyActionSink` | maze-javafx-backend | Captures which `ActionSink` methods were called and how many times |

### 4.2 JaCoCo line-coverage gate
Add to root `pom.xml` (build → plugins) once Priority 1 items are done:

```xml
<plugin>
  <groupId>org.jacoco</groupId>
  <artifactId>jacoco-maven-plugin</artifactId>
  <version>0.8.12</version>
  <executions>
    <execution>
      <id>prepare-agent</id>
      <goals><goal>prepare-agent</goal></goals>
    </execution>
    <execution>
      <id>report</id>
      <phase>verify</phase>
      <goals><goal>report</goal></goals>
    </execution>
    <execution>
      <id>check</id>
      <goals><goal>check</goal></goals>
      <configuration>
        <rules>
          <rule>
            <element>BUNDLE</element>
            <limits>
              <limit>
                <counter>LINE</counter>
                <value>COVEREDRATIO</value>
                <minimum>0.60</minimum>  <!-- raise to 0.70 after P2 done -->
              </limit>
            </limits>
          </rule>
        </rules>
        <excludes>
          <!-- generated EMF model code -->
          <exclude>main/game/maze/difficulties/impl/**</exclude>
          <exclude>main/game/maze/opponents/impl/**</exclude>
        </excludes>
      </configuration>
    </execution>
  </executions>
</plugin>
```

---

## 5. Execution Order and Milestones

| Milestone | Items | Target exit criteria |
|-----------|-------|---------------------|
| **M1 – Core logic** | P1-1 through P1-11 | All 11 test classes green; proxy coverage ≥ 55 % common-backend/frontend |
| **M2 – Coordinators & characters** | P2-1 through P2-7 | All P2 tests green; proxy coverage ≥ 65 % maze-javafx-backend |
| **M3 – LibGdx pipeline & terminal** | P3-1 through P3-4 | All P3 tests green; JaCoCo line coverage ≥ 60 % across all modules |
| **M4 – Coverage gate** | Add JaCoCo plugin + 60 % gate | CI rejects PRs below the line-coverage gate |
| **M5 – Parity completeness** | Verify every JavaFX test has a LibGdx parity counterpart | Parity-test matrix fully cross-referenced |

---

## 6. Files to Create (Checklist)

### maze-common-frontend
- [ ] `src/test/java/main/game/maze/common/movement/ChasePlayerMovementServiceTest.java`
- [ ] `src/test/java/main/game/maze/common/input/TerminalCommandParserTest.java`
- [ ] `src/test/java/main/game/maze/testutil/CapturingAudioEngine.java`
- [ ] `src/test/java/main/game/maze/testutil/CapturingUiScheduler.java`
- [ ] `src/test/java/main/game/maze/testutil/FakeWorldView.java`

### maze-common-backend
- [ ] `src/test/java/main/game/maze/CompositionResolverImplTest.java`
- [ ] `src/test/java/main/game/maze/service/DifficultyServiceTest.java`
- [ ] `src/test/java/main/game/maze/PlayerConfigTest.java`

### maze-javafx-backend
- [ ] `src/test/java/main/game/maze/actions/GameOverActionTest.java`
- [ ] `src/test/java/main/game/maze/actions/WinGameActionTest.java`
- [ ] `src/test/java/main/game/maze/actions/StartScreenActionTest.java`
- [ ] `src/test/java/main/game/maze/javafx/hud/FxHudCoordinatorTest.java`
- [ ] `src/test/java/main/game/maze/javafx/menu/FxDifficultyPickerSupportTest.java`
- [ ] `src/test/java/main/game/maze/javafx/render/FxPathHintCoordinatorTest.java`
- [ ] `src/test/java/main/game/maze/javafx/render/FxEnemyCoordinatorTest.java`
- [ ] `src/test/java/main/game/maze/commands/JavaFxGameCommandsTest.java`
- [ ] `src/test/java/main/game/maze/characters/ZombieCharacterTest.java`
- [ ] `src/test/java/main/game/maze/characters/PumpkinBomberCharacterTest.java`
- [ ] `src/test/java/main/game/maze/testutil/SpyActionSink.java`

### maze-libgdx
- [ ] `src/test/java/main/game/maze/libgdx/commands/GdxGameCommandsTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/render/GdxGameRenderPipelineExtendedTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/flow/GdxGameCombatAndEnemyFlowSupportTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/flow/GdxGameStartFlowApplySupportTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/terminal/GdxTerminalCommandSupportExtendedTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/screen/GdxGameScreenLayoutExtendedTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/asset/GdxAssetServiceExtendedTest.java`
- [ ] `src/test/java/main/game/maze/libgdx/movement/GdxChaseMovementParityTest.java`

---

## 7. Definition of Done for This Plan

- [ ] All items in the checklist above are green in CI
- [ ] No test is vacuous (every assertion can fail)
- [ ] JaCoCo line coverage gate is wired to CI (`buildtest.yml` and `main.yml`)
- [ ] Every new test class added to the Requirements Traceability Matrix (RTM)
- [ ] `suggested-requirements.md` updated with coverage-gate requirement
- [ ] This document status changed to `COMPLETE`
