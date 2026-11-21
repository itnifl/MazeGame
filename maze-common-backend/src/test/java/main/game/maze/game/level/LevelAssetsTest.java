package main.game.maze.game.level;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import main.game.maze.common.graphics.fire.BombImpact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Every sprite and sound a level names exists, so a new level cannot point at a missing file")
class LevelAssetsTest {

    private static void exists(String path, String what) {
        if (path != null) {
            assertNotNull(LevelAssetsTest.class.getResource(path), what + " is missing: " + path);
        }
    }

    @Test
    @DisplayName("the sprites and sounds of the sergeant and the portal ghost of every shipped level")
    void shippedLevelsAssets() {
        LevelCatalog catalog = LevelCatalogLoader.loadDefault();
        for (int number = 1; number <= catalog.count(); number++) {
            LevelSpec level = catalog.level(number);
            if (level.hasSergeants()) {
                exists(level.sergeant().sprite(), "level " + number + " sergeant sprite");
                exists(level.sergeant().fireSound(), "level " + number + " sergeant fire sound");
            }
            if (level.hasTeleportGhosts()) {
                exists(level.teleportGhost().sprite(), "level " + number + " portal ghost sprite");
                exists(level.teleportGhost().teleportSound(), "level " + number + " teleport sound");
            }
        }
    }

    @Test
    @DisplayName("the bomb explosion sound")
    void bombSound() {
        exists(BombImpact.SOUND, "bomb explosion sound");
    }
}
