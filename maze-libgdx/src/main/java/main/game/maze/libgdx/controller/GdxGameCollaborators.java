package main.game.maze.libgdx.controller;

import main.game.maze.libgdx.controller.state.GdxOverlayModeCoordinator;
import main.game.maze.libgdx.controller.state.PlayingModeController;
import main.game.maze.libgdx.helper.GdxGameMouseInteractionCoordinator;
import main.game.maze.libgdx.helper.GdxGameStartFlowRequestFactory;
import main.game.maze.libgdx.input.command.LibgdxInputCommandContext;
import main.game.maze.libgdx.render.GdxGameRenderCoordinator;

/**
 * Immutable holder for the collaborator object graph that
 * {@link GdxGameScreenAssembler} builds for {@link GdxGameScreenController}.
 *
 * <p>Grouping the six wired collaborators into a single value object keeps the
 * controller constructor down to plain field assignments and isolates the
 * "what gets wired" concern (this record) from the "how it gets wired" concern
 * (the assembler). The controller stays the composition target; the assembler
 * is the composition root.</p>
 */
record GdxGameCollaborators(
        GdxOverlayModeCoordinator overlayModeCoordinator,
        LibgdxInputCommandContext inputCommandContext,
        GdxGameMouseInteractionCoordinator mouseInteractionCoordinator,
        PlayingModeController.PlayingModeBridge playingBridge,
        GdxGameStartFlowRequestFactory startFlowRequestFactory,
        GdxGameRenderCoordinator renderCoordinator) {
}
