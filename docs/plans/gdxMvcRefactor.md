# GdxGameScreen MVC Refactor And Cross-Backend Domain Consolidation

Single source of truth for the libGDX `GdxGameScreen` decomposition and the
shared-domain promotion into `maze-common-backend`. Supersedes the previous
`gdxRefactor.md` and `mvcRefactorGdxgameScreen.md` plans (both merged here,
deleted from the repo).

## Status snapshot

### Done (do not redo)

- JavaFX background image classpath URLs corrected on win, game over, in-game,
  highscore screens.
- JavaFX end screens redesigned to match the libGDX overlay (centered black
  panel, cyan border, colored title, score emphasis).
- Save-score restricted to Win in both backends; Game Over has no save path.
- libGDX HP bar white background drawn behind the red fill.
- libGDX Save Score and Back to Menu buttons clickable inside the WON overlay.
- Start menu High Scores button in both backends; post-save navigates
  highscore screen then back to start menu.
- Case-insensitive high-score upsert with duplicate-name dedupe via the shared
  helper `main.game.maze.dto.HighScoreFile` (used by JavaFX `upsertScore` and
  libGDX `writeHighScore`). Covered by `HighScoreFileTest`.
- Small Phase 4 slice already in: `GdxStartMenuInputController` extracted from
  `GdxGameScreen` with `GdxStartMenuInputControllerTest`.
- Phase 1 and 2 shared-domain slice implemented:
  - Added `PathHintBudget` and integrated libGDX P-key budget consumption.
  - Added `StatusMessageBus` and integrated libGDX transient HUD/start-menu status messages.
  - Added `ScoringEngine` and integrated both libGDX runtime score computation and JavaFX action-screen scoring.
  - Added `HighScoreRepository` + `FileHighScoreRepository` and migrated libGDX and JavaFX high-score load/upsert paths to the repository.
  - Added unit tests for all new shared classes under `maze-common-backend`.
- Phase 3 slice implemented:
  - Added shared runtime contracts and orchestration: `EnemyRuntime`, `EnemyDirectorService`.
  - `GdxEnemyRuntime` now implements `EnemyRuntime`; `GdxGameScreen` delegates enemy ticking through `EnemyDirectorService`.
  - Added shared audio orchestration: `GameAudioDirector`; libGDX now delegates menu/in-game/win/game-over channel transitions through it.
  - Added unit tests: `EnemyDirectorServiceTest`, `GameAudioDirectorTest`.
- Phase 4 slice implemented:
  - Added `GdxPlayerInputController` for normalized movement intent extraction.
  - Added `GdxModeInputController` for ESC/H/O/T edge-latch handling.
  - `GdxGameScreen` now delegates movement intent and key-latch transitions through these controllers.
  - Added unit tests: `GdxPlayerInputControllerTest`, `GdxModeInputControllerTest`.
- Phase 5 slice implemented:
  - Promoted `MenuLayout` and `HudLayout` from inner host-screen state holders to top-level immutable records under `maze-libgdx/.../view/layout/`.
  - `GdxGameScreen` now computes per-frame layout snapshots and consumes record accessors for input hit-testing.
  - Extracted start-menu rendering into dedicated `GdxStartMenuView`, keeping host-screen code focused on wiring and state.
  - Extracted gameplay HUD and commands overlay rendering into dedicated `GdxHudView`, with `GdxGameScreen` delegating HUD layout and draw orchestration.
  - Extracted gameplay world rendering into dedicated `GdxGameWorldView`, including maze background tiling, world entities, fallback rendering, and path or tree world overlays.
  - Extracted high-scores overlay, centered win/game-over overlays, and infection warning sign rendering into dedicated `GdxOverlayView`, including win-button bounds projection back to host-screen input handling.
- Phase 6 slice implemented:
  - Reduced `GdxGameScreen.update(...)` branching complexity by extracting mode-specific update handling into dedicated private handlers for start menu, high scores, won, and game-over modes.
  - Reduced `GdxGameScreen.draw(...)` branching complexity by extracting mode-specific overlay rendering calls into dedicated private helpers.
  - Adopted shared `GameSession` and `GameMode` in libGDX `GdxGameScreen` as the primary holder for runtime mode and session flags (loading, paused-from-game, high-score return, win-save, base score, move count).

### Remaining

Remaining open phase is 7.

- Phase 8 slice implemented:
  - Updated root README with a libGDX MVC overview diagram.
  - Updated RTM with SR-22 partial traceability entry for session aggregate adoption.
  - Added deferred follow-up requirements in `docs/requirements-features/suggested-requirements.md` (SR-22 and SR-23).

## Architectural goals

- Preserve behavior parity; existing tests must stay green at every phase.
- Keep rendering framework code in frontend modules; no JavaFX or libGDX types
  in `maze-common-backend`.
- Promote shared domain logic and stable interfaces into `maze-common-backend`.
- Reduce `GdxGameScreen` to a thin lifecycle host that wires Model + Controllers
  + Views.

## Target package layout

- `maze-common-backend/.../game/session/` GameSession, GameMode,
  ModeTransitionListener.
- `maze-common-backend/.../game/score/` ScoringEngine, PathHintBudget,
  HighScoreRepository (interface), FileHighScoreRepository (delegates to
  existing `HighScoreFile`).
