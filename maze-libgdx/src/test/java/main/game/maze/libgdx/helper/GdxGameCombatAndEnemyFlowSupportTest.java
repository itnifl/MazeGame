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
}