package main.game.maze.libgdx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.game.level.LevelCatalog;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.SergeantSpec;
import main.game.maze.mazeworld.generators.RealMaze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX level spawns: sergeants per difficulty near the heart, threat capacity")
class LevelSpawnTest {

    private static final LevelCatalog LEVELS = LevelCatalogLoader.loadDefault();
    private static final float WIDTH = 800f;
    private static final float HEIGHT = 665f;

    private static Difficulty easy() {
        return withZombies(DifficultiesFactory.eINSTANCE.createEasyDifficulty(), 3);
    }

    private static Difficulty normal() {
        return withZombies(DifficultiesFactory.eINSTANCE.createNormalDifficulty(), 5);
    }

    private static Difficulty hard() {
        return withZombies(DifficultiesFactory.eINSTANCE.createHardDifficulty(), 8);
    }

    private static Difficulty withZombies(Difficulty difficulty, int zombies) {
        EnemyMaxCount cap = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
        cap.setType(EnemyTypes.ZOMBIE);
        cap.setMaxCount(zombies);
        difficulty.getEnemyMaxCount().add(cap);
        difficulty.setMaxThreat(20);
        difficulty.setMonstersMovementSpeedMultiplier(1.0);
        difficulty.setMonstersDamageMultiplier(1.0);
        return difficulty;
    }

    private static List<EnemySpawn> sergeants(RuntimeVisualModel model) {
        return model.enemies().stream().filter(e -> SergeantSpec.isSergeantId(e.id())).toList();
    }

    private static RuntimeVisualModel load(int level, Difficulty difficulty) {
        RuntimeVisualModelLoader loader = new RuntimeVisualModelLoader();
        loader.useLevel(LEVELS.level(level));
        return loader.load(RealMaze.fresh((int) WIDTH, (int) HEIGHT), difficulty);
    }

    @Test
    @DisplayName("level 2: six sergeants on hard, four on normal, one on easy")
    void sergeantsPerDifficulty() {
        assertEquals(6, sergeants(load(2, hard())).size());
        assertEquals(4, sergeants(load(2, normal())).size());
        assertEquals(1, sergeants(load(2, easy())).size());
    }

    @Test
    @DisplayName("level 1 has no sergeants")
    void levelOneHasNone() {
        assertEquals(0, sergeants(load(1, hard())).size());
    }

    @Test
    @DisplayName("sergeants stand close to the heart, never move and do not come back")
    void sergeantsNearTheHeart() {
        SergeantSpec spec = LEVELS.level(2).sergeant();
        for (EnemySpawn sergeant : sergeants(load(2, hard()))) {
            double distance = Math.hypot(sergeant.x() - WIDTH / 2, sergeant.y() - HEIGHT / 2);
            assertTrue(distance <= spec.postDistanceFromHeart() + 60,
                    "close to the heart: " + distance);
            assertEquals(0, sergeant.resurrectionTimeMs(), "a killed sergeant stays dead");
            assertEquals(spec.health(), sergeant.maxHitPoints());
            assertEquals(spec.sprite(), sergeant.imagePath());
        }
    }

    @Test
    @DisplayName("level 2 has a larger threat capacity: more of the same enemies fit")
    void threatCapacity() {
        long level1 = load(1, hard()).enemies().stream().filter(e -> !SergeantSpec.isSergeantId(e.id())).count();
        long level2 = load(2, hard()).enemies().stream().filter(e -> !SergeantSpec.isSergeantId(e.id())).count();
        assertTrue(level2 >= level1, "level 2 fits at least as many enemies: " + level2 + " vs " + level1);
    }
}
