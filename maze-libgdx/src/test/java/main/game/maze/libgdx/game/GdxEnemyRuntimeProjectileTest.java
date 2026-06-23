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

    // --- Edge case: BEAM blocked by wall shows blocked flag in visuals -------

    @Test
    void beamBlockedByWall_beamVisualShowsBlockedTrue() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.BEAM, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        runtime.updateRangedAttacks(1f / 60f, wallMaze(), 200f, 40f, 12f);

        List<GdxEnemyRuntime.BeamVisual> visuals = runtime.beamVisuals();
        assertFalse(visuals.isEmpty(), "BEAM must emit a visual even when blocked");
        assertTrue(visuals.get(0).blocked(), "BEAM visual must report blocked=true when a wall intercepts LOS");
    }

    // --- Edge case: BEAM blocked by wall deals no damage --------------------

    @Test
    void beamBlockedByWall_dealsNoDamage() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.BEAM, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        // wallMaze has a wall at x=120 between enemy at x=40 and player at x=200.
        int damage = runtime.updateRangedAttacks(1f / 60f, wallMaze(), 200f, 40f, 12f);

        assertEquals(0, damage, "BEAM blocked by a wall must deal zero damage");
    }

    // --- Edge case: STRAIGHT hits player when no wall between them ----------

    @Test
    void straightProjectile_hitsPlayerWithNoWall() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.STRAIGHT, 0f, 0f, 600f), // fast
                0,
                openWorld(),
                60f,
                8);

        int totalDamage = 0;
        for (int i = 0; i < 60; i++) {
            totalDamage += runtime.updateRangedAttacks(1f / 60f, openMaze(), 120f, 40f, 12f);
        }

        assertTrue(totalDamage > 0, "STRAIGHT must hit and damage the player when no wall is present");
    }

    // --- Edge case: LOB with zero arcHeight still lands and applies splash --

    @Test
    void lobProjectile_withZeroArcHeight_stillLandsAndDamages() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 80f, 0f, 300f), // arcHeight=0
                0,
                openWorld(),
                60f,
                8);

        int totalDamage = 0;
        for (int i = 0; i < 240; i++) {
            totalDamage += runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
        }

        assertTrue(totalDamage > 0, "LOB with zero arcHeight must still travel and apply splash damage");
    }

    // --- Edge case: shot cooldown prevents a second shot --------------------

    @Test
    void shotCooldown_preventsSecondShotWithinPeriod() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.BEAM, 0f, 0f),
                0,
                openWorld(),
                60f,
                8);

        // First shot fires (cooldown not yet set).
        int first = runtime.updateRangedAttacks(1f / 60f, openMaze(), 120f, 40f, 12f);
        assertTrue(first > 0, "First BEAM shot must deal damage");

        // Immediately try again within cooldown period.
        int second = runtime.updateRangedAttacks(1f / 60f, openMaze(), 120f, 40f, 12f);
        assertEquals(0, second, "Second BEAM shot within cooldown must deal no damage");
    }

    // --- Edge case: impact visual has expanding radius and fading alpha -----

    @Test
    void impactVisual_hasPositiveRadiusAndAlpha_immediatelyAfterImpact() {
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 60f, 0f, 400f),
                0,
                openWorld(),
                60f,
                8);

        boolean hadImpact = false;
        for (int i = 0; i < 240 && !hadImpact; i++) {
            runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
            List<GdxEnemyRuntime.ImpactVisual> impacts = runtime.impactVisuals();
            if (!impacts.isEmpty()) {
                GdxEnemyRuntime.ImpactVisual iv = impacts.get(0);
                assertTrue(iv.radius() > 0f, "Impact visual must have a positive radius");
                assertTrue(iv.alpha() > 0f, "Impact visual must have positive alpha immediately after impact");
                hadImpact = true;
            }
        }

        assertTrue(hadImpact, "At least one impact visual must be emitted after LOB lands");
    }

    // --- Edge case: LOB fires again after cooldown --------------------------

    @Test
    void lobProjectile_firesAgainAfterCooldown() {
        // attackCooldownMs=200 → 0.2 s cooldown; use fast projectile so it resolves quickly.
        EnemySpawn shortCooldownSpawn = new EnemySpawn(
                "lob-cooldown",
                "/main/game/maze/pumpkinbomber.png",
                40f, 40f, 40f, 2f, 5, 0, "",
                BehaviorType.AGGRESSIVE, 2f, 0d,
                EnemySpawn.DEFAULT_VISIBILITY_LEVEL,
                ProjectileType.LOB, 60f, 0f,
                240f,
                200,    // 200ms cooldown
                600f);

        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(shortCooldownSpawn, 0, openWorld(), 60f, 8);

        // Advance 1 second so first shot fires and projectile resolves.
        int firstSecondDamage = 0;
        for (int i = 0; i < 60; i++) {
            firstSecondDamage += runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
        }

        // Advance another second — cooldown has elapsed; another shot must fire and land.
        int secondSecondDamage = 0;
        for (int i = 0; i < 60; i++) {
            secondSecondDamage += runtime.updateRangedAttacks(1f / 60f, openMaze(), 200f, 40f, 12f);
        }

        assertTrue(firstSecondDamage > 0,  "LOB must deal damage in first second");
        assertTrue(secondSecondDamage > 0, "LOB must deal damage again after cooldown expires");
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
