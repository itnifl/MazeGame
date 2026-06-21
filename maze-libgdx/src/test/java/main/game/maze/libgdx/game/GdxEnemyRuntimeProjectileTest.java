package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;
import org.junit.jupiter.api.Test;

class GdxEnemyRuntimeProjectileTest {

    @Test
    void straightProjectileStopsAtWallBeforePlayer() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.STRAIGHT, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        int totalDamage = 0;
        for (int i = 0; i < 240; i++) {
            totalDamage += runtime.updateRangedAttacks(1f / 60f, wallMaze(), 200f, 40f, 12f);
        }

        assertEquals(0, totalDamage, "STRAIGHT projectile must be blocked by an intermediate wall");
    }

    @Test
    void lobProjectileIgnoresWallAndAppliesSplashAtTarget() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 70f, 80f, 160f),
                0,
                openWorld(),
                60f,
                8);

        int totalDamage = 0;
        for (int i = 0; i < 240; i++) {
            totalDamage += runtime.updateRangedAttacks(1f / 60f, wallMaze(), 200f, 40f, 12f);
        }

        assertTrue(totalDamage > 0, "LOB projectile should land and apply splash through wall separation");
    }

    @Test
    void lobImpactEmitsExplosionVisual() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 70f, 80f, 160f),
                0,
                openWorld(),
                60f,
                8);

        boolean hadImpactVisual = false;
        for (int i = 0; i < 240; i++) {
            runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
            hadImpactVisual = hadImpactVisual || !runtime.impactVisuals().isEmpty();
        }

        assertTrue(hadImpactVisual, "LOB impact should emit at least one explosion visual in libGDX");
    }

    @Test
    void beamDealsInstantDamageWithoutTravelTime() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.BEAM, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        int dealtDamage = runtime.updateRangedAttacks(1f / 60f, openMaze(), 120f, 40f, 12f);

        assertTrue(dealtDamage > 0, "BEAM should damage immediately on fire");
        assertTrue(runtime.beamVisuals().size() > 0, "BEAM should emit a visible beam effect");
    }

    @Test
    void lobWithHighArcDoesNotExpireOutOfBoundsBeforeArrival() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 80f, 1000f, 180f),
                0,
                openWorld(),
                60f,
                8);

        boolean hadLobVisual = false;
        for (int i = 0; i < 30; i++) {
            runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
            hadLobVisual = hadLobVisual || runtime.projectileVisuals().stream().anyMatch(v -> v.lob());
        }

        assertTrue(hadLobVisual, "High-arc lob should remain active in flight instead of expiring out of bounds");
        assertFalse(runtime.projectileVisuals().isEmpty(),
                "High-arc lob should still have an active projectile visual during early flight");
    }

    /** Spawn with a 5-second cooldown so only one projectile fires during 1-second speed tests. */
    private static EnemySpawn slowCooldownSpawn(ProjectileType type, float projectileSpeed) {
        return new EnemySpawn(
                "speed-test",
                "/main/game/maze/pumpkinbomber.png",
                40f, 40f, 40f, 2f, 10, 0, "",
                BehaviorType.AGGRESSIVE, 2f, 0d,
                EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                type, 0f, 0f,
                300f,  // attackRange — covers distance to (240,40)
                5000,  // attackCooldownMs — prevents a second shot during 1-second test
                projectileSpeed);
    }

    private static EnemySpawn rangedSpawn(ProjectileType type, float splashRadius, float arcHeight) {
        return rangedSpawn(type, splashRadius, arcHeight, 260f);
    }

    private static EnemySpawn rangedSpawn(ProjectileType type, float splashRadius, float arcHeight, float projectileSpeed) {
        return new EnemySpawn(
                "pumpkin-test",
                "/main/game/maze/pumpkinbomber.png",
                40f,
                40f,
                40f,
                2f,
                10,
                0,
                "",
                BehaviorType.AGGRESSIVE,
                2f,
                0d,
                EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                type,
                splashRadius,
                arcHeight,
                240f,
                200,
                projectileSpeed);
    }

    private static WorldView openWorld() {
        return new WorldView() {
            @Override public double playerX() { return 200; }
            @Override public double playerY() { return 40; }
            @Override public double minX() { return 0; }
            @Override public double minY() { return 0; }
            @Override public double maxX() { return 400; }
            @Override public double maxY() { return 400; }
            @Override public boolean wouldCollide(double centerX, double centerY, double size) { return false; }
        };
    }

    private static MazeArena openMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx() { return 400; }
            @Override public float heightPx() { return 400; }
            @Override public float startX() { return 8; }
            @Override public float startY() { return 8; }
            @Override public float goalX() { return 380; }
            @Override public float goalY() { return 380; }
        };
    }

    private static MazeArena wallMaze() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(new WallSegment(120, 0, 120, 120)); }
            @Override public float widthPx() { return 400; }
            @Override public float heightPx() { return 400; }
            @Override public float startX() { return 8; }
            @Override public float startY() { return 8; }
            @Override public float goalX() { return 380; }
            @Override public float goalY() { return 380; }
        };
    }

    // --- Edge case: negative dt must return 0 and not tick projectiles ------

    @Test
    void updateRangedAttacks_withNegativeDt_returnZeroAndFiresNothing() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.BEAM, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        int damage = runtime.updateRangedAttacks(-0.1f, openMaze(), 120f, 40f, 12f);

        assertEquals(0, damage, "Negative dt must return 0 damage");
        assertTrue(runtime.projectileVisuals().isEmpty(), "No projectile should be created for negative dt");
    }

    // --- Edge case: projectile cap prevents unbounded list growth ------------

    @Test
    void projectileCap_preventsMoreThanMaxActiveProjectiles() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.STRAIGHT, 0f, 0f, 1f),  // very slow — never arrive
                0,
                openWorld(),
                60f,
                8);

        // Fire repeatedly with cooldown=0 by passing large dt steps of exactly one cooldown period
        for (int i = 0; i < 20; i++) {
            runtime.updateRangedAttacks(0.201f, openMaze(), 120f, 40f, 1f);
        }

        assertTrue(runtime.projectileVisuals().size() <= 8,
                "Active projectile count must not exceed the cap of 8");
    }

    // --- Edge case: LOB outside splash radius deals no damage ----------------

    @Test
    void lobProjectile_playerOutsideSplashRadius_dealsNoDamage() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 10f, 0f, 300f),  // tiny splash radius
                0,
                openWorld(),
                60f,
                8);

        // Player is at 200,40; enemy at 40,40; target is the player — 10px splash means
        // player must be within 10px of impact to take damage. Impact is at player pos so
        // player IS in range in this test. Validate via opposite: player far from impact.
        GdxEnemyRuntime farRuntime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 10f, 0f, 300f),
                0,
                openWorld(),
                60f,
                8);

        int totalDamage = 0;
        for (int i = 0; i < 240; i++) {
            // Pass player coords 300,300 — far from the impact at ~200,40
            totalDamage += farRuntime.updateRangedAttacks(1f / 60f, openMaze(), 300f, 300f, 5f);
        }
        assertEquals(0, totalDamage, "Player outside splash radius must take no LOB damage");
    }

    // --- F26: projectile speed drives physics --------------------------------

    @Test
    void lobProjectile_positionAfterOneSecond_equalsProjectileSpeedPixelsFromStart() {
        // Enemy at (40,40) [from EnemySpawn]; player at (240,40): distance = 200f.
        // Speed = 100f → duration = 200 / 100 = 2.0 seconds.
        // After exactly 1 second of flight: progress = 0.5, x = 140f.
        // Distance from spawn = 140 - 40 = 100f = projectileSpeed. (F26)
        float speed = 100f;
        float playerX = 240f, playerY = 40f;

        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                slowCooldownSpawn(ProjectileType.LOB, speed),
                0, openWorld(), 60f, 8);

        // First tick fires the shot (shotCooldownRemaining starts at 0)
        runtime.updateRangedAttacks(1f / 60f, openMaze(), playerX, playerY, 5f);

        // Simulate exactly 1 second of flight (60 ticks of dt = 1/60 s)
        for (int i = 0; i < 60; i++) {
            runtime.updateRangedAttacks(1f / 60f, openMaze(), playerX, playerY, 5f);
        }

        List<GdxEnemyRuntime.ProjectileVisual> visuals = runtime.projectileVisuals();
        assertFalse(visuals.isEmpty(),
                "LOB must still be in flight after 1 s when total flight duration is 2 s");

        // LOB with arcHeight=0: x and y are the straight-line lerp (no arc offset)
        float distanceTraveled = (float) Math.hypot(
                visuals.get(0).x() - 40f,
                visuals.get(0).y() - 40f);
        assertEquals(speed, distanceTraveled, 2.0f,
                "After 1 second of flight, distance from spawn must equal projectileSpeed (F26)");
    }

    // --- Edge case: zero attackRange must not fire --------------------------

    @Test
    void updateRangedAttacks_withZeroAttackRange_neverFires() {
        EnemySpawn noRangeSpawn = new EnemySpawn(
                "no-range",
                "/main/game/maze/pumpkinbomber.png",
                40f, 40f, 40f, 2f, 10, 0, "",
                BehaviorType.AGGRESSIVE, 2f, 0d,
                EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                ProjectileType.STRAIGHT, 0f, 0f,
                0f,   // attackRange = 0
                200,
                260f);

        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(noRangeSpawn, 0, openWorld(), 60f, 8);

        int total = 0;
        for (int i = 0; i < 60; i++) {
            total += runtime.updateRangedAttacks(1f / 60f, openMaze(), 120f, 40f, 12f);
        }

        assertEquals(0, total, "Zero attackRange must never fire a projectile or deal damage");
    }
}
