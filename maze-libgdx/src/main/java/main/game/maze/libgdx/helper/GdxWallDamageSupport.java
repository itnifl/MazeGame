package main.game.maze.libgdx.helper;

import java.util.Iterator;
import java.util.List;
import main.game.maze.libgdx.game.GdxEnemyRuntime;
import main.game.maze.libgdx.game.GdxProjectileRuntime;
import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;

/**
 * Handles projectile advancement and wall-damage for the libGDX frontend.
 *
 * <p>All operations run on the libGDX render thread (no {@code postRunnable} needed).
 * Projectile and wall coordinates are in game-world (Y-down) space to align with
 * {@code GameMazeWorld.getMazeVectors()}; enemy/player screen positions are
 * Y-flipped via {@code heightPx - screenY} before use.</p>
 */
public final class GdxWallDamageSupport {

    private GdxWallDamageSupport() {
    }

    /**
     * For each ranged enemy: attempts to shoot at the player, then advances all
     * in-flight projectiles and applies damage to any breakable wall they hit.
     *
     * @param enemies    live enemy list
     * @param arena      current maze (wall damage is skipped when not a {@link RealMaze})
     * @param player     current player state (screen-space coordinates)
     * @param dt         frame delta in seconds
     */
    public static void updateProjectiles(
            List<GdxEnemyRuntime> enemies,
            MazeArena arena,
            PlayerState player,
            float dt) {
        if (enemies == null || enemies.isEmpty() || arena == null || player == null) {
            return;
        }

        float heightPx = arena.heightPx();
        double playerWorldX = player.x();
        double playerWorldY = heightPx - player.y();

        GameMazeWorld world = worldFrom(arena);

        for (GdxEnemyRuntime enemy : enemies) {
            double enemyWorldX = enemy.x();
            double enemyWorldY = heightPx - enemy.y();
            enemy.tryShootAt(playerWorldX, playerWorldY, enemyWorldX, enemyWorldY);
        }

        List<Vector2D> mazeVectors = world != null ? world.getMazeVectors() : List.of();

        for (GdxEnemyRuntime enemy : enemies) {
            Iterator<GdxProjectileRuntime> it = enemy.activeProjectiles().iterator();
            while (it.hasNext()) {
                GdxProjectileRuntime p = it.next();
                p.advance(dt);
                if (p.isExpired()) {
                    it.remove();
                    continue;
                }
                if (world != null) {
                    Vector2D hitWall = WallCollisionUtil.findFirstHitWall(
                            p.x(), p.y(), GdxProjectileRuntime.PROJECTILE_SIZE, mazeVectors);
                    if (hitWall != null) {
                        applyProjectileDamageToWall(world, hitWall, p.damage());
                        it.remove();
                    }
                }
            }
        }
    }

    static void applyProjectileDamageToWall(GameMazeWorld world, Vector2D wall, int damage) {
        BreakableWall bw = world.findBreakableWall(wall);
        if (bw != null) {
            world.applyWallDamage(bw, damage);
        }
    }

    private static GameMazeWorld worldFrom(MazeArena arena) {
        if (arena instanceof RealMaze realMaze) {
            return realMaze.sourceWorld();
        }
        return null;
    }
}
