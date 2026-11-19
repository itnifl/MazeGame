package main.game.maze.characters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import main.game.maze.FxEnemyCoordinator;
import main.game.maze.FxGameWorldModel;
import main.game.maze.characters.interfaces.ICanDie;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.Ghost;
import main.game.maze.opponents.OpponentsFactory;
import main.game.maze.opponents.Zombie;
import main.game.maze.service.MortalityRules;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * JavaFX enemies follow the same life rules as libGDX enemies: one {@code ICanDie} contract
 * backed by the shared {@code EnemyVitals} / {@code MortalityRules}.
 */
@DisplayName("JavaFX mortality parity with libGDX")
class FxMortalityParityTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    private static void onFx(Runnable action) throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
        if (failure.get() instanceof AssertionError ae) {
            throw ae;
        }
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }
    }

    private static Ghost ghostModel() {
        Ghost ghost = OpponentsFactory.eINSTANCE.createGhost();
        ghost.setHealth(120);
        ghost.setSpeed(2.0);
        ghost.setAttackDamage(1);
        ghost.setThreatLevel(1.0);
        ghost.setBehavior(BehaviorType.WANDER);
        return ghost;
    }

    private static Zombie zombieModel() {
        Zombie zombie = OpponentsFactory.eINSTANCE.createZombie();
        zombie.setHealth(60);
        zombie.setSpeed(2.0);
        zombie.setAttackDamage(1);
        zombie.setThreatLevel(1.0);
        zombie.setBehavior(BehaviorType.WANDER);
        return zombie;
    }

    private static FxEnemyCoordinator coordinatorWith(Object... characters) throws Exception {
        FxEnemyCoordinator coordinator = new FxEnemyCoordinator(
                () -> null, () -> null, new FxGameWorldModel(), () -> null, () -> null, () -> {});
        Field field = FxEnemyCoordinator.class.getDeclaredField("allComputerCharacters");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) field.get(coordinator);
        list.addAll(List.of(characters));
        return coordinator;
    }

    @Test
    @DisplayName("ghosts are mortal (ICanDie) with model health, like libGDX ghosts")
    void ghostsAreMortal() {
        GhostCharacter ghost = new GhostCharacter(new Rectangle(16, 16), 100, 100, ghostModel());
        ICanDie mortal = ghost;

        assertEquals(MortalityRules.maxHitPoints(ghostModel()), mortal.getHitPoints());
        mortal.subtractHitPoints(1_000, DeathCause.FIRE);

        assertFalse(mortal.isAlive());
        assertEquals(DeathCause.FIRE, mortal.deathCause());
    }

    @Test
    @DisplayName("bomb fire, crushing walls and /kill reach ghosts too")
    void coordinatorPathsReachGhosts() throws Exception {
        GhostCharacter burned = new GhostCharacter(new Rectangle(16, 16), 120, 40, ghostModel());
        assertTrue(coordinatorWith(burned).applyPlayerFlameAttack(1_000) > 0);
        assertEquals(DeathCause.FIRE, burned.deathCause());

        GhostCharacter crushed = new GhostCharacter(new Rectangle(16, 16), 120, 40, ghostModel());
        double wallY = 40 + main.game.maze.mazeworld.constants.StageConstants.GhostCharacterXYSize * 0.5;
        assertEquals(1, coordinatorWith(crushed).crushAlong(0, wallY, 300, wallY));
        assertEquals(DeathCause.CRUSHED, crushed.deathCause());

        GhostCharacter shot = new GhostCharacter(new Rectangle(16, 16), 120, 40, ghostModel());
        assertEquals(1, coordinatorWith(shot).killAll(DeathCause.SHOT));
        assertEquals(DeathCause.SHOT, shot.deathCause());
    }

    @Test
    @DisplayName("a resurrected zombie is whole and briefly protected, like a libGDX respawn")
    void resurrectionMatchesLibgdx() throws Exception {
        onFx(() -> {
            ZombieCharacter zombie = new ZombieCharacter(new Rectangle(16, 16), 50, 60, zombieModel());
            zombie.subtractHitPoints(1_000, DeathCause.CRUSHED);
            assertFalse(zombie.isAlive());

            zombie.resurrect();

            assertEquals(60, zombie.getHitPoints());
            assertEquals(DeathCause.OTHER, zombie.deathCause());
            assertTrue(zombie.isInvulnerable());
            zombie.subtractHitPoints(1_000, DeathCause.FIRE);
            assertTrue(zombie.isAlive(), "no damage during respawn protection");
        });
    }

    @Test
    @DisplayName("zombies resurrect even when the model leaves resurrectionTime at 0 (shared rule)")
    void zombieResurrectionRule() {
        Zombie model = zombieModel();
        model.setResurrectionTime(0);
        assertTrue(MortalityRules.resurrectionTimeMs(model) > 0);
        assertEquals(0, MortalityRules.resurrectionTimeMs(ghostModel()), "ghosts stay dead, as in libGDX");
    }
}
