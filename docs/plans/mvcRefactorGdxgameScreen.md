Plan: MVC refactor of GdxGameScreen using maze-common-backend
Split the libGDX God Object GdxGameScreen into a clean MVC stack where the Model layer lives in maze-common-backend (pure, headless, reusable by JavaFX), the Controller layer lives in maze-libgdx (input dispatch, lifecycle, glue), and the View layer lives in maze-libgdx as small renderer classes. No JavaFX or libGDX types leak into maze-common-backend. Parity tests stay green at every step.

Recommended approach
Promote shared headless game state into maze-common-backend as a GameSession aggregate plus narrow services (mode machine, high score IO, path hint budget, status flash). Keep maze-common-frontend where it is, since movement, audio facade, and configs are already pure. GdxGameScreen becomes a thin lifecycle host that wires Model + Controllers + Views.

Target package layout

maze-common-backend/.../game/session/ — GameSession, GameMode, ModeTransitionListener
maze-common-backend/.../game/score/ — ScoringEngine, PathHintBudget, HighScoreRepository, FileHighScoreRepository
maze-common-backend/.../game/status/ — StatusMessageBus
maze-common-backend/.../game/runtime/ — EnemyDirectorService and a new EnemyRuntime interface
maze-common-backend/.../game/audio/ — GameAudioDirector consuming IAudioEngine
maze-libgdx/.../controller/ — GdxGameLoopController, GdxPlayerInputController, GdxModeInputController
maze-libgdx/.../view/ — GdxStartMenuView, GdxHudView, GdxGameWorldView, GdxOverlayView
maze-libgdx/.../view/layout/ — MenuLayout, HudLayout promoted to top level records
Steps

Phase 1 — Model foundation in maze-common-backend

Add GameMode enum and GameSession aggregate replacing scattered fields.
Add PathHintBudget service replacing applyPathPenalty math.
Add StatusMessageBus replacing flashStatus and timer.
Unit tests for each.
Phase 2 — Scoring and high score persistence in maze-common-backend

ScoringEngine mirroring formula in CharacterActionScreens.java.
HighScoreRepository interface and FileHighScoreRepository replacing libGDX loadHighScores and JavaFX loadScoresFromFile.
Unit tests.
Phase 3 — Enemy director and audio director in maze-common-backend

Add EnemyRuntime interface, make GdxEnemyRuntime.java implement it.
Add EnemyDirectorService that owns the four movement services and drives advance(...).
Add GameAudioDirector using IAudioEngine. Requires maze-common-backend to depend on maze-common-frontend. No cycle since frontend has no backend dep today.
Promote PlayerCombatStateService.java to common-backend, it is already headless.
Phase 4 — Controllers in maze-libgdx

GdxModeInputController owning ESC/H/O/T/P latches and per-mode dispatch.
GdxPlayerInputController owning arrow/WASD/mouse-step intents.
GdxGameLoopController orchestrating model, controllers, and view callbacks.
Phase 5 — Views in maze-libgdx

GdxStartMenuView from drawStartMenu and dropdown overlay.
GdxHudView from drawHud, commands overlay, terminal panel, status flash.
GdxGameWorldView from main game draw body.
GdxOverlayView from centered state, high scores, infection warning.
Promote MenuLayout and HudLayout to top level records.
Phase 6 — Shrink GdxGameScreen

Reduce to ApplicationAdapter lifecycle, asset/camera/viewport, dispose, and DI wiring.
update(dt) becomes a single delegate call.
draw() becomes mode dispatch to views.
Phase 7 — JavaFX adoption

Wire HighScoreRepository into JavaFX HighScoreController and ActionScreenController save path.
Phase 8 — Cleanup and docs

Update readme.md with MVC diagram.
Update RTM and requirements per WR-4/WR-5.
Relevant files

GdxGameScreen.java — shrink to lifecycle and wiring
GdxEnemyRuntime.java — implement new EnemyRuntime interface
PlayerCombatStateService.java — promote to common-backend
pom.xml — add maze-common-frontend interface dependency
HighScoreController.java — switch to repository in Phase 7
CharacterActionScreens.java — scoring source of truth
Verification

Per phase: mvn -pl maze-libgdx -am test -DskipITs must stay green at 150+ tests, plus 3 to 6 new tests per extracted class.
After Phase 7: mvn -pl maze-javafx-backend -am test -DskipITs green.
Both chains smoke: make-javafx.ps1 quick-no-tests and make-libgdx.ps1 quick-no-tests.
Manual VS Code launches after Phases 4, 5, 6 using existing configs.
Parity gates that must not regress: GdxGameScreenParityTest, GdxGameScreenLayoutTest, GdxScoringAndSpawnParityTest, GhostTangibilityParityTest, GdxMovementParityTest, parameterized difficulty parity tests.
Decisions

New Model layer goes into maze-common-backend per your direction; existing pure code in maze-common-frontend stays put.
EnemyRuntime becomes a backend agnostic interface; concrete GdxEnemyRuntime stays in libGDX.
maze-common-backend will depend on maze-common-frontend for IAudioEngine and movement service interfaces; verified no cycle.
Out of scope: porting JavaFX GameController frame loop, changing maze generation, AI tuning, OCL constraints, DSL pipeline.
Further Considerations

Promote PlayerCombatStateService to common-backend in Phase 3. Option A promote now, Option B leave for follow-up. Recommend A.
Standardize high score file path on DataFileConstants.HighscoreFilePath. Option A standardize, Option B keep both, Option C make injectable. Recommend A with injectable override for tests.
Migrate JavaFX HighScoreController to the new repository in this same PR. Option A same PR, Option B separate PR. Recommend A.