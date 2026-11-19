package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import main.game.maze.common.graphics.DamageState;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.model.EnemyAnimationSpec;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;
import org.junit.jupiter.api.Test;

class GdxEnemyRuntimeAnimationTest {

    // -----------------------------------------------------------------------
    // deriveAnimationFramePath — static utility (delegates to SpriteAnimationUtil)
    // -----------------------------------------------------------------------

    @Test
    void deriveFramePath_frame0_returnFrame1Image() {
        assertEquals("/game/zombie1-right.png",
                GdxEnemyRuntime.deriveAnimationFramePath("/game/zombie1-right.png", 0));
    }

    @Test
    void deriveFramePath_frame2_returnsFrame3Image() {
        assertEquals("/game/zombie3-right.png",
                GdxEnemyRuntime.deriveAnimationFramePath("/game/zombie1-right.png", 2));
    }

    @Test
    void deriveFramePath_noDigitInFilename_unchanged() {
        assertEquals("/game/pumpkinbomber.png",
                GdxEnemyRuntime.deriveAnimationFramePath("/game/pumpkinbomber.png", 1));
    }

    @Test
    void deriveFramePath_null_returnsNull() {
        assertEquals(null, GdxEnemyRuntime.deriveAnimationFramePath(null, 0));
    }

    // -----------------------------------------------------------------------
    // currentFramePath — selects directional path and animation frame
    // -----------------------------------------------------------------------

    @Test
    void singleFrameEnemy_currentFramePath_returnsFallback() {
        EnemySpawn spawn = spawnWithSpec("/main/game/maze/pumpkinbomber.png",
                "/main/game/maze/pumpkinbomber.png", "/main/game/maze/pumpkinbomber.png",
                "/main/game/maze/pumpkinbomber.png", "/main/game/maze/pumpkinbomber.png",
                1, 1.0f);
        GdxEnemyRuntime runtime = makeRuntime(spawn);
        // Single-frame: any clock value returns the directional (or base) path
        assertEquals("/main/game/maze/pumpkinbomber.png", runtime.currentFramePath(0f));
        assertEquals("/main/game/maze/pumpkinbomber.png", runtime.currentFramePath(99f));
    }

