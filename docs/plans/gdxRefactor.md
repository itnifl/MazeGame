# GdxGameScreen MVC Refactor And Cross Backend UI Alignment

## Scope

This plan covers two tracks:
1. Immediate user visible fixes in JavaFX and libGDX.
2. Full MVC decomposition of `maze-libgdx/src/main/java/main/game/maze/libgdx/GdxGameScreen.java` while moving shared domain logic and interfaces to `maze-common-backend`.

The immediate fixes are shipped first to unblock gameplay and visual parity.

## Immediate Fix Track (Phase 0)

### 0.1 JavaFX background assets not displaying

Problem:
- `winScreen.fxml`, `gameOverScreen.fxml`, and `game.fxml` use relative image URLs (`@file.png`) that can fail depending on classpath merge behavior.

Fix:
- Use absolute classpath URLs in FXML:
  - `@/main/game/maze/zombieGameOverBackground1.png`
  - `@/main/game/maze/zombieBackground.png`
  - `@/main/game/maze/heart2.png`

Validation:
- Launch JavaFX backend and verify Win, Game Over, and heart graphics render.

### 0.2 JavaFX end screens must match libGDX design language

Target design based on libGDX `drawCenteredStateOverlay`:
- Full screen backdrop image.
- Dim overlay layer.
- Centered panel with:
  - Semi transparent black background.
  - Cyan border.
  - Large title (green for win, red for game over).
  - Subtext in light cyan.
  - Score emphasis in gold.

Fix:
- Rework `winScreen.fxml` and `gameOverScreen.fxml` layout to mirror the panel composition and color palette.
- Keep existing controller wiring and score labels.

Validation:
- Run both backends and compare visual layout parity by screenshot.

### 0.3 Save score access policy

Requirement:
- WinScreen only may save score.
- GameOverScreen must not expose save score.
- Applies to JavaFX and libGDX.

JavaFX fix:
- Move `saveScore()` behavior from `ActionScreenController` to `WinGameController`.
- Remove Save button from `gameOverScreen.fxml`.
- Keep helper `writeScore()` in `ActionScreenController` for reuse.

libGDX fix:
- Add WIN mode name entry and save flow only in WIN state.
- Do not add save behavior to GAME_OVER state.
- Persist using `DataFileConstants.HighscoreFilePath`.

Validation:
- JavaFX: Save appears and works on Win only.
- libGDX: Name entry shown on Win only, Enter persists score, Game Over has no save.

### 0.4 libGDX HP bar background

Requirement:
- Unfilled HP portion should be white, not transparent to gameboard.

Fix:
- In `drawHud()`, draw full width white HP background first.
- Draw red filled segment on top by ratio.
- Keep cyan outline and all other gameboard rendering unchanged.

Validation:
- Damage player, observe red decreases while white remains behind.

## MVC Refactor Track (Phases 1 to 8)

## 1. Architectural goals

- Preserve behavior parity with existing tests.
- Keep rendering framework specific code in frontend modules.
- Place shared domain logic and interfaces in `maze-common-backend`.
- Keep `maze-common-backend` free of JavaFX and libGDX classes.

## 2. Target layering

### Model layer (shared)
Module: `maze-common-backend`

New package families:
- `...game.session`
- `...game.score`
- `...game.status`
- `...game.runtime`
- `...game.audio`

Core additions:
- `GameMode`
- `GameSession`
- `PathHintBudget`
- `ScoringEngine`
- `StatusMessageBus`
- `HighScoreRepository`
- `FileHighScoreRepository`
- `EnemyRuntime` interface
- `EnemyDirectorService`
- `GameAudioDirector`

### Controller layer (backend specific glue)
Module: `maze-libgdx`

New classes:
- `GdxModeInputController`
- `GdxPlayerInputController`
- `GdxGameLoopController`

Responsibilities:
- Read inputs.
- Map to model intents.
- Trigger model transitions.
- Request view rendering payloads.

### View layer (backend specific rendering)
Module: `maze-libgdx`

New classes:
- `GdxStartMenuView`
- `GdxHudView`
- `GdxGameWorldView`
- `GdxOverlayView`

Move geometry records to top level:
- `MenuLayout`
- `HudLayout`

## 3. Detailed phase plan

### Phase 1: Session and state extraction

From `GdxGameScreen` extract to shared model:
- mode and transitions
- pause flags
- loading flags
- score counters and move counts
- win and game over latch flags

Create tests for each state transition path.

### Phase 2: Scoring and persistence extraction

Implement:
- `ScoringEngine` with explicit inputs for base score, movement penalty, path penalty, damage and death adjustments.
- `HighScoreRepository` and file implementation.

Refactor JavaFX and libGDX read and write paths to repository.

### Phase 3: Runtime orchestration extraction

Implement:
- `EnemyRuntime` interface in shared backend.
- `EnemyDirectorService` using existing movement services.
- `GameAudioDirector` for music mode transitions.

Keep concrete enemy runtime wrappers in backend modules but hide behind shared interface.

### Phase 4: Input orchestration extraction

Create controller classes and move all latch and mode key handling out of `GdxGameScreen`.

### Phase 5: Rendering decomposition

Move all draw methods from `GdxGameScreen` into dedicated view classes.

### Phase 6: Shrink host screen

`GdxGameScreen` becomes lifecycle shell:
- create
- resize
- render
- dispose
- dependency wiring only

### Phase 7: JavaFX convergence

Adopt shared score repository and scoring model in JavaFX controllers to avoid backend drift.

### Phase 8: Documentation and traceability

Update:
- requirements mapping
- RTM entries
- module README references
- suggested requirements for deferred items

## 4. Verification strategy

Per phase gates:
- `mvn -pl maze-libgdx -am test -DskipITs`
- `mvn -pl maze-javafx-backend -am test -DskipITs`

Parity gates:
- `GdxGameScreenParityTest`
- `GdxGameScreenLayoutTest`
- `GdxScoringAndSpawnParityTest`
- `GdxMovementParityTest`
- `GhostTangibilityParityTest`

Manual checks:
- Launch JavaFX and libGDX backends.
- Confirm Win and Game Over visuals.
- Confirm score save allowed only on Win.
- Confirm HP bar white background behavior.

## 5. Risks and mitigations

Risk: behavior drift during decomposition.
Mitigation: small extraction steps with green tests after each move.

Risk: model dependency cycle between common modules.
Mitigation: keep interfaces slim and verify Maven graph before merge.

Risk: UI parity subjective mismatch.
Mitigation: capture reference screenshots and compare before merging.

## 6. Deliverables

Immediate:
- Fixed image loading paths.
- JavaFX end screens aligned with libGDX style.
- Save score restricted to Win in JavaFX and libGDX.
- libGDX HP bar white background.

Refactor:
- MVC split complete with shared domain in `maze-common-backend`.
- `GdxGameScreen` reduced to a thin shell.
- Updated tests and docs.
