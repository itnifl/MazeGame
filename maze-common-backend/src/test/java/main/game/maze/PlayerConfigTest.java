package main.game.maze;

import main.game.maze.config.model.PlayerConfig;
import main.game.maze.constants.PlayerConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerConfigTest {

    @Test
    void defaults_healthIsPositive() {
        PlayerConfig cfg = PlayerConfig.defaults();
        assertTrue(cfg.health() > 0, "Default health must be positive");
    }

    @Test
    void defaults_speedIsPositive() {
        PlayerConfig cfg = PlayerConfig.defaults();
        assertTrue(cfg.speed() > 0, "Default speed must be positive");
    }

    @Test
    void defaults_enabledByDefault() {
        assertTrue(PlayerConfig.defaults().enabled(), "Default player must be enabled");
    }

    @Test
    void defaults_matchPlayerConstants() {
        PlayerConfig cfg = PlayerConfig.defaults();
        assertEquals(PlayerConstants.DefaultHealth, cfg.health());
        assertEquals(PlayerConstants.DefaultSpeed, cfg.speed(), 1e-9);
        assertEquals(3, cfg.bombCount());
        assertEquals(100, cfg.bombDamage());
    }

    @Test
    void defaults_idIsNonBlank() {
        assertFalse(PlayerConfig.defaults().id().isBlank(), "Default id must be non-blank");
    }

    @Test
    void defaults_displayNameIsNonBlank() {
        assertFalse(PlayerConfig.defaults().displayName().isBlank(), "Default displayName must be non-blank");
    }

    @Test
    void defaults_imagePathsAreNonNull() {
        PlayerConfig cfg = PlayerConfig.defaults();
        assertNotNull(cfg.imageBase());
        assertNotNull(cfg.imageTurnLeft());
        assertNotNull(cfg.imageTurnRight());
        assertNotNull(cfg.imageTurnUp());
        assertNotNull(cfg.imageTurnDown());
        assertNotNull(cfg.imageDeath());
    }

    @Test
    void toString_doesNotThrow() {
        assertDoesNotThrow(() -> PlayerConfig.defaults().toString());
    }

    @Test
    void equality_sameValues_areEqual() {
        PlayerConfig a = PlayerConfig.defaults();
        PlayerConfig b = PlayerConfig.defaults();
        assertEquals(a, b, "Two defaults() instances with same values must be equal");
        assertEquals(a.hashCode(), b.hashCode());
    }
}