    @Test
    void multiFrameZombie_advancesFrameWithClock() {
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombie1-down.png",
                "/main/game/maze/zombie1-left.png",
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-up.png",
                "/main/game/maze/zombie1-down.png",
                3, 1.0f);
        GdxEnemyRuntime runtime = makeRuntime(spawn);
        // At t=0 → frame index 0 → zombie1-*
        String t0 = runtime.currentFramePath(0f);
        assertTrue(t0.contains("zombie1"), "frame 0 should reference zombie1, got: " + t0);
        // At t=0.25s at 4fps → frame index 1 → zombie2-*
        String t1 = runtime.currentFramePath(0.25f);
        assertTrue(t1.contains("zombie2"), "frame 1 should reference zombie2, got: " + t1);
        // At t=0.50s → frame index 2 → zombie3-*
        String t2 = runtime.currentFramePath(0.50f);
        assertTrue(t2.contains("zombie3"), "frame 2 should reference zombie3, got: " + t2);
        // At t=0.75s → frame index 3 % 3 = 0 → zombie1-* again
        String t3 = runtime.currentFramePath(0.75f);
        assertTrue(t3.contains("zombie1"), "frame 3 (wraps) should reference zombie1, got: " + t3);
    }

    @Test
    void spriteScale_defaultsToOne() {
        EnemySpawn spawn = new EnemySpawn("id", "/img.png", 0f, 0f, 32f, 1f,
                1, 0, "", BehaviorType.WANDER, 1f);
        GdxEnemyRuntime runtime = makeRuntime(spawn);
        assertEquals(1.0f, runtime.spriteScale(), 1e-6f);
    }

    @Test
    void spriteScale_customValuePreserved() {
        EnemySpawn spawn = spawnWithSpec("/img.png", "/l.png", "/r.png", "/u.png", "/d.png", 1, 1.5f);
        GdxEnemyRuntime runtime = makeRuntime(spawn);
        assertEquals(1.5f, runtime.spriteScale(), 1e-6f);
    }

    // -----------------------------------------------------------------------
    // Damage-state sprite integration (Issue #139)
    // -----------------------------------------------------------------------

    @Test
    void damageStatesDisabled_hpChangeDoesNotChangeHealthyImage() {
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-left.png",
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-up.png",
                "/main/game/maze/zombie1-down.png",
                1, 1.0f, false);
        GdxEnemyRuntime runtime = makeRuntime(spawn);

        String healthyPath = runtime.currentFramePath(0f);
        assertEquals("/main/game/maze/zombie1-up.png", healthyPath);

        // Drop HP to DAMAGED, BLEEDING, TIRED
        dropHpTo(runtime, 60);
        assertEquals(healthyPath, runtime.currentFramePath(0f), "Image must remain healthy when damageStatesEnabled=false");

        dropHpTo(runtime, 30);
        assertEquals(healthyPath, runtime.currentFramePath(0f), "Image must remain healthy when damageStatesEnabled=false");

        dropHpTo(runtime, 10);
        assertEquals(healthyPath, runtime.currentFramePath(0f), "Image must remain healthy when damageStatesEnabled=false");
    }

    @Test
    void damageStatesEnabled_missingAssets_fallsBackToHealthySprite_withoutWarning() {
        // zombieGameOverBackground1.png has no damage-state variants on classpath
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombieGameOverBackground1.png",
                "/main/game/maze/zombieGameOverBackground1.png",
                "/main/game/maze/zombieGameOverBackground1.png",
                "/main/game/maze/zombieGameOverBackground1.png",
                "/main/game/maze/zombieGameOverBackground1.png",
                1, 1.0f, true);
        GdxEnemyRuntime runtime = makeRuntime(spawn);

        String healthyPath = runtime.currentFramePath(0f);
        assertEquals("/main/game/maze/zombieGameOverBackground1.png", healthyPath);

        Logger rootLogger = Logger.getLogger("main.game.maze");
        AtomicInteger warningCount = new AtomicInteger(0);
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
                    warningCount.incrementAndGet();
                }
            }
            @Override public void flush() {}
            @Override public void close() throws SecurityException {}
        };
        rootLogger.addHandler(handler);

        try {
            // Drop HP to 50 (BLEEDING / DAMAGED tier)
            dropHpTo(runtime, 50);
            String path = runtime.currentFramePath(0f);
            assertEquals(healthyPath, path, "Missing damage asset must fall back cleanly to healthy image");
            assertEquals(0, warningCount.get(), "Fallback on missing damage asset must not log WARNING");
        } finally {
            rootLogger.removeHandler(handler);
        }
    }

    @Test
    void damageStatesEnabled_realAssets_updatesPerHpTier() {
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-left.png",
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-up.png",
                "/main/game/maze/zombie1-down.png",
                1, 1.0f, true);
        GdxEnemyRuntime runtime = makeRuntime(spawn);

        // 100 HP -> HEALTHY
        dropHpTo(runtime, 100);
        assertEquals("/main/game/maze/zombie1-up.png", runtime.currentFramePath(0f));

        // 70 HP -> DAMAGED
        dropHpTo(runtime, 70);
        assertEquals("/main/game/maze/zombie1-damaged-up.png", runtime.currentFramePath(0f));

        // 40 HP -> BLEEDING
        dropHpTo(runtime, 40);
        assertEquals("/main/game/maze/zombie1-bleeding-up.png", runtime.currentFramePath(0f));

        // 15 HP -> TIRED
        dropHpTo(runtime, 15);
        assertEquals("/main/game/maze/zombie1-tired-up.png", runtime.currentFramePath(0f));
    }

    @Test
    void damageStatesEnabled_mockResolver_allFourDamageStates() {
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-left.png",
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-up.png",
                "/main/game/maze/zombie1-down.png",
                1, 1.0f, true);
        GdxEnemyRuntime runtime = makeRuntime(spawn);

        AtomicReference<DamageState> resolvedState = new AtomicReference<>();
        runtime.setDamagePathResolver((basePath, state) -> {
            resolvedState.set(state);
            return basePath.replace(".png", "-" + state.label() + ".png");
        });


        // 100 HP -> HEALTHY (resolver not called for HEALTHY)
        assertEquals("/main/game/maze/zombie1-up.png", runtime.currentFramePath(0f));

        // 70 HP -> DAMAGED
        dropHpTo(runtime, 70);
        assertEquals("/main/game/maze/zombie1-up-damaged.png", runtime.currentFramePath(0f));
        assertEquals(DamageState.DAMAGED, resolvedState.get());

        // 40 HP -> BLEEDING
        dropHpTo(runtime, 40);
        assertEquals("/main/game/maze/zombie1-up-bleeding.png", runtime.currentFramePath(0f));
        assertEquals(DamageState.BLEEDING, resolvedState.get());

        // 10 HP -> TIRED
        dropHpTo(runtime, 10);
        assertEquals("/main/game/maze/zombie1-up-tired.png", runtime.currentFramePath(0f));
        assertEquals(DamageState.TIRED, resolvedState.get());
    }

    @Test
    void damageStatesEnabled_walkCycleComposition() {
        EnemySpawn spawn = spawnWithSpec(
                "/main/game/maze/zombie1-down.png",
                "/main/game/maze/zombie1-left.png",
                "/main/game/maze/zombie1-right.png",
                "/main/game/maze/zombie1-up.png",
                "/main/game/maze/zombie1-down.png",
                3, 1.0f, true);
        GdxEnemyRuntime runtime = makeRuntime(spawn);

        dropHpTo(runtime, 70); // DAMAGED tier

        // Frame 0 at t=0
        String t0 = runtime.currentFramePath(0f);
        assertTrue(t0.contains("zombie1-damaged"), "frame 0 should reference zombie1-damaged, got: " + t0);

        // Frame 1 at t=0.25s
        String t1 = runtime.currentFramePath(0.25f);
        assertTrue(t1.contains("zombie2-damaged"), "frame 1 should reference zombie2-damaged, got: " + t1);

        // Frame 2 at t=0.50s
        String t2 = runtime.currentFramePath(0.50f);
        assertTrue(t2.contains("zombie3-damaged"), "frame 2 should reference zombie3-damaged, got: " + t2);

        // While at frame 2, HP drops to BLEEDING
        dropHpTo(runtime, 35);
        String t2Bleeding = runtime.currentFramePath(0.50f);
        assertTrue(t2Bleeding.contains("zombie3-bleeding"), "frame 2 should re-resolve to bleeding variant on HP change, got: " + t2Bleeding);
    }

    // -----------------------------------------------------------------------
    // helpers
    // -----------------------------------------------------------------------

    private static EnemySpawn spawnWithSpec(
            String base, String left, String right, String up, String down,
            int frameCount, float scale, boolean damageStatesEnabled) {
        EnemyAnimationSpec spec = new EnemyAnimationSpec(left, right, up, down, frameCount, scale, damageStatesEnabled);
        return new EnemySpawn("test_id", base, 50f, 50f, 32f, 1f,
                1, 0, "", BehaviorType.WANDER, 1f,
                0.0, 100, ProjectileType.STRAIGHT, 0f, 0f, 0f, 0, 0f, 0, 100, spec);
    }

    private static EnemySpawn spawnWithSpec(
            String base, String left, String right, String up, String down,
            int frameCount, float scale) {
        return spawnWithSpec(base, left, right, up, down, frameCount, scale, false);
    }

    /** Lowers HP through the real damage path (max HP is 100 in these spawns). */
    private static void dropHpTo(GdxEnemyRuntime runtime, int hp) {
        runtime.takeDamage(runtime.currentHitPoints() - hp);
        assertEquals(hp, runtime.currentHitPoints());
    }

    private static GdxEnemyRuntime makeRuntime(EnemySpawn spawn) {
        return GdxEnemyRuntime.fromSpawn(spawn, 0, openWorld(), 60f, 8);
    }

    private static WorldView openWorld() {
        return new WorldView() {
            @Override public double playerX() { return 400; }
            @Override public double playerY() { return 300; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return 800; }
            @Override public double maxY() { return 600; }
            @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        };
    }
}
