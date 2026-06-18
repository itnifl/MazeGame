package main.game.maze.runtime.opponents;

import javafx.application.Platform;
import javafx.scene.Node;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.difficulties.DifficultiesPackage;
import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.EnemyMaxCount;
import main.game.maze.difficulties.EnemyTypes;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.OpponentsPackage;
import main.game.maze.opponents.Zombie;
import main.game.maze.runtime.OclBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for the spawnByTarget single-attempt bug.
 *
 * Bug: spawnByTarget tried only one random candidate per spawn slot and
 * immediately broke if that candidate's effectiveThreat did not fit within the
 * remaining budget, causing 0 enemies of that type to be spawned even when
 * other valid candidates existed in the pool.
 *
 * Fix: candidates are shuffled and iterated in full before giving up, so every
 * available template is considered for each spawn slot.
 */
@DisplayName("OpponentRuntimeFactory — spawnByTarget regression tests")
class OpponentRuntimeFactorySpawnTest {

    @BeforeAll
    static void initFx() throws InterruptedException {
        // Bootstrap EMF packages so XMI model loading works in tests
        OclBootstrap.init();
        OpponentsPackage.eINSTANCE.eClass();
        DifficultiesPackage.eINSTANCE.eClass();

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX toolkit must start within 5 s");
    }

    // -----------------------------------------------------------------------
    // Stub EnemyRegistrar — counts synchronous resolveEnemySpawnPosition calls
    // -----------------------------------------------------------------------

    private static final class CountingRegistrar implements EnemyRegistrar {
        final AtomicInteger spawnCount = new AtomicInteger(0);

        @Override
        public void registerComputerCharacter(IMovingComputerCharacter character, Node node) {}

        @Override
        public Point2D resolveEnemySpawnPosition(double x, double y, double size) {
            spawnCount.incrementAndGet();
            return new Point2D(x, y);
        }
    }

    // -----------------------------------------------------------------------
    // Reflection helper — invokes private static spawnByTarget
    // -----------------------------------------------------------------------

    private static double invokeSpawnByTarget(
            List<CharacterType> all,
            Map<EnemyTypes, Integer> target,
            Map<EnemyTypes, Integer> caps,
            int maxThreat,
            EnemyRegistrar registrar,
            AtomicInteger ghosts,
            AtomicInteger zombies,
            AtomicInteger pumpkins) throws Exception {

        Method m = OpponentRuntimeFactory.class.getDeclaredMethod(
                "spawnByTarget",
                List.class, Map.class, Map.class,
                int.class, double.class, double.class, boolean.class,
                EnemyRegistrar.class,
                AtomicInteger.class, AtomicInteger.class, AtomicInteger.class);
        m.setAccessible(true);
        return (double) m.invoke(null,
                all, target, caps, maxThreat,
                1.0, 1.0, false,
                registrar, ghosts, zombies, pumpkins);
    }

    // -----------------------------------------------------------------------
    // Helper: create an enabled Ghost with a given threat level and health
    // -----------------------------------------------------------------------

    private static Ghost enabledGhost(double threatLevel, int health) {
        Ghost g = OpponentsFactory.eINSTANCE.createGhost();
        g.setEnabled(true);
        g.setThreatLevel(threatLevel);
        g.setHealth(health);
        g.setSpeed(1.0);
        g.setImageBase("/main/game/maze/ghost1.png");
        return g;
    }

    private static Zombie enabledZombie(double threatLevel, int health) {
        Zombie z = OpponentsFactory.eINSTANCE.createZombie();
        z.setEnabled(true);
        z.setThreatLevel(threatLevel);
        z.setHealth(health);
        z.setSpeed(1.0);
        z.setImageBase("/main/game/maze/zombie1.png");
        return z;
    }

    private static Difficulty easyDifficulty(int maxThreat, int ghostCap, int zombieCap) {
        Difficulty d = DifficultiesFactory.eINSTANCE.createEasyDifficulty();
        addCap(d, EnemyTypes.GHOST, ghostCap);
        addCap(d, EnemyTypes.ZOMBIE, zombieCap);
        addCap(d, EnemyTypes.PUMPKINBOMBER, 0);
        return d;
    }

    private static void addCap(Difficulty d, EnemyTypes type, int max) {
        EnemyMaxCount c = DifficultiesFactory.eINSTANCE.createEnemyMaxCount();
        c.setType(type);
        c.setMaxCount(max);
        d.getEnemyMaxCount().add(c);
    }

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    @Test
    @DisplayName("all fitting candidates — every requested slot is filled")
    void allFittingCandidatesFillsAllSlots() throws Exception {
        // Two ghosts each with effectiveThreat = round(1 * 100/100) = 1
        List<CharacterType> pool = List.of(enabledGhost(1.0, 100), enabledGhost(1.0, 100));
        Map<EnemyTypes, Integer> target = Map.of(EnemyTypes.GHOST, 3);
        Map<EnemyTypes, Integer> caps   = Map.of(EnemyTypes.GHOST, 5);

        CountingRegistrar registrar = new CountingRegistrar();
        AtomicInteger g = new AtomicInteger(), z = new AtomicInteger(), p = new AtomicInteger();
        invokeSpawnByTarget(pool, target, caps, 10, registrar, g, z, p);

        assertEquals(3, registrar.spawnCount.get(),
                "All 3 requested ghosts must be spawned when threat budget allows");
    }

