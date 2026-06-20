package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.GdxProjectileRuntime;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.RangedEnemySpawnProps;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GdxWallDamageSupport")
class GdxWallDamageSupportTest {

    private static final int WORLD_W = 800;
    private static final int WORLD_H = 800;

    private static MazeArena stubArena() {
        return new MazeArena() {
            @Override public List<WallSegment> walls() { return List.of(); }
            @Override public float widthPx()  { return WORLD_W; }
            @Override public float heightPx() { return WORLD_H; }
            @Override public float startX()   { return 24f; }
            @Override public float startY()   { return 24f; }
            @Override public float goalX()    { return WORLD_W - 24f; }
            @Override public float goalY()    { return WORLD_H - 24f; }
        };
    }

    private static GdxEnemyRuntime openArenaEnemy() {
        EnemySpawn spawn = new EnemySpawn("e1", "/img.png", 50f, 50f, 32f, 1f, 5, 0, "", 1f);
        return GdxEnemyRuntime.fromSpawn(spawn, 0,
                new main.game.maze.libgdx.movement.GdxWorldView(stubArena(), new PlayerState(400f, 400f, 16f)),
                20f, 4);
    }

    @Test
    @DisplayName("no-op with stub (non-RealMaze) arena — does not throw")
    void updateProjectiles_stubArena_doesNotThrow() {
        GdxEnemyRuntime enemy = openArenaEnemy();
        PlayerState player = new PlayerState(400f, 400f, 16f);

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                GdxWallDamageSupport.updateProjectiles(List.of(enemy), stubArena(), player, 0.016f));
    }

    @Test
    @DisplayName("no-op with null player — does not throw")
    void updateProjectiles_nullPlayer_doesNotThrow() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                GdxWallDamageSupport.updateProjectiles(List.of(), stubArena(), null, 0.016f));
    }

    @Test
    @DisplayName("no-op with empty enemy list — does not throw")
    void updateProjectiles_emptyEnemies_doesNotThrow() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->
                GdxWallDamageSupport.updateProjectiles(
                        List.of(), stubArena(), new PlayerState(50f, 50f, 16f), 0.016f));
    }

    @Test
    @DisplayName("projectile placed on breakable wall applies damage and is removed")
    void projectile_onBreakableWall_appliesDamageAndIsRemoved() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        List<BreakableWall> bwalls = world.getBreakableWalls();
        // default GameMazeWorld with seed 42 always has at least one breakable wall
        assertTrue(!bwalls.isEmpty(), "Expected at least one breakable wall");

        BreakableWall bw = bwalls.get(0);
        int hpBefore = bw.getRemainingHp();

        // Place projectile at the wall's midpoint in game-world (Y-down) space
        Vector2D wallVec = bw.geometry;
        double midX = (wallVec.getStart().getX() + wallVec.getEnd().getX()) / 2.0;
        double midY = (wallVec.getStart().getY() + wallVec.getEnd().getY()) / 2.0;

        GdxEnemyRuntime enemy = openArenaEnemy();
        // Stationary projectile (same from/to) parked exactly on the wall
        enemy.activeProjectiles().add(
                GdxProjectileRuntime.fire(midX, midY, midX, midY, 0, bw.getRemainingHp()));

        PlayerState player = new PlayerState(50f, 50f, 16f);
        GdxWallDamageSupport.updateProjectiles(List.of(enemy), realMaze, player, 0f);

        assertTrue(bw.getRemainingHp() < hpBefore,
                "Wall HP must decrease after projectile hit");
        assertTrue(enemy.activeProjectiles().isEmpty(),
                "Projectile must be removed after hitting a wall");
    }

    @Test
    @DisplayName("projectile on indestructible wall position applies no damage")
    void projectile_onIndestructibleWall_doesNotApplyDamage() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        // Find a wall vector that is NOT breakable
        Vector2D nonBreakable = world.getMazeVectors().stream()
                .filter(v -> world.findBreakableWall(v) == null)
                .findFirst().orElse(null);

        org.junit.jupiter.api.Assumptions.assumeTrue(nonBreakable != null,
                "All walls are breakable — indestructible-wall test skipped");

        double midX = (nonBreakable.getStart().getX() + nonBreakable.getEnd().getX()) / 2.0;
        double midY = (nonBreakable.getStart().getY() + nonBreakable.getEnd().getY()) / 2.0;

        GdxEnemyRuntime enemy = openArenaEnemy();
        enemy.activeProjectiles().add(GdxProjectileRuntime.fire(midX, midY, midX, midY, 0, 10));

        int totalHpBefore = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();

        PlayerState player = new PlayerState(50f, 50f, 16f);
        GdxWallDamageSupport.updateProjectiles(List.of(enemy), realMaze, player, 0f);

        int totalHpAfter = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();
        assertEquals(totalHpBefore, totalHpAfter,
                "Hitting a non-breakable wall must not change any HP");
        // Projectile still hits a wall segment — it must be removed from the list
        assertTrue(enemy.activeProjectiles().isEmpty(),
                "Projectile must be consumed even when hitting an indestructible wall");
    }

    @Test
    @DisplayName("enemy without rangedProps does not fire projectiles")
    void tryShootAt_enemyWithoutRangedProps_noProjectileCreated() {
        // EnemySpawn with no rangedProps (null) — melee enemy
        EnemySpawn spawn = new EnemySpawn("z1", "/zombie.png", 50f, 50f, 32f, 1f, 5, 0, "", 1f);
        GdxEnemyRuntime enemy = GdxEnemyRuntime.fromSpawn(spawn, 0,
                new main.game.maze.libgdx.movement.GdxWorldView(stubArena(), new PlayerState(400f, 400f, 16f)),
                20f, 4);

        enemy.tryShootAt(100, 100, 50, 50);

        assertTrue(enemy.activeProjectiles().isEmpty(),
                "Melee enemy (no rangedProps) must not create projectiles");
    }

    @Test
    @DisplayName("enemy with rangedProps fires when player is in range")
    void tryShootAt_enemyWithRangedProps_inRange_createsProjectile() {
        RangedEnemySpawnProps props = new RangedEnemySpawnProps(10000, 0, 200, 0);
        EnemySpawn spawn = new EnemySpawn("pb1", "/pumpkin.png", 50f, 50f, 32f, 1f, 5, 0, "",
                BehaviorType.WANDER, 1f, 0.0, EnemySpawn.DEFAULT_VISIBILITY_LEVEL, props);

        GdxEnemyRuntime enemy = GdxEnemyRuntime.fromSpawn(spawn, 0,
                new main.game.maze.libgdx.movement.GdxWorldView(stubArena(), new PlayerState(400f, 400f, 16f)),
                20f, 4);

        // enemy world position (~50, ~50); shoot toward player at (100, 100) world
        enemy.tryShootAt(100, 100, 50, 50);

        assertEquals(1, enemy.activeProjectiles().size(),
                "Ranged enemy in range must create exactly one projectile");
    }

    @Test
    @DisplayName("enemy with rangedProps does not fire when player is out of range")
    void tryShootAt_enemyWithRangedProps_outOfRange_noProjectile() {
        RangedEnemySpawnProps props = new RangedEnemySpawnProps(10, 0, 200, 0);
        EnemySpawn spawn = new EnemySpawn("pb2", "/pumpkin.png", 50f, 50f, 32f, 1f, 5, 0, "",
                BehaviorType.WANDER, 1f, 0.0, EnemySpawn.DEFAULT_VISIBILITY_LEVEL, props);

        GdxEnemyRuntime enemy = GdxEnemyRuntime.fromSpawn(spawn, 0,
                new main.game.maze.libgdx.movement.GdxWorldView(stubArena(), new PlayerState(400f, 400f, 16f)),
                20f, 4);

        // player is 500 units away, attackRange is only 10
        enemy.tryShootAt(550, 550, 50, 50);

        assertTrue(enemy.activeProjectiles().isEmpty(),
                "Ranged enemy out of range must not create a projectile");
    }

    @Test
    @DisplayName("expired projectile is removed without applying damage")
    void expiredProjectile_isRemovedWithoutDamage() {
        GameMazeWorld world = new GameMazeWorld();
        RealMaze realMaze = new RealMaze(world, WORLD_W, WORLD_H);

        GdxEnemyRuntime enemy = openArenaEnemy();
        // Place a projectile far from any wall and advance past lifetime (3+ seconds)
        enemy.activeProjectiles().add(GdxProjectileRuntime.fire(1, 1, 2, 1, 1, 99));

        int totalHpBefore = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();

        // 4 seconds — exceeds MAX_LIFETIME_SECONDS (3s)
        GdxWallDamageSupport.updateProjectiles(
                List.of(enemy), realMaze, new PlayerState(50f, 50f, 16f), 4f);

        int totalHpAfter = world.getBreakableWalls().stream()
                .mapToInt(BreakableWall::getRemainingHp).sum();
        assertEquals(totalHpBefore, totalHpAfter,
                "Expired projectile must not apply damage");
        assertTrue(enemy.activeProjectiles().isEmpty(),
                "Expired projectile must be removed from the list");
    }
}
