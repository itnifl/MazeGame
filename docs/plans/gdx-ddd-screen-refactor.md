# Plan: DDD refactor of `GdxGameScreen` → `Game` + `Screen` + `AssetManager`

## Goal

Break the ~1380-line `GdxGameScreen` (currently `extends ApplicationAdapter`) into a
libGDX `Game` root plus multiple `Screen`s, backed by a shared `AssetManager` and
organized around Domain-Driven Design bounded contexts. Behavior must stay identical.
Java 21 only. All tests must stay green. Mirror changes into JavaFX only where shared
domain code is touched (CRR-5).

Source file: `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxGameScreen.java`

## Domains identified in `GdxGameScreen.java`

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
- `AbstractGdxScreen implements Screen` — shared camera / viewport / batch lifecycle base.
- `MenuScreen extends AbstractGdxScreen` — start menu + difficulty + high scores overlay
  + loading delay → triggers `PlayScreen`.
- `PlayScreen extends AbstractGdxScreen` — gameplay loop: world, enemies, combat, HUD,
  terminal, path hints, camera follow. Raises win / lose → swaps to result overlay/screen.
- `ResultScreen` (or kept as a `PlayScreen` overlay) — `WON` name entry / save + `GAME_OVER`.
- Existing `view/`, `controller/`, and `game/` collaborators are reused as-is; state fields
  move into the owning screen.

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
6. Point `GdxAppLauncher` at `new GdxGame(cfg)` instead of `new GdxGameScreen(...)`.

### Phase 3 — Screen extraction (incremental, one screen at a time)

7. `AbstractGdxScreen` base (`show` / `render` / `resize` / `hide` / `pause` / `resume` /
   `dispose`, viewport).
8. Extract `MenuScreen` (start menu input/render + high scores + loading delay).
9. Extract `PlayScreen` (update/draw gameplay, enemies, combat, HUD, terminal,
   camera follow, path hints, goal positioning).
10. Extract win / game-over into `ResultScreen` or keep as a `PlayScreen` overlay; wire
    transitions through `GdxGame.setScreen` instead of `GameMode` branching.
11. Reduce / retire `GdxGameScreen` (or keep as a thin compatibility shim if tests need it).

### Phase 4 — Cleanup & parity

12. Move difficulty / board-config helpers into a `DifficultyBoardConfig` collaborator.
13. Update the JavaFX side only if shared domain classes changed (CRR-5).
14. Update RTM + requirements docs + module readme (WR-4, WR-5, WR-18).

## Relevant files

- `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxGameScreen.java` — monolith to split.
- `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxAppLauncher.java` (line 51) — change root object.
- `maze-libgdx/src/main/java/main/game/maze/libgdx/view/*`, `controller/*`, `game/*` — reuse as-is.
- Tests: `GdxGameScreenParityTest`, `GdxGameScreenLayoutTest`,
  `GdxGameScreenTerminalCommandTest` — may need package / import updates.
- `docs/requirements-features/requirements-traceability-matrix.md` — update RTM.

## Verification

1. `pwsh ./make-libgdx.ps1` build + all libGDX tests green.
2. Parity / layout / terminal tests pass unchanged (or updated with equivalent asserts).
3. New tests: `GdxAssetService` load/get/dispose; `GdxGame` screen routing; MenuScreen →
   PlayScreen start; PlayScreen win/lose transitions.
4. Manual smoke: run game, verify menu → play → win → save → high scores → game over.
5. Confirm Java 21 autodetect still works (CRR-6 / 7 / 8) via the `prepare-run` task.

## Decisions / open questions

- **Q1 — Win/GameOver as separate Screens vs overlays inside `PlayScreen`?**
  Recommendation: keep as overlays (smaller change, preserves shared HUD), route only
  `MENU` / `PLAY` as real Screens. Option B: full Screen-per-state.
- **Q2 — AssetManager loading model:** lazy null-safe (current behavior) vs preload with a
  loading screen. Recommendation: preload per-difficulty assets at screen `show()` +
  `finishLoading()`.
- **Q3 — Keep `GdxGameScreen` name as a shim or delete it.** Recommendation: delete after
  tests are migrated.
- **Scope excluded:** gameplay behavior changes, new features, JavaFX UI restructure.

## Phase Status

Last updated: 2026-06-04

| Phase | Status | Evidence | Notes |
|---|---|---|---|
| Phase 0 — Safety net | Completed | Ran `Ensure Java 21 (libGDX)` task and `Run-P2AndBuildCheck-libgdx.ps1` with `BUILD SUCCESS` | Baseline captured before refactor implementation. |
| Phase 1 — Asset management | In progress | Added `GdxAssetService` with AssetManager-backed loading and added `GdxAssetServiceTest` | `GdxGameScreen` texture loading now delegates to asset service. Full preload policy and explicit loading screen still pending. |
| Phase 2 — Game root + shared context | In progress | Added `GdxGame`, `ApplicationAdapterScreen`, and updated `GdxAppLauncher` to launch `GdxGame` | Shared context object not yet introduced. Current step uses adapter for compatibility. |
| Phase 3 — Screen extraction | Not started | N/A | Existing monolith remains, now wrapped by compatibility screen adapter. |
| Phase 4 — Cleanup & parity | Not started | N/A | Pending after screen extraction. |