- `maze-common-backend/.../game/status/` StatusMessageBus.
- `maze-common-backend/.../game/runtime/` EnemyRuntime interface,
  EnemyDirectorService.
- `maze-common-backend/.../game/audio/` GameAudioDirector consuming
  `IAudioEngine`.
- `maze-libgdx/.../controller/` GdxGameLoopController, GdxPlayerInputController,
  GdxModeInputController (start-menu controller already exists; keep its
  package once `controller/` is introduced or move it then).
- `maze-libgdx/.../view/` GdxStartMenuView, GdxHudView, GdxGameWorldView,
  GdxOverlayView.
- `maze-libgdx/.../view/layout/` MenuLayout, HudLayout promoted to top-level
  records.

## Phase plan

### Phase 1: Session and state extraction

Extract from `GdxGameScreen` into `maze-common-backend`:

- `GameMode` enum and a `GameSession` aggregate replacing scattered fields
  (mode, pause flags, loading flags, score counters, move counts, win/game-over
  latch flags).
- `PathHintBudget` service replacing the `applyPathPenalty` math.
- `StatusMessageBus` replacing `flashStatus` and its timer.
- Unit tests for every transition path and budget edge case.

### Phase 2: Scoring and persistence

- Introduce `ScoringEngine` mirroring the formula currently in
  `CharacterActionScreens` and the libGDX equivalents.
- Introduce `HighScoreRepository` interface; `FileHighScoreRepository`
  delegates to the existing `HighScoreFile` helper (already deduping). Allow
  an injectable path override for tests.
- Migrate libGDX `loadHighScores` / `writeHighScore` and JavaFX
  `loadScoresFromFile` / `upsertScore` to the repository.
- Unit tests covering scoring inputs (base, movement penalty, path penalty,
  damage and death adjustments) and repository read/write semantics.

### Phase 3: Runtime and audio directors

- Add `EnemyRuntime` interface; make `GdxEnemyRuntime` implement it. Keep the
  concrete runtime in `maze-libgdx`.
- Add `EnemyDirectorService` owning the four movement services and driving the
  `advance(...)` call.
- Add `GameAudioDirector` using `IAudioEngine` for music mode transitions.
  This requires `maze-common-backend` to depend on `maze-common-frontend`
  (interfaces only). Verify the Maven graph stays acyclic.
- Promote `PlayerCombatStateService` to `maze-common-backend` (already
  headless).

### Phase 4: Input orchestration

- `GdxModeInputController` owning ESC/H/O/T/P latches and per-mode dispatch.
- `GdxPlayerInputController` owning arrow/WASD/mouse-step intents.
- `GdxGameLoopController` orchestrating model, controllers, and view callbacks.
- Migrate the existing `GdxStartMenuInputController` into the new
  `controller/` package if not already there.

### Phase 5: Rendering decomposition

- `GdxStartMenuView` from `drawStartMenu` and dropdown overlay.
- `GdxHudView` from `drawHud`, commands overlay, terminal panel, status flash.
- `GdxGameWorldView` from the main game draw body.
- `GdxOverlayView` from centered state overlay, high scores, infection warning,
  win Save Score / Back to Menu buttons.
- Promote `MenuLayout` and `HudLayout` to top-level records.

### Phase 6: Shrink host screen

`GdxGameScreen` becomes:

- `ApplicationAdapter` lifecycle (create, resize, render, dispose).
- Asset, camera, viewport setup.
- DI wiring only.
- `update(dt)` delegates to `GdxGameLoopController`.
- `draw()` dispatches by mode to view classes.

### Phase 7: JavaFX convergence

- Wire `HighScoreRepository` into `HighScoreController` and the JavaFX save
  path so both backends share the same persistence and scoring code.

### Phase 8: Documentation and traceability

- Update `readme.md` with an MVC diagram.
- Update the RTM and requirements docs per WR-4/WR-5.
- File deferred items in `docs/requirements-features/suggested-requirements.md`
  per DOD-3.

## Verification strategy

Per-phase gates:

- `mvn -pl maze-libgdx -am test -DskipITs`
- `mvn -pl maze-javafx-backend -am test -DskipITs`

Parity gates (must not regress):

- `GdxGameScreenParityTest`
- `GdxGameScreenLayoutTest`
- `GdxScoringAndSpawnParityTest`
- `GdxMovementParityTest`
- `GhostTangibilityParityTest`
- Parameterized difficulty parity tests

Smoke checks after Phases 4, 5, 6:

- `make-javafx.ps1` quick-no-tests
- `make-libgdx.ps1` quick-no-tests
- Manual launch via the existing VS Code debug configs; verify Win, Game Over,
  high score, start menu, HP bar visuals.

## Risks and mitigations

- Behavior drift during decomposition. Mitigation: small extraction steps with
  green tests after each move.
- Model dependency cycle. Mitigation: keep promoted interfaces slim; verify
  the Maven graph before each merge.
- UI parity subjective mismatch. Mitigation: capture reference screenshots
  before and after each rendering change.

## Out of scope

JavaFX `GameController` frame loop changes, maze generation, AI tuning, OCL
constraints, DSL pipeline.
