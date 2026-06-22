package main.game.maze.libgdx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.Test;

class DeadEnemyTest {

    private static EnemySpawn spawn(int resurrectionTimeMs) {
        return new EnemySpawn(
                "z1", "zombie.png", 100f, 200f, 32f,
                1f, 5, 0, "",
                BehaviorType.WANDER, 1f,
                0d, EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                EnemySpawn.DEFAULT_PROJECTILE_TYPE,
                EnemySpawn.DEFAULT_SPLASH_RADIUS,
                EnemySpawn.DEFAULT_ARC_HEIGHT,
                EnemySpawn.DEFAULT_ATTACK_RANGE,
                EnemySpawn.DEFAULT_ATTACK_COOLDOWN_MS,
                EnemySpawn.DEFAULT_PROJECTILE_SPEED,
                resurrectionTimeMs,
                50);
    }

    @Test
    void ready_isFalseInitially() {
        DeadEnemy dead = new DeadEnemy(spawn(3000), 3f);
        assertFalse(dead.ready(), "Should not be ready immediately after creation");
    }

    @Test
    void tick_reducesCountdown() {
        DeadEnemy dead = new DeadEnemy(spawn(3000), 3f);
        dead.tick(1f);
        assertEquals(2f, dead.resurrectionSecondsRemaining(), 0.001f);
    }

    @Test
    void tick_doesNotGoBelowZero() {
        DeadEnemy dead = new DeadEnemy(spawn(1000), 1f);
        dead.tick(5f);
        assertEquals(0f, dead.resurrectionSecondsRemaining(), 0.001f);
    }

    @Test
    void ready_trueWhenCountdownReachesZero() {
        DeadEnemy dead = new DeadEnemy(spawn(2000), 2f);
        dead.tick(2f);
        assertTrue(dead.ready(), "Should be ready once countdown hits zero");
    }

    @Test
    void ready_trueWhenTickExceedsCountdown() {
        DeadEnemy dead = new DeadEnemy(spawn(1000), 1f);
        dead.tick(99f);
        assertTrue(dead.ready(), "Should be ready when tick exceeds remaining time");
    }

    @Test
    void spawn_returnsOriginalSpawn() {
        EnemySpawn s = spawn(5000);
        DeadEnemy dead = new DeadEnemy(s, 5f);
        assertSame(s, dead.spawn());
    }

    @Test
    void constructor_negativeDurationClampedToZero() {
        DeadEnemy dead = new DeadEnemy(spawn(0), -1f);
        assertTrue(dead.ready(), "Negative duration should be clamped to zero (immediately ready)");
    }
}
