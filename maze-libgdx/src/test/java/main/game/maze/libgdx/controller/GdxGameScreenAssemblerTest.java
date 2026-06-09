package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import main.game.maze.common.graphics.config.MazeRuntimeConfig;
import main.game.maze.libgdx.service.GdxAssetService;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link GdxGameScreenAssembler}, the composition root that wires
 * the {@link GdxGameScreenController} collaborator graph.
 *
 * <p>The assembler is exercised through a real controller (it implicitly runs in
 * the controller constructor); these tests assert that an explicit
 * {@code assemble(...)} call produces a fully populated
 * {@link GdxGameCollaborators} graph with no missing collaborators.</p>
 */
class GdxGameScreenAssemblerTest {

    private static GdxGameScreenController newController() {
        return new GdxGameScreenController(GdxGameScreenOptions.builder()
                .runtimeConfig(MazeRuntimeConfig.DEFAULT)
                .assetService(new GdxAssetService())
                .ownsAssetService(false)
                .autoStartOnCreate(false)
                .build());
    }

    @Test
    void assembleWiresEveryCollaborator() {
        GdxGameCollaborators collaborators = GdxGameScreenAssembler.assemble(newController());

        assertNotNull(collaborators.overlayModeCoordinator(), "overlay mode coordinator must be wired");
        assertNotNull(collaborators.inputCommandContext(), "input command context must be wired");
        assertNotNull(collaborators.mouseInteractionCoordinator(), "mouse interaction coordinator must be wired");
        assertNotNull(collaborators.playingBridge(), "playing-mode bridge must be wired");
        assertNotNull(collaborators.startFlowRequestFactory(), "start-flow request factory must be wired");
        assertNotNull(collaborators.renderCoordinator(), "render coordinator must be wired");
    }

    @Test
    void assembleProducesIndependentGraphsPerCall() {
        GdxGameScreenController controller = newController();

        GdxGameCollaborators first = GdxGameScreenAssembler.assemble(controller);
        GdxGameCollaborators second = GdxGameScreenAssembler.assemble(controller);

        assertNotNull(first.renderCoordinator());
        assertNotNull(second.renderCoordinator());
        org.junit.jupiter.api.Assertions.assertNotSame(
                first.renderCoordinator(),
                second.renderCoordinator(),
                "each assemble call must build a fresh collaborator graph");
    }
}
