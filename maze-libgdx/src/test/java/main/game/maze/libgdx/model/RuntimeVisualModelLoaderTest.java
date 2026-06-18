package main.game.maze.libgdx.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.Test;

class RuntimeVisualModelLoaderTest {

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

    // Difficulty with PumpkinBomber cap=0 must produce zero PumpkinBomber spawns —
    // regression guard for F8 cap enforcement through the full loader path.
    @Test
    void loadWithZeroPumpkinBomberCap_spawnsNoPumpkinBombers() {
        Difficulty diff = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        addCap(diff, EnemyTypes.ZOMBIE, 2);
        addCap(diff, EnemyTypes.GHOST, 2);
        addCap(diff, EnemyTypes.PUMPKINBOMBER, 0);

        RuntimeVisualModel model = new RuntimeVisualModelLoader().load(800f, 600f, diff);

        boolean hasPumpkins = model.enemies().stream().anyMatch(e ->
                e.id().toLowerCase(Locale.ROOT).contains("pumpkinbomber")
                || e.imagePath().toLowerCase(Locale.ROOT).contains("pumpkin"));
        assertFalse(hasPumpkins,
                "A PumpkinBomber cap of 0 must result in zero PumpkinBomber spawns from the loader");
    }

    private static void addCap(Difficulty d, EnemyTypes type, int max) {
        EnemyMaxCount c = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
        c.setType(type);
        c.setMaxCount(max);
        d.getEnemyMaxCount().add(c);
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
}
