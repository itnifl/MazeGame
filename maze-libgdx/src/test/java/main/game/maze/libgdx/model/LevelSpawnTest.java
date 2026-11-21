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

    private static List<EnemySpawn> portalGhosts(RuntimeVisualModel model) {
        return model.enemies().stream().filter(e -> main.game.maze.game.level.TeleportGhostSpec.isPortalGhostId(e.id())).toList();
    }

    @Test
    @DisplayName("level 3: portal ghosts, 10 on hard, 6 on normal, 3 on easy, besides the sergeants and everything else")
    void portalGhostsPerDifficulty() {
        assertEquals(10, portalGhosts(load(3, hard())).size());
        assertEquals(6, portalGhosts(load(3, normal())).size());
        assertEquals(3, portalGhosts(load(3, easy())).size());
        assertEquals(0, portalGhosts(load(2, hard())).size());
        assertEquals(6, sergeants(load(3, hard())).size(), "the sergeants of level 2 are still there");
        assertEquals(4, sergeants(load(3, normal())).size());
    }

    @Test
    @DisplayName("a portal ghost does no damage, wanders, has the level's health and stays dead when killed")
    void portalGhostValues() {
        var spec = LEVELS.level(3).teleportGhost();
        for (EnemySpawn ghost : portalGhosts(load(3, hard()))) {
            assertEquals(0, ghost.attackDamage());
            assertEquals(spec.health(), ghost.maxHitPoints());
            assertEquals(spec.sprite(), ghost.imagePath());
            assertEquals(0, ghost.resurrectionTimeMs());
            assertEquals(main.game.maze.opponents.BehaviorType.WANDER, ghost.behavior());
        }
    }

    @Test
    @DisplayName("level 3 keeps the extra enemies of its difficulty: more than level 2 can fit with the same threat")
    void levelThreeAddsToLevelTwo() {
        long levelTwo = load(2, hard()).enemies().size();
        long levelThree = load(3, hard()).enemies().size();
        assertTrue(levelThree >= levelTwo + 10, "level 3 has the 10 portal ghosts on top: " + levelThree + " vs " + levelTwo);
    }

    private static List<EnemySpawn> towers(RuntimeVisualModel model) {
        return model.enemies().stream().filter(e -> main.game.maze.game.level.GuardTowerSpec.isTowerId(e.id())).toList();
    }

    @Test
    @DisplayName("level 4: guard towers, 4 on hard, 3 on normal, 2 on easy; none on the levels below; everything else stays")
    void guardTowersPerDifficulty() {
        assertEquals(4, towers(load(4, hard())).size());
        assertEquals(3, towers(load(4, normal())).size());
        assertEquals(2, towers(load(4, easy())).size());
        assertEquals(0, towers(load(3, hard())).size());
        assertEquals(10, portalGhosts(load(4, hard())).size(), "the portal ghosts of level 3 are still there");
        assertEquals(6, sergeants(load(4, hard())).size());
    }

    @Test
    @DisplayName("a guard tower stands in the outer part of the map, never moves, is very tough, shows damage, stays dead")
    void guardTowerValues() {
        var spec = LEVELS.level(4).guardTower();
        for (EnemySpawn tower : towers(load(4, hard()))) {
            assertEquals(spec.health(), tower.maxHitPoints());
            assertEquals(spec.sprite(), tower.imagePath());
            assertEquals(0, tower.resurrectionTimeMs());
            assertEquals(0, tower.attackDamage());
            assertTrue(tower.animationSpec().damageStatesEnabled(), "a damaged tower looks damaged");
            double edge = Math.min(Math.min(tower.x(), WIDTH - tower.x()), Math.min(tower.y(), HEIGHT - tower.y()));
            assertTrue(edge <= 0.2 * Math.min(WIDTH, HEIGHT), "in the outer band: " + edge);
        }
    }

    private static List<EnemySpawn> builders(RuntimeVisualModel model) {
        return model.enemies().stream().filter(e -> main.game.maze.game.level.WallBuilderSpec.isBuilderId(e.id())).toList();
    }

    @Test
    @DisplayName("level 5: wall builders, 10 on hard, 5 on normal, 2 on easy, besides everything level 4 has")
    void wallBuildersPerDifficulty() {
        assertEquals(10, builders(load(5, hard())).size());
        assertEquals(5, builders(load(5, normal())).size());
        assertEquals(2, builders(load(5, easy())).size());
        assertEquals(0, builders(load(4, hard())).size());
        assertEquals(4, towers(load(5, hard())).size(), "the guard towers of level 4 are still there");
        assertEquals(10, portalGhosts(load(5, hard())).size());
        assertEquals(6, sergeants(load(5, hard())).size());
    }

    @Test
    @DisplayName("a wall builder is harmless, in the area round the heart, has the level health and stays dead when killed")
    void wallBuilderValues() {
        var spec = LEVELS.level(5).wallBuilder();
        for (EnemySpawn builder : builders(load(5, hard()))) {
            assertEquals(0, builder.attackDamage(), "cannot harm the player");
            assertEquals(spec.health(), builder.maxHitPoints());
            assertEquals(spec.sprite(), builder.imagePath());
            assertEquals(0, builder.resurrectionTimeMs(), "does not resurrect");
            assertTrue(Math.hypot(builder.x() - WIDTH / 2, builder.y() - HEIGHT / 2) <= spec.spawnRadius() + 1);
        }
    }
}
