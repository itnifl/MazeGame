# Plan: DDD refactor of `GdxGameScreenController` → `Game` + `Screen` + `AssetManager`

## Goal

Break the ~1380-line `GdxGameScreenController` (currently `extends ApplicationAdapter`) into a
libGDX `Game` root plus multiple `Screen`s, backed by a shared `AssetManager` and
organized around Domain-Driven Design bounded contexts. Behavior must stay identical.
Java 21 only. All tests must stay green. Mirror changes into JavaFX only where shared
domain code is touched (CRR-5).

## Mandatory Architecture Constraints

1. Domain isolation is mandatory.
  Each bounded context must have one clear owner and must not be duplicated across screens or helper classes.
  Shared policies must live in shared backend collaborators and be consumed by frontends, not reimplemented per frontend.
2. MVC is mandatory.
  Views must remain passive renderers, controllers must own input orchestration and flow decisions, and model or domain services must own business state and rules.
  `GdxGameScreenController` and all extracted screens must be evaluated against MVC boundaries in every phase before a phase is marked complete.

Source file: `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxGameScreenController.java`

## Domains identified in `GdxGameScreenController.java`

The single class currently mixes ~15 distinct responsibilities, which consolidate into
9 DDD bounded contexts.

### Raw responsibilities (15)

1. App lifecycle / orchestration (`create` / `render` / `resize` / `dispose`, loading delay)
2. Asset / texture cache (`loadTexture`, `texturesByPath`, disposal)
3. Game-mode state machine (`GameMode` transitions, `switchToStartMenu`)
4. Maze world + player simulation (`maze`, `player`, movement, goal positioning)
5. Enemy AI + combat (`enemyDirectorService`, `animatedEnemies`, `combatState`, contacts)
6. Rendering stack (`batch`, `shapes`, `font`, `camera`, `hudCamera`, `viewport`)
7. HUD (`hudView`, `hudLayout`, `hudInteractionState`)
8. Start menu (`startMenuView`, `startMenuInputController`, difficulty selection)
9. Overlays (win / game-over / infection, win name entry + buttons)
10. High scores (`highScoreRepository`, `highScoreRows`, `loadHighScores`)
11. Terminal / debug commands (`terminalController`, `show*` countdowns)
12. Audio (`gameAudioDirector`, music switching)
13. Scoring + path hints (`scoringEngine`, `pathHintBudget`, `pathPenaltyPoints`)
14. Difficulty / board config (board width/height, base score, resize-for-difficulty)
15. Status messaging (`statusMessageBus`)

### Consolidated DDD bounded contexts (9)

- **Application / Shell** — `Game` root, screen routing, `AssetManager` owner
- **Gameplay / World** — maze, player, goal
- **Enemy & Combat**
- **Scoring & Progression** — score, high scores, path hints
- **Navigation / Session State** — `GameMode`, difficulty selection
- **Presentation / Rendering** — views, cameras, viewport
- **Asset Management**
- **Audio**
- **Input & Terminal**

## Target architecture

- `GdxGame extends com.badlogic.gdx.Game` — owns `AssetManager` and a `GameContext`
  (shared singletons: session, audio, scoring, high-score repo, difficulty service,
  status bus). Routes between screens via `setScreen`.
- `GdxAssetService` — wraps `AssetManager`: queue / load / get / unload. Replaces the
  manual `loadTexture` + `texturesByPath` cache and centralizes disposal.
- `AbstractGdxScreenController implements Screen` — shared camera / viewport / batch lifecycle base.
- `MenuScreenController extends AbstractGdxScreenController` — start menu + difficulty + high scores overlay
  + loading delay → triggers `PlayScreenController`.
- `PlayScreenController extends AbstractGdxScreenController` — gameplay loop: world, enemies, combat, HUD,
  terminal, path hints, camera follow. Raises win / lose → swaps to result overlay/screen.
- `ResultScreen` (or kept as a `PlayScreenController` overlay) — `WON` name entry / save + `GAME_OVER`.
- Existing `view/`, `controller/`, and `game/` collaborators are reused as-is; state fields
  move into the owning screen.

