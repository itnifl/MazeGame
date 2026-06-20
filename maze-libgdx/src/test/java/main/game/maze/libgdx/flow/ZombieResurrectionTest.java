package main.game.maze.libgdx.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.helper.GdxGameCombatAndEnemyFlowSupport;
import main.game.maze.libgdx.model.DeadEnemy;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.Test;

class ZombieResurrectionTest {

    // -----------------------------------------------------------------------
    // killEnemies
    // -----------------------------------------------------------------------

    @Test
    void killEnemies_removesAllLivingEnemiesFromActiveList() {
        List<GdxEnemyRuntime> active = new ArrayList<>();
        active.add(enemy(0));   // no resurrection
        active.add(enemy(0));

        List<DeadEnemy> dead = new ArrayList<>();
        GdxGameCombatAndEnemyFlowSupport.killEnemies(active, dead);

        assertTrue(active.isEmpty(), "All enemies should be removed from the active list after kill");
    }

    @Test
    void killEnemies_returnsKillCount() {
        List<GdxEnemyRuntime> active = new ArrayList<>();
        active.add(enemy(0));
        active.add(enemy(0));
        active.add(enemy(0));

        int killed = GdxGameCombatAndEnemyFlowSupport.killEnemies(active, new ArrayList<>());
        assertEquals(3, killed);
    }

    @Test
    void killEnemies_queuesResurrectableEnemiesToDeadList() {
        List<GdxEnemyRuntime> active = new ArrayList<>();
        active.add(enemy(5000));  // 5-second resurrection
        active.add(enemy(0));     // no resurrection

        List<DeadEnemy> dead = new ArrayList<>();
        GdxGameCombatAndEnemyFlowSupport.killEnemies(active, dead);

        assertEquals(1, dead.size(), "Only the enemy with resurrectionTimeMs > 0 should enter the dead queue");
        assertEquals(5f, dead.get(0).resurrectionSecondsRemaining(), 0.001f);
    }

    @Test
    void killEnemies_enemyWithNoResurrectionIsNotQueued() {
        List<GdxEnemyRuntime> active = new ArrayList<>();
        active.add(enemy(0));

        List<DeadEnemy> dead = new ArrayList<>();
        GdxGameCombatAndEnemyFlowSupport.killEnemies(active, dead);

        assertTrue(dead.isEmpty(), "Enemy with resurrectionTimeMs == 0 must not enter the dead queue");
    }

    // -----------------------------------------------------------------------
    // tickResurrections
    // -----------------------------------------------------------------------

    @Test
    void tickResurrections_doesNothingOnEmptyList() {
        List<DeadEnemy> dead = new ArrayList<>();
        List<GdxEnemyRuntime> active = new ArrayList<>();
        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, active, openWorld(), 30f, 8, 1f);

