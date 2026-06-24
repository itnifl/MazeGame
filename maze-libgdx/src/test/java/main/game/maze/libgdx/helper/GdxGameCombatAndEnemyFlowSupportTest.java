package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.libgdx.model.EnemySpawn;
import main.game.maze.libgdx.model.GameWorldModel;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.opponents.BehaviorType;
import main.game.maze.opponents.ProjectileType;
import org.junit.jupiter.api.Test;

class GdxGameCombatAndEnemyFlowSupportTest {

    @Test
    void updateCombat_setsScreenShakeAfterLobExplosion() {
        GameWorldModel worldModel = new GameWorldModel();
        MazeArena maze = openMaze();
        PlayerState player = new PlayerState(200f, 40f, 24f);
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
        GdxEnemyRuntime runtime = GdxEnemyRuntime.fromSpawn(
                    rangedSpawn(ProjectileType.LOB, 0f, 0f, 120f, 40f, 160f, 150),
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

        assertTrue(applied == 100, "Explosion damage must be capped at 100 per direction");
        assertTrue(runtime.currentHitPoints() == 50,
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

        int applied = GdxGameCombatAndEnemyFlowSupport.applyDirectionalFlameExplosion(
            List.of(frontRuntime, rearRuntime),
            wallMaze(),
            20f,
            40f,
            100,
            400f);

        assertTrue(applied == 20, "Only the enemy before the wall should be damaged when the wall survives");
        assertTrue(frontRuntime.currentHitPoints() == 0,
            "Front enemy should be defeated by the available budget");
        assertTrue(rearRuntime.currentHitPoints() == 80,
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

        assertTrue(applied == 0, "Enemy outside every flame corridor must not be damaged");
        assertTrue(offCorridor.currentHitPoints() == 100,
            "Off-corridor enemy must retain full hit points after the explosion");
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