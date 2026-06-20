package main.game.maze.libgdx.controller;

import main.game.maze.game.session.GameMode;
import main.game.maze.libgdx.controller.state.GdxOverlayModeCoordinator;
import main.game.maze.libgdx.controller.state.PlayingModeController;
import main.game.maze.libgdx.helper.GdxGameInteractionSupport;
import main.game.maze.libgdx.helper.GdxGameLayoutSupport;
import main.game.maze.libgdx.helper.GdxGameMouseInteractionCoordinator;
import main.game.maze.libgdx.helper.GdxGamePlayingBridgeFactory;
import main.game.maze.libgdx.helper.GdxGameStartFlowRequestFactory;
import main.game.maze.libgdx.input.command.LibgdxInputCommandContext;
import main.game.maze.libgdx.movement.GdxWorldView;
import main.game.maze.libgdx.render.GdxGameRenderConstants;
import main.game.maze.libgdx.render.GdxGameRenderCoordinator;

/**
 * Composition root for {@link GdxGameScreenController}.
 *
 * <p>This class owns the object-graph assembly that used to live inline in the
 * controller constructor: it builds the overlay coordinator, the input command
 * context, the mouse interaction coordinator, the playing-mode bridge, the
 * start-flow request factory, and the render coordinator, wiring each of them to
 * the controller's state suppliers and behavior callbacks.</p>
 *
 * <p>Lives in the controller package on purpose so it can read the controller's
 * package-private fields and methods directly. That keeps the wiring as a single
 * cohesive responsibility (SRP) without widening the controller's API to
 * {@code public} or adding accessor boilerplate. The controller is the
 * composition target; this assembler is the composition root.</p>
 */
final class GdxGameScreenAssembler {

    private GdxGameScreenAssembler() {
    }

    /**
     * Builds the full collaborator graph for the supplied controller.
     *
     * <p>Call order matters: the playing-mode bridge depends on the mouse
     * interaction coordinator, so the coordinator is built first and reused.</p>
     */
    static GdxGameCollaborators assemble(GdxGameScreenController c) {
        GdxOverlayModeCoordinator overlayModeCoordinator = new GdxOverlayModeCoordinator(
                c.session,
                c.modeInputController,
                c.highScoreRepository,
                c.highScoresOverlayController,
                c.winOverlayController,
                c.gameOverOverlayController);

        LibgdxInputCommandContext inputCommandContext = new LibgdxInputCommandContext(
                () -> c.terminalController.isActive(),
                () -> c.requestReturnToMenu(true),
                () -> GdxGameInteractionSupport.openTerminalPrompt(c.terminalController, c::flashStatus),
                () -> {
                    c.loadHighScores();
                    c.session.setMode(GameMode.HIGH_SCORES);
                },
                () -> c.showSpanningTreeInfo = !c.showSpanningTreeInfo,
                c::applyPathHintHeld,
                c::applyMovementFromFrame);

        GdxGameMouseInteractionCoordinator mouseInteractionCoordinator = new GdxGameMouseInteractionCoordinator(
                () -> c.hudLayout,
                () -> c.hudCamera != null ? c.hudCamera.viewportHeight : 0f,
                c.hudInteractionState,
                c.terminalController,
                () -> c.viewport,
                () -> c.camera,
                () -> c.maze,
                () -> c.player,
                c.worldModel,
                c::flashStatus,
                (currentMaze, currentPlayer) -> GdxGameLayoutSupport.updateCameraFollow(c.viewport, currentMaze, currentPlayer, c.camera));

        PlayingModeController.PlayingModeBridge playingBridge = GdxGamePlayingBridgeFactory.create(
                c.terminalController,
                () -> c.currentInputFrame,
                () -> c.requestReturnToMenu(true),
                c.combatState,
                mouseInteractionCoordinator::handle,
                c::flashStatus,
                c::routeGameplayInput,
                dt -> c.enemyAnimationClock += dt,
                c::advanceEnemies,
                c::updateProjectiles,
                () -> c.player != null,
                c::updateCombat,
                c::applyDeathSequence,
                () -> GdxGameLayoutSupport.updateCameraFollow(c.viewport, c.maze, c.player, c.camera),
                c::shouldTriggerWin,
                c::triggerWin);

        GdxGameStartFlowRequestFactory startFlowRequestFactory = new GdxGameStartFlowRequestFactory(
                c.providedMaze,
                c.runtimeModelLoader,
                c.session,
                c.worldModel,
                c.debugOverlayState,
                c.hudInteractionState,
                c.terminalController,
                c.modeInputController,
                c.enemyDirectorService,
                c.winOverlayController,
                c.combatState,
                c.animatedEnemies,
                c::buildArenaForSelectedDifficulty,
                c::baseScoreForDifficulty,
                c::loadTexture,
                bootstrapResult -> new GdxWorldView(bootstrapResult.maze(), bootstrapResult.player()),
                () -> c.showHintInfo = false,
                () -> c.showSpanningTreeInfo = false,
                GdxGameScreenMetrics::toJavaFxLikeSpeed,
                c.playerSize,
                GdxGameScreenController.GOAL_SIZE,
                GdxGameScreenMetrics.JAVA_FX_TICK_RATE,
                GdxGameScreenController.MAX_ENEMY_TICKS_PER_FRAME);

        GdxGameRenderCoordinator renderCoordinator = new GdxGameRenderCoordinator(
                c.worldModel,
                c.animatedEnemies,
                c.activePathPoints,
                c.highScoreRows,
                c.session,
                c.debugOverlayState,
                c.statusMessageBus,
                c.hudInteractionState,
                c.terminalController,
                c.gameWorldView,
                c.hudView,
                c.highScoresOverlayView,
                c.winOverlayView,
                c.gameOverOverlayView,
                c.infectionOverlayView,
                c.winOverlayController,
                c::loadTexture,
                c::enemyDisplayPath,
                GdxGameRenderConstants.defaults());

        return new GdxGameCollaborators(
                overlayModeCoordinator,
                inputCommandContext,
                mouseInteractionCoordinator,
                playingBridge,
                startFlowRequestFactory,
                renderCoordinator);
    }
}
