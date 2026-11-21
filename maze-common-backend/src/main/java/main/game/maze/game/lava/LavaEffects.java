package main.game.maze.game.lava;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.common.graphics.fire.LavaEffect;
import main.game.maze.mazeworld.LavaField;
import main.game.maze.mazeworld.Point2D;

/** Turns the lava of a level into the picture both frontends draw ({@link LavaEffect}). */
public final class LavaEffects {

    private LavaEffects() {
    }

    /**
     * @param flipHeight 0 for a y-down screen (JavaFX); the board height for a y-up screen (libGDX)
     * @return the picture, or null when the level has no lava
     */
    public static LavaEffect of(LavaField lava, double boardWidth, double boardHeight, double flipHeight) {
        if (lava == null || lava.streams().isEmpty()) {
            return null;
        }
        List<LavaEffect.Stream> streams = new ArrayList<>();
        for (LavaField.Stream stream : lava.streams()) {
            List<LavaEffect.Point> path = new ArrayList<>();
            for (Point2D p : stream.path()) {
                path.add(new LavaEffect.Point(p.getX(), p.getY()));
            }
            List<LavaEffect.Bridge> bridges = new ArrayList<>();
            for (LavaField.Bridge b : stream.bridges()) {
                bridges.add(new LavaEffect.Bridge(b.x(), b.y(), b.flowAngleRadians(), b.length()));
            }
            streams.add(new LavaEffect.Stream(path, bridges));
        }
        return new LavaEffect(streams, lava.width(), LavaField.BRIDGE_OVERHANG, boardWidth, boardHeight, flipHeight);
    }
}