        assertTrue(active.isEmpty());
        assertTrue(dead.isEmpty());
    }

    @Test
    void tickResurrections_advancesTimerButDoesNotRespawnYet() {
        EnemySpawn sp = spawn(3000);
        List<DeadEnemy> dead = new ArrayList<>();
        dead.add(new DeadEnemy(sp, 3f));

        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, new ArrayList<>(), openWorld(), 30f, 8, 1f);

        assertEquals(1, dead.size(), "Enemy not yet ready should remain in the dead queue");
        assertEquals(2f, dead.get(0).resurrectionSecondsRemaining(), 0.001f);
    }

    @Test
    void tickResurrections_respawnsEnemyWhenTimerExpires() {
        EnemySpawn sp = spawn(1000);
        List<DeadEnemy> dead = new ArrayList<>();
        dead.add(new DeadEnemy(sp, 1f));
        List<GdxEnemyRuntime> active = new ArrayList<>();

        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, active, openWorld(), 30f, 8, 1f);

        assertTrue(dead.isEmpty(), "Expired dead entry should be removed from the queue");
        assertEquals(1, active.size(), "Resurrected enemy should appear in the active list");
    }

    @Test
    void tickResurrections_respawnedEnemyIsInvulnerableInitially() {
        EnemySpawn sp = spawn(500);
        List<DeadEnemy> dead = new ArrayList<>();
        dead.add(new DeadEnemy(sp, 0.5f));
        List<GdxEnemyRuntime> active = new ArrayList<>();

        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, active, openWorld(), 30f, 8, 1f);

        GdxEnemyRuntime risen = active.get(0);
        assertTrue(risen.isInvulnerable(), "Resurrected enemy must have a brief invulnerability window");
    }

    @Test
    void tickResurrections_respawnedEnemyIsAlive() {
        EnemySpawn sp = spawn(500);
        List<DeadEnemy> dead = new ArrayList<>();
        dead.add(new DeadEnemy(sp, 0.5f));
        List<GdxEnemyRuntime> active = new ArrayList<>();

        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, active, openWorld(), 30f, 8, 1f);

        GdxEnemyRuntime risen = active.get(0);
        assertTrue(risen.isAlive(), "Resurrected enemy must be alive with positive HP");
    }

    @Test
    void killAndResurrect_fullRoundTrip() {
        List<GdxEnemyRuntime> active = new ArrayList<>();
        active.add(enemy(2000));

        List<DeadEnemy> dead = new ArrayList<>();
        GdxGameCombatAndEnemyFlowSupport.killEnemies(active, dead);

        assertTrue(active.isEmpty());
        assertEquals(1, dead.size());
        assertFalse(dead.get(0).ready());

        // Advance past the resurrection window
        GdxGameCombatAndEnemyFlowSupport.tickResurrections(dead, active, openWorld(), 30f, 8, 2f);

        assertTrue(dead.isEmpty());
        assertEquals(1, active.size());
        assertTrue(active.get(0).isAlive());
    }

    // -----------------------------------------------------------------------
    // GdxEnemyRuntime HP and invulnerability
    // -----------------------------------------------------------------------

    @Test
    void takeDamage_returnsTrueWhenKilled() {
        GdxEnemyRuntime e = enemy(0);
        boolean died = e.takeDamage(Integer.MAX_VALUE);
        assertTrue(died, "takeDamage should return true when HP drops to zero");
        assertFalse(e.isAlive());
    }

    @Test
    void takeDamage_returnsFalseWhenStillAlive() {
        GdxEnemyRuntime e = enemy(0);
        boolean died = e.takeDamage(1);
        assertFalse(died, "takeDamage should return false when enemy survives the hit");
        assertTrue(e.isAlive());
    }

    @Test
    void invulnerability_preventsDamage() {
        GdxEnemyRuntime e = enemy(0);
        e.grantRespawnInvulnerability();
        boolean died = e.takeDamage(Integer.MAX_VALUE);
        assertFalse(died, "Invulnerable enemy should not take lethal damage");
        assertTrue(e.isAlive());
    }

    @Test
    void invulnerability_expiresAfterTick() {
        GdxEnemyRuntime e = enemy(0);
        e.grantRespawnInvulnerability();
        e.tickInvulnerability(10f); // well past 2-second window
        assertFalse(e.isInvulnerable(), "Invulnerability should expire after the grace period");
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static GdxEnemyRuntime enemy(int resurrectionTimeMs) {
        EnemySpawn sp = spawn(resurrectionTimeMs);
        return GdxEnemyRuntime.fromSpawn(sp, 0, openWorld(), 30f, 8);
    }

    private static EnemySpawn spawn(int resurrectionTimeMs) {
        return new EnemySpawn(
                "zombie-test", "zombie.png", 50f, 50f, 32f,
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

    private static WorldView openWorld() {
        return new WorldView() {
            @Override public double playerX() { return 0; }
            @Override public double playerY() { return 0; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return 400; }
            @Override public double maxY() { return 400; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        };
    }

}
