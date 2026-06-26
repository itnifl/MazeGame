package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.RealMaze;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;
import org.junit.jupiter.api.Test;

class GdxGameCombatAndEnemyFlowSupportTest {

    @Test
    void updateCombat_setsScreenShakeAfterLobExplosion() {
        GameWorldModel worldModel = new GameWorldModel();
        MazeArena maze = openMaze();
        main.game.maze.mazeworld.generators.PlayerState player =
                new main.game.maze.mazeworld.generators.PlayerState(200f, 40f, 24f);
        worldModel.setMaze(maze);
        worldModel.setPlayer(player);

        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 70f, 80f, 160f),
                0,
                openWorld(),
                60f,
                8);

        PlayerCombatStateService combatState = new PlayerCombatStateService();
        combatState.reset(100);
        combatState.setMaze(maze);

        for (int i = 0; i < 240; i++) {
            GdxGameCombatAndEnemyFlowSupport.updateCombat(
                    1f / 60f,
                    player,
                    combatState,
                    List.of(runtime),
                    worldModel);
            if (worldModel.explosionShakeRemainingSeconds() > 0f) {
                break;
            }
        }

        assertTrue(worldModel.explosionShakeRemainingSeconds() > 0f,
                "Lob explosion should trigger transient screen shake state");
        assertTrue(worldModel.explosionShakeIntensity() > 0f,
                "Lob explosion should carry positive shake intensity");
    }

    @Test
    void applyDirectionalFlameExplosion_capsDamagePerDirectionAtOneHundred() {
        // Enemy at (200,40): center=(220,60). East perpendicular |60-40|=20≤120; south cross-axis
        // |220-20|=200>120 so south/north corridors exclude it — enemy is in east only.
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 200f, 40f, 160f, 150),
                0,
                openWorld(),
                60f,
                8);

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(runtime),
                openMaze(),
                20f,
                40f,
                100,
                400f);

        assertEquals(100, applied, "Explosion damage must be capped at 100 per direction");
        assertEquals(50, runtime.currentHitPoints(),
                "Enemy should keep the remaining hit points after capped damage");
    }

    @Test
    void applyDirectionalFlameExplosion_stopsAtWallWhenWallSurvives() {
        GdxEnemyRuntime frontRuntime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 80f, 40f, 160f, 20),
                0,
                openWorld(),
                60f,
                8);
        GdxEnemyRuntime rearRuntime = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 160f, 40f, 160f, 80),
                0,
                openWorld(),
                60f,
                8);

        // Origin is at screen (20, 40) → world Y = 400-40 = 360.
        // Wall at world x=120, world Y span [300..420] so the origin (world Y=360) falls within it.
        Vector2D wallVec = new Vector2D(120, 300, 120, 420);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        // Clear breakable assignments so the wall is treated as indestructible
        world.assignBreakableWalls(0L, List.of());
        RealMaze maze = new RealMaze(world, 400, 400);

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(frontRuntime, rearRuntime),
                maze,
                20f,
                40f,
                100,
                400f);

        assertEquals(20, applied,
                "Only the enemy before the wall should be damaged when the wall is indestructible");
        assertEquals(0, frontRuntime.currentHitPoints(),
                "Front enemy should be defeated by the available budget");
        assertEquals(80, rearRuntime.currentHitPoints(),
                "Rear enemy must stay untouched after the wall stops the flame");
    }

    @Test
    void applyDirectionalFlameExplosion_leavesEnemiesOffTheFlameCorridorUntouched() {
        // Regression: the bomb must only hurt what the blast actually touches,
        // never every enemy in the maze. This enemy sits diagonally off all four
        // cardinal flame corridors and must survive a full-power detonation.
        GdxEnemyRuntime offCorridor = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 300f, 300f, 160f, 100),
                0,
                openWorld(),
                60f,
                8);

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(offCorridor),
                openMaze(),
                20f,
                40f,
                100,
                400f);

        assertEquals(0, applied, "Enemy outside every flame corridor must not be damaged");
        assertEquals(100, offCorridor.currentHitPoints(),
                "Off-corridor enemy must retain full hit points after the explosion");
    }

    @Test
    void applyDirectionalFlameExplosion_damagesPlayerInCorridor() {
        // Player standing directly east of the bomb origin must receive the remaining budget
        AtomicInteger playerDamageReceived = new AtomicInteger(0);
        float playerCx = 120f; // east of origin (20, 40)
        float playerCy = 40f;

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(),
                openMaze(),
                20f,
                40f,
                100,
                400f,
                playerCx,
                playerCy,
                playerDamageReceived::addAndGet);

        // No enemies, so full budget (100) reaches the player in the east direction
        assertTrue(playerDamageReceived.get() > 0,
                "Player in the flame corridor must receive damage from the explosion");
        assertEquals(100, playerDamageReceived.get(),
                "Player receives the full remaining budget in the east direction");
    }

    @Test
    void applyDirectionalFlameExplosion_playerDoesNotBlockFlameForEnemyBeyond() {
        // Player stands between origin and an enemy. Flame must pass through the player
        // and still damage the enemy.
        GdxEnemyRuntime enemy = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 200f, 40f, 160f, 100),
                0,
                openWorld(),
                60f,
                8);
        AtomicInteger playerDamage = new AtomicInteger(0);
        float playerCx = 120f; // between origin (20,40) and enemy center (~220,60)
        float playerCy = 40f;

        GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(enemy),
                openMaze(),
                20f,
                40f,
                100,
                400f,
                playerCx,
                playerCy,
                playerDamage::addAndGet);

        assertTrue(playerDamage.get() > 0, "Player must take damage from the blast");
        assertTrue(enemy.currentHitPoints() < 100, "Enemy beyond the player must also take damage");
    }

    @Test
    void applyDirectionalFlameExplosion_destroysBreakableWallAndContinues() {
        GdxEnemyRuntime enemyBeyondWall = GdxEnemyRuntime.fromSpawn(
                rangedSpawn(ProjectileType.LOB, 0f, 0f, 200f, 40f, 160f, 10),
                0,
                openWorld(),
                60f,
                8);

        // Origin at screen (20, 40) → world Y = 400-40 = 360.
        // Wall at world x=120, world Y span [300..420] so origin (world Y=360) falls within it.
        Vector2D wallVec = new Vector2D(120, 300, 120, 420);
        GameMazeWorld world = new GameMazeWorld(() -> List.of(wallVec));
        world.assignBreakableWalls(0L,
                List.of(new main.game.maze.mazeworld.WallMaterialSpec("WOOD_BASIC", "Wood", 20)));
        RealMaze maze = new RealMaze(world, 400, 400);

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
                List.of(enemyBeyondWall),
                maze,
                20f,
                40f,
                100, // 100 budget: 20 for wall, 10 for enemy = 30 used
                400f);

        // Wall costs 20 HP, enemy costs 10 HP = 30 applied in this direction
        assertTrue(applied >= 30,
                "Budget should cover the wall (20 HP) and the enemy beyond it (10 HP)");
        assertEquals(0, enemyBeyondWall.currentHitPoints(),
                "Enemy beyond the destroyed wall should be defeated");
    }

    private static EnemySpawn rangedSpawn(ProjectileType type, float splashRadius, float arcHeight, float projectileSpeed) {
        return rangedSpawn(type, splashRadius, arcHeight, 40f, 40f, projectileSpeed, 100);
    }

    private static EnemySpawn rangedSpawn(ProjectileType type,
                                          float splashRadius,
                                          float arcHeight,
                                          float x,
                                          float y,
                                          float projectileSpeed,
                                          int maxHitPoints) {
        return new EnemySpawn(
                "pumpkin-test",
                "/main/game/maze/pumpkinbomber.png",
                x,
                y,
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
                projectileSpeed,
                0,
                maxHitPoints);
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
}
