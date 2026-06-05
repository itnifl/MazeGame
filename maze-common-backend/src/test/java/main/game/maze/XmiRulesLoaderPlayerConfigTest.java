package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import main.game.maze.config.model.PlayerConfig;
import main.game.maze.config.service.XmiRulesLoader;
import main.game.maze.constants.PlayerConstants;

class XmiRulesLoaderPlayerConfigTest {

    @Test
    void loadPlayerConfigFromClasspath_readsConfiguredImagesAndStats() {
        XmiRulesLoader loader = new XmiRulesLoader();

        PlayerConfig config = loader.loadPlayerConfigFromClasspath(
                PlayerConstants.PlayerModelPath,
                PlayerConstants.PlayerModelEcorePath);

        assertNotNull(config);
        assertEquals("player_default", config.id());
        assertEquals(100, config.health());
        assertEquals(10.0, config.speed());
        assertEquals("/main/game/maze/you2.png", config.imageBase());
        assertEquals("/main/game/maze/you2-dead.png", config.imageDeath());
    }
}


