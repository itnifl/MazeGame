package main.game.maze.libgdx.helper;

import main.game.maze.mazeworld.BreakableWall;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.RealMaze;

import java.util.List;
import java.util.function.Consumer;

/**
 * Provides the wall-damage callback used by
 * {@link GdxGameCombatAndEnemyFlowSupport} when a STRAIGHT projectile hits a wall.
 *
 * <p>Runs on the libGDX render thread — no {@code postRunnable} needed.</p>
 */
public final class GdxWallDamageSupport {

    private GdxWallDamageSupport() {
    }

    /**
     * Returns a {@link Consumer} that, given a projectile hit position {@code [x, y]},
     * locates the nearest breakable wall in {@code arena} and applies {@code damage} to it.
     *
     * <p>Returns {@code null} when {@code arena} is not a {@link RealMaze} (e.g. in tests
     * that use a stub arena), so callers must guard against a null callback.</p>
     *
     * @param arena  current maze arena
     * @param damage HP damage to apply on wall hit
     */
    public static Consumer<float[]> wallHitCallback(MazeArena arena, int damage) {
        GameMazeWorld world = worldFrom(arena);
        if (world == null) {
            return null;
        }
        List<Vector2D> mazeVectors = world.getMazeVectors();
        return hitPos -> {
            Vector2D hitWall = WallCollisionUtil.findFirstHitWall(
                    hitPos[0], hitPos[1], PROJECTILE_HIT_RADIUS, mazeVectors);
            if (hitWall != null) {
                applyProjectileDamageToWall(world, hitWall, damage);
            }
        };
    }

    /** AABB lookup radius used when searching for the hit wall segment. */
    static final float PROJECTILE_HIT_RADIUS = 10f;

    static void applyProjectileDamageToWall(GameMazeWorld world, Vector2D wall, int damage) {
        BreakableWall bw = world.findBreakableWall(wall);
        if (bw != null) {
            world.applyWallDamage(bw, damage);
        }
    }

    public static GameMazeWorld worldFrom(MazeArena arena) {
        if (arena instanceof RealMaze realMaze) {
            return realMaze.sourceWorld();
        }
        return null;
    }
}