    @Test
    @DisplayName("mix of fitting and non-fitting candidates — fitting candidates are always used")
    void mixedCandidatesAlwaysPicksFittingOne() throws Exception {
        // 9 ghosts with effectiveThreat = round(10 * 100/100) = 10 — too large for remaining budget of 3
        // 1 ghost with effectiveThreat = round(1 * 100/100) = 1 — fits
        // Bug: old code picks one at random; 9/10 chance of picking a non-fitter and breaking.
        // Fix: iterates all; always finds the fitting ghost.
        List<CharacterType> pool = new ArrayList<>();
        for (int i = 0; i < 9; i++) pool.add(enabledGhost(10.0, 100));
        pool.add(enabledGhost(1.0, 100));

        Map<EnemyTypes, Integer> target = Map.of(EnemyTypes.GHOST, 3);
        Map<EnemyTypes, Integer> caps   = Map.of(EnemyTypes.GHOST, 5);

        // maxThreat=3: each ghost costs 1 → up to 3 can be spawned
        CountingRegistrar registrar = new CountingRegistrar();
        AtomicInteger g = new AtomicInteger(), z = new AtomicInteger(), p = new AtomicInteger();
        invokeSpawnByTarget(pool, target, caps, 3, registrar, g, z, p);

        assertEquals(3, registrar.spawnCount.get(),
                "All 3 requested ghosts must be spawned — pool-retry must find the fitting candidate each time");
    }

    @Test
    @DisplayName("no fitting candidate — spawn count is zero")
    void noFittingCandidateSpawnsZero() throws Exception {
        // All ghosts have effectiveThreat = 10, but maxThreat = 2 → none fit
        List<CharacterType> pool = List.of(enabledGhost(10.0, 100));
        Map<EnemyTypes, Integer> target = Map.of(EnemyTypes.GHOST, 3);
        Map<EnemyTypes, Integer> caps   = Map.of(EnemyTypes.GHOST, 5);

        CountingRegistrar registrar = new CountingRegistrar();
        AtomicInteger g = new AtomicInteger(), z = new AtomicInteger(), p = new AtomicInteger();
        invokeSpawnByTarget(pool, target, caps, 2, registrar, g, z, p);

        assertEquals(0, registrar.spawnCount.get(),
                "No enemies must be spawned when all candidates exceed the threat budget");
    }

    @Test
    @DisplayName("cap enforcement — spawn count does not exceed per-type cap")
    void capEnforcementLimitsSpawnCount() throws Exception {
        List<CharacterType> pool = List.of(enabledGhost(1.0, 100), enabledGhost(1.0, 100));
        // target requests 5 but cap allows only 2
        Map<EnemyTypes, Integer> target = Map.of(EnemyTypes.GHOST, 5);
        Map<EnemyTypes, Integer> caps   = Map.of(EnemyTypes.GHOST, 2);

        CountingRegistrar registrar = new CountingRegistrar();
        AtomicInteger g = new AtomicInteger(), z = new AtomicInteger(), p = new AtomicInteger();
        invokeSpawnByTarget(pool, target, caps, 20, registrar, g, z, p);

        assertEquals(2, registrar.spawnCount.get(),
                "Spawn count must be clamped by the per-type cap");
    }

    @Test
    @DisplayName("multi-type spawning — each type is spawned independently")
    void multiTypeSpawningFillsBothTypes() throws Exception {
        List<CharacterType> pool = new ArrayList<>();
        pool.add(enabledGhost(1.0, 100));
        pool.add(enabledZombie(1.0, 100));

        Map<EnemyTypes, Integer> target = new EnumMap<>(EnemyTypes.class);
        target.put(EnemyTypes.GHOST,  2);
        target.put(EnemyTypes.ZOMBIE, 2);
        Map<EnemyTypes, Integer> caps = new EnumMap<>(EnemyTypes.class);
        caps.put(EnemyTypes.GHOST,  5);
        caps.put(EnemyTypes.ZOMBIE, 5);

        CountingRegistrar registrar = new CountingRegistrar();
        AtomicInteger g = new AtomicInteger(), z = new AtomicInteger(), p = new AtomicInteger();
        invokeSpawnByTarget(pool, target, caps, 10, registrar, g, z, p);

        assertEquals(4, registrar.spawnCount.get(),
                "Both ghost and zombie slots must be filled independently");
    }

    @Test
    @DisplayName("instantiateFromModel with default difficulty spawns at least one enemy")
    void instantiateFromModelSpawnsEnemies() throws InterruptedException {
        CountingRegistrar registrar = new CountingRegistrar();

        // Pass null to let the factory use the DifficultyService / model's own difficulty
        // (avoids cross-resource dangling-reference validation errors from EMF).
        OpponentRuntimeFactory.instantiateFromModel(registrar);

        // Drain Platform.runLater queue twice to let character registration complete
        flushFx();
        flushFx();

        assertTrue(registrar.spawnCount.get() > 0,
                "At least one enemy must be scheduled for spawning with easy difficulty");
    }

    private static void flushFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        assertTrue(latch.await(5, TimeUnit.SECONDS), "FX flush timed out");
    }
}
