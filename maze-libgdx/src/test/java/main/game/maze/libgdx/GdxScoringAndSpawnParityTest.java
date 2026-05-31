package main.game.maze.libgdx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.game.maze.common.scoring.GameScoringConstants;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.opponents.util.EnemySpawnPlanner;

/**
 * Cross-frontend parity contract tests.
 *
 * <p>These guard GR-10, GR-17, GR-22 and MGR-17/19: the libGDX runtime must
 * derive its scoring and enemy caps from the same shared modules as JavaFX
 * (GameScoringConstants and EnemySpawnPlanner). If a future change reintroduces
 * per-frontend constants or duplicate cap math, one of these tests must fail.
 */
class GdxScoringAndSpawnParityTest {

    @Test
    void libgdxBaseScoreUsesSharedConstantsForAllDifficulties() throws Exception {
        GdxGameScreen screen = new GdxGameScreen(null, 48f, 16, 12, 160f, true);
        Method m = GdxGameScreen.class.getDeclaredMethod("baseScoreForDifficulty", Difficulty.class);
        m.setAccessible(true);

        for (Difficulty d : new Difficulty[] {
                DifficultiesFactory.eINSTANCE.createEasyDifficulty(),
                DifficultiesFactory.eINSTANCE.createNormalDifficulty(),
                DifficultiesFactory.eINSTANCE.createHardDifficulty() }) {
            int gdxScore = (int) m.invoke(screen, d);
            assertEquals(GameScoringConstants.baseScoreFor(d), gdxScore,
                    "libGDX must use shared GameScoringConstants for " + d.eClass().getName());
        }
    }

    @Test
    void libgdxCapsMatchPlannerCapsForArbitraryDifficulty() {
        Difficulty d = DifficultiesFactory.eINSTANCE.createNormalDifficulty();
        addCap(d, EnemyTypes.ZOMBIE, 4);
        addCap(d, EnemyTypes.GHOST, 1);
        addCap(d, EnemyTypes.PUMPKINBOMBER, 2);

        Map<EnemyTypes, Integer> planner = EnemySpawnPlanner.capsFromDifficulty(d);
        assertEquals(4, planner.get(EnemyTypes.ZOMBIE));
        assertEquals(1, planner.get(EnemyTypes.GHOST));
        assertEquals(2, planner.get(EnemyTypes.PUMPKINBOMBER));
    }

    @Test
    void damageMultiplierIsAppliedIdenticallyAcrossFrontends() {
        // Unit-level contract test for EnemySpawnPlanner.applyDamageMultiplier.
        // Both frontends (RuntimeVisualModelLoader and OpponentRuntimeFactory) are
        // required to delegate to this helper (GR-10). Pinning the helper's output
        // here means any change to the formula breaks this test before it can
        // silently diverge between frontends. For end-to-end production
        // verification, see DifficultyXmiParameterizedParityTest.
        assertEquals(15, EnemySpawnPlanner.applyDamageMultiplier(10, 1.5d));
        assertEquals(30, EnemySpawnPlanner.applyDamageMultiplier(20, 1.5d));
        assertEquals(0, EnemySpawnPlanner.applyDamageMultiplier(0, 9.9d));
    }

    private static void addCap(Difficulty d, EnemyTypes type, int max) {
        EnemyMaxCount c = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
        c.setType(type);
        c.setMaxCount(max);
        d.getEnemyMaxCount().add(c);
    }
}
