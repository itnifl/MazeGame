package main.game.maze.mazeworld.flame;

import java.util.List;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Vector2D;

/** Picks the beam rules for a weapon: the laser stops at the first wall, the pulse beam breaks through weak ones. */
public final class BeamEngines {

    private BeamEngines() {
    }

    /** Fires a beam with the pulse beam's rules when {@code piercing}, else the laser's; the arguments are those of {@link LaserBeamEngine#fire}. */
    public static LaserBeamEngine.Shot fire(boolean piercing, List<FlameTarget> candidates, GameMazeWorld world, List<Vector2D> walls,
                                            double originX, double originY, int dirX, int dirY,
                                            int damage, double maxRange, double halfWidth) {
        return piercing
                ? PulseBeamEngine.fire(candidates, world, walls, originX, originY, dirX, dirY, damage, maxRange, halfWidth)
                : LaserBeamEngine.fire(candidates, world, walls, originX, originY, dirX, dirY, damage, maxRange, halfWidth);
    }
}
