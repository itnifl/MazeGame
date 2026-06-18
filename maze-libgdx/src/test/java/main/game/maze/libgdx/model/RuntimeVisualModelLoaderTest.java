package main.game.maze.libgdx.model;

import main.game.maze.common.graphics.config.MazeVisualStyleConfig;
import main.game.maze.generated.WallRegistry;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

class RuntimeVisualModelLoaderTest {

    // ------------------------------------------------------------------
    // Existing integration tests
    // ------------------------------------------------------------------

    @Test
    void loadsPlayerGoalWallAndEnemyDataFromSharedModels() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        RuntimeVisualModel model = loader.load(800f, 600f);

        assertNotNull(model);
        assertTrue(model.playerSpeed() > 0f);
        assertTrue(model.playerSize() > 0f);
        assertTrue(model.goalSize() > 0f);
        assertTrue(model.goalX() > 0f);
        assertTrue(model.goalY() > 0f);
        assertNotNull(model.playerImagePath());
        assertNotNull(model.wallImagePath());
        assertNotNull(model.goalImagePath());
        assertNotNull(model.enemies());
        assertFalse(model.enemies().isEmpty(), "expected enemies derived from opponent model");
    }

    @Test
    void enemyPlacementsStayInsideBoardBounds() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();
        float width = 900f;
        float height = 700f;

        RuntimeVisualModel model = loader.load(width, height);
        for (EnemySpawn enemy : model.enemies()) {
            assertTrue(enemy.x() >= 0f && enemy.x() <= width);
            assertTrue(enemy.y() >= 0f && enemy.y() <= height);
            assertTrue(enemy.size() > 0f);
            assertNotNull(enemy.behavior());
            assertTrue(enemy.behavior() == BehaviorType.AGGRESSIVE
                            || enemy.behavior() == BehaviorType.PASSIVE
                            || enemy.behavior() == BehaviorType.WANDER
                            || enemy.behavior() == BehaviorType.PATROL,
                    "spawn behavior must be one of the generated rules values");
        }
    }

    // ------------------------------------------------------------------
    // resolveWallDefinition guard tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("resolveWallDefinition returns a non-null definition for a known wall ID")
    void resolveWallDefinitionReturnsDefinitionForKnownWallId() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        WallRegistry.WallDefinition def = loader.resolveWallDefinition(null, MazeVisualStyleConfig.DEFAULT);

        assertNotNull(def, "resolveWallDefinition must return a definition when WallRegistry has a matching entry");
        assertNotNull(def.baseImage, "returned WallDefinition must have a non-null baseImage");
    }

    @Test
    @DisplayName("resolveWallDefinition returns null when WallRegistry.get always returns null")
    void resolveWallDefinitionReturnsNullWhenRegistryHasNoMatch() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        try (MockedStatic<WallRegistry> registry = Mockito.mockStatic(WallRegistry.class)) {
            registry.when(() -> WallRegistry.get(anyString())).thenReturn(null);

            WallRegistry.WallDefinition def =
                    loader.resolveWallDefinition(null, MazeVisualStyleConfig.DEFAULT);

            assertNull(def, "resolveWallDefinition must return null when both WallRegistry.get() calls return null");
        }
    }

    @Test
    @DisplayName("resolveWallDefinition returns null when WallRegistry.get throws ExceptionInInitializerError")
    void resolveWallDefinitionReturnsNullOnRegistryInitializerError() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        try (MockedStatic<WallRegistry> registry = Mockito.mockStatic(WallRegistry.class)) {
            registry.when(() -> WallRegistry.get(anyString()))
                    .thenAnswer(inv -> { throw new ExceptionInInitializerError(new RuntimeException("simulated")); });

            WallRegistry.WallDefinition def =
                    loader.resolveWallDefinition(null, MazeVisualStyleConfig.DEFAULT);

            assertNull(def, "resolveWallDefinition must return null when WallRegistry throws ExceptionInInitializerError");
        }
    }

    @Test
    @DisplayName("resolveWallDefinition returns null when WallRegistry.get throws NoClassDefFoundError")
    void resolveWallDefinitionReturnsNullOnNoClassDefFoundError() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        try (MockedStatic<WallRegistry> registry = Mockito.mockStatic(WallRegistry.class)) {
            registry.when(() -> WallRegistry.get(anyString()))
                    .thenAnswer(inv -> { throw new NoClassDefFoundError("simulated missing class"); });

            WallRegistry.WallDefinition def =
                    loader.resolveWallDefinition(null, MazeVisualStyleConfig.DEFAULT);

            assertNull(def, "resolveWallDefinition must return null when WallRegistry throws NoClassDefFoundError");
        }
    }

    @Test
    @DisplayName("load() uses DEFAULT_WALL_IMAGE when WallRegistry is unavailable")
    void loadUsesDefaultWallImageWhenRegistryUnavailable() {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();

        try (MockedStatic<WallRegistry> registry = Mockito.mockStatic(WallRegistry.class)) {
            registry.when(() -> WallRegistry.get(anyString())).thenReturn(null);

            RuntimeVisualModel model = loader.load(800f, 600f);

            assertEquals("/main/game/maze/woodWall.png", model.wallImagePath(),
                    "when resolveWallDefinition returns null, load() must fall back to the default wall image");
        }
    }
}
