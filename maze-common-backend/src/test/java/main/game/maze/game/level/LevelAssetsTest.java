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
            }
            if (level.hasTowers()) {
                GuardTowerSpec tower = level.guardTower();
                exists(tower.sprite(), "level " + number + " guard tower sprite");
                for (String state : new String[] {"damaged", "bleeding", "tired"}) {
                    exists(tower.sprite().replace(".png", "-" + state + ".png"), "level " + number + " guard tower " + state + " sprite");
                }
                exists(tower.rpgLaunchSound(), "level " + number + " rpg launch sound");
                exists(tower.rpgExplosionSound(), "level " + number + " rpg explosion sound");
                exists(tower.gatlingSound(), "level " + number + " tower gatling sound");
                exists(tower.hitSound(), "level " + number + " tower hit sound");
                exists(tower.destroyedSound(), "level " + number + " tower destroyed sound");
            }
            if (level.hasLava()) {
                exists(level.lava().deathSound(), "level " + number + " lava death sound");
            }
            if (level.hasBuilders()) {
                exists(level.wallBuilder().sprite(), "level " + number + " wall builder sprite");
                exists(level.wallBuilder().buildSound(), "level " + number + " wall build sound");
            }
            exists(level.teleport().zapSound(), "level " + number + " teleport zap");
            exists(level.teleport().voiceSound(), "level " + number + " teleport voice");
        }
    }

    @Test
    @DisplayName("the bomb explosion sound")
    void bombSound() {
        exists(BombImpact.SOUND, "bomb explosion sound");
    }
}
