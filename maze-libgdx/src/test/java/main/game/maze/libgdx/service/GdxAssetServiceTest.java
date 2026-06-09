package main.game.maze.libgdx.service;

import main.game.maze.libgdx.adapter.*;
import main.game.maze.libgdx.controller.*;
import main.game.maze.libgdx.helper.*;
import main.game.maze.libgdx.service.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class GdxAssetServiceTest {

    @Test
    void normalizeInternalPathRemovesLeadingSlash() {
        assertEquals("main/game/maze/player.png",
                GdxAssetService.normalizeInternalPath("/main/game/maze/player.png"));
    }

    @Test
    void normalizeInternalPathKeepsRelativePath() {
        assertEquals("main/game/maze/player.png",
                GdxAssetService.normalizeInternalPath("main/game/maze/player.png"));
    }

    @Test
    void normalizeInternalPathReturnsNullForBlankInput() {
        assertNull(GdxAssetService.normalizeInternalPath(" "));
        assertNull(GdxAssetService.normalizeInternalPath(null));
    }

    @Test
    void getTextureReturnsNullWhenGdxFilesNotAvailable() {
        GdxAssetService service = new GdxAssetService();

        assertNull(service.getTexture("/main/game/maze/player.png"));
    }

    @Test
    void queueTextureAndDisposeAreSafeWhenGdxFilesNotAvailable() {
        GdxAssetService service = new GdxAssetService();

        service.queueTexture("/main/game/maze/player.png");
        service.dispose();
    }

    @Test
    void loadingProgressDefaultsToOneWithoutQueuedAssets() {
        GdxAssetService service = new GdxAssetService();

        assertEquals(1f, service.loadingProgress());
    }

    @Test
    void updateLoadingReturnsTrueWithoutQueuedAssets() {
        GdxAssetService service = new GdxAssetService();

        assertEquals(true, service.updateLoading());
    }
}