## MVC mapping (libGDX)

- **Model**
  `GameSession`, `ScoringEngine`, `PathHintBudget`, `EnemyDirectorService`,
  `PlayerCombatStateService`, `RuntimeVisualModel`, `RuntimeVisualModelLoader`,
  `DifficultyService`, `DifficultyPresentationSupport`, `DifficultyBoardConfig`,
  and shared backend domain services.
- **View**
  `GdxStartMenuView`, `GdxGameWorldView`, `GdxHudView`, `GdxOverlayView`,
  and layout DTOs under `view/layout/*`.
- **Controller**
  `MenuScreenController` and `PlayScreenController` as flow controllers, with input orchestration in
  `GdxStartMenuInputController`, `GdxPlayerInputController`, `GdxModeInputController`,
  `GdxTerminalController`, and interaction state in `GdxHudInteractionStateController`.

Rule enforced: views stay passive renderers, controller classes own flow and input decisions,
and models/services own state and rules.

## Steps

### Phase 0 — Safety net (do first)

1. Run full build + tests to capture a green baseline (`make-libgdx.ps1`).
2. Add characterization tests if gaps exist: screen routing, asset load/dispose, mode flow.

### Phase 1 — Asset management

3. Create `GdxAssetService` backed by `AssetManager` (Texture loader, internal files).
   Preserve null-on-missing behavior, or switch to explicit preload + `finishLoading`.
4. Replace `loadTexture` / `texturesByPath` usages; centralize disposal via `AssetManager`.

### Phase 2 — Game root + shared context

5. Add `GdxGame extends Game`; decide ownership of `batch` / `font` / cameras; create
   `GameContext` holding shared services + `GdxAssetService`.
6. Point `GdxAppLauncher` at `new GdxGame(cfg)` instead of `new GdxGameScreenController(...)`.

### Phase 3 — Screen extraction (incremental, one screen at a time)

7. `AbstractGdxScreenController` base (`show` / `render` / `resize` / `hide` / `pause` / `resume` /
   `dispose`, viewport).
8. Extract `MenuScreenController` (start menu input/render + high scores + loading delay).
9. Extract `PlayScreenController` (update/draw gameplay, enemies, combat, HUD, terminal,
   camera follow, path hints, goal positioning).
10. Extract win / game-over into `ResultScreen` or keep as a `PlayScreenController` overlay; wire
    transitions through `GdxGame.setScreen` instead of `GameMode` branching.
11. Reduce / retire `GdxGameScreenController` (or keep as a thin compatibility shim if tests need it).

### Phase 4 — Cleanup & parity

12. Move difficulty / board-config helpers into a `DifficultyBoardConfig` collaborator.
13. Update the JavaFX side only if shared domain classes changed (CRR-5).
14. Update RTM + requirements docs + module readme (WR-4, WR-5, WR-18).

## Relevant files

- `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxGameScreenController.java` — monolith to split.
- `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxAppLauncher.java` (line 51) — change root object.
- `maze-libgdx/src/main/java/main/game/maze/libgdx/view/*`, `controller/*`, `game/*` — reuse as-is.
- Tests: `GdxGameScreenParityTest`, `GdxGameScreenLayoutTest`,
  `GdxGameScreenTerminalCommandTest` — may need package / import updates.
- `docs/requirements-features/requirements-traceability-matrix.md` — update RTM.

## Verification

1. `pwsh ./make-libgdx.ps1` build + all libGDX tests green.
2. Parity / layout / terminal tests pass unchanged (or updated with equivalent asserts).
3. New tests: `GdxAssetService` load/get/dispose; `GdxGame` screen routing; MenuScreenController →
   PlayScreenController start; PlayScreenController win/lose transitions.
4. Manual smoke: run game, verify menu → play → win → save → high scores → game over.
5. Confirm Java 21 autodetect still works (CRR-6 / 7 / 8) via the `prepare-run` task.

## Decisions / open questions

- **Q1 — Win/GameOver as separate Screens vs overlays inside `PlayScreenController`?**
  Recommendation: keep as overlays (smaller change, preserves shared HUD), route only
  `MENU` / `PLAY` as real Screens. Option B: full Screen-per-state.
