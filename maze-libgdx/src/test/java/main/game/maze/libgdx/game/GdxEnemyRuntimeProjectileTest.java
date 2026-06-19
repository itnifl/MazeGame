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
}
