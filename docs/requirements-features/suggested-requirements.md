# Suggested Requirements

## Candidate Additions



### JavaFX MVC + Command/Registry refactor (Phase 5 of `docs/plans/javafx-gamecontroller-mvc-command-refactor.md`)

These requirements bring the JavaFX frontend to structural parity (CRR-5) with the libGDX MVC + Command/Registry architecture. They are scheduled to be ratified in Phase 5 of the JavaFX refactor plan, on a dedicated branch and pull request separate from the libGDX round.

- SR-42: The Command and key binding registry input core (logical action enum, key binding registry, input frame contract, input router, edge key tracker, and command/context types) shall be shared in `maze-common-frontend` and consumed by both frontends. The dispatch loop shall be generalized over a frontend neutral key type (or a neutral key code with a per frontend adapter) so adding a new key or action does not require modifying the router. libGDX shall be migrated to consume the promoted types so it keeps compiling and its existing input tests stay green.
- SR-43: JavaFX gameplay input shall be handled via the shared key binding registry resolving logical actions to command objects, replacing the inline `handleKeyPressed` switch on `KeyCode`. Key polling shall be centralized in one JavaFX input snapshot reader, and movement, terminal, high scores, spanning tree, path hint, and return to menu shall each be expressed as a command without scattered keyboard checks in the controller.
- SR-44: JavaFX gameplay mutable runtime state shall live in a dedicated model boundary, separate from lifecycle, rendering, input, and FXML concerns, so the controller coordinates behavior without directly owning every gameplay field.
- SR-45: JavaFX gameplay start and reset flow shall be encapsulated in a dedicated bootstrap boundary that initializes arena, runtime model, player configuration, board background, enemies, and per session runtime flags in one place, preserving current setup behavior.
- SR-46: The JavaFX movement thread, movement animation timer, and movement watchdog shall be owned by a dedicated concurrency coordinator with unchanged thread affinity, join timeout, start, stop, and disposal semantics, keeping the controller free of direct thread and timer lifecycle management.
- SR-47: JavaFX mode specific update logic shall be dispatched by a deterministic mode state machine router that evaluates mode handlers in a fixed order and returns after the first handling controller, mirroring the libGDX mode router.
- SR-48: JavaFX frame rendering shall be orchestrated by a dedicated render coordinator that consumes an immutable render snapshot from the gameplay model and delegates actual drawing to existing canvas, HUD, and overlay view helpers, with all scene graph mutation remaining on the JavaFX Application Thread.
- SR-49: JavaFX audio transitions for menu, in game, win, and game over shall be encapsulated behind a dedicated coordinator boundary over `GameAudioDirector`, keeping the controller free from direct channel switching policy.
- SR-50: During JavaFX input migration to command and registry architecture, command dispatch shall emit lightweight structured diagnostics (command name, input action, and mode) through a frontend neutral observability hook enabled in debug builds. This shall support parity troubleshooting between JavaFX and libGDX without changing gameplay behavior.