- **Q2 — AssetManager loading model:** resolved to preloading from `MenuScreenController` before route
  to gameplay using `GdxAssetService.queueTexture(...)`, `updateLoading()`, and progress display.
  Gameplay route is now gated until queued assets complete loading.
- **Q3 — Keep `GdxGameScreenController` name as a shim or delete it.** Resolved: keep as a temporary
  gameplay-only adapter while package-level tests remain anchored to ApplicationAdapter
  compatibility; remove once those tests are migrated to direct `Screen`-native coverage.
- **Scope excluded:** gameplay behavior changes, new features, JavaFX UI restructure.

## Phase Status

Last updated: 2026-06-05

| Phase | Status | Evidence | Notes |
|---|---|---|---|
| Phase 0 — Safety net | Completed | Ran `Ensure Java 21 (libGDX)` task, repeated `Run-P2AndBuildCheck-libgdx.ps1` with `BUILD SUCCESS`, and added characterization tests (`GdxGameRoutingTest`, `ApplicationAdapterScreenTest`, `GdxAssetServiceTest`) | Baseline captured and safety gaps for screen routing plus asset load/dispose behavior are now covered. |
| Phase 1 — Asset management | Completed | Added `GdxAssetService` with AssetManager-backed loading plus `updateLoading()` and `loadingProgress()`, added `GdxAssetServiceTest`, and implemented per-difficulty gameplay asset preloading in `MenuScreenController` with explicit loading progress before gameplay route | Asset preload policy is now explicit and route-to-play is gated on asset loading completion. |
| Phase 2 — Game root + shared context | Completed | Added `GdxGameContext`, wired `GdxGame` to build context on create, and kept full build-check green | Shared context object now exists and is used by default startup route. |
| Phase 3 — Screen extraction | Completed | Added `AbstractGdxScreenController`, `MenuScreenController`, `PlayScreenController`, and `LegacyPlayScreenController`; default route launches `MenuScreenController`, transitions into `PlayScreenController`, and return-to-menu is routed through `GdxGame` callbacks. `GdxGameScreenController` has been reduced to gameplay-only adapter behavior and no longer owns START_MENU mode update/input/draw paths. Split monolithic `GdxOverlayView` into dedicated overlay views (`GdxHighScoresOverlayView`, `GdxWinOverlayView`, `GdxGameOverOverlayView`, `GdxInfectionOverlayView`) and extracted overlay controllers (`GdxHighScoresOverlayController`, `GdxWinOverlayController`, `GdxGameOverOverlayController`) out of `GdxGameScreenController`. | Menu and play domains are now isolated at screen level with game-router-owned transitions and explicit overlay MVC boundaries. Remaining adapter retirement is tracked as follow-up cleanup, not a screen-routing blocker. |
| Phase 4 — Cleanup & parity | Completed | Added `DifficultyBoardConfig` and wired `GdxGameScreenController` plus `MenuScreenController` to dedicated board-size policy with `DifficultyBoardConfigTest`. Moved difficulty naming and board dimension policy into shared backend `DifficultyPresentationSupport`, consumed by both libGDX and JavaFX with `DifficultyPresentationSupportTest`. Added `GdxVisualStyleSupport` and wired `GdxGameScreenController`, `MenuScreenController`, and `GdxAppLauncher` to shared visual-style loading policy with `GdxVisualStyleSupportTest`. Added `GdxTerminalCommandSupport` and delegated terminal command parsing and interpretation from `GdxGameScreenController`, covered by `GdxTerminalCommandSupportTest`. Added `GdxDebugOverlayState` and delegated enemy-label and enemy-path overlay timer state from `GdxGameScreenController`, covered by `GdxDebugOverlayStateTest`. Added `GdxScoreSupport` and `GdxWinScoreSupport` with shared win-score submission path in `GdxGameScreenController`, covered by `GdxScoreSupportTest` and `GdxWinScoreSupportTest`. Updated RTM, suggested requirements, and module readme. | Phase 4 collaborator extraction and documentation updates are complete. |
