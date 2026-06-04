package main.game.maze.libgdx;

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
}
